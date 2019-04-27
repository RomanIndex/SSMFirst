<#-- 专门 存放一些 弹框Demo，供其他页面调用，调用方式 见：XX -->

<style>
    .form-group label {
        white-space:  nowrap;
    }
</style>
<!--这里用js render，方便，要Y轴滚动 -->
<div class="modal fade bs-example-modal-lg in" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
     style="display: none; padding-right: 10px; overflow-y: auto;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3 class="modal-title" id="myModalLabel"></h3>
            </div>
            <div class="modal-body form-horizontal form-label-left">

                <form class="form-horizontal" id="inputForm" autocomplete="off">

                    <div class="form-group">
                        <label class="col-sm-2 control-label">名称：</label>
                        <div class="col-sm-10">
                            <input type="text" id="" name="name" class="form-control col-md-12">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">来源：</label>
                        <div class="col-sm-10">
                            <select class="form-control" id="source" name="source">
                                <option value="">--选择--</option>
                                <option value="1">朋友推荐</option>
                                <option value="2">广告</option>
                                <option value="3">自己搜索</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">精确到天：</label>
                        <div class="col-sm-10">
                            <input type="text" name="startDay" class="col-sm-8 form-control datePickerYMD">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">精确到秒：</label>
                        <div class="col-sm-10">
                            <input type="text" name="endDay" class="col-sm-8 form-control datePicker">
                        </div>
                    </div>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="api.account.save(this)" id="save">确认</button>
            </div>
        </div>
    </div>
</div>