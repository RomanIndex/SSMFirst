<#import "admin/master/lte_master.ftl" as my>
<@my.page
title="PRIVILEGE页面"
css="<style>body{}</style>"
script="<script src='/admin/js/privilege_index.js?v2'></script>"
>

<section class="content">
    <div class="row">
        <div class="col-xs-12">
            <!-- 一个table对应一个box-->
            <div class="box">
                <div class="box-header">
                    <!--<h3 class="box-title">权限列表</h3>-->
                    <ol class="breadcrumb">
                        <li><strong><span>1、在【角色--权限管理】页面，可以快速生成票据<br>
                            2、左侧菜单显隐说明：
                            （1）要把对应模块设置成票据，且是“菜单类型”
                            （2）要把对应票据授权给当前登录账户<br>这样就是该账户的可见左侧菜单了</span></strong></li>
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