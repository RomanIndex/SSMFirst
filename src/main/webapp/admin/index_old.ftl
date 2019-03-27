<#assign treeMenu = Session["menu"]>
<#assign styleList = ["&#xe631;", "&#xe637;", "&#xe62d;", "&#xe62c;", "&#xe630;", "&#xe63c;", "&#xe639;"]>
<!DOCTYPE html>
<html>
	<head>
	  <base href="/">
	  <meta charset="utf-8">
	  <title>SSM管理平台index页面</title>
	  <meta name="renderer" content="webkit">
	  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	  <LINK REL="SHORTCUT ICON" HREF="/images/favicon.ico">
	  <link href="../webPlug/bootstrap-v3.3.5/dist/css/bootstrap.min.css" rel="stylesheet">
	  <link rel="stylesheet" href="css/layui.css"  media="all">
	  <link rel="stylesheet" href="css/home.css"/>
	  <link rel="stylesheet" href="css/loaders.css"/>
	  <link rel="stylesheet" href="css/change.css"/>
	  <link rel='stylesheet' href='css/jquery-ui.css'/>
	  </head>
	<body>
		<div class="layui-layout layui-layout-admin show-full">
			<!-- header start -->
			<div class="layui-header header ">
				<div class="layui-main">
			    <div class="navbar nav_title" style="border: 0;">
			    	<a class="site_title">
			    		<img src="images/admin_logo.png">
			    		<span class="title">SSM</span>
			    	</a>
			    	<a class="icon"></a>
			    </div>
			    <!-- 搜索框 -->
			    <div class="layui-search">
			      	<i class="layui-icon layui-search-icon">&#xe615;</i>
			      	<input id='search' type="text" placeholder="">
			    </div>
		    
		    
		    	<ul class="layui-nav">
					  <li class="layui-nav-item layui-nav-item1">
					    <a href="javascript:;">
					    	<img src="images/person.png" class="personimg"/>
					    	<i class="layui-icon icon-person">&#xe612;</i>
					    	<span class="user" id="userName">酉卒粑粑</span>
					    	<i class="layui-icon ">&#xe61a;</i>
					    </a>
					    <dl class="layui-nav-child">
					      <dd><a href="">编号：<span>EF-00123</span></a></dd>
					      <dd><a href="">当前角色：<span>${empInfo.jobName ! "无"}</span></a></dd>
					      <dd id="modifyPsd"><a onclick="javascript:openNewTab('修改密码','/user/edit?source=index&employeeId=${account.employeeId}')"><span>修改密码</span></a></dd>
					      <dd class="quit"><a href="/logout"><img src="images/back.png"/>退出</a></dd>
					    </dl>
					  </li>
					</ul>
			  </div>
		  </div>
		  <!--  header end  -->
		  
		  <!-- siderBar start-->
			<div class="layui-side layui-bg-black" >
				<div class="layui-side-scroll">   
					<ul class="layui-nav layui-nav-tree">
					 <#list treeMenu as menu>
					  <li class="layui-nav-item">
					    <a class="javascript:;" href="javascript:;">
						    <i class="layui-icon data-icon">${getStyle(menu_index)}</i>
						    <span>${menu_index + 1}、${menu.name}</span>
						    <i class="layui-icon icon-right">&#xe602;</i>
						    <i class="layui-icon icon-down">&#xe61a;</i>
					    </a>
					    
					    <dl class="layui-nav-child">
					      <#list menu.childMenus as menuitem>
					      <dd class="">
					        <a data-type="tabAdd" title="${menuitem.name}" data-title="${menuitem.name}" data-url="${menuitem.url}">${menuitem.name}</a>
					      </dd>
					      </#list>
					    </dl>
					  </li>
					 </#list>
					</ul>
					
				    <!-- 递归  宏定义 -->
					<#macro bpTree list>
					  <#if list?? && list?size gt 0>
					 <#list list as child>
					  <#if child.childMenus?? && child.childMenus?size gt 0>
					  <li>
					    <a href="javascript:void(0)">
					      <i class="${(child.icon=='')?string('fa fa-share',child.icon) }" aria-hidden="true"></i> <span>${child.name}</span>
					      <i class="fa fa-angle-left pull-right" aria-hidden="true"></i>
					    </a>
					    
					    <ul class="dropdown-menu">
					      <@bpTree list = child.childMenus />
					    </ul>
					  </li>
					  <#else>
					      <li>
					      <a href="javascript:void(0)" onclick="loadContent('${child.url}')">
					      <i class="${ (child.icon=='')?string('fa fa-circle-o',child.icon) }" aria-hidden="true"></i> 
					      ${child.name}
					      </a>
					      </li>   
					    </#if>
					   </#list>
					</#if>
					</#macro>
					
					<ul class="layui-nav layui-nav-tree dropdown">
				    <!-- 调用宏 生成递归树 -->
				    <@bpTree list = treeMenu />
				    </ul>
					
					<ul class="layui-nav layui-nav-tree">
                      <li class="layui-nav-item">
                        <a class="javascript:;" href="javascript:;">
                            <i class="layui-icon data-icon">&#xe639;</i>测试菜单
                            <i class="layui-icon icon-right">&#xe602;</i>
                            <i class="layui-icon icon-down">&#xe61a;</i>
                        </a>
                        
                        <dl class="layui-nav-child">
                          <dd class="">
                            <a data-type="tabAdd" title="" data-title="新炫酷首页" data-url="/freeView/xuanku_index.html">新炫酷首页</a>
                          </dd>
                          <dd class="">
                            <a data-type="tabAdd" title="" data-title="用户管理" data-url="/admin/account/index/noMaster">用户管理（noMaster）</a>
                          </dd>
                          <dd class="">
                            <a data-type="tabAdd" title="" data-title="行内编辑" data-url="/admin/account/edit/inline">行内编辑例子</a>
                          </dd>
                          <dd class="">
                            <a data-type="tabAdd" title="" data-title="bt_table Demo" data-url="/freeView/bt_table_demo.html">bt表格Demo</a>
                          </dd>
                          <dd class="">
                            <a data-type="tabAdd" title="" data-title="bootstrap表格" data-url="/admin/account/bt">bootstrap简单表格（404）</a>
                          </dd>
                          <dd class="">
                            <a data-type="tabAdd" title="" data-title="ECharts示例" data-url="/admin/eCharts/index">ECharts示例（404）</a>
                          </dd>
                          <dd>
                            <a data-type="tabAdd" title="" data-title="左侧炫酷菜单" data-url="/admin/zuoce_master/index">左侧炫酷菜单</a>
                          </dd>
                        </dl>
                      </li>
                    </ul>
				</div>
			</div>
			<!-- siderBar end !-->
			
			<!-- ftl 创建集合： -->
			<#function getStyle index>
		        <#list styleList as st>
		        <#if st_index == index>
				    <#return st>
				</#if>
		        </#list>
		    </#function>
		    
			<!-- Body start -->
			<div class="layui-body  site-text">
				<div id="tab-list" class="layui-tab" lay-filter="tab-list" lay-allowclose="true">
					<ul class="layui-tab-title">
					  <li  class="layui-this" lay-id="/welcome" ><i><img src="images/home.png" style="padding-right: 5px; width: 28px;position: relative;top: -2px;" ></i></li>
					</ul>
					<div id="tab-content" class="layui-tab-content" style="position: absolute;">
					  <div class="layui-tab-item layui-show"><iframe src='/welcome'></iframe></div>
					</div>
				</div>
			</div>
			<!-- body end -->
			
			<!-- fixbar start -->
			<ul class="layui-fixbar">
				<li class="layui-icon full-screen" lay-type="bar1" ><img src="images/fs.png"></li>
			</ul>
			<!-- fixbar end -->
		</div>
		<script src="../webPlug/jquery/jquery-2.2.4.min.js"></script>
		<script src="../webPlug/bootstrap-v3.3.5/dist/js/bootstrap.min.js" crossorigin="anonymous"></script>
		<script src="../webPlug/layer/layui.js"></script>
		<script src='../js/jquery-ui.js'></script>
		<script src='js/home.js'></script>
	</body>
</html>