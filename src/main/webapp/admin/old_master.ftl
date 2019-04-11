<#macro page css script title="SSM Maven后台">
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<base href="/">  
    <title>${title}</title>
    <!-- Bootstrap -->
    <link href="../webPlug/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="../webPlug/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <!-- iCheck -->
    <link href="../webPlug/iCheck/skins/flat/green.css" rel="stylesheet">
    <!-- Datatables -->
    <link href="../webPlug/datatables.net-bs/css/dataTables.bootstrap.min.css" rel="stylesheet">
    
	<!-- bootstrap-daterangepicker -->
    <link href="../webPlug/bootstrap-daterangepicker/daterangepicker.css" rel="stylesheet">
    <link href="../css/custom.min.css" rel="stylesheet">
    <!--弹出框样式-->
    <link href="../webPlug/layer/skin/default/layer.css" rel="stylesheet">
	${css}
  </head>

  <body class="nav-md">
    <div class="container body">
      <div class="main_container">
        <#nested>
      </div>
    </div>
    
    <!-- jQuery -->
    <script src="../webPlug/jquery/dist/jquery.min.js"></script>
    <!-- Bootstrap -->
    <script src="../webPlug/bootstrap/dist/js/bootstrap.min.js" crossorigin="anonymous"></script>
    
    <!-- FastClick -->
    <script src="../webPlug/fastclick/lib/fastclick.js"></script>
    <!-- NProgress -->
    <script src="../webPlug/nprogress/nprogress.js"></script>
    <!-- iCheck -->
    <script src="../webPlug/iCheck/icheck.min.js"></script>
    <!-- Datatables -->
    <script src="../webPlug/datatables.net/js/jquery.dataTables.min.js"></script>
    <script src="../webPlug/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
    
    <script src="../webPlug/jszip/dist/jszip.min.js"></script>
    <script src="../webPlug/pdfmake/build/pdfmake.min.js"></script>
    <script src="../webPlug/pdfmake/build/vfs_fonts.js"></script>
    <script src="../webPlug/moment/min/moment.min.js"></script>
    <script src="../webPlug/bootstrap-daterangepicker/daterangepicker.js"></script>
    <!-- Custom Theme Scripts -->
    <script src="../webPlug/layer/layer.js"></script>
    <script src="../js/custom.js"></script>
    <!-- <script src="../js/third/jsrender.js"></script> -->
    ${script}
  </body>
</html>
</#macro>