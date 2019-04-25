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

    <!-- jquery easyui -->
    <link href="../webPlug/jquery-easyui/themes/default/easyui.css" rel="stylesheet" />
    <link href="../webPlug/jquery-easyui/themes/icon.css" rel="stylesheet" />

    <!-- Bootstrap 3.3.7 -->
    <link href="./admin/dist/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet"/>

    <!-- 3.bootstrap table组件以及中文包的引用-->
    <link href="../webPlug/bootstrap-table/src/bootstrap-table.css" rel="stylesheet" />
    <!-- Font Awesome -->
    <link href="../webPlug/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <!-- Ionicons -->
    <link href="../webPlug/Ionicons/css/ionicons.min.css" rel="stylesheet">

    <!-- 自动校样 bootstrap-daterangepicker -->
    <link href="../admin/bootstrapValidator/css/bootstrapValidator.min.css" rel="stylesheet">

    <!-- 日历选择样式bootstrap-daterangepicker -->
    <link href="../webPlug/bootstrap-daterangepicker/daterangepicker.css" rel="stylesheet">

    <!-- fileinput -->
    <link rel="stylesheet" href="../webPlug/bootstrap-fileinput/css/fileinput.min.css">

    <!--唯一保留layui--弹出框样式-->
    <link href="../webPlug/layui-v2.2.6/css/layui.css" rel="stylesheet">
    <link href="../webPlug/layer/skin/default/layer.css" rel="stylesheet">

    <!-- Theme style 页面整体布局样式 -->
    <link href="../admin/dist/css/AdminLTE.min.css" rel="stylesheet">
    <!--选择一个页面 色调，可以从右上方的设置的下方选择-->
    <link href="../admin/dist/css/skins/_all-skins.min.css" rel="stylesheet">

    <!-- 表格样式 -->
    <link href="../admin/css/sub_iframe_layout.css" rel="stylesheet">
    ${css}
</head>

<body class="hold-transition skin-blue sidebar-mini">

    <#nested>

<!-- jQuery 3 -->
<script src="../admin/dist/jquery/dist/jquery.min.js"></script>

<!-- Jquery easyui -->
<script src="../webPlug/jquery-easyui/jquery.easyui.min.js"></script>
<script src="../webPlug/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>

<!-- Bootstrap 3.3.7 -->
<script src="../admin/dist/bootstrap/dist/js/bootstrap.min.js"></script>

<!-- bootstrap table组件以及中文包的引用-->
<script src="../webPlug/bootstrap-table/src/bootstrap-table.js"></script>
<script src="../webPlug/bootstrap-table/src/locale/bootstrap-table-zh-CN.js"></script>

<!-- 处理移动端click事件 300 毫秒延迟FastClick -->
<#--<script src="../../webPlug/fastclick/lib/fastclick.js"></script>-->

<#-- 自动校样 -->
<script src="../admin/bootstrapValidator/js/bootstrapValidator.min.js" crossorigin="anonymous"></script>

<!-- cuntom.js 日历相关的 -->
<script src="../webPlug/moment/min/moment.min.js"></script>
<script src="../webPlug/bootstrap-daterangepicker/daterangepicker.js"></script>

<!-- fileinput -->
<script src="../webPlug/bootstrap-fileinput/js/fileinput.min.js"></script>
<script src="../webPlug/bootstrap-fileinput/js/locales/zh.js"></script>

<script src="../webPlug/layer/layer.js"></script>

<#-- 自己定义的公共api -->
<script src='../js/common-api.js'></script>
<script src='../js/date-util-api.js'></script>

<!-- AdminLTE App -->
<script src="../admin/dist/js/adminlte.min.js"></script>

<!-- AdminLTE for demo purposes -->
<!--<script src="../admin/dist/js/demo.js"></script>-->

<#-- admin公共配置js-->
<script src='../admin/js/config.js'></script>
<script src='../admin/js/url_config.js'></script>

    ${script}

<#-- 可拖拽模态框 -->
<script src="../admin/js/dragModal.js"></script>
</body>

</html>
</#macro>