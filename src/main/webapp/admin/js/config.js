// local 本地; dev 开发环境; test 测试环境; pub 生产环境
var ACTIVE = 'TEST'; //【上线前注意调整】
var ACCOUNT = Cookies.get('account'); //当前用户
var ACCESS_TOKEN = Cookies.get('accessToken');
var OTC_LOGIN_ACCOUNT = Cookies.get('account');

// 配置域名信息
var CONFIGLIST = {
    'LOCAL': {
        'DOMAIN':  'http://localhost:8080',
    },
    'PUB': {
        'DOMAIN':  'http://zzroman.com',
    }
};

var CONFIG = CONFIGLIST[ACTIVE];  //页面通过该变量直接引用，不要删除
var DOMAIN = CONFIG.DOMAIN;		  //配置当前公众号使用哪个域名，有页面通过该变量直接引用，不要删除
var API_DOMAIN = CONFIG.OTC_CORE;
var API_FILE = CONFIG.FILE;

// 获取URL中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);  //获取url中"?"符后的字符串并正则匹配
    var context = "";
    if (r != null)
        context = r[2];
    reg = null;
    r = null;
    return context == null || context == "" || context == "undefined" ? "" : context;
}

//获取cookies
function getCookie(cookieName) {
    var strCookie = decodeURI(document.cookie);
    var arrCookie = strCookie.split("; ");
    for (var i = 0; i < arrCookie.length; i++) {
        var arr = arrCookie[i].split("=");
        if (cookieName == arr[0]) {
            return arr[1];
        }
    }
    return "";
}

// 网络请求配置
/*
$.ajaxSetup({
    beforeSend: function(xhr) {
        var url = arguments[1].url;

        if(url.indexOf("fdfs/uploadFile") != -1 || url.indexOf("dripAward/import") != -1){
            //。。。
        }else if(arguments[1].type == "POST" || arguments[1].type == "post"){
            arguments[1].data += "&access_token="+ACCESS_TOKEN;
        }else{
            arguments[1].url += "&access_token="+ACCESS_TOKEN;
        }
    }
});*/

var config_contextPath = "assda";
//ajax预处理 和 后置处理，用于权限控制的页面控制
/*if(window.jQuery){
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
}*/
