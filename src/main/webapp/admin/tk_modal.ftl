<style>
#inputForm.form-group label{
    white-space:  nowrap;
}
.datePicker{padding: 6px 10px;}
</style>
<!--这里用js render，方便，要Y轴滚动 -->
<div class="modal fade bs-example-modal-lg in" id="addAccountModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
    style="display: none; padding-right: 10px; overflow-y: auto;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3 class="modal-title" id="myModalLabel">tkModal弹框</h3>
            </div>
            <div class="modal-body form-horizontal form-label-left">

                <form class="form-horizontal" id="inputForm">

                    <div class="form-group">
                        <label class="col-sm-2 control-label">account：</label>
                        <div class="col-sm-10">
                            <input type="text" id="" name="account" class="form-control col-md-12">
                            <input type="hidden" name="period">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">role：</label>
                        <div class="col-sm-10">
                            <input type="text" id="" name="role" class="form-control">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">活动时间：</label>
                        <div class="col-sm-5">
                            <label class="col-sm-2 control-label">起：</label>
                            <div class="col-sm-10">
                                <input type="text" name="activeBegin" class="col-sm-12 form-control datePicker">
                            </div>
                        </div>
                        <div class="col-sm-5">
                            <label class="col-sm-2 control-label">止：</label>
                            <div class="col-sm-10">
                                <input type="text" name="activeEnd" class="col-sm-12 form-control datePicker">
                            </div>
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label class="col-sm-2 control-label">user_type：</label>
                        <div class="col-sm-10">
                            <select class="form-control" id="" name="user_type">
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                            </select>
                        </div>
                    </div>

                </form>

                <!-- <div class="error_tips error_tips1">
                    <span id="" style="color: red; margin-left: 27%">错误提示！！！！</span>
                </div> -->
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="save">确认</button>
            </div>
        </div>
    </div>
</div>