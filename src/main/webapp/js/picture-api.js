var pictureApi = {
    CHANGE_PIC: false,
    uploadResult: function(){
        var result = {}
        result.code = 0;
        if(pictureApi.CHANGE_PIC){
            result.imgPath = pictureApi.uploadImg();
            if(result.imgPath == undefined || result.imgPath == false){
                result.msg = "图片上传异常！【"+ result.imgPath +"】";
                result.code = -1;
            }
        }
        pictureApi.CHANGE_PIC = false;
        return result;
    },
    getFullPath: function(e){
        pictureApi.CHANGE_PIC = true;

        /*if (e) {
			var src = e.value;
			if (e.files) {
				src =  window.URL.createObjectURL(e.files.item(0));
			}
			$("#pictureFile").next().find("img").attr("src", src);
		}*/

        var files = e.files[0]
        if (!e || !window.FileReader) return
        var reader = new FileReader()
        reader.readAsDataURL(files)
        reader.onloadend = function () {
            $("#pictureFile").next().find("img").attr("src", this.result);
        }
    },
    uploadImg: function(){
        var backImgPath;//这里直接返回 图片地址
        var anfiles = $("#pictureFile")[0].files[0]
        if(anfiles == undefined){
            layer.msg('请选择文件');
            return;
        }else{
            var formdata = new FormData();
            formdata.append("file", anfiles);
            $.ajax({
                url: "/admin/uploadImg/accountImg",
                type:'post',
                data: formdata,
                async:false,//更改为同步
                processData: false,
                contentType: false,
                success:function(result){
                    if(result.code == 200){
                        backImgPath = result.data;
                    }else{
                        layer.msg(result.msg)
                    }
                }
            });
        }
        return backImgPath;
    }
}