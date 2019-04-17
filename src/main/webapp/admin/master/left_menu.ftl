<aside class="main-sidebar">
   <!-- sidebar: style can be found in sidebar.less -->
   <section class="sidebar">
     <!-- Sidebar user panel -->
     <div class="user-panel">
       <div class="pull-left image">
         <img src="../admin/dist/img/user2-160x160.jpg" class="img-circle" alt="User Image">
       </div>
       <div class="pull-left info">
         <p>Alexander Pierce</p>
         <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
       </div>
     </div>
     <!-- search form -->
     <form action="#" method="get" class="sidebar-form">
       <div class="input-group">
         <input type="text" name="q" class="form-control" placeholder="Search...">
         <span class="input-group-btn">
               <button type="submit" name="search" id="search-btn" class="btn btn-flat">
                 <i class="fa fa-search"></i>
               </button>
             </span>
       </div>
     </form>
     <!-- /.search form -->
     <!-- sidebar menu: : style can be found in sidebar.less -->
     <ul class="sidebar-menu" data-widget="tree">
       <li class="header">左侧菜单MAIN NAVIGATION</li>
       <#-- 系统模板自带两个首页样式-->
       <li class="treeview">
         <a href="#">
           <i class="fa fa-dashboard"></i> <span>Dashboard</span>
           <span class="pull-right-container">
             <i class="fa fa-angle-left pull-right"></i>
           </span>
         </a>
         <ul class="treeview-menu">
           <li><a href="index.html"><i class="fa fa-circle-o"></i> Dashboard v1</a></li>
           <li class="active"><a href="index2.html"><i class="fa fa-circle-o"></i> Dashboard v2</a></li>
         </ul>
       </li>
       <#-- 系统初始化配置-->
       <li class="treeview">
           <a href="#">
               <i class="fa fa-dashboard"></i> <span>系统初始化配置</span>
               <span class="pull-right-container">
               <i class="fa fa-angle-left pull-right"></i>
         </span>
           </a>
           <ul class="treeview-menu">
               <li><a href="../ssm_import.ftl"><i class="fa fa-circle-o"></i>基础数据导入</a></li>
           </ul>
       </li>

         <!-- 调用宏 生成递归树 -->
       <@bpTree list = treeMenu />
         <!-- 宏生成菜单结束 -->

       <!-- 测试菜单 -->
       <li class="treeview menu-open">
         <a href="#">
           <i class="fa fa-files-o"></i>
           <span>测试菜单</span>
           <span class="pull-right-container">
             <span class="label label-primary pull-right">6</span>
           </span>
         </a>
         <ul class="treeview-menu">
           <li><a data-type="tabAdd" href="/admin/examples/404.html"><i class="fa fa-circle-o"></i>不经过后台404页面</a></li>
             <li><a data-type="tabAdd" data-url="/admin/route/account_index">SSM用户管理（--）</a></li>
             <li><a data-type="tabAdd" data-url="/admin/route/role_index">SSM角色管理（--）</a></li>
             <li><a data-type="tabAdd" data-url="/admin/route/privilege_index">SSM权限管理（--）</a></li>
             <li><a data-type="tabAdd" data-url="/admin/route/module_index">SSM模块管理（--）</a></li>
           <li><a data-type="tabAdd" data-url="/admin/route/bootstrap_index">bootstrap_index（no master基于account）</a></li>
             <li><a data-type="tabAdd" data-url="/admin/route/bootstrap_edit">bootstrap_edit</a></li>
           <li><a data-type="tabAdd" data-url="/admin/route/echart_demo">ECharts示例</a></li>
             <li><a data-type="tabAdd" data-url="/admin/route/collect_notepad">简易编辑器</a></li>
             <li><a data-type="tabAdd" data-url="/admin/route/resubmit_form">表单重复提交</a></li>
             <li><a data-type="tabAdd" data-url="/admin/route/ssm_import">SSM基础数据导入</a></li>
             <li><a data-type="tabAdd" data-url="/freeView/bt_table_demo.html">bt表格Demo（freeView）</a></li>
             <li><a data-type="tabAdd" data-url="/freeView/cron.html">cron tab页面（freeView）</a></li>
         </ul>
       </li>
       <li>
         <a href="pages/calendar.html">
           <i class="fa fa-calendar"></i> <span>Calendar</span>
           <span class="pull-right-container">
             <small class="label pull-right bg-red">3</small>
             <small class="label pull-right bg-blue">17</small>
           </span>
         </a>
       </li>
       <li class="treeview">
         <a href="#">
           <i class="fa fa-folder"></i> <span>Examples</span>
           <span class="pull-right-container">
             <i class="fa fa-angle-left pull-right"></i>
           </span>
         </a>
         <ul class="treeview-menu">
           <li><a href="pages/examples/invoice.html"><i class="fa fa-circle-o"></i> Invoice</a></li>
           <li><a href="pages/examples/profile.html"><i class="fa fa-circle-o"></i> Profile</a></li>
           <li><a href="pages/examples/login.html"><i class="fa fa-circle-o"></i> Login</a></li>
           <li><a href="pages/examples/register.html"><i class="fa fa-circle-o"></i> Register</a></li>
           <li><a href="pages/examples/lockscreen.html"><i class="fa fa-circle-o"></i> Lockscreen</a></li>
           <li><a href="pages/examples/404.html"><i class="fa fa-circle-o"></i> 404 Error</a></li>
           <li><a href="pages/examples/500.html"><i class="fa fa-circle-o"></i> 500 Error</a></li>
           <li><a href="pages/examples/blank.html"><i class="fa fa-circle-o"></i> Blank Page</a></li>
           <li><a href="pages/examples/pace.html"><i class="fa fa-circle-o"></i> Pace Page</a></li>
         </ul>
       </li>
       
       <!-- 多级菜单示例 -->
       <li class="treeview">
         <a href="#">
           <i class="fa fa-share" 左侧图标></i>
           <span 中间文字>顶级菜单Multilevel</span>
           <span class="pull-right-container" 右侧指示>
             <i class="fa fa-angle-left pull-right"></i>
           </span>
         </a>
         <ul class="treeview-menu">
           <li><a href="#"><i class="fa fa-circle-o"></i><span>一级菜单（无子分级）</span></a></li>
           <li class="treeview">
             <a href="#">
                <i class="fa fa-circle-o"></i>
                <span>一级菜单（有子分级）</span>
	            <span class="pull-right-container">
	              <i class="fa fa-angle-left pull-right"></i>
	            </span>
             </a>
             <ul class="treeview-menu">
               <li><a href="#"><i class="fa fa-circle-o"></i><span>二级菜单（无子分级）</span></a></li>
               <li class="treeview">
                 <a href="#">
                    <i class="fa fa-circle-o"></i>
                    <span>二级菜单（有子分级）</span>
	                <span class="pull-right-container">
	                  <i class="fa fa-angle-left pull-right"></i>
	                </span>
                 </a>
                 <ul class="treeview-menu">
                   <li><a href="#"><i class="fa fa-circle-o"></i><span>三级菜单（无子分级）</span></a></li>
                   <li><a href="#"><i class="fa fa-circle-o"></i><span>三级菜单（无子分级）</span></a></li>
                 </ul>
               </li>
             </ul>
           </li>
           <li><a href="#"><i class="fa fa-circle-o"></i><span>一级菜单（无子分级）</span></a></li>
         </ul>
       </li>

     <#--<li class="active treeview menu-open"> 控制左侧菜单 -->
       
       <!-- 递归  宏定义 -->
       <#macro bpTree list>
	       <#if list?? && list?size gt 0>
		   <#list list as child>
		      <#if child.childMenus?? && child.childMenus?size gt 0>
	          <li class="treeview">
	            <a data-url="javascript:void(0)">
	               <i class="${(child.icon=='')?string('fa fa-share',child.icon) }"></i>
	               <span>${child.name}</span>
	               <span class="pull-right-container">
	                 <i class="fa fa-angle-left pull-right"></i>
	               </span>
	            </a>
	            
	            <ul class="treeview-menu">
	              <@bpTree list = child.childMenus />
	            </ul>
	          </li>
	          <#else>
	              <li><a data-url="${child.url}"><i class="${ (child.icon=='')?string('fa fa-circle-o',child.icon) }"></i><span>${child.name}</span></a></li>
	              <!-- <a href="javascript:void(0)" onclick="loadContent('${child.url}')"></a> -->
	          </#if>
		   </#list>
		   </#if>
       </#macro>
       <!-- ***********宏定义结束************ -->
       
       <li><a href="https://adminlte.io/docs"><i class="fa fa-book"></i> <span>Documentation</span></a></li>
       <li class="header">LABELS</li>
       <li><a href="#"><i class="fa fa-circle-o text-red"></i> <span>Important</span></a></li>
       <li><a href="#"><i class="fa fa-circle-o text-yellow"></i> <span>Warning</span></a></li>
       <li><a href="#"><i class="fa fa-circle-o text-aqua"></i> <span>Information</span></a></li>
     </ul>
   </section>
   <!-- /.sidebar -->
</aside>