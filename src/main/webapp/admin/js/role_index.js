$(function () {
    var tableId = "table";
    api.role.initTable(tableId)
});

api.role = {
    $table: null,
    selectId: null,
    query: function(){
        this.$table.bootstrapTable('refresh');
    },
    menuMg: function(e){
        var index = $(e).parents('tr').data("index");
        var row = this.$table.bootstrapTable('getData')[index];
        getMenuMgTree("#repeatDialog", row.roleId)
    },
    getAddModal: function(e){
        $("#tk").load("admin/role_edit.html")
        setTimeout(function(){
            dateUtilApi.calendar.single_datepicker();
            $("#tkModal :header.modal-title").text("新增")
            $("#save").val("add").text("新增")
            $("#tkModal").modal("show");
        },400)
    },
    getUpdateModal: function(e){
        var index = $(e).parents('tr').data("index");
        var row = this.$table.bootstrapTable('getData')[index];
        this.selectId = row.roleId;
        $("#tk").load("admin/role_edit.html")
        setTimeout(function(){
            $("input[name='roleId']").val(row.roleId)
            $("input[name='name']").val(row.name)
            $("#type").val(row.type)
            $("#level").val(row.level)
            $("#remark").text(row.remark)

            $(":header.modal-title").text("修改角色")
            $("#save").val("update").text("保存")
            $("#tkModal").modal("show");
        },400)
    }
}

api.role.initTable = function (tableId) {
    var jq = $("#"+ tableId).bootstrapTable({
        url: URL_API.ROLE.query,            //请求后台的URL（*）
        method: 'GET',                      //请求方式（*）
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                   //是否显示分页（*）
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        //queryParamsType: '',              //设置会改变默认传入后台的分页参数
        contentType: "application/x-www-form-urlencoded",//关键，后台可以以对象形式接收参数
        //宽高
        //height: ($(window).height() - 80) * 0.9,
        //width: $(window).width(),
        toolbar: '#toolbar',                //工具按钮用哪个容器
        //右上方按钮
        showRefresh: true,                  //是否显示刷新按钮
        showToggle: true,                   //是否显示详细视图和列表视图的切换按钮
        showColumns: true,                  //是否显示所有的列（选择显示的列）
        showExport: true,                   //导出（配置好）
        uniqueId: "roleId",                 //每一行的唯一标识，一般为主键列
        //detailView: true,                 //是否显示父子表
        responseHandler: commonApi.table.responseJpaHandler,
        columns: [
            {field: 'id', title: 'ID', visible: false},
            {field: 'name', title: '名称', class: "mouseShow", sortable: true},
            {field: 'roleId', title: '角色ID', sortable: true},
            {field: 'type', title: '类型', sortable: true},
            {field: 'level', title: '级别'},
            {field: 'weight', title: '权重值'},
            {field: 'priValues', title: '权限值', sortable: true},
            {field: 'status', title: '状态', formatter: commonApi.format.status},
            {field: 'createTime', title: '创建时间', formatter: dateUtilApi.formatDate},
            {field: 'remark', title: '备注'},
            {field:'id', title: '操作', formatter: this.actionFormatter},
        ],
        onLoadError: function () {layer.msg("数据加载失败！");},
        queryParams: function(params){return commonApi.table.queryParams(params);},
        /*onLoadSuccess: function (data) {
            layer.msg("------------------------------------")
            var h = 400;//getElementToPageTop(el);
            $("#table").parent().height(h);
        },*/
        /*onPostBody: function (data) {
            layer.msg("sdsdssfsdfsdfsdf")//在onLoadSuccess之前触发
        }*/
    });
    this.$table = jq;
}

function getElementToPageTop(el) {
    if(el.parentElement) {
        return this.getElementToPageTop(el.parentElement) + el.offsetTop
    }
    return el.offsetTop
}

/* 鼠标移动显示详情内容 */
$('body').on('mouseover','td.mouseShow', function(e){
    var index = $(e.target).parents('tr').data("index");
    var row = api.role.$table.bootstrapTable('getData')[index];
    var data = row.roleId;

    var that = $(this);//区别
    commonApi.utils.mouseTip.start(this);
    that.attr('tips', data);
})

api.role.save = function (e) {
    var param = commonApi.form.getFormObj("inputForm");
    var saveType = $(e).val();
    if(saveType == "add"){
        url = URL_API.ROLE.add;
    }else{
        url = URL_API.ROLE.update;
        param.roleId = this.selectId;
    }
    //return false;
    var result = AJAX_HELPER("POST", url, param);
    if(result.code == 0){
        setTimeout('$("#tkModal").modal("hide");', 1500);
        this.selectId = null;
        this.query();
    }
}

api.role.del = function (e) {
    var index = $(e).parents('tr').data("index");
    var row = this.$table.bootstrapTable('getData')[index];
    var tipMsg = "";

    if(row.status == 0){
        tipMsg = "对无效状态的模块执行删除，将永久从系统移除，确认？"
    }else{
        tipMsg = "模块改为无效，页面将不会显示，确定？"
    }
    var index = layer.confirm(tipMsg, {
        btn: ['确认','取消']
    },function(){
        $.post(URL_API.ROLE.del, {"roleId": row.roleId}, function(result){
            if(result.code == 0){
                layer.msg(result.msg);
                setTimeout('$("#tkModal").modal("hide");', 1500);
                //this.query();//这里的this是ajax对象（确认一下），要下面那样用
                api.role.query();
            }else{layer.msg("操作异常，请稍后重试！", {icon : 2});}
        },'json');
    },function(){return});
}

//加载 菜单 管理树
function getMenuMgTree(divId, roleId){
    var dialogParent = $(divId).parent();
    var dialogOwn = $(divId).clone();
    dialogOwn.hide();

    var $dialog = $(divId).dialog({
        autoOpen: true,
        //closed: false,
        //cache: false,
        modal: true,
        width: 760,
        height: 540,
        title: '角色 菜单权限 管理',
        buttons:[{
            text:'testBtn',
            handler:function(){
                var selects = $treegrid.treegrid("getSelections")
                var checkeds = $treegrid.treegrid("getChecked")
                layer.msg("只测试获取页面数据，但没有保存！")
            }
        },{
            text:'Close关闭',
            handler:function(){
                $(divId).dialog("close")//不是$(this),是open，可以看jar包源码
            }
        }],
        //加on前缀才能删掉html
        onClose: function () {
            dialogOwn.appendTo(dialogParent);//html可在tk后面看到
            $(this).dialog("destroy").remove();
        },
        //加on前缀即可显示数据
        onOpen: function (){
            $("#roleId").val(roleId);
            getTreegridData();
        }
    });
}

var $treegrid;
//加载 树形网格 和数据
function getTreegridData(){
    //$tree.treegrid('loadData', []);//清空数据
    $treegrid = $("#tt").treegrid({
        lines: true,
        rownumbers: true,
        //fit:true,               //网格自动撑满
        fitColumns:true,        //设置为 true，则会自动扩大或缩小列的尺寸以适应网格的宽度并且防止水平滚动。
        //定义关键字段来标识树节点
        idField: 'moduleId',
        //treeField属性定义哪个字段显示为树形菜单
        treeField: 'name',
        //checkbox: true,
        singleSelect : false,
        checkOnSelect : true,
        selectOnCheck : true,
        //参考https://blog.csdn.net/ytangdigl/article/details/76117736
        columns:[[
            //{title:'多选', field:'id', width:60, checkbox:true},
            {title:'模块名称',field:'name',width:180,align:'left'},
            {title:'状态',field:'status',width:60,align:'center'},
            {title:'类型',field:'type',width:60,align:'center'},
            {title:'生成票据',field:'ticket',width:80, align:'center', formatter: formatCreateTicket},
            {title:'角色授权（多余）',field:'roleTicket',width:80, align:'center', formatter: formatRoleAuth},
            {title:'URL',field:'url',width:180,align:'left'}
        ]],
        //toolbar: '#search_div',//加这个会覆盖掉toolbar的按钮
        toolbar: [{
            text : "测试按钮",
            iconCls : "icon-add",
            handler : function() {
                layer.msg("只是测试一下，并无实际功能！");
            }
        },{
            text : "保存",
            iconCls : "icon-save",
            handler : function() {
                var selects = $treegrid.treegrid('getSelections')//只选中背景变色的那一行
                //var checkeds = $treegrid.treegrid('getChecked')//同上
                //var sss = $treegrid.treegrid('getCheckedNodes')//选中所有勾选的行
                //var moduleIds = $treegrid.treegrid("getAllChecked", true);//选中所有勾选的行及其所有父节点node_id（符合要求）
                var codes = [];
                $.each(selects, function(i, item){
                    console.log(item);
                    codes[i] = item.ticket.code;
                })
                updateRolePri(codes);
            }
        }],
        loader: treeDataLoader,//向后台请求数据，返回 指定格式 的数据
        onLoadSuccess: function(row, result){
            //被这个函数坑好久，必须使用接口返回的数据结构和“标准”一致
            var data = result.rows;
            $.each(data, function(index, item){
                if(item.roleTicket){
                    //$treegrid.treegrid('selectRecord', item.moduleId)
                    $treegrid.treegrid('select', item.moduleId)
                }
            })
        },
        onSelect: function(node){
            if(node.ticket != null){
                //layer.msg(node.moduleId);
                var pNode = $treegrid.treegrid('getParent',node.moduleId);//得到父节点
                if(pNode != null){
                    //$treegrid.treegrid('check', pNode.moduleId);
                    $treegrid.treegrid('select', pNode.moduleId);
                }
            }else{
                layer.msg("请先给【"+ node.name +"】模块添加show！");
                //$treegrid.treegrid('unselectRow', node.moduleId)
                $treegrid.treegrid('unselect', node.moduleId)
            }
        },
        onUnselect: function(node){
            var cNode = $treegrid.treegrid('getChildren',node.moduleId);//得到子节点
            if(cNode != null){
                $.each(cNode, function(i, item){
                    //$treegrid.treegrid('unselectRow', item.moduleId);
                    $treegrid.treegrid('unselect', item.moduleId);
                })
            }
        },
        /*onCheck: function(node, checked){
            layer.msg(node.moduleId);
        },*/
        //searcher: doSearch,//放这里不生效？？
        prompt: 'Please Input Value',
    });
}

var treeDataLoader = function(param, success, error){
    var roleId = $("#roleId").val();
    $.ajax({
        //url:"json/treegrid.json",
        url: URL_API.ROLE_PRIVILEGE.getMenuTree,
        data:{
            "roleId": roleId
        },
        type:"get",
        //dataType:"jsonp",//(跨域)
        //jsonpCallback:"callback",
        success: function(result){
            //$('#tt').treegrid('reload');//死循环，添加成功重新加载数据
            //$('#tt').treegrid('loadData', []);//清空数据
            //$('#tt').treegrid('selectRow',3);
            success(result.data);//这句貌似还不能省，下面会执行到treegrid onLoadSuccess函数
        }
    })
}

function formatCreateTicket(value, row){
    var flag = value == null ? false : true;
    if(flag){
        return "OK!";
    }else{
        return '<a class="createTicket btn btn-default btn-sm" data-module-id="'+ row.moduleId +'">生成</a>';
    }
}

function formatRoleAuth(value, row){
    //var kv = $("#XXX").treegrid("checkNode",id);  根据ID勾选节点
    //var kv = $("#XXX").treegrid("uncheckNode",id);  根据ID取消勾选节点
    var re = '<input type="checkBox">'
    if(value){
        re = '<input type="checkBox" checked>';
        var kv = $("#tt").treegrid("checkNode", row.moduleId);//根据ID勾选节点//放到onLoadSuccess里面去
    }else{
        if(row.ticket != null){
            re = '<input type="checkBox">'
        }else{
            re = '<input type="checkBox" disabled>'
        }
    }
    return re;
}

//向 后台 更新（有新增有删除）角色 的菜单权限
function updateRolePri(codes){
    var roleId = $("#roleId").val()
    console.log("ids的类型："+ typeof(codes))
    var ss = Array.prototype.slice.call(codes)
    console.log("ss的类型："+ typeof(codes))
    //return false;
    $.ajax({
        url: URL_API.ROLE_PRIVILEGE.updateByRole,
        data:{
            "roleId": roleId,
            "codes": codes
        },
        type:"post",
        //contentType: "application/json",
        traditional: true,
        success: function(result){
            if(result.code == 0){
                layer.msg(result.msg)
            }else{
                layer.msg(result.msg)
            }
        }
    })
};

//生成 菜单票据，也是 一种权限
$(document).on("click", ".createTicket", function(){
    var moduleId = $(this).data("moduleId");
    var obj = {}
    obj.moduleId = moduleId;
    obj.operateEnumName = "show";
    obj.name = moduleId+ "（show票据）";
    var result = AJAX_HELPER("POST", URL_API.PRIVILEGE.add, obj);
    if(result.code == 0){
        $(this).parent().html("OK!");
    }
})

function doSearch(value,name){
    var paramData={
        name:value
    };

    $('#tt').treegrid({
        queryParams:paramData
    });
}

$.extend($.fn.treegrid.methods, {
    //iscontains是否包含父节点（即子节点被选中时是否也取父节点）
    getAllChecked: function (jq, iscontains) {
        var keyValues = new Array();
        /*
          tree-checkbox2 有子节点被选中的css
          tree-checkbox1 节点被选中的css
          tree-checkbox0 节点未选中的css
        */
        var checkNodes = jq.treegrid("getPanel").find(".tree-checkbox1");
        for (var i = 0; i < checkNodes.length; i++) {
            var keyValue1 = $($(checkNodes[i]).closest('tr')[0]).attr("node-id");
            keyValues.push(keyValue1);
        }

        if (iscontains) {
            var childCheckNodes = jq.treegrid("getPanel").find(".tree-checkbox2");
            for (var i = 0; i < childCheckNodes.length; i++) {
                var keyValue2 = $($(childCheckNodes[i]).closest('tr')[0]).attr("node-id");
                keyValues.push(keyValue2);
            }
        }

        return keyValues;
    }
});

//操作栏的格式化
api.role.actionFormatter = function (value, row, index) {
    var id = value;
    var re = "";
    re += '<a onclick=\'api.role.menuMg(this)\'><span class="btn-sm glyphicon glyphicon-leaf" aria-hidden="true"></a>';
    re += '<a onclick=\'api.role.getUpdateModal(this)\'><span class="btn-sm glyphicon glyphicon-pencil" aria-hidden="true"></a>';
    re += '<a onclick=\'api.role.del(this)\'><span class="btn-sm glyphicon glyphicon-trash" aria-hidden="true"></a>';
    return re;
}