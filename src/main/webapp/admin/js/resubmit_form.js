$(function () {
	layer.msg("comming................................")

	$("#submit").click(function () {
		submit();
	})

});

function submit() {
	var token = $("input[name='token']").val();
	$.ajax({
		type: "GET",
		url: "admin/account/submitForm",
		dataType: "json",
		data: { "token": token },
		success: function (result) {
			if (result.code != 0) {
				layer.msg(result.msg, {
					icon: 2,
					time: 3000
				}); //1:成功图标；2：失败
			} else {
				layer.msg(result.msg, {
					icon: 1,
					time: 3000
				});
			}
		}
	})
}
