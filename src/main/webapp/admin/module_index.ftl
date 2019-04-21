<#import "admin/master/lte_master.ftl" as my>
<#assign typeList = {"查询":1, "编辑":2, "排序":3, "控制":4}>
<@my.page 
    title="MODULE页面"
    css=""
    script="
	<script src='../admin/js/module_index.js'></script>
    "
>

<section class="content">
    <div class="row">
        <div class="col-xs-12">
            <!-- 一个table对应一个box-->
            <div class="box">
                <div class="box-header">
                    <!--<h3 class="box-title">模块列表</h3>-->
                    <ol class="breadcrumb">
                        <li><strong><span>（这里是页面等备注说明）</span></strong></li>
                    </ol>
                </div>
                <div class="box-body" style="width: 100%">
                    <table id="table" class="col-xs-12"></table>
                </div>
            </div>
        </div>
    </div>
</section>
      
      <div id="toolbar">
		<form id="queryForm" class="form-inline" autocomplete="off">
			<div class="form-group">
				<label class="control-label" for="name">名称：</label>
				<input type="text" class="form-control" name="name" placeholder="请输入要搜索关键词">
			</div>
			<div class="form-group">
				<label class="control-label" for="type">类型：</label> 
				<select class="form-control" id="type" name="type">
					<option value="">全部</option>
					<#list typeList?keys as key>
					<option value="${typeList[key]}">${key}</option>
					</#list>
				</select>
			</div>
			<a class="btn btn-primary" id="query" onclick="api.module.query()" data-icon="search">查询</a>
			<a class="btn btn-primary" id="addFirst" onclick="api.module.add(this)" >新增顶级菜单</a>
		</form>
	</div>
	<div id="tk"></div>
</@my.page>