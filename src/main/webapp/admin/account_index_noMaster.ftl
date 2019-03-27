<!DOCTYPE html>
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <base href="/">
    <title>（全jar包）--ftl通用Demo</title>
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
        a,a:visited{color:#3366cc;text-decoration:none;}
        a:hover{color:#f60;text-decoration:underline;}
        a{cursor: pointer;}
        .tip{width:200px;border:2px solid #ddd;padding:8px;background:#f1f1f1;color:#666;}
    </style>
  </head>

  <body class="nav-md">

	<div class="container body">
		<div class="main_container">
			
			<!-- nested begin -->
			<div class="right_col" role="main">
				<div class="row">
					<div class="col-md-12 col-sm-12 col-xs-12">
						<div class="x_panel">
							<div class="x_content">
								<table id="ssmtable" class="table table-bordered dt-responsive table-hover" cellspacing="0" width="100%">
									<tfoot class="my_tfoot"></tfoot>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- nested end!! -->
			
		</div>
	</div>

	<div id="tk"></div>
	<div id="mjs:tip" class="tip" style="position:absolute;left:0;top:0;display:none;"></div>
    <#include "/admin/bt_fileInput.html">
      
      <div id="filter_div">
        <form id="fform" class="form-inline">
            <a class="btn btn-default" id="export_member"><span>导出</span></a>
            
            <div class="form-group">
              <label class="control-label" for="name">keyWord：</label>
              <input type="text" class="form-control" id="name" name="name" placeholder="请输入要搜索关键词">
            </div>
            
            <div class="form-group">
              <label class="control-label" for="name">账号：</label>
                <select id="name" name="name" class="form-control">
		            <option value="">所有</option>
		             <#list list as act>
		             <option value=${act.id}>${act.role}</option>
		             </#list>
		        </select>
            </div>
            
            <!-- Font Awesome 完美兼容bootstrap -->
            <div class="btn-group">
			  <a class="btn btn-default" href="#">
			    <i class="fa fa-align-left" title="左对齐"></i>
			  </a>
			  <a class="btn btn-default" href="#">
			    <i class="fa fa-align-center" title="剧中对齐"></i>
			  </a>
			  <a class="btn btn-default" href="#">
                <i class="fa fa-cog fa-spin fa-lg fa-fw" title="just a test"></i>
              </a>
			  <a class="btn btn-default" href="#">
			    <i class="fa fa-align-right" title="右对齐"></i>
			  </a>
			  <a class="btn btn-default" href="#">
			    <i class="fa fa-align-justify" title="平均分布"></i>
			  </a>
			</div>
            
            <div class="form-group">
              <label class="control-label" for="name">时间：</label>
                <div id="reportrange" class="form-control" style="background: #fff; cursor: pointer; padding: 5px 10px; border: 1px solid #ccc">
	              <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
	              <span></span><b class="caret"></b>
	            </div>
            </div>
            
            <a class="btn btn-default" onclick="accountApi.query()" id="query"><span><i class="fa fa-camera-retro fa-lg"></i>查询</span></a>
            
            <a class="btn btn-default" onclick="getTKModal()"><span>（测mapper+外html弹窗）</span></a>
            
            <!-- 有引入单独js，id需保留 -->
            <a class="btn btn-default" id="addModel"><span>上传图片tkModal</span></a>
            
            <a class="btn btn-default" onclick="submitForm()"><span>表单重复提交</span></a>
            
        </form>
      </div>
      
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
    
    <!-- ${script} js -->
    <script src='../js/util/date.js'></script><!-- 一些公共处理，如日期类的JS，推荐引入 -->
    <script src='../admin/js/account_index.js'></script>
    <script src="../admin/js/fileInput.js"></script>
    <script src="../admin/js/common.js"></script>
    <script src="../admin/js/common_time_handle.js"></script>
    <script src="../admin/js/dragModal.js"></script>
</html>