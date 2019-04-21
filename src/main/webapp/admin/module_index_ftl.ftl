<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <base href="/">
  <title>模块管理</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <link href="../webPlug/bootstrap-v3.3.5/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="../webPlug/datatables.net-bs/css/dataTables.bootstrap.css" rel="stylesheet">
  <#--<link href="../admin/css/custom.min.css" rel="stylesheet">-->
  <link rel="stylesheet" href="../admin/css/table_classify.css"  media="all">
  <style>
  </style>
</head>

<body>
    <div class="container body">
      <div class="main_container">
        <!-- begin -->
        
        <!-- page content -->
	    <div class="right_col" style="background-color:white" role="main">
	        <div class="row">
	          <div class="col-md-12 col-sm-12 col-xs-12">
	            <div class="x_panel">
	             <fieldset class="platform_title">
	                 <legend>
	                     <a>静态模块页面</a>
	                 </legend>
	             </fieldset>
	             
	             <#assign colspan = 3 ><!-- 几列换行 -->
	             <#assign zhanbi = (100/colspan)?string('#,##') ><!-- 占行百分比 -->
	             <#assign subClassSize = 0><!-- 循环当前 类 的 子类 数目 -->
	             <#assign maxRow = 0><!-- 最多需要的行 -->
	             <#assign needNum = 0><!-- 需要补几个，可以和colspan凑整 -->
	                <div class="x_content" id="accordion">
	                
	                <table id="datatable" class="table table-striped table-bordered dt-responsive nowrap order-column table_css" cellspacing="0" width="100%">
	                  <thead>
	                    <tr>
	                      <th colspan=${colspan}>
	                          <a class="btn" name="add"><span class="btn-sm glyphicon glyphicon-plus" aria-hidden="true"></span>
	                          <span>添加一级分类</span></a>
	                      </th>
	                    </tr>
	                  </thead>
	                </table>
	                
	                  <#list topMenus as mix>
	                  
	                  <#assign subClassSize = mix.childMenus?size + 1><!-- 计算当前 td 数目，子类数目+1“添加” -->
	                  <#assign maxRow = ((subClassSize - 1)/colspan)?floor + 1>
	                  <#assign needNum = (subClassSize%colspan == 0) ?string(0, colspan - subClassSize%colspan)><!-- needNum这个字很关键 -->
	                  <table id="datatable" class="table table-striped table-bordered dt-responsive nowrap order-column table_css" cellspacing="0" width="100%">
	                    <thead>
	                      <tr data-toggle="collapse" data-target="#${mix.menuId}" data-parent="#accordion" data-first-classify-id=${mix.menuId} >
	                        <th colspan=${colspan} subClassSize：${subClassSize}；maxRow：${maxRow}；needNum：${needNum} zhanbi:${zhanbi}>
	                        <span style="font-size: 18px;color: rgb(127, 85, 165);">${mix.name}</span>
	                        <a class="btn" name="exchange" disabled="disabled"><span class="btn-sm glyphicon glyphicon-refresh" aria-hidden="true"></span></a>
	                        <div style="float: right;">
	                            <a class="btn" name="update" data-type="first"><span class="btn-sm glyphicon glyphicon-pencil" aria-hidden="true">编辑</span></a>
	                            <a class="btn" name="deleteParent"><span class="btn-sm glyphicon glyphicon-trash" aria-hidden="true">删除</span></a>
	                        </div>
	                        </th>
	                      </tr>
	                    </thead>
	                    <tbody id="${mix.menuId}" class="collapse ${(mix_index + 1 == 1) ? string('in','')}">
	                      
	                      <#list 1..subClassSize + (needNum?eval) as num>
	                        <!-- 如果 子集合[index]个存在，显示出来，是否是最后一个，多余的全部补空td对 -->
	                        <#if num % colspan == 1><tr></#if>
	                          
	                          <#if num &lt; subClassSize>
	                          
	                              <#list mix.childMenus as ms>
	                              
	                              <#if ms_index == num - 1>
	                              <td class='dt-left' style="width: ${zhanbi}%">
	                                <input class="menuItem" type="checkbox" id="${ms.menuId}" name="menuItem">
	                                <label for="${ms.menuId}" data-seq=${ms_index + 1}><span>${ms.name}</span></label>
	                                <div style="float: right;">
	                                    <a class="btn" data-type="second" name="update"><span class="btn-sm glyphicon glyphicon-pencil" aria-hidden="true"></span></a>
	                                    <a class="btn" name="delete"><span class="btn-sm glyphicon glyphicon-trash" aria-hidden="true"></span></a>
	                                </div>
	                              </td>
	                              </#if>
	                              
	                              </#list>
	                          
	                          <#elseif subClassSize == num>
	                          <td class='dt-left' style="width: ${zhanbi}%">
	                              <a class="btn" name="add"><span class="btn-sm glyphicon glyphicon-plus" aria-hidden="true"></span>
	                              <span>新增二级分类</span></a>
	                          </td>
	                          <#else><td></td>
	                          </#if>
	                          
	                         <#if num % colspan == 0></tr></#if> 
	                      </#list>
	                    </tbody>
	                  </table>
	                </#list>
	                </div>
	            </div>
	          </div>
	        </div>
	    </div>
	    <!-- /page content -->
        
        <!-- end！！ -->
      </div>
    </div>
    
</body>

<script src="../webPlug/jquery/jquery-2.2.4.min.js"></script>
<script src="../webPlug/bootstrap-v3.3.5/dist/js/bootstrap.min.js" crossorigin="anonymous"></script>
<#--<script src="../js/system/module_index_ftl.js"></script>-->

</html>