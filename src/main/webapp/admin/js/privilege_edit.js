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
        },
        submitHandler: function (validator, form, submitButton) {
            var type = $("button[name='submit']").data("type")
            //disabled 的 input框，取不到name值，改成readonly，也是不能编辑，但是能取到name的值
            var moduleId = $("input[name='xz']:checked").next().val()
            if(moduleId == undefined){
                layer.msg("请先选择一个静态资源模板！")
                setTimeout('$("button[name=\'submit\']").removeAttr("disabled")',4000)
                return false;
            }
            var data = $('#formId').serialize();
            var dataType = typeof(data)
            var ddd = data + "&moduleId=" + moduleId
            console.log(ddd)
            //return false;
            $("button[name='submit']").attr("disabled","disabled")
            $.ajax({
                type : "POST",
                url : "mg/admin/privilege/save?operate=" +type,
                dataType: "json",
                data : ddd,
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

$("#firstMenu").change(function () {
    var selectEl = $(this);
    var selectValue = selectEl.val();
    layer.msg(selectValue)
    $.get("mg/admin/module/getSecond",{"parentId" : selectValue}, function(result){
        if(result.code == 0){
            $("#secondMenu").html("")
            secondCTL = ""
            if(result.data.length == 0){
                secondCTL += "<option value=''>空空如也！</option>"
            }else{
                $.each(result.data, function(index, item){
                    secondCTL += "<option value='"+item.moduleId+"'>"+ item.name +"</option>"
                })
            }
            $("#secondMenu").html(secondCTL)
            //$("#sCatalogue")[0].innerHTML = secondCTL;
        }else{
            //layer.msg("操作异常，请稍后重试！", {icon : 2});
        }
    },'json');
});

$("#secondMenu").change(function () {
    var selectEl = $(this);
    var selectValue = selectEl.val();
    layer.msg(selectValue)
    $.get("mg/admin/module/getBtn",{"belongId" : selectValue}, function(result){
        if(result.code == 0){
            $("#btn").html("")
            secondCTL = ""
            if(result.data.length == 0){
                secondCTL += "<option value=''>空空如也！</option>"
            }else{
                $.each(result.data, function(index, item){
                    secondCTL += "<option value='"+item.moduleId+"'>"+ item.name +"</option>"
                })
            }
            $("#btn").html(secondCTL)
        }else{
            layer.msg("操作异常，请稍后重试！", {icon : 2});
        }
    },'json');
});