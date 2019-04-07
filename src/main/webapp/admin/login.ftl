<!DOCTYPE html>
<html>
    <head>
        <base href="/">
        <meta charset="utf-8" />
        <title>SSM管理平台login页面</title>
        <LINK rel="shortcut icon" href="/images/favicon.ico">
        <link rel="stylesheet" href="css/style.css" />
        <link rel="stylesheet" href="css/base.css" />
        <style>
	      .baseBody {background-image: linear-gradient(312deg, rgb(58, 231, 243) 0%, rgb(30, 150, 229) 100%); height: 100%;}
	    </style>
    </head>
    <!-- onload="checkCookie()"  -->
    <body class="baseBody">
        <div class="nav_top">
            <div><img src="img/logo.png"></div>
            <p>SSM管理平台</p>
        </div>
        <div class="login_content_bg">
            <div class="login_content">
                <p>用户登录</p>
                <div id="loginSubmit" class="login_input">
                    <div class="account"><input type="text" placeholder="账号" value="admin" id="employeeId"></div>
                    <div><input type="password" placeholder="密码" id="password" value="123456"></div>
                    <a id='login'></a>
                </div>
                <div class="error_tips"><span id="error"></span></div>
                <div class="layui-form-item">
                   <!-- <input type="checkbox" id="remember"><label for="remember" class="remember_me" id="checkspan">记住我?</label> -->
                   <!--<a id="register" style="color: #ffffff;font-size: 12px;cursor:pointer;float:right;">马上注册</a>-->
                </div>
            </div>  
        </div>
        <div class="footer">
            <!--<p></p>-->
            <div>&copy;2018 深圳。。。科技有限公司版权所有</div>
        </div>
        
        <!--canvas-->
        <div id="particles-js"></div>
        
        <!--修改密码弹窗-->
        <div class="alter_psd_bg" style="display:none;">
            <div class="alter_psd">
                <p style="color:#F00" >您的密码为初始密码，请修改密码！<a class="delete"></a></p>
                <div class="psd_msg">
                    <div class="user_name">用户名：<span id ='username' ></span></div>
                    <div class="login_input">
                        <div class="account"><input type="password"  id='newPass' placeholder="新密码"></div>
                        <div><input type="password" id='checkPass' placeholder="确认密码"></div>
                        <a id= 'updatePassword'></a>
                    </div>
                </div>
            </div>  
        </div>
        
    </body>
    <script src="../webPlug/jquery/jquery-2.2.4.min.js"></script>
    <script src="../webPlug/layer/layer.js"></script>
    <script src="/js/particles.js" ></script>
    <script src="/js/app.js" ></script>
    <script src="/js/stats.js" ></script>
    <script src="/js/js.cookie.js"></script>
    <script src="/js/login.js"></script>
</html>