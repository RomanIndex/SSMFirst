$(function () {
    var tableId = "table";
    api.privilege.initTable(tableId)
});

api.privilege = {
    $table: null,
    initTable: function (tableId) {
        var jq = $("#"+ tableId).bootstrapTable({
            url: URL_API.PRIVILEGE.query,
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
            uniqueId: "empNo",
            responseHandler: responseHandler,
            columns: [
                {field: 'id', title: 'ID', visible: false},
                {field: 'status', title: '状态', formatter: commonApi.format.status},
                {field: 'type', title: '类型', sortable: true},
                {field: 'level', title: '级别', sortable: true},

                {field: 'code', title: '标识Code'},
                {field: 'name', title: '名称'},

                {field: 'moduleName', title: '资源模块', sortable: true},
                {field: 'moduleId', title: '模块ID', sortable: true},

                {field: 'operateName', title: '关联操作', sortable: true},
                {field: 'operateEnumName', title: '操作Enum', sortable: true},

                {field: 'validDate', title: '生效时间', sortable: true},

                {field: 'createTime', title: '创建时间', formatter: dateUtilApi.formatDate},
                {field:'id', title: '操作', width: 120, align: 'center', valign: 'middle', formatter: this.actionFormatter},
            ],
            onLoadError: function () {layer.msg("数据加载失败！");},
            queryParams: function(params){return queryParams(params);},
        });
        this.$table = jq;
    },
    query: function(){
        this.$table.bootstrapTable('refresh');
    },
    getAddModal: function(e){
        window.parent.openNewTab("新增权限票据","/admin/privilege/add")
    },
    getUpdateModal: function(e){
        var index = $(e).parents('tr').data("index");
        var row = this.$table.bootstrapTable('getData')[index];
        //带参数的，统一路径处理可能不行了
        window.parent.openNewTab("修改","admin/privilege/update?code="+ row.code)
    },
    del: function (e) {
        var index = $(e).parents('tr').data("index");
        var row = this.$table.bootstrapTable('getData')[index];

        var index = layer.confirm("确定删除吗？", {
            btn: ['确认','取消']
        },function(){
            $.post(URL_API.PRIVILEGE.del, {"code": row.code}, function(result){
                if(result.code == 0){
                    layer.msg(result.msg);
                    setTimeout('$("#tkModal").modal("hide");', 1500);
                    //$tr.attr("style", "display:none;")
                    api.privilege.query();
                }else{layer.msg("操作异常，请稍后重试！", {icon : 2});}
            },'json');
        },function(){return});
    }
}

//操作栏的格式化
api.privilege.actionFormatter = function(value, row, index) {
    var id = value;
    var re = "";
    re += '<a onclick=\'api.privilege.getUpdateModal(this)\'><span class="btn-sm glyphicon glyphicon-pencil" aria-hidden="true"></a>';
    re += '<a onclick=\'api.privilege.del(this)\'><span class="btn-sm glyphicon glyphicon-trash" aria-hidden="true"></a>';
    return re;
}