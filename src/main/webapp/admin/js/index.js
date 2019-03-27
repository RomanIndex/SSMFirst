$(function(){
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
  
  $(".treeview-menu li a").click(function(){
	  var url = $(this).data("url");
	  if(url == "#" || url == "javascript:void(0)"){
		  return;
	  }
	  layer.msg("页面url："+ url)
	  var targetUrl = url;
	  var targetTitle = $(this).text();
	  openNewTab(targetTitle, targetUrl);
  })
  
  //触发侧边栏新建Tab事件
  var active = {
    tabAdd: function(e){
    	var targetUrl = $(this).data('url');
    	var targetTitle = $(this).text();
    	openNewTab(targetTitle,targetUrl);
    }
  };
  
  //侧边栏监听 新建Tab事件（为siderBar的a标签新增iframe）
  $('.treeview-menu a').on('click', function(){
    var othis = $(this), type = othis.data('type');
    active[type] ? active[type].call(this, othis) : '';
  });
  
  //全屏事件
  $(".full-screen").on('click',function(){
  	toggleFullScreen($("#tab-content .layui-show iframe")[0]);
  });
  
});

/**
 * 关闭侧边栏（li 和class之间要有 空格）
 * 右侧有没有，有：显示右侧第一个；没有：显示左侧第一个，且不是index=0的那个
 * 且要 阻止 冒泡事件
 */
$("#tab-title").on("click", "li .tab_close", function(e){
	e.stopPropagation();
	var nextTab = $(this).parents("li").next();
	if(nextTab.length == 0){
		syncTabTitle($(this).parents("li").prev());//且不能是第一个tab-title
	}else{
		syncTabTitle(nextTab);
	}
	var index = $(this).parents("li").index();
	$(this).parents("li").remove();
	$("#tab-content").children().eq(index).remove();
})

/**
 * 切换tab-title
 */
$("#tab-title").on("click", "li", function(){
	syncTabTitle($(this));
})

var TAB_TITLE_SHOW_CLASS = "tab-title-show";
var TAB_CONTENT_SHOW_CLASS = "tab-content-show";

/**
 * 同步一个tab-title（右键事件、相应tab-content的显隐）
 * 先检查并 关闭 其他的，再同步这个
 * $st 是 对应的 li 的jquery对象
 */
function syncTabTitle($st){
	$("#tab-title").children().removeClass(TAB_TITLE_SHOW_CLASS);
	$("#tab-content").children().removeClass(TAB_CONTENT_SHOW_CLASS);
	
	var index = $st.index();
	$st.addClass(TAB_TITLE_SHOW_CLASS);
	$("#tab-content").children().eq(index).addClass(TAB_CONTENT_SHOW_CLASS);
	setTimeout("$(\"#tab-title .tab-title-show\").on('mousedown', callMenu)", 1500);//添加 右键点击事件
}

//右键功能均有问题，但 坚决 不用layui做2018-12-18 20:40
var TARGET_URL;
function openNewTab(targetTitle, targetUrl){
	//校样targetUrl权限
	//TARGET_URL = targetUrl;
	/*$.ajaxSettings.async = false;
	$.post("/admin/account/checkAuth", {"url": targetUrl}, function(result){
		if(result.code == 0){
			//targetUrl = "admin/noAuthority/page";
			//targetUrl = "admin/blank.ftl";//X会自动下载
			TARGET_URL = "admin/404.html";
		}else{layer.msg(result.msg, {icon : 2});}
	},'json');
	$.ajaxSettings.async = true;*/
	//targetUrl = "admin/404.html";
	//不存在则添加
	if(!$("#tab-title li[data-url='" + targetUrl + "']").length){
		var timestamp = new Date().getTime();
		
		$("#tab-title").children().removeClass(TAB_TITLE_SHOW_CLASS);
		$("#tab-content").children().removeClass(TAB_CONTENT_SHOW_CLASS);
		
		$("#tab-content").append("<div class=\"tab-content-item tab-content-show\"><iframe  id='"+timestamp+"'></iframe></div>");
		$("#tab-title").append("<li data-url="+ targetUrl +" class=\"tab-title-show\"><span>"+ targetTitle +"</span><i class=\"tab_close fa fa-close\"></i></li>");
	    var iframe = $('#'+timestamp)[0];
			iframe.src = targetUrl
			iframeLoader(iframe)
    }
	//新建tab，（1）要更新 tab-title右键 点击事件（2）同步tab-content的显隐
  	// 点击后刷新
  	//$("#tab-content .layui-show iframe:first")[0].src = $("#tab-content .layui-show iframe:first")[0].src;
	// 监听悬浮事件
  	//$('.layui-tab-title li').off()
  	$(".tab-title .tab-title-show").on('mousedown', callMenu);//添加 右键点击事件
}

function iframeLoader(iframe){
	$(iframe).siblings('.mask').remove();
	var timestamp = new Date().getTime()
	//遮罩层
	$(iframe).after('<div id="mask_'+timestamp+'" class="mask" style="width:110%;height:120%;background:#FFF;position: absolute;left:0;right: 0;top: 0;z-index:10;"><main class="loaded"><div class="loaders"><div class="loader"><div class="loader-inner ball-spin-fade-loader"><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div></div></div></div></main></div>')
	if(iframe.attachEvent){
		iframe.attachEvent("onload", function(){$("#mask_"+timestamp).remove();});
	}else{
		iframe.onload = function(){ $("#mask_"+timestamp).remove();};
	}
}

//tab 上的菜单功能
var style_ul = "padding:0px;margin:0px;border: 1px solid #ccc;background-color: #fff;position: absolute;left: 0px;top: 0px;z-index: 9999;display: none;";
var style_li = "list-style:none;padding: 5px; cursor: pointer; padding: 5px 20px;margin:0px;";
var style_li_hover = style_li + "background-color: #00A0E9; color: #fff;";
//右键显示框
menubox = $("<div class='echartboxMenu' style='" + style_ul + "'><ul style='margin:0px;padding:0px;'></ul></div>").appendTo($(document.body));
var callMenu = function(param){
	if( param.type == 'mouseout' ) return menubox.delay(3000).hide();
	if(param.delegateTarget.className != 'tab-title-show') return;
	//移除自带右键
	if(param.button!=2) return;
	document.oncontextmenu = function () { return false; }; 
	showMenu([
		{
			"name": "刷新(F5)",
            "fn": function() {
            	var iframe = $("#tab-content .tab-content-show iframe:first")[0]
            	iframe.src = iframe.src;
				iframeLoader(iframe)
            }
		}
		,{
			"name": "全屏(F11)",
            "fn": function() {
            	toggleFullScreen($("#tab-content .tab-content-show iframe")[0]);
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

//关闭全部标签
function closeAllTab(self){
  	$("#tab-title li").not($("#tab-title li:first")).each(function(){
  		if(self && $(this).hasClass(TAB_TITLE_SHOW_CLASS)) return;
  		var index = $(this).index();
  		$(this).remove();
  		$("#tab-content").children().eq(index).remove();
  	})
}

$(document).click(function(){menubox.hide()});

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