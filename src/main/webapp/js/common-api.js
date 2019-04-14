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

/**
 * 公共 根据权限控制 页面元素 的显隐
 * @param hasAry 拥有的权限数组
 * @param doms 页面需要进行控制的元素
 * @returns
 */
function ssmAuthCtr(hasAry, doms){
    $.each(doms, function(index, item){
        var that = $(this);
        var idDom = that.attr("id");
        var classDoms = that.attr("class");

        $.each(hasAry, function(i, each){
            if(each == "#"+ idDom){
                that.show();
            }
            var name = each.replace("\.", "");
            if(classDoms.indexOf(name) >= 0){
                that.show();
            }
        })
    })
}

//实现 批量处理表单的查询条件（ 必须 放在 InitMainTable 前面，否则报错）
$.fn.serializeJsonObject = function() {
    var json = {};
    var form = this.serializeArray();
    $.each(form, function(i, item) {
        if (json[this.name]) {
            if (!json[this.name].push) {
                json[this.name] = [ json[this.name] ];
            }
            json[this.name].push();
        } else {
            if (this.value.indexOf("'") >= 0) {
                json["valid"] = false;
            }
            json[this.name] = this.value || '';
        }
    });
    return json;
}

var AJAX_HELPER = function (requestType, fullUrl, param) {
    var re = {}
    re.code = -1;
    re.msg = "O(∩_∩)O出错啦，还请联系技术小哥~"
    $.ajax({
        type : requestType,
        url : fullUrl,
        cache : false,
        data: param,
        async:false,
        //data : {"str": JSON.stringify(param)},
        //traditional : true,
        dataType: "json",
        success: function(result) {
            if (result.code == 0) {
                layer.msg(result.msg);
                re = result;
            } else {
                layer.msg(result.msg);
            }
        },
        error: function (result) {
            layer.msg("请求异常，请稍后重试！");
        }
    });
    return re;
}

function queryParams(params) {
    var temp = $("#queryForm").serializeJsonObject();
    if(temp["valid"] != undefined && !temp["valid"]){
        layer.msg("有不合法字符，请重新输入");
        return false;
    }
    temp["pageSize"] = params.limit;
    temp["pageNo"] = params.offset/params.limit+1;
    //temp["sort"] = params.sort,      //排序列名
    //temp["sortOrder"] = params.order //排位命令（desc，asc）
    //特殊格式的条件处理...
    return temp;
}

/**
 * 用于server 分页，表格数据量太大的话 不想一次查询所有数据，可以使用server分页查询， 数据量小的话可以直接把sidePagination:
 * "server" 改为 sidePagination: "client"， 同时去掉responseHandler:
 * responseHandler就可以了
 */
function responseHandler(result) {
    if (result.code == 0) {
        return {
            "rows" : result.data.list,
            "total" : result.data.totalRecords
        };
    } else {
        return {
            "rows" : [],
            "total" : 0
        };
    }
}

function responseJpaHandler(result) {
    if (result.code == 0) {
        return {
            "rows" : result.data.content,
            "total" : result.data.totalElements
        };
    } else {
        return {
            "rows" : [],
            "total" : 0
        };
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