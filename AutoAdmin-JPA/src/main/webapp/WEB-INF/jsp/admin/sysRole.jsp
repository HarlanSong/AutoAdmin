<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>系统角色</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
</head>

<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <jsp:include page="nav.jsp"/>

    <div class="layui-body" style="padding: 10px;bottom: 0">
        <div class="layui-card">
            <div class="layui-card-header">系统角色</div>
            <div class="layui-card-body">
                <table id="table" lay-filter="sys-role">
                </table>
            </div>
        </div>
    </div>
</div>

<script type="text/html" id="toolBar">
    <div class="layui-btn-container">
        <a class="layui-btn layui-btn-sm" lay-event="add">
            <i class="layui-icon">&#xe654;</i>
        </a>
        <a class="layui-btn layui-btn-sm layui-btn-normal" lay-event="update">
            <i class="layui-icon">&#xe642;</i>
        </a>
        <a class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete">
            <i class="layui-icon">&#xe640;</i>
        </a>
    </div>
</script>

<script>
    layui.use(['element', 'table', 'jquery'], function () {
        var element = layui.element;
        var table = layui.table;
        var $ = layui.jquery;
        table.render({
            elem: '#table',
            id: "sys-role-table",
            toolbar: "#toolBar",
            url: '/admin/sysRole/getSysRolePage',
            page: true,
            limit: 20,
            cols: [[
                {checkbox: true},
                {field: 'name', title: '名称'},
                {field: 'des', title: '描述'},
                {field: 'updateTime', title: '创建时间'},
                {field: 'createTime', title: '创建时间'}
            ]]
        });
        table.on('toolbar(sys-role)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            switch (obj.event) {
                case 'add':
                    layer.open({
                        formType: 2,
                        type: 2,
                        content: '/admin/sysRole/addSysRoleView',
                        title: '添加角色',
                        area: ['800px', '600px'],
                        end: function () {
                            location.reload();
                        }
                    });
                    break;
                case 'update':
                    var checkData = checkStatus.data;
                    if (checkData.length != 1) {
                        layer.msg("请选中一条记录编辑！");
                        return;
                    }
                    var id = checkData[0].id;
                    layer.open({
                        formType: 2,
                        type: 2,
                        content: '/admin/sysRole/updateSysRoleView?id=' + id,
                        title: '编辑角色',
                        area: ['800px', '600px'],
                        end: function () {
                            location.reload();
                        }
                    });
                    break;
                case 'delete':
                    var checkData = checkStatus.data;
                    if (checkData.length < 1) {
                        layer.msg("请选中要删除的数据！");
                        return;
                    }
                    layer.confirm('您确定要删除这' + checkData.length + '条数据吗？', {
                        btn: ['确认', '取消']
                    }, function (index) {
                        layer.close(index);
                        var ids = [];
                        $.each(checkData, function (index, val) {
                            ids.push(val.id);
                        });
                        $.post("/admin/sysRole/deleteSysRole", {ids: ids.join(",")}, function (data) {
                            if (data.code === 0) {
                                layer.msg('删除成功', {icon: 1});
                                location.reload();
                                table.reload("sys-role-table");
                            } else {
                                layer.alert(data.msg, {icon: 5});
                            }
                        }, "json");
                    });
                    break;
            }
        });
    });
</script>
</body>
</html>