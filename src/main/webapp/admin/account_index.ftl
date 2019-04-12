<#import "/admin/old_master.ftl" as my>
<@my.page
title="Account管理"
css="
    <link rel='stylesheet' href='../otc/css/fix.css'/>
    "
script="
    <script src='../js/picture.js'></script>
    <script src='../otc/dnh/js/banner_index.js?v2'></script>
    "
>
    <style>
        a{cursor: pointer;}
    </style>
    <div class="right_col" role="main">
        <div class="row">
            <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                    <div class="x_content">
                        <form class="form-inline" id="bannerForm">
                            <div class="form-group">
                                <label class="control-label" for="hospital">标题：</label>
                                <input type="text" class="form-control" name="title" placeholder="banner标题关键字">
                            </div>

                            <div class="form-group">
                                <label class="control-label" for="type">类型：</label>
                                <select class="form-control" id="type" name="type">
                                    <option value="">所有</option>
                                    <option value="1">助手端</option>
                                    <option value="2">店员端</option>
                                </select>
                            </div>

                            <a class="btn btn-default" onclick="api.banner.initTable()"><span>查询</span></a>
                            <a class="btn btn-default" onclick="api.banner.getAddModal()"><span>新增</span></a>
                        </form>
                        <table id="bannerTable" class="table table-striped table-bordered dt-responsive nowrap order-column" cellspacing="0" width="100%">
                            <tfoot class="my_tfoot"></tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <#include "../dnh/banner_edit.ftl">
</@my.page>