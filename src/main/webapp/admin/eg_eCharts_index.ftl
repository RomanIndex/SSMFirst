<!DOCTYPE html>
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <base href="/">
    <title>EChart用例</title>
    <!-- Bootstrap -->
    <link href="../webPlug/bootstrap-v3.3.5/dist/css/bootstrap.min.css" rel="stylesheet" />
    
    <!-- Font Awesome：字体、极好的 （象形文字语言）就是图标-->
    <link href="../webPlug/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <!-- iCheck 表单美化插件 -->
    <link href="../webPlug/iCheck/skins/flat/green.css" rel="stylesheet">
    
    <!-- Datatables -->
    <link href="../webPlug/datatables.net-bs/css/dataTables.bootstrap.min.css" rel="stylesheet">
    
    <!-- bootstrap-daterangepicker -->
    <link href="../admin/bootstrapValidator/css/bootstrapValidator.min.css" rel="stylesheet"><!-- 自动校样 -->
    
    <!-- 日历选择样式 -->
    <link href="../webPlug/bootstrap-daterangepicker/daterangepicker.css" rel="stylesheet">
    
    <!-- fileinput -->
    <link rel="stylesheet" href="../webPlug/bootstrap-fileinput/css/fileinput.min.css">
    
    <!--唯一保留layui--弹出框样式-->
    <link href="../webPlug/layui-v2.2.6/css/layui.css" rel="stylesheet">
    <link href="../webPlug/layer/skin/default/layer.css" rel="stylesheet">
    
    <!-- 页面基本布局样式 -->
    <link href="../css/sub_iframe_layout.css" rel="stylesheet">
    
    <!-- <link href="../css/custom.min.css" rel="stylesheet"> --><!-- 也是外部导的css文件 -->
    <!-- ${css} css -->
    
    <style>
        tr td{text-align:center;}
    </style>
  </head>

  <body class="nav-md">

    <div class="container body">
        <div class="main_container">
            
            <!-- nested begin -->
            <div id="chartmain" style="width:600px; height: 400px;"></div>
            <!-- nested end!! -->
            
        </div>
    </div>

    <div id="tk"></div>
      
  </body>
    <!-- jQuery -->
    <script src="../webPlug/jquery/jquery-2.2.4.min.js"></script>
    <!-- Bootstrap -->
    <script src="../webPlug/bootstrap-v3.3.5/dist/js/bootstrap.min.js"></script>
    
    <!-- Datatables -->
    <script src="../webPlug/datatables.net/js/jquery.dataTables.min.js"></script>
    <script src="../webPlug/datatables.net-bs/js/dataTables.bootstrap.min.js"></script><!-- table foot初始化（table分页） -->
    
    <script src="../admin/bootstrapValidator/js/bootstrapValidator.min.js" crossorigin="anonymous"></script>
    
    <!-- custom.js 里面的方法依赖的两个js -->
    <script src="../webPlug/moment/min/moment.min.js"></script>
    <script src="../webPlug/bootstrap-daterangepicker/daterangepicker.js"></script>
    
    <!-- fileinput -->
    <script src="../webPlug/bootstrap-fileinput/js/fileinput.min.js"></script>
    <script src="../webPlug/bootstrap-fileinput/js/locales/zh.js"></script>
    
    <script src="../webPlug/layer/layer.js"></script>
    
    <!-- <script src="../js/third/jsrender.js"></script> -->
    
    <!-- Custom Theme Scripts 内含：两个初始化日期，一个datatable的方法-->
    <script src="../js/custom.js"></script><!--  -->
    
    <script src="../admin/ECharts/SourceCode.js"></script>
    
    <!-- ${script} js -->
    <script src='../js/util/date.js'></script><!-- 一些公共处理，如日期类的JS，推荐引入 -->
    <script src='../admin/js/account_index.js'></script>
    <script src="../admin/js/fileInput.js"></script>
    <script src="../admin/js/dragModal.js"></script>
    <!-- <script src="../admin/js/eg_echart_cpx.js"></script> --><!-- 可动态拖拽 -->
    <script src="../admin/js/eg_echart.js"></script> <!-- 最简单的引用-->
</html>