<#import "admin/lte_master.ftl" as my>
<@my.page title="医患咨询报表"
css="
<link href='../webPlug/layui-v2.2.6/css/layui.css' rel='stylesheet'>
<link href='../webPlug/layer/skin/default/layer.css' rel='stylesheet'>
"
script="
<script src='../admin/js/base_import.js'></script>
">
<section class="content">
    <div class="error-page">

        <div class="error-content">
            <h3>
                <i class="fa fa-warning text-yellow"></i> Oops! Page not found.
            </h3>

            <p>
                We could not find the page you were looking for. Meanwhile, you may
                <a href="../../index.html">return to dashboard</a> or try using the
                search form.
            </p>

            <button class="btn" onclick="submit()">上传初始化Module Excel数据</button>
        </div>
    </div>
</section>

</@my.page>