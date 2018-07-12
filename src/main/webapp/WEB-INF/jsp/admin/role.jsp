<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>系统管理 | 角色管理</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
</head>

<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <jsp:include page="nav.jsp"/>

    <div class="layui-body" style="left:0; padding: 15px;">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
            <legend>系统管理 - 角色管理</legend>
        </fieldset>

        <div class="layui-btn-group">
            <button class="layui-btn layui-btn-sm btn-add">
                <i class="layui-icon">&#xe654;</i>
            </button>
            <button class="layui-btn layui-btn-sm btn-update">
                <i class="layui-icon">&#xe642;</i>
            </button>
        </div>

        <table id="table">
        </table>
    </div>
</div>

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
            limit: 20,
            cols: [[
                {checkbox: true},
                {field: 'id', title: 'ID', width: 80},
                {field: 'name', title: '名称', width: 150},
                {field: 'des', title: '描述', width: 400},
                {field: 'updateTime', title: '创建时间', width: 200},
                {field: 'createTime', title: '创建时间', width: 200}
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
            var checkData = table.checkStatus('menu').data;
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