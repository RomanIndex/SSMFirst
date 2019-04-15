$(function () {
        $('form').bootstrapValidator({
        	message: 'This value is not valid',
            　feedbackIcons: {
            	valid: 'glyphicon glyphicon-ok',
            	invalid: 'glyphicon glyphicon-remove',
            	validating: 'glyphicon glyphicon-refresh'
            　},
            fields: {
                name: {
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
                        stringlength:{
                            min:11,
                            max:11,
                            message:'请输入11位手机号码'
                        },
                        regexp:{
                            regexp:/^1[3|5|8]{1}[0-9]{9}$/,
                            message:'请输入正确的手机号码'
                        }
                    }
                }
            },
            submitHandler: function (validator, form, submitButton) {
            	var type = $("button[name='submit']").data("type")
            	//disabled 的 input框，取不到name值，改成readonly，也是不能编辑，但是能取到name的值
            	var data = $('#formId').serialize();
            	$("button[name='submit']").attr("disabled","disabled")
            	$.ajax({
    				type : "POST",
    				url : "mg/admin/module/save?operate=" +type,
    				dataType: "json",
    				data : data,
    				success : function(result) {
    					if (result.code != 0) {
    						layer.msg(result.msg, {icon: 2, time:3000});//1:成功图标；2：失败
    						$("button[name='submit']").removeAttr("disabled")
    					} else {
    						layer.msg(result.msg, {icon: 1, time:3000});
    						setTimeout('$("button[name=\'submit\']").removeAttr("disabled")',8000)
    					}
    				}
    			})
            }
        });
});