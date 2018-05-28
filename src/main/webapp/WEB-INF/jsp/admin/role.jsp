<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>系统管理 | 角色管理</title>
    <link rel="stylesheet" href="/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="/css/base.css">
</head>
<body>
<jsp:include page="nav.jsp"></jsp:include>
<div class="content-body">
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
        <legend>系统管理 - 角色管理</legend>
    </fieldset>

    <div class="layui-btn-group">
        <button class="layui-btn layui-btn-sm btn-add">
            <i class="layui-icon">&#xe654;</i>
        </button>
        <button class="layui-btn layui-btn-sm btn-update layui-btn-danger">
            <i class="layui-icon">&#xe642;</i>
        </button>
    </div>

    <table id="table">
    </table>
</div>


<script src="/layui/layui.js"></script>
<script>
    layui.use(['element', 'table', 'jquery'], function () {
        var element = layui.element;
        var table = layui.table;
        var $ = layui.jquery;
        table.render({
            elem: '#table',
            id: "menu",
            url: '/admin/role/getRolePage',
            page: true,
            height: 'full-200',
            limit: 20,
            cols: [[
                {checkbox: true}
                , {field: 'id', title: 'ID', width: 80}
                , {field: 'name', title: '名称', width: 150}
                , {field: 'des', title: '描述', width: 400}
                , {field: 'updateTime', title: '创建时间', width: 200}
                , {field: 'createTime', title: '创建时间', width: 200}
            ]]
        });

        $(".btn-add").on("click", function () {
            layer.open({
                formType: 2,
                type: 2,
                content: '/admin/role/addRoleView',
                title: '添加角色',
                area: ['800px', '600px'],
                end: function () {
                    location.reload();
                }
            });
        });

        $(".btn-update").on("click", function () {
            var checkData = table.checkStatus('menu').data; //test即为基础参数id对应的值
            if (checkData.length != 1) {
                layer.msg("请选中一条记录编辑！");
                return;
            }
            var id = checkData[0].id;
            layer.open({
                formType: 2,
                type: 2,
                content: '/admin/role/editRoleView?id=' + id,
                title: '编辑角色',
                area: ['800px', '600px'],
                end: function () {
                    location.reload();
                }
            });
        });

    });
</script>
</body>
</html>