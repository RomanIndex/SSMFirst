$(function() {
	initTable();
	//*appendTo、after、append、html 的 效果 和 区别
	$("#filter_div").appendTo('.top');//依赖于dom里面定义的.top，可浏览器F12看生成页面样式
	
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
               //"data": "/json/edit_inline.json",
               "type": "post",
               "error": function(){alert("服务器未正常响应，请重试");}
           },
           "columns": [
               { "data": "id", "visible" : false, "title":"ID","defaultContent":""},
               { "data": "account", "title":"用户名","defaultContent":""},
               { "data": "role", "title":"姓名","defaultContent":""},
               { "data": "remark", "title":"备注","defaultContent":""},
               { "data": null, "title":"操作","defaultContent": "<button class='edit-btn' type='button'>编辑</button>"}
           ],
       });
 
       $("#table tbody").on("click",".edit-btn",function(){
           var tds=$(this).parents("tr").children();
           $.each(tds, function(i,val){
               var jqob=$(val);
               if(i < 1 || jqob.has('button').length ){return true;}//跳过第1项 序号,按钮
               var txt=jqob.text();
               var put=$("<input class='' type='text'>");
               put.val(txt);
               jqob.html(put);
           });
           $(this).html("保存");
           $(this).toggleClass("edit-btn");
           $(this).toggleClass("save-btn");
       });
 
       $("#table tbody").on("click",".save-btn",function(){
           var row=table.row($(this).parents("tr"));
           var tds=$(this).parents("tr").children();
           $.each(tds, function(i,val){
               var jqob=$(val);
               //把input变为字符串
               if(!jqob.has('button').length){
                   var txt=jqob.children("input").val();
                   jqob.html(txt);
                   table.cell(jqob).data(txt);//修改DataTables对象的数据
               }
           });
           var data = row.data();
           
           /*console.log("typeof dataa:---"+ typeof(data))
           console.log("typeof JSON.stringify():"+ typeof(JSON.stringify(data)))
           console.log("typeof $(data).serialize()):"+ typeof($(data).serialize()))//$(data).serialize() = ""; data.serialize() is not a function
           //console.log("typeof JSON.parse(data):---"+ typeof(JSON.parse($(data))))//Unexpected token o in JSON at position 1
           //console.log(eval('(' + JSON.stringify(data) + ')'))
           var json = eval('(' + JSON.stringify(data) + ')')
           console.log("typeof eval:---"+ typeof( eval('(' + JSON.stringify(data) + ')') ))
           console.log(data)*/
           
           $.ajax({
               "url":"/admin/account/edit/inline/save",
               //"data":{"str":json},
               //"data":{"str":JSON.parse($(data))},//Unexpected token o in JSON at position 1
               //"data":json.parseJSON(),//json.parseJSON is not a function json包版本太低
               "data":{"str":JSON.stringify(data)},//（OK）"data":JSON.stringify(data) 直接这种是错的，且键要与 接口 入参名称 一致
               "type":"post",
               "error":function(){
            	   layer.msg("服务器未正常响应，请重试")
               },
               "success":function(response){
            	   layer.msg(response)
               }
           });
           $(this).html("编辑");
           $(this).toggleClass("edit-btn");
           $(this).toggleClass("save-btn");
       });
 
       //批量点击编辑按钮
       $("#batch-edit-btn").click(function(){
           $(".edit-btn").click();
       });
       $("#batch-save-btn").click(function(){
           $(".save-btn").click();
       });
   };