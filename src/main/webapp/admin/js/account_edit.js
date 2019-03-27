$(function () {
	init_singleDatePicker();
	init_btValidator();
	//模拟修改环境，初始化数据
	get_init_data();
});

$("#savePic").click(function(){
	var imgResult = pictureApi.uploadResult();
	if(imgResult.code == 0){
		layer.msg(imgResult.imgPath);
	}else{
		layer.msg(imgResult.msg);
	}
})

function get_init_data(){
	//读取JSON数据
	$.getJSON("/json/mulit_value.json", function(result){
		if(result.code == 0){
			var data = result.data;
			$("#formId input[name='name']").val(data.name)
			$("#formId input[name='startDay']").val(data.startDay)
			$("#formId input[name='endDay']").val(data.endDay)
			$("#formId input[name='startTime']").val(data.startTime)
			$("#formId input[name='endTime']").val(data.endTime)
			$("#formId input[name='area']").val(data.area)
			$("#formId input[name='leaveReason']").val(data.leaveReason)
			$("#formId input[name='area']").val(data.area)
			//多行文本框处理
			$("#remark").text(data.remark == null ? "" : data.remark)
			$("#fixedImg").attr("src", "/ssmFile/ssm/account/123456.jpg")
			//选中单选框
			$(":radio[name='levelRadio'][value="+ data.levelRadio +"]").prop('checked', true);
			//选中复选框
			$(":checkbox[name='goodAtPosition']").each(function(){
				var val = $(this).val()
				var that = $(this)
				$.each(data.goodAtPosition, function(i, item){
					if(val == item){
						that.prop("checked", true)
					}
				})
			});
		}
	});
}

var pictureApi = {
	CHANGE_PIC: false,
	uploadResult: function(){
		var result = {}
		result.code = -1;
		result.msg = "";
		result.imgPath = "";
		if(pictureApi.CHANGE_PIC){
			result.imgPath = pictureApi.uploadImg();
			if(result.imgPath == undefined || result.imgPath == false){
				result.msg = "图片上传异常！【"+ result.imgPath +"】";
			}else{
				result.code = 0;
			}
		}else{
			result.imgPath = $("#formId input[name='pictureFile']").val()
			result.code = 0;
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
		//是否支持FileReader
		if (!e || !window.FileReader) return
		let reader = new FileReader()
		//将图片将转成 base64 格式
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
			return false;
		}else{
			var formdata = new FormData();
			formdata.append("files", anfiles);
		    $.ajax({
		    	url: "/admin/uploadImg/accountImg",
		    	type:'post',
		        data: formdata,
		        async:false,//更改为同步 
		        processData: false,
		        contentType: false,
		        success:function(result){
		            if(result.code == 0){
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

function init_btValidator(){
	$('form').bootstrapValidator({
		message: 'This value is not valid',
		　feedbackIcons: {
			valid: 'glyphicon glyphicon-ok',
			invalid: 'glyphicon glyphicon-remove',
			validating: 'glyphicon glyphicon-refresh'　
		},
		fields: {
			/*name: {
				message: '用户名验证失败',
				validators: {
					notEmpty: {
						message: '用户名不能为空'
					},
					stringLength: {
						min: 2,
						max: 10,
						message: '用户名长度必须在2到10位之间'
					},
				}
			},
			mobile: {
				validators: {
					notEmpty: {
						message: '电话不能为空'
					},
					stringlength: {
						min: 11,
						max: 11,
						message: '请输入11位手机号码'
					},
					regexp: {
						regexp: /^1[3|5|8]{1}[0-9]{9}$/,
						message: '请输入正确的手机号码'
					}
				}
			},
			email: {
				validators: {
					notEmpty: {
						message: '邮箱不能为空'
					},
					emailAddress: {
						message: '邮箱地址格式有误'
					}
				}
			}*/
		},
		submitHandler: function (validator, form, submitButton) {
			//var data = $('#formId').serialize();
			var param = getFormObj("formId");
			//有些getFormObj方法取不到的，需要单独取
			var type = $("button[name='submit']").data("type");
			if(getBLen(param.leaveReason) > 28){layer.msg("理由太长！");return false;}
			//获取复选框的值（单选框、下拉框的可以直接获取到）
			var goodAtPosition = new Array();
			$(":checkbox[name='goodAtPosition']:visible").each(function(){
				if($(this).is(":checked")){
					goodAtPosition.push($(this).val());
				}
			})
			param.goodAtPosition = goodAtPosition;
			//文本框的值
			param.remark = $("#remark").val();//去首位空格
			//开始时间 < 当前时间 且 开始时间 < 结束时间
			if(new Date(param.startDay) >= new Date(param.endDay)){layer.msg("开始时间要小于结束时间！");return false;}
			if(new Date(param.startDay) >= new Date()){layer.msg("开始时间要小于当前时间！");return false;}
			//var intValue = Number("123")//string转int
			
			//处理图片
			//var imgResult = pictureApi.uploadResult();
			if(imgResult.code == 0){
				param.imgPath = imgResult.imgPath;
			}else{
				layer.msg(imgResult.msg);
				return false;
			}
			
			layer.open({
				type: 1,
				title: "提交信息",
				area: ['960px', '240px'],
				content: JSON.stringify(param)//js对象转字符串
			})
			return false;
			$("button[name='submit']").attr("disabled", "disabled")
			$.ajax({
				type: "POST",
				url: "ek/admin/member/save?type=" + type,
				dataType: "json",
				data: data,
				success: function (result) {
					if (result.code != 0) {
						layer.msg(result.msg, {
							icon: 2,
							time: 3000
						}); //1:成功图标；2：失败
						$("button[name='submit']").removeAttr("disabled")
					} else {
						layer.msg(result.msg, {
							icon: 1,
							time: 3000
						});
						setTimeout('$("button[name=\'submit\']").removeAttr("disabled")', 8000)
					}
				}
			})
		}
	});
}