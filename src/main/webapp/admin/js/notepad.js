$(document).ready(function() {
    $('#summernote').summernote({
    	minHeight: ($(window).height() - 80),
        //width: 800,
        lang: 'zh-CN',
        dialogsFade : true,
        //dialogsInBody : true,// Dialogs can be placed in body, not in
        disableDragAndDrop : false,
        placeholder : "asdsasdsd实打实大师",
        focus:true,
        toolbar: [
            ['style', ['style']],
            [ 'font', [ 'bold', 'italic', 'underline', 'clear' ] ],
            ['fontsize', ['fontsize']],
            ['fontname', ['fontname']],
            ['color', ['color']],
            ['para', ['ul', 'ol', 'paragraph']],
            [ 'height', [ 'height' ] ], [ 'table', [ 'table' ] ],
            [ 'insert', [ 'link', 'picture', 'video', 'hr' ] ],
            ['view', [ 'fullscreen', 'codeview']]
        ],
        callbacks: {
            // onImageUpload的参数为files，summernote支持选择多张图片
            /*onImageUpload : function(files) {
                var $files = $(files);
                // 通过each方法遍历每一个file
                $files.each(function() {
                    var file = this;
                    // FormData，新的form表单封装，具体可百度，但其实用法很简单，如下
                    var data = new FormData();
                    // 将文件加入到file中，后端可获得到参数名为“file”
                    data.append("file", file);
                    // ajax上传
                    $.ajax({
                        data : data,
                        type : "POST",
                        url : "/action/sysUpload/uploadProductImage",// div上的action
                        cache : false,
                        contentType : false,
                        processData : false,
                        // 成功时调用方法，后端返回json数据
                        success : function(response) {
                            console.log(response+response.status+response.data);
                            if (response.status==1) {
                                // 文件不为空
                                if (response.data) {
                                    // 获取后台数据保存的图片完整路径
                                    var imageUrl =response.data[0];
                                    // 插入到summernote
                                    $('.summernote').summernote('insertImage', imageUrl, function($image) {
                                    });
                                }
                            }
                        },
                     
                    });
                });
            }*/
        }
    });

});

function save(){
	var text = getContext();
	layer.msg(text);
}

function getContext(){
	var sHTML;
    if($('#summernote').summernote('isEmpty')){
    	layer.msg("为空，提交失败！");
    }else{
    	sHTML = $('#summernote').summernote('code');
    }
    return sHTML;
}