/**
 * 存放处理时间的一些函数
 * @author zz
 * 2018-02-08
 * @returns
 */
/**
 * 初始化单片时间选择input
 * 对应的input引入datePicker class即可
 */
function init_singleDatePicker(){
	$('input.datePicker').daterangepicker({
		showDropdowns : true,  
		timePicker : true, //是否显示小时和分钟  
		timePicker24Hour: true,  
		timePickerIncrement : 1, //时间的增量，单位为分钟  
		timePickerSeconds : true,  //显示秒
		linkedCalendars : false,
		singleDatePicker: true, //是否是单个时间选择器 
		locale : {  
			format: "YYYY-MM-DD HH:mm:ss",  //控件中from和to 显示的日期格式  
			applyLabel : "确定",  
			cancelLabel : "取消",  
			daysOfWeek : ["日", "一", "二", "三", "四", "五", "六"],
			monthNames : ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月 "],  
			firstDay : 1  
		},
	}, function(start) {
		//layer.msg("start："+ start.format('YYYY-MM-DD HH:mm:ss'),{icon:1,time:4000})
	}).on('hide.daterangepicker', function() {
		//可以定义选中后的触发事件
	})
}