/**
 * 工具类JS
 * @author ZHUZI
 * @date 2018-01-13
 * 每个方法，尽量说明 入参 及 类型，存在的目的，和 返回的结果
 */

/**
 * 格式化 时间戳
 */
function formatDate(data) {
	//时间戳是整数，否则要parseInt转换  
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
}

function add0(m){return m<10?'0'+m:m }