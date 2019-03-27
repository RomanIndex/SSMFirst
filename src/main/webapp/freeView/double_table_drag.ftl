<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" name="viewport" content="width=device-width" />
    <title>BootStrap Table 简单演示</title>
    <!-- 1。Jquery组件引用-->
    <script src="../webPlug/jquery/jquery-2.2.4.min.js"></script>
    
    <!-- <link href="../webPlug/jquery-ui-v1.12.1/jquery-ui.css" rel="stylesheet" /> -->
    <link href="../webPlug/jquery-ui-v1.12.1/jquery-ui.theme.css" rel="stylesheet" />
    <script src="../webPlug/jquery-ui-v1.12.1/jquery-ui.min.js"></script>
 
    <!--2。bootstrap组件引用-->
    <link href="../webPlug/bootstrap-v3.3.5/dist/css/bootstrap.css" rel="stylesheet" />
    <script src="../webPlug/bootstrap-v3.3.5/dist/js/bootstrap.min.js"></script>
    
    <!-- 3.bootstrap table组件以及中文包的引用-->
    <link href="../webPlug/bootstrap-table/src/bootstrap-table.css" rel="stylesheet" />
    <script src="../webPlug/bootstrap-table/src/bootstrap-table.js"></script>
    <script src="../webPlug/bootstrap-table/src/locale/bootstrap-table-zh-CN.js"></script>
    
    <!--4.拖放组件引用-->
    <link href="../webPlug/bootstrap-table/src/extensions/reorder-rows/bootstrap-table-reorder-rows.css" rel="stylesheet" />
    <script src="../webPlug/bootstrap-table/src/extensions/reorder-rows/bootstrap-table-reorder-rows.js"></script>
    <!-- 拖放，不能少 -->
    <script src="../webPlug/bootstrap-table/tuofang/jquery.tablednd.js"></script>
    
    <!--4.导出-->
    <script src="../webPlug/bootstrap-table/src/extensions/export/bootstrap-table-export.js"></script>
    <script src="../webPlug/bootstrap-table/export/tableExport.js"></script>
    
    <!--5.过滤-->
    <script src="../webPlug/bootstrap-table/src/extensions/filter-control/bootstrap-table-filter-control.js"></script>
    
</head>
<body>
    <div class="container">
        <h1>双表拖拽 + tab导航</h1>
        
        <div id="toolbar" style="display: none;">
            <form id="exp_form" class="form-inline">
                
                <div class="form-group">
                    <label class="control-label" for="name">keyWord：</label> 
                    <input type="text" class="form-control" name="name" placeholder="请输入要搜索关键词">
                </div>
                
                <button class="btn"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></button>
                
                <div class="form-group">
                    <label class="control-label" for="bu">BU：</label>
                    <select class="form-control" name="bu">
                        <option value="">所有BU</option>
                        <#list buList as list>
                        <option value="${list}">${list}</option>
                        </#list>
                    </select>
                </div>
                
                <button type="button" class="btn btn-default" onclick="search()" data-icon="search">查询</button>
                <button type="button" class="btn btn-default" id="del">批量删除</button>
            </form>
        </div>
        
        <div class="row base-margin" id="query">
            <ol class="breadcrumb">
                <li><strong><span style="color: #27a0d7">用户列表</span></strong></li>
            </ol>
            <form class="form-inline" role="form" style="float: left; width: 100%" method="post" id="queryForm">
                <div class="form-group">
                    <label for="orgCode">部门:</label> 
                    <select class="form-control" id="orgCode" name="orgCode">   
                        <option value="">默认选择</option>
                        <c:forEach var="data" items="${orgList}">
                            <option value="${data.orgCode }">${data.orgName }</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="userName">名称:</label> 
                    <input type="text" class="form-control" name="userName" id="userName"  placeholder="请输入名称">
                </div>
                <div class="form-group">
                    <label >日期:</label>
                    <input placeholder="开始日期" class="form-control layer-date" id="startDate" name="startDate">
                    <input placeholder="结束日期" class="form-control layer-date" id="endDate" name="endDate">
                </div>
                
                <div class="form-group">
                    <button type="button" id="queryBtn" onclick="search();" class="btn btn-primary">查询</button>
                </div>
                <div class="form-group btn-right">
                    <button type="button" class="btn btn-primary" id="addBtn" onclick="addUser();">新增用户</button>
                </div>
                <button type="button" class="btn btn-default" id="del">批量删除</button>
            </form>
        </div>

        <!-- 如果我们采用JS代码方式来初始化表格插件，那么只需要在HTML上声明一个表格对象即可 -->
        <div class="container" style="width: 100%">
            <table id="table" class="col-xs-12" data-toolbar="#toolbar" data-use-row-attr-func="true" data-reorderable-rows="true"></table>
        </div>
        
    </div>
    
    <div id="dialog" title="基本的对话框">
      <p>这是一个默认的对话框，用于显示信息。对话框窗口可以移动，调整尺寸，默认可通过 'x' 图标关闭。</p>
    </div>
</body>
<script src="/freeView/js/test.js"></script>
</html>