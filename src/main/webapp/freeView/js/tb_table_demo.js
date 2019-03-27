var $table;
var oInit = new Object();

$(function () {
    //initDate();//layui，找别的解决方案
    InitMainTable();
    
    $("#del").click(function(){
    	Delete();
    })
    
});

//查询 即 刷新
function search(){
	$('#table').bootstrapTable('refresh');
}

function addUser(){
	$("#dialog").dialog({
		height: 140,
		modal: true
	});
}

//实现 批量处理表单的查询条件（ 必须 放在 InitMainTable 前面，否则报错）
$.fn.serializeJsonObject = function() {
    var json = {};
    var form = this.serializeArray();
    $.each(form, function() {
        if (json[this.name]) {
            if (!json[this.name].push) {  
                json[this.name] = [ json[this.name] ];
            }
            json[this.name].push( );
        } else {
            json[this.name] = this.value || '';
        }
    });
    return json;
}

//初始化bootstrap-table的内容
function InitMainTable () {
    //记录页面bootstrap-table全局变量$table，方便应用
    //var queryUrl = '/TestUser/FindWithPager?rnd=' + Math.random()
	//var queryUrl = '/json/bootstrap_table_account.json?rnd=' + Math.random()
	var queryUrl = '/admin/account/bt'
    $table = $('#table').bootstrapTable({
        url: queryUrl,                      //请求后台的URL（*）
        method: 'GET',                      //请求方式（*）
        //toolbar: '#toolbar',              //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        //****
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                   //是否显示分页（*）
        
        //重点字段**
        sortable: true,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        
        //queryParamsType:'', //默认值为 'limit' ,在默认情况下 传给服务端的参数为：offset,limit,sort
                            // 设置为 ''  在这种情况下传给服务器的参数为：pageSize,pageNumber
        
        pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
        pageSize: 10,//rows,                //每页的记录行数（*）
        pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
        search: false,                      //是否显示表格搜索
        strictSearch: true,
        
        //显示列 和 刷新**
        showColumns: true,                  //是否显示所有的列（选择显示的列）
        showRefresh: true,                  //是否显示刷新按钮
        
        //height: $(window).height() - 110,
        //width: $(window).width(),
        
        minimumCountColumns: 2,             //最少允许的列数
        clickToSelect: true,                //是否启用点击选中行
        //height: 500,                      //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId: "id",                     //每一行的唯一标识，一般为主键列
        showToggle: true,                   //是否显示详细视图和列表视图的切换按钮
        cardView: false,                    //是否显示详细视图
        detailView: true,                   //是否显示父子表
        
        //导出
        showExport: true,
        //过滤
        filterControl: true,
        
        responseHandler: responseHandler,
        
        columns: [
        	//是否显示复选框  
        	{checkbox: true, visible: true}, 
        	//align: 'center', // 左右居中
            //valign: 'middle' // 上下居中
        	{field: 'name', title: '姓名', sortable: true, align: 'center', valign: 'middle', filterControl: "input"}, 
        	{field: 'mobile', title: '手机', sortable: true}, 
        	{field: 'email', title: '邮箱', sortable: true, editable: true}, 
        	{field: 'account', title: '账号',filterControl: "select"}, 
        	{field: 'role', title: '角色'}, 
        	{field: 'Gender', title: '性别', sortable: true}, 
        	{field: 'age', title: '年龄'}, 
        	{field: 'BirthDate', title: '出生日期', formatter: dateFormatter}, 
        	{field: 'id', title: 'ID'}, 
        	{field: 'Remark', title: '备注'}, 
        	{field:'id', title: '操作', width: 120, align: 'center', valign: 'middle', formatter: actionFormatter},
        ],
        onLoadSuccess: function () {
        },
        onLoadError: function () {
            //showTips("数据加载失败！");
        	layer.msg("数据加载失败！");
        },
        
        //注册加载子表的事件。注意下这里的三个参数！
        onExpandRow: function (index, row, $detail) {
            oInit.InitSubTable(index, row, $detail);
        },
        
        //查询参数，已优化
        queryParams : function(params){
        	return queryParams(params);
        },
        
        //表格行 双击事件
        onDblClickRow: function (row, $element) {
            var id = row.id;
            EditViewById(id, 'view');
        },
        
        //列 双击事件
        onDblClickCell: function(field, value, row, $element){
        	var i = field;
        	var total = row;
        },
        
        //换页时
        onPageChange:function(number, size){
        	queryParams.search=number;
        },
        
        //设置行的不同的样式展示
        rowStyle: function (row, index) {
            //这里有5个取值颜色['active', 'success', 'info', 'warning', 'danger'];
            var strclass = "";
            if (index == 0) {
                strclass = "warning";
            }
            return { classes: strclass }
        },
        
        //当选中行，拖拽时的哪行数据，并且可以获取这行数据的上一行数据和下一行数据
		onReorderRowsDrag: function(table, row) {
			return false;
		},
		//拖拽完成后的这条数据，并且可以获取这行数据的上一行数据和下一行数据
	    onReorderRowsDrop: function (table, row) {
	        return false;
	    },
		//当拖拽结束后，整个表格的数据
	    onReorderRow: function (newData) {
	         return false;
	    }
    });
};

function initDate(){
    var start = {
            elem: '#startDate',
            format: 'YYYY-MM-DD hh:mm:ss',
            min: laydate.now(-7),       
            max: laydate.now(),
            istime: true,
            istoday: false,
            choose: function (datas) {
                end.min = datas; //开始日选好后，重置结束日的最小日期
                end.start = datas //将结束日的初始值设定为开始日
            }
        };
        var end = {
            elem: '#endDate',
            format: 'YYYY-MM-DD hh:mm:ss',
            min: laydate.now(-7),       
            max: laydate.now(),
            istime: true, //是否开启时间选择
            isclear: true, //是否显示清空
            istoday: true, //是否显示今天
            issure: true, //是否显示确认
            choose: function (datas) {
                start.max = datas; //结束日选好后，重置开始日的最大日期
            }
        };
        laydate(start);
        laydate(end);
}

function queryParams(params) {
    var temp = $("#queryForm").serializeJsonObject();//
    temp["limit"] = params.limit;
    temp["offset"] = params.offset/params.limit+1;
    //temp["sort"] = params.sort,      //排序列名  
    //temp["sortOrder"] = params.order //排位命令（desc，asc） 
    //特殊格式的条件处理...
    return temp;
}

//用于server 分页，表格数据量太大的话 不想一次查询所有数据，可以使用server分页查询，数据量小的话可以直接把sidePagination: "server"  改为 sidePagination: "client" ，同时去掉responseHandler: responseHandler就可以了，
function responseHandler(res) {
	return {
        "rows" : res.rows,
        "total" : res.total
    };
	//可以兼容result
    if (res) {
        return {
            "rows" : res.result,
            "total" : res.totalCount
        };
    } else {
        return {
            "rows" : [],
            "total" : 0
        };
    }
}

//实现 批量删除 数据的方法
function Delete() {
    var ids = "";//得到用户选择的数据的ID
    var rows = $table.bootstrapTable('getSelections');//*简单而强大的方法调用
    for (var i = 0; i < rows.length; i++) {
        ids += rows[i].id + ',';
    }
    ids = ids.substring(0, ids.length - 1);

    layer.msg(ids);
    //DeleteByIds(ids);
}

//初始化子表格(可以无限循环)
oInit.InitSubTable = function (index, row, $detail) {
    var parentid = row.MENU_ID;
    var cur_table = $detail.html('<table></table>').find('table');
    var url = '/json/bt_account.json'
    $(cur_table).bootstrapTable({
        url: url,
        method: 'get',
        //queryParams: { strParentID: parentid },
        //ajaxOptions: { strParentID: parentid },
        clickToSelect: true,
        detailView: true,//父子表
        uniqueId: "id",
        pageSize: 10,
        pageList: [10, 25],
        columns: [
        	{checkbox: true, visible: true}, 
        	{field: 'id', title: 'ID'}, 
        	{field: 'name', title: '姓名', sortable: true}, 
        	{field: 'mobile', title: '手机', sortable: true}, 
        	{field: 'email', title: '邮箱', sortable: true, editable: true}, 
        	{field: 'account', title: '账号'}, 
        ],
        //无线循环取子表，直到子表里面没有记录（按实际业务场景处理）
        onExpandRow: function (index, row, $Subdetail) {
            //oInit.InitSubTable(index, row, $Subdetail);
        }
    });
};

/*----------------Formatter-------------------*/
function emailFormatter(value, row, index){
	var e = value == undefined ? "" : value;
	return "<a href='" + e + "' title='单击打开连接' target='_blank'>" + e + "</a>";
}

function dateFormatter(){
	//
}

//操作栏的格式化
function actionFormatter(value, row, index) {
    var id = value;
    var result = "";
    result += "<a href='javascript:;' class='btn btn-xs green' onclick=\"EditViewById('" + id + "', view='view')\" title='查看'><span class='glyphicon glyphicon-search'></span></a>";
    result += "<a href='javascript:;' class='btn btn-xs blue' onclick=\"EditViewById('" + id + "')\" title='编辑'><span class='glyphicon glyphicon-pencil'></span></a>";
    result += "<a href='javascript:;' class='btn btn-xs red' onclick=\"DeleteByIds('" + id + "')\" title='删除'><span class='glyphicon glyphicon-remove'></span></a>";

    return result;
}