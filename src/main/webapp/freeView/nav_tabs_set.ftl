<#import "admin/master.ftl" as my>
<@my.page 
    title="设置推送人员"
    css="
    <style>
        body{background-color:white;font-size: 14px;}
        tr th, tr td{text-align:center;}
        .dataTables_length {width: 30%;}
        .dataTables_info {width: 30%;}
        .btn-group{display: block;}
        .parent{display: flex; justify-content: center;}
        .addBtn, .delBtn{width: 200px;}
    </style>
    "
    script="
    <script src='../yypz/js/common-api.js'></script>
    <script src='../yypz/js/set_push.js'></script>
    <script>
    function init_page(){
        $(".nav-tabs").children().each(function(i, item){
            if(SEND_TYPE == i){
                $(this).addClass("active");
            }
        })
        
        $('.filter_div').hide().eq(SEND_TYPE).show();
        
        $('.filter_div').eq(SEND_TYPE).appendTo('.doctor_top');
        $('.filter_div').eq(SEND_TYPE).appendTo('.patient_top');
        $('.filter_div').eq(SEND_TYPE).appendTo('.assistant_top');
        
        $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
            //e.target // newly activated tab
            //e.relatedTarget // previous active tab
            var that = $(this).attr("href")
            layer.msg(that)
        })
    }
    </script>
    ">
    <div class="right_col" style="background-color:white" role="main">
        <div class="row">
          <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
            
            <ul class="col-md-12 col-sm-12 col-xs-12 nav nav-tabs">
                <li><a data-toggle="tab" href="#assistant"><span>助手列表</span></a></li>
                <li><a data-toggle="tab" href="#doctor"><span>医生列表</span></a></li>
                <li><a data-toggle="tab" href="#patient"><span>患者列表</span></a></li>
            </ul>
            
            <div class="col-md-12 col-sm-12 col-xs-12 tab-content">
                
                <!-- 助手 -->
                <div class="x_content tab-pane fade" id="assistant">
                  <div class="x_concol-md-6 col-sm-6 col-xs-6">
                    <table id="assistant_table_left" class="table table-striped table-bordered dt-responsive nowrap order-column" cellspacing="0" width="100%">
                    <tfoot class="my_tfoot"></tfoot>
                    </table>
                  </div>
                  
                  <div class="col-md-6 col-sm-6 col-xs-6">
                    <table id="assistant_table_right" class="table table-striped table-bordered dt-responsive nowrap order-column" cellspacing="0" width="100%">
                    <tfoot class="my_tfoot"></tfoot>
                    </table>
                  </div>
                </div>
                
                <!-- 医生  -->
                <div class="x_content tab-pane fade" id="doctor">
                  <div class="x_concol-md-6 col-sm-6 col-xs-6">
                    <table id="doctor_table_left" class="table table-striped table-bordered dt-responsive nowrap order-column" cellspacing="0" width="100%">
                    <tfoot class="my_tfoot"></tfoot>
                    </table>
                    
                    <div class="parent"><a class="addBtn btn btn-primary"><span>添加</span></a></div>
                  </div>
                  
                  <div class="col-md-6 col-sm-6 col-xs-6">
                    <table id="doctor_table_right" class="table table-striped table-bordered dt-responsive nowrap order-column" cellspacing="0" width="100%">
                    <tfoot class="my_tfoot"></tfoot>
                    </table>
                    
                    <div class="parent"><a class="delBtn btn btn-primary"><span>删除</span></a></div>
                  </div>
                  
                </div>
                
                <!-- 患者  -->
                <div class="x_content tab-pane fade" id="patient">
                  <div class="x_concol-md-6 col-sm-6 col-xs-6">
                    <table id="patient_table_left" class="table table-striped table-bordered dt-responsive nowrap order-column" cellspacing="0" width="100%">
                    <tfoot class="my_tfoot"></tfoot>
                    </table>
                  </div>
                  
                  <div class="col-md-6 col-sm-6 col-xs-6">
                    <table id="patient_table_right" class="table table-striped table-bordered dt-responsive nowrap order-column" cellspacing="0" width="100%">
                    <tfoot class="my_tfoot"></tfoot>
                    </table>
                  </div>
                </div>
                
            </div>
            <input type="hidden" id="parentCode" value="${parentCode}">
            <input type="hidden" id="addType" value="${addType}">
            <input type="hidden" id="sendType" value="${sendType}">
            
            </div>
          </div>
        </div>
      </div>
      
      <div class="filter_div">
        <form id="assistant_form" class="form-inline">
            
            <div class="form-group">
              <label class="control-label" for="">名字/电话：</label>
              <input type="text" class="form-control" name="" placeholder="请输入要搜索关键词">
            </div>
            
            <div class="form-group">
              <label class="control-label" for="">员工编号：</label>
              <input type="text" class="form-control" name="empNo" placeholder="员工编号">
            </div>
            
            <div class="form-group">
              <label class="control-label" for="">BU：</label>
              <input type="text" class="form-control" name="bu" placeholder="BU">
            </div>
            
            <a class="btn btn-default query"><span>查询</span></a>
        </form>
      </div>
      
      <div class="filter_div">
        <form id="doctor_form" class="form-inline">
            
            <div class="form-group">
              <label class="control-label" for="">名字/电话：</label>
              <input type="text" class="form-control" name="name" placeholder="请输入要搜索关键词">
            </div>
            
            <div class="form-group">
              <label class="control-label" for="">医院省份：</label>
              <input type="text" class="form-control" name="hospital" placeholder="省份">
            </div>
            
            <div class="form-group">
              <label class="control-label" for="">职称：</label>
              <input type="text" class="form-control" name="position" placeholder="职称">
            </div>
            
            <a class="btn btn-default query"><span>查询</span></a>
        </form>
      </div>
      
      <div class="filter_div">
        <form id="patient_form" class="form-inline">
            
            <div class="form-group">
              <label class="control-label" for="">名字/电话：</label>
              <input type="text" class="form-control" name="" placeholder="请输入要搜索关键词">
            </div>
            
            <div class="form-group">
              <label class="control-label" for="">昵称：</label>
              <input type="text" class="form-control" name="nick" placeholder="昵称">
            </div>
            
            <a class="btn btn-default query"><span>查询</span></a>
        </form>
      </div>
      
</@my.page>