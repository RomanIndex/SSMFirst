$(function() {
	initTable();
	$("#filter_div").appendTo('.top');
	
	$("#query").click(function(){
		table.draw();
	});
	
	$("#add").click(function(){
		layer.msg("window.parent.openNewTab(\"新增用户\",\"admin/user/add\");");
	});
})

var table;
function initTable(){
	table = $('#table').DataTable({
		"dom": '<"top">t<"row page_break"lip<"clear">>',
		"ajax": {
			"url": "admin/account/query/inline",
			//"dataSrc": "data",//默认为data
			//"data": "/json/edit_inline.json",//请求的参数
			"type": "post",
			"error": function(){alert("服务器未正常响应，请重试");}
		},
		"columns": [
			{ "data": "id", "visible" : false, "title":"ID"},
			{ "data": "account", "title":"用户名"},
			{ "data": "role", "title":"姓名"},
			{ "data": "remark", "title":"备注"},
			{ "data": null, "title":"操作","render": function (data, type, full, meta) {return formatOperate(full);}},
		],
		"fnInitComplete": function(oSettings, json) {
			//var ary = ["#export", "#add", "#query", "#batch-edit", "#batch-save", ".update", ".delete"];
			var ary = ["#add", "#query", ".update"];
			var doms = $("a[id], button[id], a.update, a.delete");
			doms.hide();
			$.each(doms, function(index, item){
				var that = $(this);
				var idDom = that.attr("id");
				var classDoms = that.attr("class");
				
				$.each(ary, function(i, each){
					if(each == "#"+ idDom){
						that.show();
					}
					var name = each.replace("\.", "");
					if(classDoms.indexOf(name) >= 0){
						that.show();
					}
				})
			})
		},
    });
};

function formatOperate(full){
	var re = "";
	re += '<a class="update"><span class="btn-sm glyphicon glyphicon-pencil" aria-hidden="true"></a>';
	re += '<a class="delete"><span class="btn-sm glyphicon glyphicon-trash" aria-hidden="true"></a>';
	return re;
}