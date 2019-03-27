var i_statuindex = 0;
//此数组用于保存撤销操作每一步的数据
var arrdata = [];

var m_oTable = null;

$(function () {
    //1.初始化表格
    m_oTable = new TableInit();
    m_oTable.Init();

    //2.初始化按钮事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();

    //3.日期控件的初始化
    /*$(".datetimepicker").datetimepicker({
        format: 'yyyy-mm-dd hh:ii',
        autoclose: true,
        todayBtn: true,
    });*/

});

//表格相关事件和方法
var TableInit = function () {
    var oTableInit = new Object();

    oTableInit.Init = function () {
　　　　 //初始化左边表格
        $('#tb_order_left').bootstrapTable({
            url: '/admin/account/bt',
            method: 'get',
            striped: true,
            cache: false,
            striped: true,
            pagination: true,
            height: 600,
            uniqueId:"id",
            queryParams: oTableInit.queryParams,
            queryParamsType: "limit",
            sidePagination: "server",
            pageSize: 10,
            pageList: [10, 25, 50, 100],
            search: true,
            strictSearch: true,
            showColumns: true,
            showRefresh: true,
            minimumCountColumns: 2,
            clickToSelect: true,
            columns: [
	            {checkbox: true},
	            {field: 'id', title: 'ID'}, 
	            {field: 'name', title: '姓名'}, 
	        	{field: 'mobile', title: '手机'}, 
	        	{field: 'email', title: '邮箱'}, 
	        	{field: 'account', title: '账号'}, 
	        	{field: 'role', title: '角色'}, 
	        	{field: 'Gender', title: '性别'}, 
	        	{field: 'age', title: '年龄'}, 
	        	{field: 'BirthDate', title: '出生日期', formatter: dateFormatter}, 
	        	{field: 'Remark', title: '备注'}, 
            ],
            onLoadSuccess: function (data) {
                 //表格加载完成之后初始化拖拽
　　　　　　　　　　oTableInit.InitDrag();
            }
        });
　　　　 //初始化右边表格
        $('#tb_order_right').bootstrapTable({
            url: '/admin/account/bt',
            method: 'get',
            toolbar: '#toolbar_right',
            striped: true,
            cache: false,
            striped: true,
            pagination: true,
            height: 600,
            queryParams: oTableInit.queryParamsRight,
            queryParamsType: "limit",
            //ajaxOptions: { departmentname: "", statu: "" },
            sidePagination: "server",
            pageSize: 10,
            pageList: [10, 25, 50, 100],
            search: true,
            strictSearch: true,
            showRefresh: true,
            minimumCountColumns: 2,
            columns: [
            	{field: 'id', title: 'ID'}, 
	            {field: 'name', title: '姓名'}, 
	        	{field: 'mobile', title: '手机'}, 
	        	{field: 'email', title: '邮箱'}, 
	        	{field: 'account', title: '账号'}, 
	        	{field: 'role', title: '角色'}, 
	        	{field: 'Gender', title: '性别'}, 
	        	{field: 'age', title: '年龄'}, 
	        	{field: 'BirthDate', title: '出生日期', formatter: dateFormatter}, 
	        	{field: 'Remark', title: '备注'}, 
            ],
            onLoadSuccess: function (data) {
                oTableInit.InitDrop();
            }
        });
    };
    //注册表格行的draggable事件
    oTableInit.InitDrag = function () {
        $('#tb_order_left tr').draggable({
            helper: "clone",
            start: function (event, ui) {
                var old_left_data = JSON.stringify($('#tb_order_left').bootstrapTable("getData"));
                var old_right_data = JSON.stringify($('#tb_order_right').bootstrapTable("getData"));
                var odata = { index: ++i_statuindex, left_data: old_left_data, right_data: old_right_data };
                arrdata.push(odata);
            },
            stop: function (event, ui) {
                
            }
        });
    };
    //注册右边表格的droppable事件
    oTableInit.InitDrop = function () {
        $("#tb_order_right").droppable({
            drop: function (event, ui) {
                var arrtd = $(ui.helper[0]).find("td");
                var rowdata = {
                		
                    id: $(ui.helper[0]).attr("data-uniqueid"),
                    name: $(arrtd[2]).text(),
                    mobile: $(arrtd[3]).text(),
                    email: $(arrtd[4]).text(),
                    account: $(arrtd[5]).text(),
                    role: $(arrtd[6]).text(),
                    Gender: $(arrtd[7]).text() == "-" ? null : $(arrtd[7]).text(),
                    age: $(arrtd[8]).text() == "-" ? null : $(arrtd[8]).text(),
                    BirthDate: $(arrtd[9]).text(),
                    Remark: $(arrtd[10]).text()

                };
                var oTop = ui.helper[0].offsetTop;
                var iRowHeadHeight = 40;
                var iRowHeight = 37;
                var rowIndex = 0;
                if (oTop <= iRowHeadHeight + iRowHeight / 2) {
                    rowIndex = 0;
                }
                else {
                    rowIndex = Math.ceil((oTop - iRowHeadHeight) / iRowHeight);
                }
　　　　　　　　　 //插入右边表格指定位置行数据
                $("#tb_order_right").bootstrapTable("insertRow", { index: rowIndex, row: rowdata });
                $('#tb_order_left').bootstrapTable("removeByUniqueId", $(ui.helper[0]).attr("data-uniqueid"));
                oTableInit.InitDrag();
            }
        });
    };

    oTableInit.queryParams = function (params) {  //配置参数
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit: params.limit,   //页面大小
            offset: params.offset,  //页码
            strBodyno: $("#txt_search_bodynumber").val(),
            strVin: $("#txt_search_vinnumber").val(),
            strOrderno: $("#txt_search_ordernumber").val(),
            strEngincode: $("#txt_search_engin_code").val(),
            strOrderstatus: 0,
            strTranscode: $("#txt_search_trans_code").val(),
            strVms: $("#txt_search_vms").val(),
            strCarcode: $("#txt_search_carcode").val(),
            strImportStartdate: $("#txt_search_import_startdate").val(),
            strImportEnddate: $("#txt_search_import_enddate").val(),
            strSendStartdate: $("#txt_search_send_startdate").val(),
            strSendEnddate: $("#txt_search_send_enddate").val(),

        };
        return temp;
    };

    oTableInit.queryParamsRight = function (params) {  //配置参数
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit: params.limit,   //页面大小
            offset: params.offset,  //页码
            strBodyno: "",
            strVin: "",
            strOrderno: "",
            strEngincode: "",
            strOrderstatus: 5,
            strTranscode: "",
            strVms: "",
            strCarcode: "",
            strImportStartdate: "",
            strImportEnddate: "",
            strSendStartdate: "",
            strSendEnddate: "",

        };
        return temp;
    };

    return oTableInit;
};

//页面按钮初始化事件
var ButtonInit = function () {
    var oInit = new Object();
    var postdata = {};

    oInit.Init = function () {

        //查询点击事件
        $("#btn_query").click(function () {
            $("#tb_order_left").bootstrapTable('refresh');
        });

        //重置点击事件
        $("#btn_reset").click(function () {
            $(".container-fluid").find(".form-control").val("");
            $("#tb_order_left").bootstrapTable('refresh');
        });
        //撤销操作点击事件
        $("#btn_cancel").click(function () {
            if (i_statuindex <= 0) {
                return;
            }
            for (var i = 0; i < arrdata.length; i++) {
                if (arrdata[i].index != i_statuindex) {
                    continue;
                }
                var arr_left_data = eval(arrdata[i].left_data);
                var arr_right_data = eval(arrdata[i].right_data);

                $('#tb_order_left').bootstrapTable('removeAll');
                $('#tb_order_right').bootstrapTable('removeAll');
                $('#tb_order_left').bootstrapTable('append', arr_left_data);
                for (var x = 0; x < arr_right_data.length; x++) {
                    $("#tb_order_right").bootstrapTable("insertRow", { index: x, row: arr_right_data[x] });
                }
                
                //$('#tb_order_right').bootstrapTable('append', arr_right_data);//append之后不能drop
                break;
            }
            i_statuindex--;

            //重新注册可拖拽
            m_oTable.InitDrag();
            //m_oTable.InitDrop();
        });

        //搜索栏展开收起点击事件
        $("#span_collapse").click(function () {
            if ($(this).text() == "收起") {
                $(this).html('展开<label class="glyphicon glyphicon-menu-down"></label>');
                $("#div_more_search").collapse('hide');
            }
            else {
                $(this).html('收起<label class="glyphicon glyphicon-menu-up"></label>');
                $("#div_more_search").collapse('show')
            }
        });
    };

    return oInit;
};

/*-------------------------------------------------------------------------------------*/
function dateFormatter(){
	//
}