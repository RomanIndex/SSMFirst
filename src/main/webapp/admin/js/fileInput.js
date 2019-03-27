$(function() {
	$("#addModel").on("click", function(){
		$("#picModal").modal("show");
	})
	
	//初始化fileinput
    var oFileInput = new FileInput();
    oFileInput.Init("file-pic", "/admin/file/addPic");
});

//$("#add_bachPic").fileinput('reset'); //重置预览表中的所有文件
//$("#add_bachPic").fileinput('clear'); //只是清理还未上传的预览，上传成功的不会清除

//初始化fileinput
var FileInput = function () {
    var oFile = new Object();

    //初始化fileinput控件（第一次初始化）
    oFile.Init = function(ctrlName, uploadUrl) {
		var control = $('#' + ctrlName);
		//初始化上传控件的样式
		control.fileinput({
			language: 'zh', //设置语言
			uploadUrl: uploadUrl, //上传的地址
			allowedFileExtensions: ['jpg', 'gif', 'png'],//接收的文件后缀
			showUpload: true, //是否显示上传按钮
			showCaption: false,//是否显示标题
			browseClass: "btn btn-primary", //按钮样式     
			//dropZoneEnabled: false,//是否显示拖拽区域
			//minImageWidth: 50, //图片的最小宽度
			//minImageHeight: 50,//图片的最小高度
			//maxImageWidth: 1000,//图片的最大宽度
			//maxImageHeight: 1000,//图片的最大高度
			//maxFileSize: 0,//单位为kb，如果为0表示不限制文件大小
			//minFileCount: 0,
			maxFileCount: 2, //表示允许同时上传的最大文件个数
			enctype: 'multipart/form-data',
			validateInitialCount:true,
			previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
			msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
		});

		//导入文件上传完成之后的事件（同时上传多个文件的时候，前台会发送多个异步的请求到后台，也就是说，当同时上传三个文件的时候，后台的ImportOrder方法会进入三次。这样就能使用多线程同时去处理三个文件。）
		control.on("fileuploaded", function (event, data, previewId, index) {
			var result = data.response; // 后台返回的json
			//实际情况，肯定要回显图片 相当路径，如何解决？
			if(result.code == 0){
				console.log(result.data);
				var fileCount = $('#file-pic').fileinput('getFilesCount');
				console.log("已成功上传的数量："+ fileCount);
			}
		});
	}
	
    return oFile;
};

//初始化文件上传插件
// $('#file-pic').fileinput({//初始化上传文件框
//     uploadAsync: true,//默认异步上传
//     uploadLabel: "上传",//设置上传按钮的汉字
    
//     showUpload : false,//是否显示上传按钮
//     showRemove : false,//显示移除按钮
//     showPreview : true, //是否显示预览
//     showCaption: false,//是否显示标题
//     showBrowse: false,//是否显示浏览按钮（与拖拽区域选一个即可）
//     browseOnZoneClick: true,//是否显示拖拽区域
//     dropZoneClickTitle: "<p>（点击此区域添加图片）<p>",
//     browseClass: "btn btn-primary",//按钮样式
//     uploadClass: "btn btn-primary",//设置上传按钮样式
    
//     language: "zh",//配置语言
//     uploadUrl: "/admin/file/addPic",
//     //autoReplace: true,//是否自动替换当前图片，设置为true时，再次选择文件，会将当前的文件替换掉
//     maxFileSize: 0,
//     maxFileCount: 2,/*允许最大上传数，可以多个，当前设置单个*/
//     enctype: 'multipart/form-data',
//     //allowedPreviewTypes: [ 'image' ], //allowedFileTypes: ['image', 'video', 'flash'],
//     allowedFileExtensions: ["jpg", "png","gif"],/*上传文件格式*/
//     msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
//     dropZoneTitle: "<p>请通过拖拽图片文件放到这里<p>",
//     //uploadExtraData: {"id": id, "fileName": "abc.jpg"},//这个是外带数据
//     slugCallback: function(filename) {
//         return filename.replace('(', '_').replace(']', '_');
//     }
// });

// //上传文件成功，回调函数 
// $('#file-pic').on("fileuploaded", function(event, data, previewId, index) {
// 	var result = data.response; // 后台返回的json
// 	if(result.code == 0){
// 		//$('#picId').val(result.data.id);// 拿到后台传回来的id，给图片的id赋值序列化表单用
// 		//$('#picName').val(result.data.fileName);
// 		$('#picId').val("测试多图片上传，看控制台")
// 		console.log(result.data);
// 	}else{
// 		alert("文件上传失败!");
// 		return false;
// 	}
// 	// 如果是上传多张图
// 	// 计数标记，用于确保全部图片都上传成功了，再提交表单信息
// 	var fileCount = $('#file-pic').fileinput('getFilesCount');
// 	if (fileCount == 2) {
// 		// 上传文件成功后再保存图片信息
// 		var picData = $('#picInfoForm').serialize();
// 		$.ajax({
// 			url: '/admin/file/saveForm',//保存表单信息
// 			type: 'post',
// 			dataType: 'json',
// 			cache: false, // 不缓存
// 			data: {"formStr" : picData},//OK!
// 			//data: {"str":JSON.stringify(param)},//OK!
// 			//data: picData,//可以调接口，但参数为null!
// 			success: function(data) {
// 				if(data.code == 0) {
// 					alert(".................")
// 				}else{
// 					alert("添加失败,ajax请求返回失败!");
// 				}
// 			},
// 		});
// 	}else{
// 		alert("...有上传失败的...")
// 	}
// });

//提交图片信息
function uploadPicture(){
	//先上传文件，然后在回调函数提交表单
    $('#file-pic').fileinput('upload');
}

$("#save").click(function(){
	var picData = $('#picInfoForm').serialize();
	$.ajax({
		url : '/admin/file/saveForm',//保存表单信息
		type : 'post',
		dataType : 'json',
		cache : false, // 不缓存
		//data : picData,//可以调接口，但参数为null!
		data : {"formStr": picData},// OK!
		//data: {"formStr": JSON.stringify(picData)},//OK!
		success : function(data) {
			if (data.code == 0) {
				alert(".................")
			} else {
				alert("添加失败,ajax请求返回失败!");
			}
		},
	});
})