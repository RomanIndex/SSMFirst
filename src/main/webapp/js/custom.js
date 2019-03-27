/**
 * Resize function without multiple trigger
 * 
 * Usage:
 * $(window).smartresize(function(){  
 *     // code here
 * });
 */

function init_validator () {
	if( typeof (validator) === 'undefined'){ return; }
 
	// initialize the validator function
	validator.message.date = 'not a real date';
	
	// validate a field on "blur" event, a 'select' on 'change' event & a '.reuired' classed multifield on 'keyup':
	$('form')
	.on('blur', 'input[required], input.optional, select.required', validator.checkField)
	.on('change', 'select.required', validator.checkField)
	.on('keypress', 'input[required][pattern]', validator.keypress);
	
	$('.multi.required').on('keyup blur', 'input', function() {
	    validator.checkField.apply($(this).siblings().last()[0]);
	});
	
	$('form').submit(function(e) {
		e.preventDefault();
		var submit = true;
	
		// evaluate the form using generic validaing
		if (!validator.checkAll($(this))) {
			submit = false;
		}

	    if (submit)
	        this.submit();
	    
	    return false;
	});
};

function init_daterangepicker() {
	var cb = function(start, end, label) {
		$('#reportrange span').html(start.format('YYYY-MM-DD') + ' - ' + end.format('YYYY-MM-DD'));
	};

	var optionSet1 = {
	  startDate: moment().subtract(29, 'days'),
	  endDate: moment(),
	  minDate: '01/01/2016',
	  maxDate: '12/31/2027',
	  dateLimit: {
		days: 36500
	  },
	  showDropdowns: true,
	  showWeekNumbers: true,
	  timePicker: false,
	  timePickerIncrement: 1,
	  timePicker12Hour: true,
	  ranges: {
		'今天': [moment(), moment()],
		'昨天': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
		'最近7天': [moment().subtract(6, 'days'), moment()],
		'最近30天': [moment().subtract(29, 'days'), moment()],
		'本月': [moment().startOf('month'), moment().endOf('month')],
		'上月': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')],
	  	'所有': [moment().subtract(100, 'month'), moment()],
	  },
	  opens: 'left',
	  buttonClasses: ['btn btn-default'],
	  applyClass: 'btn-small btn-primary',
	  cancelClass: 'btn-small',
	  format: 'YYYY-MM-DD',
	  separator: ' to ',
	  locale: {
		applyLabel: '确定',
		cancelLabel: '取消',
		fromLabel: '从',
		toLabel: '至',
		customRangeLabel: '自定义',
		daysOfWeek: ['日', '一', '二', '三', '四', '五', '六'],
		monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
		firstDay: 1
	  }
	};
	
	$('#reportrange span').html(moment().subtract(29, 'days').format('YYYY-MM-DD') + ' - ' + moment().format('YYYY-MM-DD'));
	$('#reportrange').daterangepicker(optionSet1, cb);
}

$(document).ready(function() {
	$.extend( true, $.fn.dataTable.defaults, {
		"dom": 'Bftl<"row page_break1"ip<"clear">>',
		"drawCallback":function(){
			setTimeout(function(){$(window).trigger("resize")},200);
		},
		serverSide : false,
		scrollCollapse : true,
	    scrollY : ($(window).height() - 140)+"px", //滚动条
		scrollX : true,
		select : true,		
		autoWidth : true,   //禁用自动调整列宽
		pageLength : 10,  //默认显示条数
		//keys: true,
		destroy: true,
		"responsive": true,
		"stateSave": true,
		"columnDefs": [
			{"className": "dt-center", "targets": "_all"}],
		language: {
		    "processing": "处理中...",
		    "lengthMenu": "显示 _MENU_ 项结果",
		    "zeroRecords": "没有匹配结果",
		    "info": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
		    "infoEmpty": "显示第 0 至 0 项结果，共 0 项",
		    "infoFiltered": "(由 _MAX_ 项结果过滤)",
		    "infoPostFix": "",
		    "search": "搜索:",
		    "url": "",
		    "emptyTable": "表中数据为空",
		    "loadingRecords": "载入中...",
		    "thousands": ",",
		    "paginate": {
		        "first": "首页",
		        "previous": "上页",
		        "next": "下页",
		        "last": "末页"
		    },
		    "aria": {
		        "sortAscending": ": 以升序排列此列",
		        "sortDescending": ": 以降序排列此列"
		    },
		    "select": {
	            rows: ""
	        }
		}
	});
});