/**
 * @Function	使指定元素 全屏显示
 * @param 		element	要全屏的元素
 */
function toggleFullScreen(element) {
	if (!element.fullscreenElement &&    // alternative standard method
		!element.mozFullScreenElement && !element.webkitFullscreenElement && !element.msFullscreenElement ) {  // current working methods
		if (element.requestFullscreen) {
			element.requestFullscreen();
		} else if (element.msRequestFullscreen) {
			element.msRequestFullscreen();
		} else if (element.mozRequestFullScreen) {
			element.mozRequestFullScreen();
		} else if (element.webkitRequestFullscreen) {
			element.webkitRequestFullscreen(Element.ALLOW_KEYBOARD_INPUT);
		}
	} else {
		if (document.exitFullscreen) {
			document.exitFullscreen();
		} else if (document.msExitFullscreen) {
			document.msExitFullscreen();
		} else if (document.mozCancelFullScreen) {
			document.mozCancelFullScreen();
		} else if (document.webkitExitFullscreen) {
			document.webkitExitFullscreen();
		}
	}
}

/**
 * @Function	新建Tab
 * @param 		targetTitle	tab的标题
 * @param 		targetUrl	tab的链接
 * e.p			子iFrame中  调用方法为  window.parent.openNewTab('百度一下','http://www.baidu.com')
 */
function openNewTab(targetTitle,targetUrl){
	var $ = layui.jquery, element = layui.element();
	if(!$("#tab-list [lay-id='"+targetUrl+"']").length){//不存在则添加
		var timestamp = new Date().getTime()
		element.tabAdd('tab-list', {
	        title: targetTitle
	        ,content:"<iframe  id='"+timestamp+"'></iframe>"
	        //,content:"<iframe src='"+targetUrl+"'></iframe>"
	        ,id: targetUrl//实际使用一般是规定好的id，这里以时间戳模拟下
	    })
	    var iframe = $('#'+timestamp)[0];
			iframe.src= targetUrl
			iframeLoader(iframe)
    }
  	element.tabChange('tab-list', targetUrl);
  	// 点击后刷新
  	//$("#tab-content .layui-show iframe:first")[0].src = $("#tab-content .layui-show iframe:first")[0].src;
	// 监听悬浮事件
  	//$('.layui-tab-title li').off()
	$('.layui-tab-title .layui-this').on('mousedown',callMenu);
}
	

// 关闭当前标签
function closeNowTab(self){
	var $ = layui.jquery, element = layui.element();
  	$("#tab-list li").not($("#tab-list li:first")).each(function(){
  		if( self && !$(this).hasClass('layui-this')) return ;
  		element.tabDelete('tab-list',$(this).attr('lay-id'));
  	})
}

layui.use('element', function(){
	var that = this
  	var $ = layui.jquery
  	,element = layui.element(); //Tab的切换功能，切换事件监听等，需要依赖element模块
  	
 
  // 计算iframe父级的宽高
  $(window).resize(function(){
		$("#tab-content").height($(window).height() - $("#tab-content").offset().top - 10);
		$("#tab-content").width($(window).width() - $("#tab-content").offset().left - 25 );
	});
	
	$(window).on('load',function (){
		$("#tab-content").height($(window).height() - $("#tab-content").offset().top -10);
		$("#tab-content").width($(window).width() - $("#tab-content").offset().left - 25 );
	})
	$(function(){
		$("#tab-content").height($(window).height() - $("#tab-content").offset().top -10);
		$("#tab-content").width($(window).width() - $("#tab-content").offset().left - 25 );
	})
	$("#tab-content").css("display","block");
	// 处理用户F5 F11事件
	$(document).on('keydown', function (e) {
//		var e = event || window.event || arguments.callee.caller.arguments[0];
    	if(e && e.keyCode == 122) {
    		e.preventDefault();
    		e.returnValue = false;
    		toggleFullScreen($("#tab-content .layui-show iframe")[0]);
    	}
    	if(e && e.keyCode == 116) {
    		e.preventDefault() ;
    		e.returnValue = false;
//  		console.log($("#tab-content .layui-show iframe:first")[0].src);
    		var iframe = $("#tab-content .layui-show iframe:first")[0]
			iframe.src = iframe.src;
			iframeLoader(iframe)
    		//$("#tab-content .layui-show iframe:first")[0].src = $("#tab-content .layui-show iframe:first")[0].src;
    	}
    })
	
  //触发侧边栏新建Tab事件
  var active = {
    tabAdd: function(e){
    	var targetUrl = $(this).data('url');
    	var targetTitle = $(this).data('title');
    	openNewTab(targetTitle,targetUrl);
    }
  };
  //侧边栏监听 新建Tab事件（为siderBar的a标签新增iframe）
  $('.layui-nav-child a').on('click', function(){
    var othis = $(this), type = othis.data('type');
    active[type] ? active[type].call(this, othis) : '';
  });
  //全屏事件
  $(".full-screen").on('click',function(){
  	toggleFullScreen($("#tab-content .layui-show iframe")[0]);
  });
  
  //关闭侧边栏
  $('.icon').click(function(){
	$('body').toggleClass('clicked');
	$("#tab-content").width($(window).width() - $("#tab-content").offset().left - 25 );
  })
  
  //列表显示
$('.layui-nav-tree .layui-nav-item').click(function(){
//	$('.layui-nav-tree .layui-nav-itemed').removeClass("layui-nav-itemed")
//	$(this).addClass("layui-nav-itemed")
})
});

/**
** iFrame loader
**/
function iframeLoader(iframe){
	$(iframe).siblings('.mask').remove();
	var timestamp = new Date().getTime()
	$(iframe).after('<div id="mask_'+timestamp+'" class="mask" style="width:110%;height:120%;background:#FFF;position: absolute;left:0;right: 0;top: 0;z-index:10;"><main class="loaded"><div class="loaders"><div class="loader"><div class="loader-inner ball-spin-fade-loader"><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div></div></div></div></main></div>')
	if (iframe.attachEvent){
		iframe.attachEvent("onload", function(){$("#mask_"+timestamp).remove();}); 
	} else { 
		iframe.onload = function(){ $("#mask_"+timestamp).remove();}; 
	} 
}

function logout(){
	history.go(0);
}
// 关闭全部标签
function closeAllTab (self){
	var $ = layui.jquery
  	,element = layui.element();
  	$("#tab-list li").not($("#tab-list li:first")).each(function(){
  		if( self && $(this).hasClass('layui-this')) return ;
  		element.tabDelete('tab-list',$(this).attr('lay-id'));
  	})
}

//tab 上的菜单功能
var style_ul = "padding:0px;margin:0px;border: 1px solid #ccc;background-color: #fff;position: absolute;left: 0px;top: 0px;z-index: 9999;display: none;";
var style_li = "list-style:none;padding: 5px; cursor: pointer; padding: 5px 20px;margin:0px;";
var style_li_hover = style_li + "background-color: #00A0E9; color: #fff;";
menubox = $("<div class='echartboxMenu' style='" + style_ul + "'><ul style='margin:0px;padding:0px;'></ul></div>").appendTo($(document.body));
var callMenu = function(param){
	if( param.type == 'mouseout' ) return menubox.delay(3000).hide();
	if(param.delegateTarget.className!='layui-this') return ;
	//移除自带右键
	if(param.button!=2) return ;
	document.oncontextmenu = function () { return false; }; 
	showMenu([
		{
			"name": "刷新(F5)",
            "fn": function() {
            	//$("#tab-content .layui-show iframe:first")[0].src = $("#tab-content .layui-show iframe:first")[0].src;
            	var iframe = $("#tab-content .layui-show iframe:first")[0]
            	iframe.src = iframe.src;
				iframeLoader(iframe)
            }
		}
		,{
			"name": "全屏(F11)",
            "fn": function() {
            	toggleFullScreen($("#tab-content .layui-show iframe")[0]);
            }
		}
		,{
			"name": "关闭全部标签",
            "fn": function() {
            	closeAllTab(false)
            }
		}
		,{
			"name": "关闭其他标签",
            "fn": function() {
            	closeAllTab(true)
            }
		}
	]);  
}
 
var showMenu = function(menus){
	var event = event || window.event || arguments.callee.caller.arguments[0];
    var menulistbox = $("ul", menubox).empty();
    $(menus).each(function(i, item) {
        var li = $("<li data-toggle='modal' data-target='#myModal' style='" + style_li + "'>" + item.name + "</li>")
            .mouseenter(function() {
                $(this).attr("style", style_li_hover);
            })
            .mouseleave(function() {
                $(this).attr("style", style_li);
            })
            .click(function() {
                item["fn"].call(this);
                menubox.hide();
            });
        menulistbox.append(li);
    });
    menubox.css({
        "left": event.pageX||event.clientX  + document.body.scrollLeft - document.body.clientLeft,
        "top": event.pageY||event.clientY + document.body.scrollTop - document.body.clientTop 
    }).show();
}

$(document).click(function() { menubox.hide()});