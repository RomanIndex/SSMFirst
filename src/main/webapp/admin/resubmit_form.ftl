<#import "admin/lte_master.ftl" as my> 
<@my.page title="医患咨询报表" 
	css="
		<link href='../webPlug/layui-v2.2.6/css/layui.css' rel='stylesheet'>
	    <link href='../webPlug/layer/skin/default/layer.css' rel='stylesheet'>
	"
	script="
	   <script src='../admin/js/account_form.js'></script>
	">
	<section class="content">
		<div class="error-page">
	
			<div class="error-content">
				<h3>
					<i class="fa fa-warning text-yellow"></i> Oops! Page not found.
				</h3>
	
				<p>
					We could not find the page you were looking for. Meanwhile, you may
					<a href="../../index.html">return to dashboard</a> or try using the
					search form.
				</p>
	
				<form class="search-form form-horizontal" onsubmit="return false">
					<div class="form-group">
						<label for="name" class="col-sm-2 control-label">姓名：</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" name="name" value="${object.name}" placeholder="名字">
						</div>
					</div>
	
					<div class="form-group">
						<label for="name" class="col-sm-2 control-label" style="white-space: nowrap;">隐藏的token：</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" name="name" value="${token}" readonly="readonly">
							<input type="hidden" name="token" value="${token}">
						</div>
					</div>
					
					<a class="btn btn-warning" onclick="submit()">a标签可以<i class="fa fa-search"></i></a>
					
					<button class="btn" onclick="submit()">button绑定onclick有问题</button>
	
					<div class="input-group">
						<input type="text" name="search" class="form-control" value="404.ftl页面" placeholder="Search">
						<div class="input-group-btn">
						<a class="btn btn-warning" onclick="submit()"><i class="fa fa-search"></i></a>
						</div>
					</div>
				</form>
				
				<button class="btn" onclick="submit()">button在form外面也可以</button>
			</div>
		</div>
	</section>

</@my.page>