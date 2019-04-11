<#assign buList = ["CX", "CB", "澳美华"]>
<#assign positionMap = {"最强王者": 1, "傲世宗师": 2, "超凡大师": 3, "坚韧黑铁": 9}>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <base href="/">
    <title>展示页面编辑功能</title>
    <!-- Bootstrap -->
    <link href="../webPlug/bootstrap-v3.3.5/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <link href="../admin/bootstrapValidator/css/bootstrapValidator.min.css" rel="stylesheet"><!-- 自动校样 -->
    <!-- bootstrap-daterangepicker -->
    
    <link href="../webPlug/bootstrap-daterangepicker/daterangepicker.css" rel="stylesheet">
    
    <!--唯一保留layui--弹出框样式-->
    <link href="../webPlug/layui-v2.2.6/css/layui.css" rel="stylesheet">
    <link href="../webPlug/layer/skin/default/layer.css" rel="stylesheet">
    <style type="text/css">
        #formId .form-group label{
            white-space: nowrap;
        }
    </style>
  </head>
  
  <!-- 该页面没有使用master公共页面，仅引入所需css和js文件，
                    利于测试bootstrap原生的样式，及测试与freemarker的交互，还有也是后台直接返回的页面
                    注意：改页面不是弹框，仅仅是个表单 -->

  <body class="nav-md">
    
	<div class="container body">
		<div class="main_container">
			<!-- nested begin -->

			<div class="right_col" role="main">
				<div class="row">
					<div class="col-md-12 col-sm-12 col-xs-12">
						<fieldset class="layui-elem-field layui-field-title">
							<legend style="border-bottom: 0px">编辑医生信息</legend>
						</fieldset>

						<form id="formId" class="form-horizontal">
							<div class="form-group">
								<label for="name" class="col-sm-2 control-label">姓名：</label>
								<div class="col-sm-8">
									<input type="text" class="form-control" name="name" value="${object.name}" placeholder="名字">
									<input type="hidden" name="id" value="${object.id}">
								</div>
							</div>
							
							<div class="form-group">
                                <label for="name" class="col-sm-2 control-label">大区：</label>
                                <div class="col-sm-8">
                                    <input type="text" class="form-control" name="area" 
                                        value="${(operate == 'update') ?string('disabled', 'readonly')}" placeholder=""
                                       ${(operate == 'update') ?string('disabled', 'readonly')}>
                                </div>
                            </div>

							<div class="form-group">
								<label for="mobile" class="col-sm-2 control-label">电话：</label>
								<div class="col-sm-8">
									<input type="text" class="form-control" name="mobile" value="${object.mobile}" placeholder="电话" >
								</div>
							</div>
							
							<div class="form-group">
                                <label for="mobile" class="col-sm-2 control-label">邮箱：</label>
                                <div class="col-sm-8">
                                    <input type="text" class="form-control" name="email" placeholder="邮箱" value="${object.mobile}" >
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label for="mobile" class="col-sm-2 control-label">离开理由：</label>
                                <div class="col-sm-8">
                                    <div class="input-group">
		                              <input type="text" class="form-control" name="leaveReason" placeholder="Recipient's username">
		                              <span class="input-group-addon">欢迎召唤师回来！</span>
		                            </div>
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label for="mobile" class="col-sm-2 control-label">备注：</label>
                                <div class="col-sm-8">
                                    <textarea class="form-control" id="remark" name="remark" placeholder="备注说明限200字！"></textarea>
                                </div>
                            </div>
                            
                            <div class="form-group">
		                        <label class="col-sm-2 control-label">生效时段：</label>
		                        <div class="col-sm-4">
		                            <label class="col-sm-2 control-label">开始：</label>
		                            <div class="col-sm-10">
		                                <input type="text" name="startDay" class="col-sm-8 form-control datePicker">
		                            </div>
		                        </div>
		                        <div class="col-sm-4">
		                            <label class="col-sm-2 control-label">结束：</label>
		                            <div class="col-sm-10">
		                                <input type="text" name="endDay" class="col-sm-8 form-control datePicker">
		                            </div>
		                        </div>
		                    </div>
		                    
		                    <div class="form-group">
                                <label class="col-sm-2 control-label">预计回归时间：</label>
                                <div class="col-sm-4">
                                    <label class="col-sm-2 control-label">开始：</label>
                                    <div class="col-sm-10">
                                        <input type="text" name="startTime" class="col-sm-8 form-control datePickerYMD">
                                    </div>
                                </div>
                                <div class="col-sm-4">
                                    <label class="col-sm-2 control-label">结束：</label>
                                    <div class="col-sm-10">
                                        <input type="text" name="endTime" class="col-sm-8 form-control datePickerYMD">
                                    </div>
                                </div>
                            </div>
							
                            <div class="form-group">
                                <label for="mobile" class="col-sm-2 control-label">等级：</label>
                                <div class="col-sm-8">
                                    <input type="text" onkeyup="this.value=this.value.replace(/\D/g,'').replace(/\b[0]/g,'')" 
                                        class="form-control" name="level" placeholder="幸运数字" value="${object.mobile}" >
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label for="mobile" class="col-sm-2 control-label">擅长位置：</label>
                                <div class="col-sm-4">
                                    <label class="checkbox-inline">
									  <input type="checkbox" name="goodAtPosition" value="MID"><span>中单</span>
									</label>
									<label class="checkbox-inline">
									  <input type="checkbox" name="goodAtPosition" value="JUN"><span>打野</span>
									</label>
									<label class="checkbox-inline">
									  <input type="checkbox" name="goodAtPosition" value="TOP"><span>上单</span>
									</label>
									<label class="checkbox-inline">
                                      <input type="checkbox" name="goodAtPosition" value="ADC"><span>射手</span>
                                    </label>
                                    <label class="checkbox-inline">
                                      <input type="checkbox" name="goodAtPosition" value="SUP"><span>辅助</span>
                                    </label>
                                </div>
                                <div class="col-sm-3">
                                    <label class="col-sm-2 control-label">其他：</label>
                                    <div class="col-sm-10">
                                        <div class="input-group">
                                          <input type="text" class="form-control input_underline_stress">
                                          <span class="input-group-addon">__紧跟左侧</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label for="mobile" class="col-sm-2 control-label">等级：</label>
                                <div class="col-sm-8">
                                    <label class="radio-inline">
                                      <input type="radio" name="levelRadio" value="1"><span>0 ~ 30</span>
                                    </label>
                                    <label class="radio-inline">
                                      <input type="radio" name="levelRadio" value="2"><span>31 ~ 100</span>
                                    </label>
                                    <label class="radio-inline">
                                      <input type="radio" name="levelRadio" value="3"><span>101及以上</span>
                                    </label>
                                </div>
                            </div>
                            
		                    <div class="form-group">
	                            <label class="col-sm-2 control-label">荣耀截图：</label>
	                            <div class="col-sm-10">
	                                <!-- input可以加限制accept="image/gif,image/jpeg,image/png,image/jpg,image/bmp" -->
	                                <input id="pictureFile" style="display: none;" type="file" name="pictureFile" onchange="pictureApi.getFullPath(this)" /> 
	                                <label for="pictureFile">
	                                    <img alt="图片加载失败！" src="images/add.png" style="width: 100%; height: auto;">
	                                </label>
	                                <p class="help-block" style="color: rgb(195, 194, 194);">（建议：720 * 450PX）</p>
	                                <input type="button" class="btn default-btn" id="savePic" value="保存" />
	                            </div>
	                        </div>
	                        
	                        <div class="form-group">
                                <label class="col-sm-2 control-label">已有图片（写死的路径）：</label>
                                <div class="col-sm-10">
                                    <label>
                                        <img alt="图片加载失败！" id="fixedImg" src="" style="width: 120px; height: 90px;">
                                    </label>
                                    <p class="help-block" style="color: rgb(195, 194, 194);">（建议：720 * 450PX）</p>
                                </div>
                            </div>
                            
							<div class="form-group">
								<label class="col-sm-2 control-label">段位：</label>
								<div class="col-sm-8">
									<select class="form-control" id="rank" name="rank">
										<option value="">--所有--</option>
					                    <#list positionMap?keys as key>
					                    <option value="${positionMap[key]}">${key}</option>
					                    </#list>
									</select>
								</div>
							</div>
							
							<div class="form-group">
                                <label for="mobile" class="col-sm-2 control-label"></label>
                                <div class="col-sm-8">
                                    <div class="alert alert-warning alert-dismissible" role="alert">
		                              <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		                              <strong>Warning!</strong>
		                              <span>Better check yourself, you're not looking too good.</span>
		                            </div>
                                </div>
                            </div>
                            
							<div class="form-group">
								<label class="col-sm-2 control-label">信仰大陆：</label>
								<div class="col-sm-8">
									<select class="form-control" name="faithMainland">
									<#list buList as list>
										<#if list == 'CX'>
										<option value="${list}" selected>${list}</option>
										<#else>
										<option value="${list}">${list}</option>
										</#if>
									</#list>
									</select>
								</div>
							</div>
							
							<div class="form-group">
								<div class="col-sm-offset-4 col-sm-4">
									<button type="submit" name="submit" data-type="${operate!'add'}" 
									   class="btn btn-default btn-block">${(operate == 'update') ?string('保存更新', '立即提交')}</button>
								</div>
							</div>
						</form>

					</div>
				</div>
			</div>
			<!-- nested end!! -->
		</div>
	</div>

</body>
    <!-- jQuery -->
    <script src="../webPlug/jquery/jquery-2.2.4.min.js"></script>
    <!-- Bootstrap -->
    <script src="../webPlug/bootstrap-v3.3.5/dist/js/bootstrap.min.js"></script>
    
    <!-- Validator -->
    <script src="../admin/bootstrapValidator/js/bootstrapValidator.min.js" crossorigin="anonymous"></script>
    
    <script src="../webPlug/moment/min/moment.min.js"></script>
    <script src="../webPlug/bootstrap-daterangepicker/daterangepicker.js"></script>
    
    <script src="../webPlug/layer/layer.js"></script>
    
    <!-- ${script} js -->
    <script src='../js/util/date.js'></script><!-- 一些公共处理，如日期类的JS，推荐引入 -->
    <script src="../admin/js/common.js"></script>
    <script src="../admin/js/common_time_handle.js"></script>
    <script src='../admin/js/bootstrap_edit.js'></script>
</html>