$(function(){
	/**
	 * init_daterangepicker()、init_validator()
	 * 两个存在于custom.js的两个方法
	 */
	init_daterangepicker();
	init_validator();
	initTable();
	//*appendTo、after、append、html 的 效果 和 区别
	$("#filter_div").appendTo('.top');//依赖于dom里面定义的.top，可浏览器F12看生成页面样式
})

/**
 * 表单重复提交
 */
function submitForm(){
	window.parent.openNewTab("测试表单重复提交", "/admin/route/resubmit_form")
}
/**
 * 行点击事件
 * 1、没有放在$(function(){...})里面，且都是有效的
 */
//行点击事件2
$('#ssmtable').on('click','tr',function(e){
	//先拿到点击的 行号 （从0开始）
	var index = $(this).context._DT_RowIndex;//context._DT_RowIndex百度
	//此处拿到隐藏列的值（分页加载下，对应 columns 里面的 值）
	var id = $('#ssmtable').DataTable().row(index).data().id;
	layer.msg("点击_"+index+"_行,名字："+ id)
	//点击空白无效（一些校样，根据实际情况）
	if(id == undefined || id == ""){
		return false;
	}
})

/**
 * -----------鼠标移动事件
 */
//<a class="mouseMove" style="cursor:pointer;color:#6d9eef;">${emp.name}、</a>
//鼠标移上去触发事件，由于是分页，所有绑定事件要写在callback后面，这里定义触发效果（描述不好）
function mouseMove(){
	layer.msg("点击会触发调整事件！")
}

/**
 * -----------初始化表格-----------------
 */
var table;
function initTable() {
	table = $('#ssmtable').DataTable({
		//"dom": 'Bt<"row page_break"lip<"clear">>',
		"dom": 'B<"top">t<"row page_break"lip<"clear">>',
		"searching" : true,
		"select" : true,
		"responsive" : true,
		"stateSave" : true,
		"searchDelay" : 3000,
		"serverSide": true,
		"ajax" : function (data, callback, settings) {
			var param = commonApi.form.getFormObj("teamForm");
			if(commonApi.form.checkIllegalChar(param)){layer.msg("有不合法字符，请重新输入");return false}
			param.pageNo = (data.start / data.length) + 1;
			param.pageSize = data.length;
			 $.ajax({
				  type: "GET",
				  url: URL_API.ACCOUNT.query,
				  cache: false,
				  data: param,
				  dataType: "json",
				  success: function (result) {
					  var returnData = {}
					  returnData.recordsTotal = result.data.totalRecords;
					  returnData.recordsFiltered = result.data.totalRecords;
					  returnData.data = result.data.list;
					  callback(returnData);
					  //分页模式下：给选定列，绑定点击事件（方式一）
					  $(".skip_a").on('click', function(){
						  var param = $(this).parent().text()
						  window.parent.openNewTab("编辑用户","admin/account/edit?empNo="+ param);
					  }).mouseover(mouseMove);//推荐这种用法
				  }
			 });
		},
		"columns": [
		  { "data": "name", "title":"用户", "render": function (data, type, full, meta) {
			  return '<a style="cursor:pointer;color:red;" onclick="accountApi.getAccountDetail(this)">'+ data +'</a>';
		  }},
		  { "data": "empNo", "title":"登录账号", "className": "mouseShow"},
		  { "data": "mobile", "title":"电话"},
		  { "data": "email", "title":"邮箱"},
		  { "data": "onlineStatusName", "title":"在线"},
		  { "data": "statusName", "title":"状态"},
		  { "data": "createTimeName", "title":"创建时间"},
		  { "data": null, "title":"操作", "render": function (data, type, full, meta) {
				var re = '<a onclick="accountApi.getAccountDetail(this)"><span class="btn-sm glyphicon glyphicon-pencil" aria-hidden="true"></a>'
				re += '<a onclick="accountApi.delAccount(this)"><span class="btn-sm glyphicon glyphicon-trash" aria-hidden="true"></a>'
				return re;
			  }
		  }
		],
		"columnDefs" : [],
	});
};

/**
 * ------------本页面特别处理JS-----------------
 */
var accountApi = {};

accountApi = {
	query: function(){
		table.draw();
	},
	addAccount: function(){
		
	},
	getAccountDetail: function(el){
		var row = table.row($(el).parents('tr')).data();
		window.parent.openNewTab(row.teamName +"成员","/admin/account/update?empNo="+ row.id)
	},
	delAccount: function(el){
		var row = table.row($(el).parents('tr')).data();
		if(row.status == 0){
			layer.msg("还有医生在该队伍，请先给医生离队！");
			return false
		}
		var index = layer.confirm("删除将清空队伍信息，且不能找回，确认删除吗？", {
			btn: ['确认','取消']
		},function(){
			$.ajax({
				type : "POST",
				url : "/admin/account/delete",
				data : {
					"teamNo": "ads",
				},
				success : function(result) {
					if (result.code == 0) {
						layer.msg(result.msg);
						teamApi.query();
					} else {
						layer.msg(result.msg)
					}
				},
				error : function(){
		        	layer.msg("异常错误，请联系技术人员！");
		        }
			})
		},function(){return});
	}
}

/**
 * 一些测试函数（测完通过后，就 “强行” 应用到别的页面，这里删除）
 */