<#import "admin/lte_master.ftl" as my>
<@my.page 
    title="医患咨询报表"
    css="
        <link rel='stylesheet' href='../admin/css/bootstrap-clearmin.min.css'>
        <link rel='stylesheet' href='../admin/css/summernote.css'>
    "
    script="
        <script src='../admin/js/summernote.js'></script>
        <script src='../admin/js/notepad.js'></script>
    ">
    <section class="content">
        <div id="global">
          <div class="container-fluid">
              <div class="panel panel-default">
                  <div class="panel-heading">--收录--</div>
                  <div class="panel-body">
                      <div class="form-group">
                          <label>问题：</label>
                          <input type="text" class="form-control" placeholder="Enter title">
                      </div>
                      <label>答案：</label>
                      <div id="summernote"></div>
                      <div class="form-group text-right" style="margin-top:20px">
                          <button type="submit" class="btn btn-default">Save Draft</button>
                          <button type="submit" class="btn btn-primary" onclick="save()">Save changes</button>
                      </div>
                  </div>
              </div>
          </div>
          <footer class="cm-footer"><span class="pull-left">Connected as John Smith</span><span class="pull-right">&copy; PAOMEDIA SARL</span></footer>
      </div>
        
    </section>
      
</@my.page>