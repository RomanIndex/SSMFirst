<!DOCTYPE html>
<html>
<head>
    <base href="/">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>基于LTE 2 | SSM后台</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.7 -->
    <link href="../webPlug/bootstrap-v3.3.5/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <#--<link href="./admin/dist/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet"/>-->
    <!-- Font Awesome -->
    <link href="../webPlug/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <!-- Ionicons -->
    <link href="../webPlug/Ionicons/css/ionicons.min.css" rel="stylesheet">
    <!-- Theme style -->
    <link href="../admin/dist/css/AdminLTE.min.css" rel="stylesheet">
    <!-- iCheck -->
    <link rel="stylesheet" href="../webPlug/iCheck/skins/square/blue.css">
    <#-- -->
    <link rel="shortcut icon" href="/images/favicon.ico">
    <link rel="stylesheet" href="system/login/css/login.css">
</head>
<body class="hold-transition login-page baseBody" style="overflow: hidden;">
<div class="login-box">
    <div class="login-logo">
        <a href="#"><b>SSM</b>后台管理</a>
    </div>
    <!-- /.login-logo -->
    <div class="login-box-body">
        <p class="login-box-msg">Sign in to start your session</p>

        <form id="loginForm" autocomplete="off">
            <div class="form-group has-feedback">
                <input type="text" name="empNo" value="admin" class="form-control edit-able" placeholder="Account/Email">
                <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <input type="password" name="password" value="123456" class="form-control edit-able" placeholder="Password">
                <span class="glyphicon glyphicon-lock form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback error_tips">
                <span></span>
            </div>
            <div class="row">
                <div class="col-xs-8">
                    <div class="checkbox icheck edit-able">
                        <label>
                            <input type="checkbox" id="checkspan"> Remember Me
                        </label>
                    </div>
                </div>
                <!-- /.col -->
                <div class="col-xs-4">
                    <a onclick="api.login.login();" class="btn btn-primary btn-block btn-flat edit-able">Sign In</a>
                </div>
                <!-- /.col -->
            </div>
        </form>

        <div class="social-auth-links text-center">
            <p>- - -OR - - -</p>
            <a href="#" class="btn btn-block btn-social btn-facebook btn-flat"><i class="fa fa-facebook"></i> Sign in
                using
                Facebook</a>
            <a href="#" class="btn btn-block btn-social btn-google btn-flat"><i class="fa fa-google-plus"></i> Sign in
                using
                Google+</a>
        </div>
        <!-- /.social-auth-links -->

        <a href="#" class="edit-able">I forgot my password</a><br>
        <a href="register.html" class="text-center edit-able">Register a new membership</a>

    </div>
    <!-- /.login-box-body -->
</div>
<!-- /.login-box -->

<!--canvas-->
<div id="particles-js"></div>

<div class="footer">
    <div>&copy;2019 无畏 逐浪，酉卒舞乱华年 O(∩_∩)O~</div>
</div>

<!-- jQuery 3 -->
<script src="../admin/dist/jquery/dist/jquery.min.js"></script>
<!--<script src="../webPlug/jquery/jquery-2.2.4.min.js"></script>-->
<!-- Bootstrap 3.3.7 -->
<script src="../admin/dist/bootstrap/dist/js/bootstrap.min.js"></script>
<!-- iCheck -->
<script src="../webPlug/iCheck/icheck.min.js"></script>
<!-- 登陆 -->
<script src="/system/login/js/particles.js"></script>
<script src="/system/login/js/app.js"></script>
<script src="/system/login/js/js.cookie.js"></script>
<script src="/system/login/js/login.js"></script>
</body>
</html>
