<#import "admin/master/lte_master.ftl" as my>
<#assign typeList = {"查询":1, "编辑":2, "排序":3, "控制":4}>
<@my.page 
    title="MODULE页面"
    css="
    <style>
        body{background-color:white;font-size: 14px;}
        tr th, tr td{text-align:center;}
    </style>
    "
    script="
	<script src='/system/js/ssm_common.js'></script>
	<script src='/system/js/module_index.js'></script>
    ">
    <div class="right_col" role="main">
        <div class="row">
          <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
              <div class="x_content">
                <h3>模块列表</h3>
				<ol class="breadcrumb">
					<li><strong><span style="color: #27a0d7">。。。</span></strong></li>
				</ol>
                <div style="width: 100%">
					<table id="table" class="col-xs-12"></table>
				</div>
              </div>
            </div>
          </div>
        </div>
      </div>
      
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
			<a class="btn btn-primary" id="query" data-icon="search">查询</a>
			<a class="btn btn-primary" id="addFirst" >新增一级模块</a>
		</form>
	</div>
	<div id="tk"></div>
</@my.page>