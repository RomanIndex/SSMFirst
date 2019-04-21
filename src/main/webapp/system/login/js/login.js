var api = {};

api.login = {};

api.login.login = function(){
    $(".error_tips").children().first().text("");
    var form = new FormData(document.getElementById("loginForm"));
    var boxFlag = $("#checkspan").hasClass("checked");
    form.append("boxFlag",boxFlag);

    $.ajax({
        type : "POST",
        url : "/login/check",
        data : form,
        processData: false,
        contentType: false,
        success : function(result) {
            if (result.code != 0) {
                $(".error_tips").children().first().text(result.msg);
            } else {
                var url = result.data;
                window.location.href = url;
            }
        },
        error:function(e){
            alert("错误！！");
            window.clearInterval(timer);
        }
    });
}

api.login.register = function(){
    window.location.href = "/register";
}

$(function() {
    $('input').iCheck({
        checkboxClass: 'icheckbox_square-blue',
        radioClass: 'iradio_square-blue',
        increaseArea: '20%' /* optional */
    });

	if($.isFunction(window.parent.logout)) window.parent.logout();	
	checkCookie();
	
	$('.remember_me').click(function(){
		$(this).toggleClass('checked')
	})
	
	$("body").on('mousedown',function(param){
		if(param.button==2) document.oncontextmenu = function () { return false; };
	})

	$('#loginSubmit').keypress(function(e) {
		if (e.which == 13) {
			$("#login").click();
			return false;
		}
	});

});

// 如果有点击'记住我'，将员工编号和密码填充到输入框中
function checkCookie() {
	var memberId = Cookies.get('memberId');
	if (memberId != undefined && memberId != "") {
		$("#checkspan").addClass("checked");
		$('#employeeId').val(memberId);
	}
}
