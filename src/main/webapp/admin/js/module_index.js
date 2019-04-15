var $table;
var oInit = new Object();
var main_table_id = "table";

$(function () {
    InitMainTable();
    $("#query").click(function(){$("#"+ main_table_id).bootstrapTable('refresh');})
    $("#addFirst").on('click', add)
});

function InitMainTable () {
	var queryUrl = '/mg/admin/module/getFirst'
    $table = $("#"+ main_table_id).bootstrapTable({
        url: queryUrl,
        method: 'GET',
        cache: false,
        pagination: true,
        sidePagination: "server",
        contentType: "application/x-www-form-urlencoded",
        toolbar: '#toolbar',
        showRefresh: true,
        showToggle: true,
        showColumns: true,
        showExport: true,
        uniqueId: "moduleId",
        detailView: true,//是否显示父子表
        responseHandler: moduleHandler,
        columns: [
        	{field: 'id', title: 'ID', visible: false}, 
        	{field: 'status', title: '状态', formatter: statusFormatter},  
        	{field: 'type', title: '类型'}, 
        	{field: 'belongId', title: '从属ID'}, 
        	{field: 'parentId', title: '父ID'}, 
        	{field: 'seq', title: '排序'}, 
        	{field: 'moduleId', title: '标识', sortable: true}, 
        	{field: 'name', title: '名称', sortable: true}, 
        	{field: 'style', title: '风格', sortable: true}, 
        	{field: 'url', title: 'URL'}, 
        	{field: 'remark', title: '备注'}, 
        	{field: 'createTime', title: '创建时间', formatter: dateFormatter}, 
        	{field:'id', title: '操作', width: 120, align: 'center', valign: 'middle', formatter: actionFormatter},
        ],
        onLoadSuccess: function () {layer.msg("【一级菜单】加载成功！");},
        onLoadError: function () {layer.msg("【一级菜单】加载失败！");},
        queryParams: function(params){return queryParams(params);},
        onPostBody: function(){initFirstAction();},
        //注册加载子表的事件。注意下这里的三个参数！
        onExpandRow: function (index, row, $detail) {
            oInit.InitSubTable(index, row, $detail);
        },
    });
};

function moduleHandler(result) {
    if (result.code == 0) {
        return {
            "rows" : result.data,
            "total" : result.data.length
        };
    } else {
        return {
            "rows" : [],
            "total" : 0
        };
    }
}

function initFirstAction(){
	$(".update").on('click', update).attr("style","cursor:pointer");
	$(".delete").on('click', del).attr("style","cursor:pointer");
	$(".add").on('click', add).attr("style","cursor:pointer");
}

function update(){
	var index = $(this).parents('tr').data("index");
	var tableId = $(this).parents('table').attr("id")
	var row = $("#"+ tableId).bootstrapTable('getData')[index];
	refresh_table_id = tableId;
	
	$("#tk").load("system/module_edit.html")
	setTimeout(function(){
		$("input[name='belongId']").attr("disabled", true).parents(".flexible").hide()
		$("input[name='parentId']").attr("disabled", true).parents(".flexible").hide()
		$("#typee").val(row.type).attr("disabled", true)
		
		$("input[name='moduleId']").val(row.moduleId).attr("readonly", true)//.attr("style", "cursor:not-allowed")
		$("#inputForm input[name='name']").val(row.name)
		$("#inputForm input[name='style']").val(row.style)
		$("#inputForm input[name='url']").val(row.url)
		$("#remark").text(row.remark)
		$(":header.modal-title").text("修改模块")
		$("#save").val("update").text("保存")
		$("#tkModal").modal("show");
    },400)
}

var shangjiId;//select 更换类型时，记录上级ID（parentId 或者 belongId）
function add(){
	var index = $(this).parents('tr').data("index");
	var tableId = $(this).parents('table').attr("id")
	var row = $("#"+ tableId).bootstrapTable('getData')[index];//在其下增加子模块，判断row的模块类型
	
	$("#tk").load("system/module_edit.html")
	setTimeout(function(){
		$("input[name='moduleId']").attr("disabled", true).parents(".flexible").hide()
		if(row == undefined){
			//row无定义，默认为新增一级菜单
			$("#typee").val(2).attr("disabled", true)//原页面有id="type"的dom，不能重复
			$("input[name='belongId']").attr("disabled", true).parents(".flexible").hide()
			$("input[name='parentId']").attr("disabled", true).parents(".flexible").hide()
			
			refresh_table_id = main_table_id;
			
		}else if(row.type == 2 && isBlank(row.belongId) && isBlank(row.parentId)){
			//row为一级菜单，新增二级菜单（或按钮）//因为一级页面也可能有按钮
			$("#typee").val(2)//子菜单由类型决定，默认为二级菜单（后台需要协助处理）
			$("input[name='belongId']").attr("disabled", true).parents(".flexible").hide()
			$("input[name='parentId']").val(row.moduleId).attr("readonly", true).attr("style", "cursor:not-allowed")
			
			shangjiId = row.moduleId;
			refresh_table_id = row.moduleId + "_second_table";
			
		}else if(row.type == 2 && isBlank(row.belongId) && !isBlank(row.parentId)){
			//row为二级菜单，新增按钮
			$("#typee").val(1).attr("disabled", true)//按钮type=1
			$("input[name='belongId']").val(row.moduleId).attr("readonly", true).attr("style", "cursor:not-allowed")
			$("input[name='parentId']").attr("disabled", true).parents(".flexible").hide()
			
			refresh_table_id = row.moduleId + "_btn_table";
			
		}else{
			layer.msg("不存在的模块类型！")
			return false;
		}
		
		$(":header.modal-title").text("新增模块")
		$("#save").val("add").text("新增")
		$("#tkModal").modal("show");
    },400)
}

function isBlank(val){
	var flag = false;
	if(val == null || val == ''){
		flag = true;
	}
	return flag;
}

$(document).on("change", "#typee", function(){
	var type = $(this).val()
	layer.msg(type)
	$(".flexible").hide().find("input").attr("disabled", true).attr("readonly", true)
	if(type == 2){
		$("input[name='parentId']").val(shangjiId).removeAttr("disabled").parents(".flexible").show()
	}else if(type == 1){
		$("input[name='belongId']").val(shangjiId).removeAttr("disabled").parents(".flexible").show()
	}else{
		$(this).val(2)
		$("input[name='parentId']").val(shangjiId).removeAttr("disabled").parents(".flexible").show()
	}
})

$(document).on("click", "#save", function(){
	var type = $(this).val()
	if(type == "add"){
		$("#typee").removeAttr("disabled").attr("readonly", true)
	}
	var form_data = $('#inputForm').serialize();
	
	//layer.msg("【"+ type +"】"+ form_data)
	//return false;
	
	$.ajax({
		type : "POST",
		url : "/mg/admin/module/save?operate="+type,
		data : form_data,
		async : false,
		success : function(result) {
			if (result.code != 0) {
				layer.msg(result.msg, {icon : 2});
			} else {
				layer.msg(result.msg, {icon : 1});
				setTimeout('$("#tkModal").modal("hide")',800);
				setTimeout('$("#'+ refresh_table_id +'").bootstrapTable("refresh")', 1500);
			}
		},
		error : function() {
			layer.msg("操作异常，请稍后重试！", {icon : 2});
		}
	})
})

var refresh_table_id;
function del(){
	var index = $(this).parents('tr').data("index");
	var tableId = $(this).parents('table').attr("id")
	var row = $("#"+ tableId).bootstrapTable('getData')[index];
	refresh_table_id = tableId;
	
	var tipMsg = "";
	if(row.status == 0){
		tipMsg = "对无效状态的模块执行删除，将永久从系统移除，确认？"
	}else{
		tipMsg = "模块改为无效，页面将不会显示，确定？"
	}
	
	//layer.msg("【"+ row.name +"】"+ row.moduleId)
	//return false;
	
	var index = layer.confirm(tipMsg, {
		btn: ['是的','取消']
	},function(){
		$.ajax({
			type : "POST",
			url : "/mg/admin/module/delete",
			data : {"signId" : row.moduleId},
			success : function(result) {
				if (result.code != 0) {
					layer.msg(result.msg)
				} else {
					layer.msg(result.msg, {icon : 1});
					layer.close(index)
					setTimeout('$("#'+ refresh_table_id +'").bootstrapTable("refresh")', 1500);
				}
			},
			error : function(){
	        	layer.msg("异常错误，请联系技术人员！");
	        }
		})
	},function(){return});
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

//操作栏的格式化
function actionFormatter(value, row, index) {
    var type = row.type;
    var result = "";
    if( type != 1){
    	result += '<a class="add"><span class="btn-sm glyphicon glyphicon-plus" aria-hidden="true"></a>';
    }
    result += '<a class="update"><span class="btn-sm glyphicon glyphicon-pencil" aria-hidden="true"></a>';
    result += '<a class="delete"><span class="btn-sm glyphicon glyphicon-trash" aria-hidden="true"></a>';

    return result;
}

//初始化 二级菜单
oInit.InitSubTable = function (index, row, $detail) {
    var parentId = row.moduleId;
    var cur_table = $detail.html('<table id="'+ parentId +'_second_table"></table>').find('table');
    var url = '/mg/admin/module/getSecond'
    $(cur_table).bootstrapTable({
        url: url,
        method: 'get',
        clickToSelect: true,
        detailView: true,//父子表
        uniqueId: "moduleId",
        queryParams: {"parentId": parentId},//传递参数（*）
        //pageSize: 10,
        //pageList: [10, 25],
        columns: [
        	{checkbox: true, visible: true}, 
        	{field: 'id', title: 'ID', visible: false}, 
        	{field: 'status', title: '状态', formatter: statusFormatter},  
        	{field: 'type', title: '类型'}, 
        	{field: 'belongId', title: '从属ID'}, 
        	{field: 'parentId', title: '父ID'}, 
        	{field: 'moduleId', title: '标识', sortable: true}, 
        	{field: 'name', title: '名称', sortable: true}, 
        	{field: 'style', title: '风格', sortable: true}, 
        	{field: 'url', title: 'URL'}, 
        	{field: 'remark', title: '备注'}, 
        	{field: 'createTime', title: '创建时间', formatter: dateFormatter}, 
        	{field:'id', title: '操作', width: 120, align: 'center', valign: 'middle', formatter: actionFormatter},
        ],
        onLoadSuccess: function () {layer.msg("【二级菜单】加载成功！");},
        onLoadError: function () {layer.msg("【二级菜单】加载失败！");},
        //queryParams: function(params){return queryParams(params);},
        onPostBody: function(){initSecondAction();},
        //注册加载子表的事件。注意下这里的三个参数！
        onExpandRow: function (index, row, $detail) {
            oInit.InitBtnTable(index, row, $detail);
        },
    });
};

oInit.InitBtnTable = function (index, row, $detail) {
    var parentId = row.moduleId;
    var cur_table = $detail.html('<table id="'+ parentId +'_btn_table"></table>').find('table');
    var url = '/mg/admin/module/getBtn'
    $(cur_table).bootstrapTable({
        url: url,
        method: 'get',
        clickToSelect: true,
        //detailView: true,//父子表
        uniqueId: "moduleId",
        queryParams: {"belongId": parentId},//传递参数（*）
        //pageSize: 10,
        //pageList: [10, 25],
        columns: [
        	{checkbox: true, visible: true}, 
        	{field: 'id', title: 'ID', visible: false}, 
        	{field: 'status', title: '状态', formatter: statusFormatter},  
        	{field: 'type', title: '类型'}, 
        	{field: 'belongId', title: '从属ID'}, 
        	{field: 'parentId', title: '父ID'}, 
        	{field: 'moduleId', title: '标识', sortable: true}, 
        	{field: 'name', title: '名称', sortable: true}, 
        	{field: 'style', title: '风格', sortable: true}, 
        	{field: 'url', title: 'URL'}, 
        	{field: 'remark', title: '备注'}, 
        	{field: 'createTime', title: '创建时间', formatter: dateFormatter}, 
        	{field:'id', title: '操作', width: 120, align: 'center', valign: 'middle', formatter: actionFormatter},
        ],
        onLoadSuccess: function () {layer.msg("【页面按钮】加载成功！");},
        onLoadError: function () {layer.msg("【页面按钮】加载失败！");},
        //queryParams: function(params){return queryParams(params);},
        onPostBody: function(){initSecondAction();},
        //注册加载子表的事件。注意下这里的三个参数！
        onExpandRow: function (index, row, $detail) {
            //oInit.InitBtnTable(index, row, $detail);
        },
    });
};

function initSecondAction(){
	$(".update").off("click").on('click', update).attr("style","cursor:pointer");
	$(".delete").off("click").on('click', del).attr("style","cursor:pointer");
	$(".add").off("click").on('click', add).attr("style","cursor:pointer");
}