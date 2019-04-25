/**
 * 工具类JS
 * @author ZHUZI
 * @date 2018-01-13
 * 每个方法，尽量说明 入参 及 类型，存在的目的，和 返回的结果
 */
var dateUtilApi = {
    dealwith: function(bothday, i){
        var k = bothday.split(" ", 3)
        var begin = k[0]
        var end = k[2]
        return k[i]
	},
    strData2Int: function (strDate) {
        var date = eval('new Date(' + strDate.replace(/\d+(?=-[^-]+$)/, function (a) { return parseInt(a, 10) - 1; }).match(/\d+/g) + ')');
        return Date.parse(date);
    },
    formatYmd: function (data) {
        if(data != "" && data != null){
            var time = new Date(data)
            var y = time.getFullYear()
            var m = time.getMonth()+1
            var d = time.getDate()
            return y+ '-' +dateUtilApi.add0(m)+ '-' +dateUtilApi.add0(d);
        }else{
            return "";
        }
    },
    formatDate: function (data) {
        //shijianchuo是整数，否则要parseInt转换
        if(data != "" && data != null){
            var time = new Date(data);
            var y = time.getFullYear();
            var m = time.getMonth()+1;
            var d = time.getDate();
            var h = time.getHours();
            var mm = time.getMinutes();
            var s = time.getSeconds();
            return y+ '-' +dateUtilApi.add0(m)+ '-' +dateUtilApi.add0(d)+' '+dateUtilApi.add0(h)+ ':' +dateUtilApi.add0(mm)+ ':' +dateUtilApi.add0(s);
        }else{
            return "";
        }
    },
    add0: function (m) {return m<10?'0'+m:m}
}

/**
 * 存放一些 选取时间 的函数（可扩展，甚至其他插件）
 * @author zz
 * 2018-02-08
 * @returns
 */
dateUtilApi.calendar = {};
/**
 * 初始化 时间范围 查询
 */
dateUtilApi.calendar.daterange_picker = function(pickerId){
    var cb = function(start, end, label) {
        $('#'+ pickerId + ' span').html(start.format('YYYY-MM-DD') + ' - ' + end.format('YYYY-MM-DD'));
    };

    var optionSet = {
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

    $('#'+ pickerId + ' span').html(moment().subtract(29, 'days').format('YYYY-MM-DD') + ' - ' + moment().format('YYYY-MM-DD'));
    $('#'+ pickerId).daterangepicker(optionSet, cb);
}

/**
 * 初始化单片时间选择input
 * 对应的input引入datePicker/datePickerYMD class即可
 */
dateUtilApi.calendar.single_datepicker = function () {
    //单个选择器，YYYY-MM-DD HH:mm:ss
    $('input.datePicker').daterangepicker({
        //startDate : //设置默认的开始日期 ,格式：MM/DD/YYYY
        //endDate : //设置默认的结束日期
        //minDate : //设置最小可用日期
        //maxDate : //设置最大可用日期
        //autoApply : //不用点击Apply或者应用按钮就可以直接取得选中的日期
        singleDatePicker: true, //是否是单个时间选择器
        showDropdowns : true,  //当设置值为true的时候，允许年份和月份通过下拉框的形式选择
        timePicker : true, //可选中时分
        timePicker24Hour: true,  //设置小时为24小时制
        timePickerIncrement : 1, //时间的增量，单位为分钟
        timePickerSeconds : true,  //可选中秒
        linkedCalendars : false,
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

    //单个，只显示年月日
    $('input.datePickerYMD').daterangepicker({
        singleDatePicker: true, //是否是单个时间选择器
        showDropdowns : true,  //当设置值为true的时候，允许年份和月份通过下拉框的形式选择
        linkedCalendars : false,
        locale : {
            format: "YYYY-MM-DD",  //控件中from和to 显示的日期格式
            applyLabel : "确定",
            cancelLabel : "取消",
            daysOfWeek : ["日", "一", "二", "三", "四", "五", "六"],
            monthNames : ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月 "],
            firstDay : 1
        },
    }, function(start) {
        //layer.msg("start："+ start.format('YYYY-MM-DD HH:mm:ss'),{icon:1,time:4000})
    })
}