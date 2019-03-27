function formatStatus(data){
	return data == 0 ? "无效" : data == 1 ? "有效" : "";
}

function add0(m){return m<10?'0'+m:m }
function formatDate(data) {
	//shijianchuo是整数，否则要parseInt转换  
	if(data != "" && data != null){
		var time = new Date(data);  
		var y = time.getFullYear();  
		var m = time.getMonth()+1;  
		var d = time.getDate();  
		var h = time.getHours();  
		var mm = time.getMinutes();  
		var s = time.getSeconds();  
		return y+'-'+add0(m)+'-'+add0(d)+' '+add0(h)+':'+add0(mm)+':'+add0(s);  
	}else{
		return "";
	}
}

function changeLength(obj, textArea){
	var txtval = obj.val().length;
	var str = parseInt(250 - txtval);
    if(str > 0){
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
	$("#inputForm :input").not(":button, :submit, :reset, :hidden, :checkbox, :radio").val("");
	$("#inputForm :input").removeAttr("checked").removeAttr("disabled");
	$("#inputForm textarea").text("")
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