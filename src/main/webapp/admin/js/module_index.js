//var $table;
var oInit = new Object();
var main_table_id = "table";

$(function () {
    api.module.initTable(main_table_id);
});

api.module = {
    $table: null,
    refresh_table_id: null,
    shang_ji_id: null,//select 更换类型时，记录上级ID（parentId 或者 belongId）
    query: function(){
        this.$table.bootstrapTable('refresh');
    },
    selectId: null,
	/* 这种 外面调用 里面定义的属性，只能用api.module.xx，不能用this.xx */
}

api.module.initTable = function (tableId) {
    var jq = $("#"+ tableId).bootstrapTable({
        url: URL_API.MODULE.getTop,
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
        responseHandler: this.moduleHandler,
        columns: [
            {field: 'id', title: 'ID', visible: false},
            {field: 'status', title: '状态', formatter: commonApi.format.status},
            {field: 'type', title: '类型'},
            {field: 'belongModule', title: '从属ID'},
            {field: 'parentId', title: '父ID'},
            {field: 'seq', title: '排序'},
            {field: 'moduleId', title: '标识', sortable: true},
            {field: 'name', title: '名称', sortable: true},
            {field: 'style', title: '风格', sortable: true},
            {field: 'url', title: 'URL'},
            {field: 'remark', title: '备注'},
            {field: 'createTime', title: '创建时间', formatter: dateUtilApi.formatDate},
            {field:'id', title: '操作', width: 120, align: 'center', valign: 'middle', formatter: this.actionFormatter},
        ],
        onLoadSuccess: function () {layer.msg("【一级菜单】加载成功！");},
        onLoadError: function () {layer.msg("【一级菜单】加载失败！");},
        queryParams: function(params){return commonApi.table.queryParams(params);},
        onPostBody: function(){api.module.initFirstAction()},
        //注册加载子表的事件。注意下这里的三个参数！
        onExpandRow: function (index, row, $detail) {
            oInit.InitSubTable(index, row, $detail);
        },
    });
    this.$table = jq;
}

api.module.moduleHandler = function(result) {
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

api.module.initFirstAction = function(){
	$(".update").on('click', api.module.update).attr("style","cursor:pointer");
	$(".delete").on('click', api.module.del).attr("style","cursor:pointer");
	$(".add").on('click', api.module.add).attr("style","cursor:pointer");
}

api.module.update = function(e){
	var index = $(e.target).parents('tr').data("index");
	var tableId = $(e.target).parents('table').attr("id");
	var row = $("#"+ tableId).bootstrapTable('getData')[index];
	api.module.refresh_table_id = tableId;
	api.module.selectId = row.moduleId;
	
	$("#tk").load("admin/module_edit.html")
	setTimeout(function(){
		$("input[name='belongModule']").attr("disabled", true).parents(".flexible").hide()
		$("input[name='parentId']").attr("disabled", true).parents(".flexible").hide()
		$("#inputForm #type").val(row.type).attr("disabled", true)
		
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

api.module.add = function(e){
	var index = $(e.target).parents('tr').data("index");
	var tableId = $(e.target).parents('table').attr("id")
	var row = $("#"+ tableId).bootstrapTable('getData')[index];//在其下增加子模块，判断row的模块类型
	
	$("#tk").load("admin/module_edit.html")
	setTimeout(function(){
		$("input[name='moduleId']").attr("disabled", true).parents(".flexible").hide()
		if(row == undefined){
			//row无定义，默认为新增顶级菜单
			$("#inputForm #type").val(2).attr("disabled", true);//原页面有id="type"的dom，不能重复？？限定form，加表单ID
			$("#inputForm input[name='belongModule']").attr("disabled", true).parents(".flexible").hide()
			$("#inputForm input[name='parentId']").attr("disabled", true).parents(".flexible").hide()
			
			api.module.refresh_table_id = main_table_id;
			
		}else if(row.type == 2 && commonApi.utils.isBlank(row.belongId) && commonApi.utils.isBlank(row.parentId)){
			//row为一级菜单，新增二级菜单（或按钮）//因为一级页面也可能有按钮
			$("#inputForm #type").val(2)//子菜单由类型决定，默认为二级菜单（后台需要协助处理）
			$("#inputForm input[name='belongModule']").attr("disabled", true).parents(".flexible").hide()
			$("#inputForm input[name='parentId']").val(row.moduleId).attr("readonly", true).attr("style", "cursor:not-allowed")

            api.module.shang_ji_id = row.moduleId;
			api.module.refresh_table_id = row.moduleId + "_second_table";
			
		}else if(row.type == 2 && commonApi.utils.isBlank(row.belongId) && !commonApi.utils.isBlank(row.parentId)){
			//row为二级菜单，新增按钮
			$("#inputForm #type").val(3).attr("disabled", true)//按钮type=3
			$("#inputForm input[name='belongModule']").val(row.moduleId).attr("readonly", true).attr("style", "cursor:not-allowed")
			$("#inputForm input[name='parentId']").attr("disabled", true).parents(".flexible").hide()
			
			api.module.refresh_table_id = row.moduleId + "_btn_table";
			
		}else{
			layer.msg("不存在的模块类型！")
			return false;
		}
		
		$("#tkModal :header.modal-title").text("新增模块")
		$("#save").val("add").text("新增")
		$("#tkModal").modal("show");
    },400)
}

$(document).on("change", "#inputForm #type", function(){
	var type = $(this).val()
	layer.msg(type)
	$(".flexible").hide().find("input").attr("disabled", true).attr("readonly", true)
	if(type == 2){
		//切换成 菜单
		$("input[name='parentId']").val(api.module.shang_ji_id).removeAttr("disabled").parents(".flexible").show()
	}else if(type == 3){
		//切换成按钮
		$("input[name='belongId']").val(api.module.shang_ji_id).removeAttr("disabled").parents(".flexible").show()
	}else{
		$(this).val(2)//其余默认
		$("input[name='parentId']").val(api.module.shang_ji_id).removeAttr("disabled").parents(".flexible").show()
	}
})

api.module.save = function(e){
    var saveType = $(e).val()

	if(saveType == "add"){
        $("#inputForm #type").removeAttr("disabled").attr("readonly", true)
    }

    //var form_data = $('#inputForm').serialize();
    var param = commonApi.form.getFormObj("inputForm");

    if(saveType == "add"){
        url = URL_API.MODULE.add;
    }else{
        url = URL_API.MODULE.update;
        param.moduleId = api.module.selectId;
    }
    //mysql null和空值，暂时特殊处理字段
    if(param.parentId == undefined){
        param.parentId = "";
    }
    var result = AJAX_HELPER("POST", url, param);
    if(result.code == 0){
        setTimeout('$("#tkModal").modal("hide")',800);
        setTimeout('$("#'+ api.module.refresh_table_id +'").bootstrapTable("refresh")', 1500);
        api.module.selectId = null;
        api.module.query();
    }
}

api.module.del = function(){
	var index = $(this).parents('tr').data("index");
	var tableId = $(this).parents('table').attr("id")
	var row = $("#"+ tableId).bootstrapTable('getData')[index];
	api.module.refresh_table_id = tableId;
	
	var tipMsg = "";
	if(row.status == 0){
		tipMsg = "对无效状态的模块执行删除，将永久从系统移除，确认？"
	}else{
		tipMsg = "模块改为无效，页面将不会显示，确定？"
	}
	
	var index = layer.confirm(tipMsg, {
		btn: ['是的','取消']
	},function(){
		var param = {};
		param.moduleId = row.moduleId;
		var result = AJAX_HELPER("POST", URL_API.MODULE.del, param);
		if(result.code == 0){
            layer.close(index)
            setTimeout('$("#'+ api.module.refresh_table_id +'").bootstrapTable("refresh")', 1500);
		}
	},function(){return});
}

//操作栏的格式化
api.module.actionFormatter = function(value, row, index) {
    var type = row.type;
    var result = "";
    if( type != 3){
    	result += '<a class="add"><span class="btn-sm glyphicon glyphicon-plus" aria-hidden="true"></a>';
    }
    result += '<a class="update"><span class="btn-sm glyphicon glyphicon-pencil" aria-hidden="true"></a>';
    result += '<a class="delete"><span class="btn-sm glyphicon glyphicon-trash" aria-hidden="true"></a>';

    return result;
}

/**
 * ----------------------------------初始化 二级菜单
 */
oInit.InitSubTable = function (index, row, $detail) {
    var parentId = row.moduleId;
    var cur_table = $detail.html('<table id="'+ parentId +'_second_table"></table>').find('table');
    var url = URL_API.MODULE.getSecond;
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
        	{field: 'status', title: '状态', formatter: commonApi.format.status},
        	{field: 'type', title: '类型'}, 
        	{field: 'belongId', title: '从属ID'}, 
        	{field: 'parentId', title: '父ID'}, 
        	{field: 'moduleId', title: '标识', sortable: true}, 
        	{field: 'name', title: '名称', sortable: true}, 
        	{field: 'style', title: '风格', sortable: true}, 
        	{field: 'url', title: 'URL'}, 
        	{field: 'remark', title: '备注'}, 
        	{field: 'createTime', title: '创建时间', formatter: dateUtilApi.formatDate},
        	{field:'id', title: '操作', width: 120, align: 'center', valign: 'middle', formatter: api.module.actionFormatter},
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
    $(cur_table).bootstrapTable({
        url: URL_API.MODULE.getBtn,
        method: 'get',
        clickToSelect: true,
        //detailView: true,//父子表
        uniqueId: "moduleId",
        queryParams: {"belongModule": parentId},//传递参数（*）
        columns: [
        	{checkbox: true, visible: true}, 
        	{field: 'id', title: 'ID', visible: false}, 
        	{field: 'status', title: '状态', formatter: commonApi.format.status},
        	{field: 'type', title: '类型'}, 
        	{field: 'belongModule', title: '从属ID'},
        	{field: 'parentId', title: '父ID'}, 
        	{field: 'moduleId', title: '标识', sortable: true}, 
        	{field: 'name', title: '名称', sortable: true}, 
        	{field: 'style', title: '风格', sortable: true}, 
        	{field: 'url', title: 'URL'}, 
        	{field: 'remark', title: '备注'}, 
        	{field: 'createTime', title: '创建时间', formatter: dateUtilApi.formatDate},
        	{field:'id', title: '操作', width: 120, align: 'center', valign: 'middle', formatter: api.module.actionFormatter},
        ],
        onLoadSuccess: function () {layer.msg("【页面按钮】加载成功！");},
        onLoadError: function () {layer.msg("【页面按钮】加载失败！");},
        onPostBody: function(){initSecondAction();},
        //注册加载子表的事件。注意下这里的三个参数！
        onExpandRow: function (index, row, $detail) {
            //oInit.InitBtnTable(index, row, $detail);
        },
    });
};

function initSecondAction(){
	$(".update").off("click").on('click', api.module.update).attr("style","cursor:pointer");
	$(".delete").off("click").on('click', api.module.del).attr("style","cursor:pointer");
	$(".add").off("click").on('click', api.module.add).attr("style","cursor:pointer");
}