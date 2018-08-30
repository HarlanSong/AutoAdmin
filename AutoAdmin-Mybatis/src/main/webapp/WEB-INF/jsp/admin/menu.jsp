<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>菜单管理</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <jsp:include page="nav.jsp"/>

    <div  class="layui-body" style="padding: 15px;bottom: 0">
        <div class="layui-card">
            <div class="layui-card-header">菜单管理</div>
            <div class="layui-card-body">
                <table class="layui-hide"  id="table" lay-filter="menu">
                </table>
            </div>
        </div>
    </div>
</div>

<script type="text/html" id="toolBar">
    <div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm" lay-event="add">
            <i class="layui-icon">&#xe654;</i>
        </button>
        <button class="layui-btn layui-btn-sm layui-btn-normal" lay-event="update">
            <i class="layui-icon">&#xe642;</i>
        </button>
        <button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete">
            <i class="layui-icon">&#xe640;</i>
        </button>
    </div>
</script>

<script>
    layui.use(['element', 'table', 'jquery'], function () {
        var element = layui.element;
        var table = layui.table;
        var $ = layui.jquery;
        table.render({
            elem: '#table',
            toolbar: "#toolBar",
            url: '/admin/menu/getMenuPage',
            page: true,
            limit: 20,
            cols: [[
                {checkbox: true},
                {field: 'id', title: 'ID', width: 80},
                {field: 'name', title: '名称', width: 120},
                {field: 'url', title: 'URL'},
                {field: 'icon', title: '图标', width: 150},
                {field: 'parentId', title: '上级ID', width: 100},
                {field: 'orderNo', title: '排序号', width: 100},
                {field: 'updateTime', title: '更新时间', width: 200},
                {field: 'createTime', title: '创建时间', width: 200}
            ]]
        });

        table.on('toolbar(menu)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            switch (obj.event) {
                case 'add':
                    layer.open({
                        formType: 2,
                        type: 2,
                        content: '/admin/menu/addMenuView',
                        title: '添加菜单',
                        area: ['600px', '400px'], //自定义文本域宽高
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
                        content: '/admin/menu/updateMenuView?id=' + id,
                        title: '编辑菜单',
                        area: ['600px', '400px'],
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
                        $.post("/admin/menu/deleteMenu", {ids: ids.join(",")}, function (data) {
                            if (data.code === 0) {
                                layer.msg('删除成功', {icon: 1});
                                location.reload();
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