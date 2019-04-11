var commonApi = {}

commonApi.form = {
	//初始化表单
	clearForm: function(formId){
		$("#tk").html("");
		$("#"+ formId).get(0).reset();
		$("#"+ formId +" :input").not(":button, :submit, :reset, :hidden, :checkbox, :radio").val("");
		$("#"+ formId +" :input").removeAttr("checked").removeAttr("disabled")//.remove("selected");
		$("#"+ formId +" textarea").text("")
	},
    //公共处理表单，入参：表单ID
    getFormObj: function(formId){
		var init_data = $('#'+ formId).serialize();//string类型，空格转加号（+）
		var reg_data = init_data.replace(/\+/g, " ");
		var data = decodeURIComponent(reg_data, true);
		var param = commonApi.form.parseQuery(data);
		return param;
	},
    parseQuery: function (query) {
        var reg = /([^=&]+)[=\s]*([^&]*)/g;
        var obj = {};
        while (reg.exec(query)) {
            obj[RegExp.$1] = RegExp.$2;
        }
        return obj;
    },
    checkIllegalChar: function(param){
		var flag = false;
		$.each(param, function(i, item){
			if(item.indexOf("'") >= 0){
				flag = true;
				return false;
			}
		})
		return flag;
	}

}

commonApi.format = {
	status: function (data) {
        return data == 0 ? "无效" : data == 1 ? "有效" : "";
    },
    reach: function (data) {
        return data == true ? "达标" : "未达标";
    }
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

//获取请求体参数
function GetRequest() {
	var url = location.search; //获取url中"?"符后的字串
	var theRequest = new Object();
	if (url.indexOf("?") != -1) {
		var str = url.substr(1);
		strs = str.split("&");
		for(var i = 0; i < strs.length; i ++) {
			theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);
		}
	}
	return theRequest;
}