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

$('body').on('mouseover','td.mouseShow', function(){
	var that = $(this)
	var row = table.row(that.parents('tr')).data();
	var rowindex = that[0]._DT_CellIndex.row; //行号
	var cellindex = that[0]._DT_CellIndex.column; //列号
	var data = table.cell(rowindex, cellindex).data();
	tip.start(this)
	that.attr('tips', data);
})

//弹框html里面的dom，绑定事件
$(document).on("click", "#save", function(){
	var type = $("#save").val()
	console.log("type = "+ type)
	var data = $('#inputForm').serialize();
	var param = parseQuery(data)
	$.ajax({
		  type: "POST",
		  url: "/admin/account/mapper/save?type="+ type,
		  cache: false,
		  data: {"str":JSON.stringify(param)},
		  dataType: "json",
		  success: function (result) {
			  if(result.code == 0){
				  layer.msg(result.msg);
				  $("#tkModal").modal("hide");
				  table.draw();
			  }else{
				  layer.msg(result.msg);
			  }
		  }
	 });
})

/**
 * 表单重复提交
 */
function submitForm(){
	window.parent.openNewTab("测试表单重复提交", "/admin/account/gotoForm")
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
			var param = getFormObj("teamForm");
			if(checkInlegalChar(param)){layer.msg("有不合法字符，请重新输入");return false}
			param.pageNo = (data.start / data.length) + 1;
			param.pageSize = data.length;
			 $.ajax({
				  type: "POST",
				  url: "admin/account/mapperQuery",
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
		  { "data": "id", "visible": false},
		  { "data": "name", "title":"用户", "render": function (data, type, full, meta) {
			  return '<a style="cursor:pointer;color:red;" onclick="accountApi.getAccountDetail(this)">'+ data +'</a>';
		  }},
		  { "data": "empNo", "title":"登录账号", "className": "mouseShow"},
		  { "data": "mobile", "title":"电话"},
		  { "data": "birth", "title":"年龄" },
		  { "data": "email", "title":"邮箱"},
		  { "data": "address", "title":"地址" },
		  { "data": "onlineStatus", "title":"在线" },
		  { "data": "status", "title":"状态", "render": function (data, type, full, meta) {return formatStatus(data);}},
		  { "data": "createTime", "title":"创建时间", "render": function (data, type, full, meta) {return formatDate(data);}},
		  { "data": null, "title":"操作", "render": function (data, type, full, meta) {
				var re = '<a onclick="accountApi.getAccountDetail(this)"><span class="btn-sm glyphicon glyphicon-pencil" aria-hidden="true"></a>'
				re += '<a onclick="accountApi.delAccount(this)"><span class="btn-sm glyphicon glyphicon-trash" aria-hidden="true"></a>'
				return re;
			  }
		  }
		],
		"columnDefs" : [],
		//buttons : []
	});
};

/**
 * 一些测试函数
 */
function getTKModal(){
	/**
	 * 1、load 可以加载html页面，也可以是ftl页面
	 * 2、加载公共tk_modal里面，id的部分模态框
	 * 2、可以引入对应的js
	 */
	$("#tk").load("admin/tk_modal.ftl #addAccountModal", function(){
		jQuery.getScript("admin/js/tk_modal.js", function(){
			$("#save").css({'color': 'red;'})
		})
	})
	setTimeout(function(){
		init_singleDatePicker();
		$(":header.modal-title").text("头部名称（JS自定义）")
		$("#save").val("add").text("新增（JS自定义）")
		$("#addAccountModal").modal("show");
	},400)
}

/**
 * ------------本页面特别处理JS-----------------
 */
var accountApi = {
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

//鼠标悬浮提示
var tip = {
	$: function(ele){
		if(typeof(ele)=="object")
			return ele;
		else if(typeof(ele)=="string"||typeof(ele)=="number")
			return document.getElementById(ele.toString());
		return null;
	},
	mousePos:function(e){
		var x,y;
		var e = e||window.event;
		return{x:e.clientX+document.body.scrollLeft+document.documentElement.scrollLeft,y:e.clientY+document.body.scrollTop+document.documentElement.scrollTop}; 
	}, 
	start:function(obj){
		var self = this;
		var t = self.$("mjs:tip");
		obj.onmousemove=function(e){
			var mouse = self.mousePos(e);
			t.style.left = mouse.x + 10 + 'px';
			t.style.top = mouse.y + 10 + 'px';
			t.innerHTML = obj.getAttribute("tips");
			t.style.display = '';
		}; 
		obj.onmouseout=function(){
			t.style.display = 'none';
		};
	}
}