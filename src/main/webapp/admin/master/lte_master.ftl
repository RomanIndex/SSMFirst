<#macro page css script title="SSM Maven后台">
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>${title}</title>
    <base href="/">
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.7 -->
    <link rel="stylesheet" href="./admin/dist/bootstrap/dist/css/bootstrap.min.css"/>
    <!-- Font Awesome -->
    <link rel="stylesheet" href="../../webPlug/font-awesome/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="../../webPlug/Ionicons/css/ionicons.min.css">
    <!-- Theme style 页面整体布局样式 -->
    <link rel="stylesheet" href="../dist/css/AdminLTE.min.css">
    <!--选择一个页面 色调，可以从右上方的设置的下方选择-->
    <link rel="stylesheet" href="../dist/css/skins/_all-skins.min.css">
    ${css}
</head>

<body class="hold-transition skin-blue sidebar-mini">

    <#nested>

<!-- jQuery 3 -->
<script src="../dist/jquery/dist/jquery.min.js"></script>
<!-- Bootstrap 3.3.7 -->
<script src="../dist/bootstrap/dist/js/bootstrap.min.js"></script>
<!-- 处理移动端click事件 300 毫秒延迟FastClick -->
<script src="../../webPlug/fastclick/lib/fastclick.js"></script>
<!-- AdminLTE App -->
<script src="../dist/js/adminlte.min.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="../dist/js/demo.js"></script>
<script src="../../webPlug/layer/layer.js"></script>
<#-- 自己定义的公共api -->
<script src='../js/common-api.js'></script>
<script src='../js/date-util-api.js'></script>
<#-- admin公共配置js-->
<script src='../admin/js/config.js'></script>
<#--<script src='../admin/js/api_config.js'></script>-->
    ${script}
</body>

</html>
</#macro>