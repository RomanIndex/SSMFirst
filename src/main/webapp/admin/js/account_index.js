$(function () {
    var tableId = "table";
    dateUtilApi.calendar.daterange_picker("reportrange");
    api.account.initTable(tableId);

    $("#uploadImgModel").on("click", function(){
        $("#picModal").modal("show");
    })

    /* 多图上传，初始化fileinput */
    var oFileInput = new FileInput();
    oFileInput.Init("file-pic", "/admin/file/addPic");
});

/**
 * -----------鼠标移动事件
 */
//<a class="mouseMove" style="cursor:pointer;color:#6d9eef;">${emp.name}、</a>
//鼠标移上去触发事件，由于是分页，所以绑定事件要写在callback后面，这里定义触发效果（描述不好）
function mouseMove(){
    layer.msg("点击会触发调整事件！")
}

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
            responseHandler: commonApi.table.responseHandler,
            columns: [
                //align: 'center' 左右居中；valign: 'middle' 上下居中
                {field: 'empNo', title: '编号', align: 'center', sortable: true},
                {field: 'name', title: '名字', formatter: this.nameFormatter},
                {field: 'mobile', title: '电话'},
                {field: 'email', title: '邮箱'},
                {field: 'loginUrl', title: '登陆首页'},
                {field: 'onlineStatusName', title: '线上状态'},
                {field: 'lastLoginTimeName', title: '最近登陆'},
                {field: 'sourceName', title: '来源'},
                {field: 'statusName', title: '状态'},
                {field: 'createTimeName', title: '创建时间'},
                {field: 'empNo', title: '操作', width: 120, align: 'center', valign: 'middle', formatter: actionFormatter},
            ],
            onLoadError: function () {layer.msg("数据加载失败！");},
            queryParams: function(params){return commonApi.table.queryParams(params);},
            onLoadSuccess: function () {
                //layer.msg("成功加载数据！");
                //var ary = ["#export", "#add", "#query", "#openDialog", ".update", ".delete", ".mgActRole"];
                var hasAry = ["#add", "#query", "#openDialog", ".update"];
                var doms = $("a[id], button[id], a.update, a.delete, a.mgActRole");
                //doms.attr("style", "display:none;");//注释掉
                /**
                 * 2019-04-25巨坑，#toolbar 里面 a标签只要 有id，不管是什么值，甚至只要id=""，a标签都会被隐藏掉
                 * 胡乱试半天。接近8点才找到，问题来自这里------定位问题 思维！Import！
                 */
                ssmAuthCtr(hasAry, doms);
                //测试鼠标移动事件
                doms.on('click', function(){
                    layer.msg("控制权限点！");
                }).mouseover(mouseMove);//推荐这种用法
            },
        });
        this.$table = jq;
    },
    query: function(){
        this.$table.bootstrapTable('refresh');
    },
    nameFormatter: function(value, row, index){
        return '<a style="cursor:pointer;color:red;" onclick="api.account.getUpdateModal(this)">'+ value +'</a>';
    },
    getRoleMgModal: function(e){
        var index = $(e).parents('tr').data("index");
        var row = this.$table.bootstrapTable('getData')[index];
        $("#tk").load("admin/mg_act_role.html")
        var param = {};
        param.empNo = row.empNo;
        setTimeout(function(){
            var result = AJAX_HELPER("GET", URL_API.ACCOUNT_ROLE.getRoleScope, param);
            if(result.code != 0){
                return false;
            }
            var map = result.data;
            var hadRole = map.hadRole;
            var leftRole = map.leftRole;

            $("#from").html("")
            var leftOpts = ""
            $.each(leftRole, function(i, v){
                leftOpts += "<option value='"+ v.roleId +"'>"+ v.name +"</option>"
            })
            $("#from").html(leftOpts)

            $("#to").html("")
            var options = ""
            $.each(hadRole, function(i, v){
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
        param.roleIds = select;
        $.ajax({
            url: URL_API.ACCOUNT_ROLE.updateByAccount,
            data: param,//JSON.stringify(param)
            type:"post",
            traditional: true,
            success: function(result){
                if(result.code == 0){
                    setTimeout('$("#tkModal").modal("hide")',800);
                    api.account.query();
                }else{
                    layer.msg(result.msg)
                }
            }
        })
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

        if(param.source == ""){
            layer.msg("来源不能为空！");
            return false;
        }

        //return false;
        var result = AJAX_HELPER("POST", url, param);
        if(result.code == 0){
            setTimeout('$("#tkModal").modal("hide");', 1500);
            api.account.selectId = null;
            api.account.query();
        }else{
            layer.msg(result.msg, {icon : 2});
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
                }else{layer.msg(result.msg, {icon : 2});}
            },'json');
        },function(){return});
    }
}

//操作栏的格式化
function actionFormatter(value, row, index) {
    var empNo = value;
    var re = "";
    re += '<a onclick=\'api.account.getRoleMgModal(this)\'><span class="btn-sm glyphicon glyphicon-th-list" aria-hidden="true"></a>';
    re += '<a onclick=\'api.account.getUpdateModal(this)\'><span class="btn-sm glyphicon glyphicon-pencil" aria-hidden="true"></a>';
    re += '<a onclick=\'api.account.del(this)\'><span class="btn-sm glyphicon glyphicon-trash" aria-hidden="true"></a>';
    return re;
}