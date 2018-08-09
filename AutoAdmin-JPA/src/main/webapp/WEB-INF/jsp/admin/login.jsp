<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
    <link rel="stylesheet" href="/layui/css/layui.css" media="all">


    <style>
        body {
            background-color: #00b2b2;
        }

        .login-frame {
            height: 240px;
            width: 300px;
            padding: 20px;
            background-color: rgba(0, 0, 0, 0.2);
            border-radius: 4px;
            position: absolute;
            left: 45%;
            top: 50%;
            margin: -150px 0 0 -150px;
            z-index: 99;
        }
    </style>
</head>
<body>


<div class="login-frame">
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
        <legend>Login</legend>
    </fieldset>
    <form class="layui-form" action="">
            <input type="text" name="account" lay-verify="required" autocomplete="off" placeholder="账号" class="layui-input"
                   lay-verify="required">

        <input type="password" name="password" lay-verify="required" autocomplete="off" placeholder="密码"
               class="layui-input"
               style="margin-top: 20px;" lay-verify="required">
                <button class="layui-btn layui-btn-normal" lay-submit lay-filter="formDemo"
                        style="width: 100%; margin-top:50px;">登录
                </button>


    </form>
</div>
<script src="/layui/layui.js"></script>
<script>
    //Demo
    layui.use(['form', 'jquery'], function () {
        var form = layui.form;
        var $ = layui.jquery;
        //监听提交
        form.on('submit(formDemo)', function (data) {
            $.post("/admin/sysUser/login", data.field, function (data) {
                if (data.code === 0) {
                    location.href = data.data;
                } else {
                    layer.msg(data.msg, {icon: 5});
                }
            }, "json");
            return false;
        });
    });
</script>
</body>
</html>
