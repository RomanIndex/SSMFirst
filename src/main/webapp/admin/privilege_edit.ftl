<#import "admin/master/lte_master.ftl" as my>
<#assign typeList = {"未定义":0, "活动型":1, "临时型":2, "管理员型":3, "系统型":4, "菜单型":9}>
<#assign levelList = {"低":1, "中":2, "高":3, "极高":4}>
<@my.page
title="权限票据管理"
css="
    <link href='../adminn/bootstrapValidator/css/bootstrapValidator.css' rel='stylesheet'>
    <style></style>
    "
script="
	<script src='../adminn/bootstrapValidator/js/bootstrapValidator.js' crossorigin='anonymous'></script>
	<script src='../admin/js/privilege_edit.js'></script>
    ">
    <div class="right_col" role="main">
        <div class="row">
            <div class="col-md-12 col-sm-12 col-xs-12">
                <fieldset class="layui-elem-field layui-field-title">
                    <legend style="border-bottom: 0px">${(operate == 'update') ?string('编辑', '新增')}权限票据</legend>
                </fieldset>

                <form id="formId" class="form-horizontal">
                    <div class="form-group">
                        <label for="operateId" class="col-sm-2 control-label">标识Code：</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" id="code" name="code" value="${object.code}" readonly >
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="name" class="col-sm-2 control-label">名称：</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" id="name" name="name" value="${object.name}" placeholder="名称">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">类型：</label>
                        <div class="col-sm-8">
                            <select class="form-control" id="type" name="type">
                            <#list typeList?keys as key>
                                <option value="${typeList[key]}" ${(typeList[key] == object.type) ?string('selected', '')}>${key}</option>
                            </#list>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="level" class="col-sm-2 control-label">级别：</label>
                        <div class="col-sm-8">
                            <select class="form-control" id="level" name="level">
                            <#list levelList?keys as key>
                                <option value="${levelList[key]}" ${(levelList[key] == object.level) ?string('selected', '')}>${key}</option>
                            </#list>
                            </select>
                        </div>
                    </div>

                    <div class="form-group" mType:${mType}>
                        <label for="moduleId" class="col-sm-2 control-label">资源模块：</label>
                        <div class="col-sm-8">
                            <div class="row">
                                <div class="col-xs-4">
                                    <input type="radio" class="col-xs-3" name="xz" style="height:30px;" value="firstMenu" ${getMType(1)}>
                                    <select class="col-xs-8 form-control" style="width:75%;" id="firstMenu" name="firstMenu">
		                        <#list firstMenu as list>
                                    <option value="${list.moduleId}" ${(list.moduleId == object.moduleId) ?string('selected', '')} >${list.name}</option>
                                </#list>
                                    </select>
                                </div>
                                <div class="col-xs-4">
                                    <input type="radio" class="col-xs-3" name="xz" style="height:30px;" value="option1" ${getMType(2)}>
                                    <select class="col-xs-8 form-control" style="width:75%;" id="secondMenu" name="secondMenu">
	                          <#list secondMenu as list>
                                  <option value="${list.moduleId}" ${(list.moduleId == object.moduleId) ?string('selected', '')} >${list.name}</option>
                              </#list>
                                    </select>
                                </div>
                                <div class="col-xs-4">
                                    <input type="radio" class="col-xs-3" name="xz" style="height:30px;" value="option1" ${getMType(3)}>
                                    <select class="col-xs-8 form-control" style="width:75%;" id="btn" name="btn">
	                          <#list btn as list>
                                  <option value="${list.moduleId}" ${(list.moduleId == object.moduleId) ?string('selected', '')} >${list.name}</option>
                              </#list>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="type" class="col-sm-2 control-label">操作类型：</label>
                        <div class="col-sm-8">
                            <select class="form-control" id="operateId" name="operateId">
	                        <#list operateList as list>
                                <option value="${list.operateId}" ${(list.operateId == object.operateId) ?string('selected', '')} >${list.name}</option>
                            </#list>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="validDate" class="col-sm-2 control-label">生效时间：</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" id="validDate" name="validDate" value="${object.validDate}" placeholder="生效时间">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="city" class="col-sm-2 control-label">状态：</label>
                        <div class="col-sm-8">
                            <label class="radio-inline">
                                <input type="radio" name="status" id="status1" value="0" ${(object.status == 0) ?string('checked', '')}>无效
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="status" id="status2" value="1" ${(object.status == 1) ?string('checked', '')}>有效
                            </label>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-offset-4 col-sm-4">
                            <button type="submit" name="submit" data-type="${operate!'add'}"
                                    class="btn btn-default btn-block">${(operate == 'update') ?string('修改', '新增')}</button>
                        </div>
                    </div>
                </form>

            </div>
        </div>
    </div>

<!-- ftl函数：判断MType -->
    <#function getMType index>
        <#if mType == index>
            <#return "checked">
        </#if>
    </#function>
</@my.page>