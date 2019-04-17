$(function () {
    var tableId = "table";
    api.account.initTable(tableId)
});

api.account = {
    $table: null,
    initTable: function (tableId) {
        var jq = $("#"+ tableId).bootstrapTable({
            url: URL_API.ACCOUNT.query,
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
                //align: 'center' 左右居中；valign: 'middle' 上下居中
                {field: 'name', title: '名字'},
                {field: 'empNo', title: '编号', align: 'center', sortable: true},
                {field: 'mobile', title: '电话'},
                {field: 'email', title: '邮箱'},
                {field: 'loginUrl', title: '登陆首页'},
                {field: 'onlineStatus', title: '线上状态'},
                {field: 'lastLoginTime', title: '最近登陆', formatter: dateUtilApi.formatDate},
                {field: 'status', title: '状态', formatter: commonApi.format.status},
                {field: 'source', title: '来源'},
                {field: 'createTime', title: '创建时间', formatter: dateUtilApi.formatDate},
                {field:'id', title: '操作', width: 120, align: 'center', valign: 'middle', formatter: actionFormatter},
            ],
            onLoadError: function () {layer.msg("数据加载失败！");},
            queryParams: function(params){return queryParams(params);},
            onLoadSuccess: function () {
                //layer.msg("成功加载数据！");
                //var ary = ["#export", "#add", "#query", "#openDialog", ".update", ".delete", ".mgActRole"];
                var hasAry = ["#add", "#query", "#openDialog", ".update"];
                var doms = $("a[id], button[id], a.update, a.delete, a.mgActRole");
                doms.attr("style", "display:none;")
                ssmAuthCtr(hasAry, doms);
            },
        });
        this.$table = jq;
    },
    query: function(){
        this.$table.bootstrapTable('refresh');
    },
    getRoleMgModal: function(e){
        var index = $(e).parents('tr').data("index");
        var row = this.$table.bootstrapTable('getData')[index];
        $("#tk").load("admin/mg_act_role.html")
        var param = {};
        param.empNo = row.empNo;
        setTimeout(function(){
            var result = AJAX_HELPER("GET", URL_API.ACCOUNT.getRoleByEmoNo, param);
            var map = result.data;
            var havedRole = map.havedRole;
            var leftRole = map.leftRole;

            $("#from").html("")
            var leftOpts = ""
            $.each(leftRole, function(i, v){
                leftOpts += "<option value='"+ v.roleId +"'>"+ v.name +"</option>"
            })
            $("#from").html(leftOpts)

            $("#to").html("")
            var options = ""
            $.each(havedRole, function(i, v){
                options += "<option value='"+ v.roleId +"'>"+ v.name +"</option>"
            })
            $("#to").html(options)

            //$(":header.modal-title").text("标签管理["+ row.sceneName +"]")
            $("#accountName").text(row.name)
            $("#submit").val("add").text("保存")
            $("#empNo").val(row.empNo)
            $("#tkModal").modal("show");
        },400)
    },
    getAddModal: function(e){
        $("#tk").load("admin/account_edit.html")
        setTimeout(function(){
            $("#tkModal :header.modal-title").text("新增")
            $("#save").val("add").text("新增")
            $("#tkModal").modal("show");
        },400)
    },
    getUpdateModal: function(e){
        var index = $(e).parents('tr').data("index");
        var row = this.$table.bootstrapTable('getData')[index];
        this.selectId = row.empNo;
        $("#tk").load("admin/account_edit.html")
        setTimeout(function(){
            $("input[name='empNo']").val(row.empNo).attr("readonly", true).attr("style", "cursor:not-allowed")
            $("#inputForm input[name='name']").val(row.name)
            $("#inputForm input[name='mobile']").val(row.mobile)
            $("#inputForm input[name='email']").val(row.email)
            $("#inputForm input[name='loginUrl']").val(row.loginUrl)
            $("#source").val(row.source)

            $("#tkModal :header.modal-title").text("修改账号")
            $("#save").val("update").text("保存")
            $("#tkModal").modal("show");
        },400)
    },
    selectId: null,
    roleMgSave: function(){
        var select = [];
        $("#to").children().each(function(i, item){
            select.push($(this).val());
        })

        var param = {}
        param.empNo = $("#empNo").val()
        param.roleIds = select

        //return false;
        var result = AJAX_HELPER("POST", URL_API.ACCOUNT.updateRole, JSON.stringify(param));
        if(result.code == 0){
            setTimeout('$("#tkModal").modal("hide")',800);
            this.$table.bootstrapTable('refresh');
        }
    },
    save: function (e) {
        var param = commonApi.form.getFormObj("inputForm");
        //var form_data = $('#inputForm').serialize();
        //""字符串传到后台还是""，但是undefined字段到后台就是null
        var saveType = $(e).val();//data("type");
        if(saveType == "add"){
            url = URL_API.ACCOUNT.add;
            //param.creator = OTC_LOGIN_ACCOUNT;
        }else{
            url = URL_API.ACCOUNT.update;
            param.id = api.account.selectId;
            //param.modifyUser = OTC_LOGIN_ACCOUNT;
        }
        //return false;
        var result = AJAX_HELPER("POST", url, param);
        if(result.code == 0){
            setTimeout('$("#tkModal").modal("hide");', 1500);
            api.account.selectId = null;
            api.account.query();
        }
    },
    del: function (e) {
        var index = $(e).parents('tr').data("index");
        var row = this.$table.bootstrapTable('getData')[index];

        var index = layer.confirm("确定删除吗？", {
            btn: ['确认','取消']
        },function(){
            $.post(URL_API.ACCOUNT.del, {"empNo": row.empNo}, function(result){
                if(result.code == 0){
                    layer.msg(result.msg);
                    setTimeout('$("#tkModal").modal("hide");', 1500);
                    //$tr.attr("style", "display:none;")
                    api.account.query();
                }else{layer.msg("操作异常，请稍后重试！", {icon : 2});}
            },'json');
        },function(){return});
    }
}

//操作栏的格式化
function actionFormatter(value, row, index) {
    var id = value;
    var re = "";
    re += '<a onclick=\'api.account.getRoleMgModal(this)\'><span class="btn-sm glyphicon glyphicon-th-list" aria-hidden="true"></a>';
    re += '<a onclick=\'api.account.getUpdateModal(this)\'><span class="btn-sm glyphicon glyphicon-pencil" aria-hidden="true"></a>';
    re += '<a onclick=\'api.account.del(this)\'><span class="btn-sm glyphicon glyphicon-trash" aria-hidden="true"></a>';
    return re;
}