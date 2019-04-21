<#assign treeMenu = Session["menu"]>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>AdminLTE 2 | Dashboard</title>
  <base href="/">
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.7 -->
  <link rel="stylesheet" href="./admin/dist/bootstrap/dist/css/bootstrap.min.css"/>
  <!-- Font Awesome -->
  <link rel="stylesheet" href="../webPlug/font-awesome/css/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="../webPlug/Ionicons/css/ionicons.min.css">
  <!-- Theme style 页面整体布局样式 -->
  <link rel="stylesheet" href="../admin/dist/css/AdminLTE.min.css">
  <!-- AdminLTE Skins. Choose a skin from the css/skins
                    选择一个页面 色调，可以从右上方的设置的下方选择
       folder instead of downloading all of them to reduce the load. -->
  <link rel="stylesheet" href="../admin/dist/css/skins/_all-skins.min.css">

  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->

  <!-- Google Font -->
  <!-- <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic"> -->
  
  <style>
    .self-defined-class.tab-title-list{
        background-color: #BEE7E9;
    }
    .self-defined-class.tab-title-list > ul{
        /*margin: 0 0 0 15px;*/
        padding: 0;
        /*border-bottom: 1px solid rgb(226, 226, 226);*/
    }
	.self-defined-class.tab-title-list > ul > li{
	   list-style: none;
	   line-height: 40px;
	   padding: 0 0 0 8px;
       margin-right: 4px;
	   color: #A9A9A9;
	   -webkit-transition: none;
	   display: inline-block; /*保证都在一行*/
	   vertical-align: middle;
	   font-size: 14px;
	   min-width: 65px;
	   cursor: pointer;
	   text-align: center;
       background-color: beige;
	}
    .self-defined-class.tab-title-list > ul > li > span{
        margin-right: 12px;
    }
    .self-defined-class.tab-title-list > ul > li > i{
        width: 24px;
    }
	.self-defined-class .tab_close::before{
	   font-size: 18px;/* 控制 X 大小的关键*/
	}

	.self-defined-class > ul > li.tab-title-show:after {
	    position: absolute;
	    left: 0;
	    top: 0;
	    content: none;
	    width: 100%;
	    height: 41px;
	    border: 1px solid rgb(226, 226, 226);
	    border-bottom-color: rgb(255, 255, 255);
	    border-radius: 2px 2px 0 0;
	    box-sizing: border-box!important;
	    pointer-events: none;
    }
    .self-defined-class .tab-content-item{display: none;}
    .self-defined-class .tab-content-show{display: block!important;}
    .self-defined-class .tab-title-show{color: #875eff !important;}

    /* --------------表格内容模块------------------ */
    .self-defined-class.tab-content{
        position: relative;/* 很重要 */
        display: block;
        height: -webkit-fill-available;;
        /* 如果不加 绝对定位，表格右侧会超出视距，效果可以吗？*/
    }

	.self-defined-class iframe {
	    border: 0;
	    width: 100%;
	    height: 100%;/* 最好由JS计算得出来*/
	    position: absolute;/* 也比较重要 */
	}
      .breadcrumb{
          margin: 0px;
      }
  </style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

  <#include "/admin/master/top_header.ftl">

  <!-- Left side column. contains the logo and sidebar -->
  <#include "/admin/master/left_menu.ftl">

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">

	<!-- 最上方，打开页面的Tab -->
    
    <!-- 第二部分，页面指引，暂时是写死的 -->
	<section class="content-header" style="padding: 0px;">
      <#--<h4>《《页面指引》》</h4>-->
      <div class="self-defined-class tab-title-list">
          <ul id="tab-title">
              <li class="tab-title-show" data-url="/welcome">
                  <i><img src="images/home.png" style="margin-right: 8px; width: 26px; position: relative; top: -2px;"></i>
              </li>
          </ul>
      </div>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i>Home</a></li>
        <li><a href="#">Examples</a></li>
        <li class="active">404 error</li>
      </ol>
    </section>
    
	<!-- 内容部分 start -->

    <div id="tab-content" class="self-defined-class tab-content">
      <!-- 默认一直存在的 首页，即一张封面图 -->
      <div class="tab-content-item tab-content-show"><iframe src='/welcome'></iframe></div>
	  <!--每个打开的子页面 对应 一个子iframe-->
    </div>

	<!-- 内容部分 end -->

  </div>
  <!-- /.content-wrapper -->

  <footer class="main-footer">
    <div class="pull-right hidden-xs">
      <b>Version</b> 2.4.0
    </div>
    <strong>Copyright &copy; 2014-2016 <a href="https://adminlte.io">Almsaeed Studio</a>.</strong>
    <span>All rights reserved.</span>
  </footer>

  <!-- 右上方的页面布局、样式设置Control Sidebar -->
  <#include "/admin/master/hide_setting.ftl">
  <!-- /.control-sidebar -->
  <!-- Add the sidebar's background. This div must be placed immediately after the control sidebar -->
  <div class="control-sidebar-bg"></div>

</div>
<!-- ./wrapper -->

<!-- jQuery 3 -->
<script src="../admin/dist/jquery/dist/jquery.min.js"></script>
<!-- Bootstrap 3.3.7 -->
<script src="../admin/dist/bootstrap/dist/js/bootstrap.min.js"></script>
<!-- 处理移动端click事件 300 毫秒延迟FastClick -->
<script src="../webPlug/fastclick/lib/fastclick.js"></script>
<!-- AdminLTE App -->
<script src="../admin/dist/js/adminlte.min.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="../admin/dist/js/demo.js"></script>
<script src="../webPlug/layer/layer.js"></script>
<script src="../admin/js/index.js"></script>
</body>
</html>