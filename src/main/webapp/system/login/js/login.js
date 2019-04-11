$(function() {
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

	//登陆
	$("#login").click(function() {
		$("#error").text("");
		var employeeId = $('#employeeId').val();
		var password = $('#password').val();
		if (employeeId == undefined || employeeId == ""
				|| password == undefined || password == "") {
			$("#error").text("用户名和密码不能为空");
			return;
		}
		boxFlag = $("#checkspan").hasClass("checked");
		
		$.ajax({
			type : "POST",
			url : "/login/check",
			data : {
				"empNo" : employeeId,
				"password" : password,
				"boxFlag" : boxFlag
			},
			success : function(result) {
				if (result.code != 0) {
					$("#error").text(result.msg);
				} else {
					var url = result.data;
					window.location.href = url;
				}
			}
		});
	});
	
	/**
	 * 注册
	 */
	  $('#register').click(function(event) {
		  window.location.href = "/register";
	  })
	  
	 //重置密码
	 $('#updatePassword').click(function(){
		 var employeeId = $('#employeeId').val();
		 var password = $('#password').val();
		 var newPass = $("#newPass").val();
		 var checkPass = $("#checkPass").val();
		 if (newPass==""||checkPass=="") {
			 layer.msg('输入框不能为空');
			 return;
		 }
		 if (newPass != checkPass) {
			 layer.msg('两次密码不一样，请检查后重新提交');
			 return;
		 }
		 $.ajax({
				type : "POST",
				url : "/updatePWD",
				data : {
					"employeeId" : employeeId,
					"oldPass" : password,
					"newPass" : newPass
				},
				success : function(result) {
					if (!result.isSuccess) {
						layer.msg(result.message);
					} else {
						layer.confirm('修改成功！', {
							  btn: ['确定']
							}, function(){
								$(".alter_psd_bg").hide();
								$("#login").click();
							});
					}
				}
			});
	 })
  
});

// 如果有点击'记住我'，将员工编号和密码填充到输入框中
function checkCookie() {
	var memberId = Cookies.get('memberId');
	if (memberId != undefined && memberId != "") {
		$("#checkspan").addClass("checked");
		$('#employeeId').val(memberId);
	}

}
