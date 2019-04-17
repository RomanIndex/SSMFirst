<#import "admin/master/lte_master.ftl" as my>
<@my.page
title="PRIVILEGE页面"
css="<style>body{}</style>"
script="<script src='/admin/js/privilege_index.js?v2'></script>"
>

    <div class="right_col" role="main">
        <div class="row">
            <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                    <div class="x_content">
                        <h3>权限列表</h3>
                        <ol class="breadcrumb">
                            <li><strong><span style="color: #27a0d7">（这里是页面等备注说明）</span></strong></li>
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
            <label class="control-label" for="name">模块：</label>
            <input type="text" class="form-control" name="name" placeholder="关键词">
        </div>
        <div class="form-group">
            <label class="control-label" for="type">操作类型：</label>
            <select class="form-control" id="type" name="type">
                <option value="">所有</option>
                <option value="select">查询</option>
                <option value="2">。。</option>
            </select>
        </div>
        <a class="btn btn-primary" onclick="api.privilege.query()" data-icon="search">查询</a>
        <a class="btn btn-primary" onclick="api.privilege.getAddModal(this)">新增</a>
    </form>
</div>

<div id="tk"></div>

</@my.page>