$(function() {
	//选择一项
	$("#addOne").click(function() {
		$("#from option:selected").clone().appendTo("#to");
		$("#from option:selected").remove();
	});

	//选择全部
	$("#addAll").click(function() {
		$("#from option").clone().appendTo("#to");
		$("#from option").remove();
	});

	//移除一项
	$("#removeOne").click(function() {
		$("#to option:selected").clone().appendTo("#from");
		$("#to option:selected").remove();
	});

	//移除全部
	$("#removeAll").click(function() {
		$("#to option").clone().appendTo("#from");
		$("#to option").remove();
	});

	//移至顶部
	$("#Top").click(function() {
		var allOpts = $("#to option");
		var selected = $("#to option:selected");

		if (selected.get(0).index != 0) {
			for (i = 0; i < selected.length; i++) {
				var item = $(selected.get(i));
				var top = $(allOpts.get(0));
				item.insertBefore(top);
			}
		}
	});

	//上移一行
	$("#Up").click(function() {
		var selected = $("#to option:selected");
		if (selected.get(0).index != 0) {
			selected.each(function() {
				$(this).prev().before($(this));
			});
		}
	});

	//下移一行
	$("#Down").click(function() {
		var allOpts = $("#to option");
		var selected = $("#to option:selected");

		if (selected.get(selected.length - 1).index != allOpts.length - 1) {
			for (i = selected.length - 1; i >= 0; i--) {
				var item = $(selected.get(i));
				item.insertAfter(item.next());
			}
		}
	});

	//移至底部
	$("#Buttom").click(function() {
		var allOpts = $("#to option");
		var selected = $("#to option:selected");

		if (selected.get(selected.length - 1).index != allOpts.length - 1) {
			for (i = selected.length - 1; i >= 0; i--) {
				var item = $(selected.get(i));
				var buttom = $(allOpts.get(length - 1));
				item.insertAfter(buttom);
			}
		}
	});
});