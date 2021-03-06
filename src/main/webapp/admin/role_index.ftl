<#import "admin/master/lte_master.ftl" as my>
<@my.page
title="ROLE页面"
css="
<style>
    .createTicket{
        padding: 2px 10px;
        background-color: rgb(232, 219, 220);
        border-color: rgb(228, 208, 217);
    }
    .tip {
            width: 200px;
            border: 2px solid #ddd;
            padding: 8px;
            background: #f1f1f1;
            color: #666;
        }
</style>
"
script="<script src='/admin/js/role_index.js?v2'></script>"
>

<section class="content">
    <div class="row">
        <div class="col-xs-12">
            <!-- 一个table对应一个box-->
            <div class="box">
                <div class="box-header">
                    <h3 class="box-title">用户列表</h3>
                    <ol class="breadcrumb">
                        <li><strong><span>1、现在主要是【菜单类角色】，还有操作类角色；2、还要加入 角色组 的类型，方便批量管理；3、jpaQuery实现模糊查询</span></strong></li>
                    </ol>
                </div>
                <div class="box-body" style="width: 100%; background-color: #f7f7f7;overflow-y: auto;" 这个div的高度应该用JS动态计算得出>
                    <table id="table" class="col-xs-12"></table>
                </div>
            </div>
        </div>
    </div>
</section>

<div id="tk"></div>
<div id="mjs:tip" class="tip" style="position:absolute; left:0; top:0; display:none;"></div>

<div id="toolbar">
    <form id="queryForm" class="form-inline" autocomplete="off">
        <div class="form-group">
            <label class="control-label" for="roleKeyword">名称：</label>
            <input type="text" class="form-control" id="roleKeyword" name="roleKeyword" placeholder="角色关键词">
        </div>
        <div class="form-group">
            <label class="control-label" for="type">类型：</label>
            <select class="form-control" id="type" name="type">
                <option value="">所有</option>
                <option value="1">普通</option>
                <option value="2">会员</option>
            </select>
        </div>
        <a class="btn btn-primary" onclick="api.role.query()" data-icon="search">查询</a>
        <a class="btn btn-primary" onclick="api.role.getAddModal(this)">新增</a>
        <a class="btn btn-default" onclick="commonApi.utils.getTKModal('addModal')"><span>（TKModal+加载JS）</span></a>
    </form>
</div>

<div id="repeatDialog">
<#--<div id="search_div">
    <input class="easyui-searchbox" style="width: 300px" data-options="searcher:doSearch,prompt:'Please Input Value'" name="keyWord"></input>
</div>-->
    <table id="tt"></table>
    <input type="hidden" id="roleId">
</div>

</@my.page>