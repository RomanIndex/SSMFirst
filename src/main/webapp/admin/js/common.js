var config_contextPath = "assda";
//ajax预处理 和 后置处理，用于权限控制的页面控制
if(window.jQuery){
    jQuery(document).bind("ajaxSend", function(event, request, settings){
        var token = "what???";//getUserToken();
        //config_contextPath 为需要设置token的 全局host,严格判断防止 token发送到其他站点被盗取
        if(token && config_contextPath && settings.url && settings.url.indexOf(config_contextPath) === 0){
            var headers = settings.headers || {};
            headers["X-Auth-Token"] = token;
            request.setRequestHeader("X-Auth-Token", token);
            settings.headers = headers;
        }
    }).bind("ajaxComplete", function(event, xhr, settings){
    	if(config_contextPath && settings.url){
    	//if(config_contextPath && settings.url && settings.url.indexOf(config_contextPath) === 0 && (settings.dataType === 'JSON' || settings.dataType === 'json')){
        	if(xhr.status == 200 && xhr.responseText){
        	//if(xhr.status == 200){
                try{
                    var reObj = {};
                    if(xhr.responseText != ""){
                    	reObj = JSON.parse(xhr.responseText);
                    }
                    //特殊code 没有权限 和token失效
                    //if(reObj && (reObj.code==3001 || reObj.code==3002 )){
                    if(reObj && reObj.code != 0){
                        window.setTimeout(function () {
                            if($(".layui-layer-dialog.layui-layer-msg:visible").length < 1){
                                layer.alert(reObj.msg +"（ajax后置处理）!", {icon: 2}, function () {
                                	var topWindow = parent ? (parent.parent ? (parent.parent.parent ? parent.parent.parent : parent.parent) : parent) : window;
                                	//topWindow.location.href='/login.html';
                                	//window.parent.openNewTab("无权限访问", "admin/noAuthority/page");
                                	layer.msg("您没有访问该功能的权限！")
                                });
                            }
                        }, 500);
                    }
                }catch (e){console.error("位于admin -> js -> common.js下，会吃掉后台异常，主动try catch："+ e)}
            }else{
            	layer.msg(xhr.status)
            }
        }
    });
}

function formatStatus(data){
	return data == 0 ? "无效" : data == 1 ? "有效" : "";
}

function changeLength(obj, textArea){
	var txtval = obj.val().length;
	var str = parseInt(250 - txtval);
    if(str > 0 ){
    	textArea.html('剩余可输入'+str+'字');
    }else {
    	textArea.html('剩余可输入0字');
    	obj.val(obj.val().substring(0, 250));
    }
}

//判断字符串长度
var getBLen = function(str) {
	if (str == null)
		return 0;
	if (typeof str != "string") {
		str += "";
	}
	return str.replace(/[^x00-xff]/g, "01").length;
}

function clearForm(formId){
	$("#tk").html("");
	$("#"+ formId).get(0).reset();
	$("#"+ formId +" :input").not(":button, :submit, :reset, :hidden, :checkbox, :radio").val("");
	$("#"+ formId +" :input").removeAttr("checked").removeAttr("disabled")//.remove("selected");
	$("#"+ formId +" textarea").text("")
}

String.format = function() {
    if (arguments.length == 0)
        return null;
    var str = arguments[0];
    for ( var i = 1; i < arguments.length; i++) {
        var re = new RegExp('\\{' + (i - 1) + '\\}', 'gm');
        str = str.replace(re, arguments[i]);
    }
    return str;
};

//公共处理表单，入参：表单ID
function getFormObj(formId){
	var init_data = $('#'+ formId).serialize();//string类型，空格转加号（+）
	var reg_data = init_data.replace(/\+/g, " ");
	var data = decodeURIComponent(reg_data, true);
	var param = parseQuery(data);
	return param;
}

var parseQuery = function (query) {
	var reg = /([^=&]+)[=\s]*([^&]*)/g;
	var obj = {};
	while (reg.exec(query)) {
		obj[RegExp.$1] = RegExp.$2;
	}
	return obj;
};

function checkInlegalChar(param){
	var flag = false;
	$.each(param, function(i, item){
		if(item.indexOf("'") >= 0){
			flag = true;
			return false;
		}
	})
	return flag;
}