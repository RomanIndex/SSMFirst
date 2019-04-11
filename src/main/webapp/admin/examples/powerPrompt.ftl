<!DOCTYPE html>
<head lang="en">
    <meta charset="UTF-8">
	<base href="/admin/">
    <title>提示信息</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no">
    <link href="../css/prompt.css" rel="stylesheet" />
</head>
<body>
    <div class="warp">
        <div class="icon-bg"></div>
        <div class="prompt-txt1">${prompt!"抱歉，您没有权限访问该页面"}</div>
        <div class="prompt-txt2"></div>
        <div class="prompt-time" id="time"></div>
    </div>
</body>
    <script>
        var c = 5, t;
        var url = '', txt = '关闭当前页面';
        function times() {
            c = c - 1;
            document.getElementById('time').innerHTML = c + '秒后，' + txt;
            t = setTimeout("times()", 1000);
            if (c <= 0) {
                if (url != "") {
                    window.location.href = url;
                } else {
                    //WeixinJSBridge.invoke('closeWindow', {}, function (res) { });
                	window.location.href = "/login";//"/admin/login";
                }
                clearTimeout(t);
            }
        }
        times();
    </script>
</html>
