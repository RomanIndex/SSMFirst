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
            return y+'-'+add0(m)+'-'+add0(d);
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
            return y+'-'+add0(m)+'-'+add0(d)+' '+add0(h)+':'+add0(mm)+':'+add0(s);
        }else{
            return "";
        }
    },
    add0: function (m) {return m<10?'0'+m:m}
}

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
/**
 * 初始化单片时间选择input
 * 对应的input引入datePicker class即可
 */
function init_singleDatePicker2(){
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

/* 整合进api */
function init_singleDatePicker(){
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

    //单个，只显示时分秒
    $('input.datePickerHSMsss').daterangepicker({
        singleDatePicker: true, //是否是单个时间选择器
        //
    })

    //只能用datetimepicker的，主要css和js的引用
    /*$('.datePickerHSM').datetimepicker({
        //bootcssVer:3,
        format: "hh:ii",
        startView: 1,  //首先显示的视图, 0当前时的分钟区间 1该天的每时 2该月的每天 3该年的每月 4年视图
        minView: 0,
        maxView: 4,
        //todayBtn: true,  //是否显示今日按钮
        startDate: new Date("2017/6/15"),  //开始时间
        endDate: new Date("2017/7/15"),  //结束时间
        autoclose: true,  //当选择一个日期之后是否立即关闭此日期时间选择器。
        language: 'zh-CN'
    });*/
}