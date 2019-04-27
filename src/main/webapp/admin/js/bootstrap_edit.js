$(function () {
	dateUtilApi.calendar.single_datepicker();
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

            //上传图片
			//var imgResult = pictureApi.uploadResult();
            if(imgResult.code != 0){
                layer.msg(imgResult.msg);
                return false;
            }
            param.imgPath = imgResult.imgPath;//""字符串传到后台还是""，但是undefined字段到后台就是null
			
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