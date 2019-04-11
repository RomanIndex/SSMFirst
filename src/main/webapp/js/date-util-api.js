/**
 * 一些常用的时间处理函数
 * @author ZZ
 * @time 2018-09-19
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