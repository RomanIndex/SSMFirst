<#import "admin/master/lte_master.ftl" as my>
<@my.page
title="ACCOUNT页面"
css="
    <style>body{}</style>"
script="
    <script src='/admin/js/account_index.js?v2'></script>
    <script src='/admin/js/fileInput.js'></script>
"
>

<section class="content">
    <div class="row">
        <div class="col-xs-12">
            <!-- 一个table对应一个box-->
            <div class="box">
                <div class="box-header">
                    <h3 class="box-title">用户列表</h3>
                    <ol class="breadcrumb">
                        <li><strong><span style="color: #27a0d7">（这里是页面等备注说明）</span></strong></li>
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
            <label class="control-label" for="accountKeyword">名称：</label>
            <input type="text" class="form-control" name="accountKeyword" placeholder="关键词">
        </div>
        <div class="form-group">
            <label class="control-label" for="type">类型：</label>
            <select class="form-control" id="type" name="type">
                <option value="">所有</option>
                <option value="1">普通</option>
                <option value="2">会员</option>
            </select>
        </div>
        <div class="form-group">
            <label class="control-label" for="">时间：</label>
            <div id="reportrange" class="form-control" style="background: #fff; cursor: pointer; padding: 5px 10px; border: 1px solid #ccc">
                <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                <span></span><b class="caret"></b>
            </div>
        </div>
        <a class="btn btn-primary" onclick="api.account.query()" data-icon="search">查询</a>
        <a class="btn btn-primary" onclick="api.account.getAddModal(this)"><span>新增</span></a>
        <!-- 有引入单独js，id 需保留 -->
        <a class="btn btn-default" id="uploadImgModel"><span>多图上传</span></a>
    </form>
</div>

<div id="tk"></div>
<#include "/admin/bt_fileInput.html">

</@my.page>