<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>系统管理 | 菜单管理</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <jsp:include page="nav.jsp"/>

    <div class="layui-body" style="left:0; padding: 15px;">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
            <legend>系统管理 - 菜单管理</legend>
        </fieldset>
        <div class="layui-btn-group">
            <a class="layui-btn layui-btn-sm btn-add">
                <i class="layui-icon">&#xe654;</i>
            </a>
            <a class="layui-btn layui-btn-sm btn-update layui-btn-normal">
                <i class="layui-icon">&#xe642;</i>
            </a>
            <a class="layui-btn layui-btn-sm btn-delete layui-btn-danger">
                <i class="layui-icon">&#xe640;</i>
            </a>
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
            url: '/admin/menu/getMenuPage',
            page: true,
            limit: 20,
            cols: [[
                {checkbox: true},
                {field: 'id', title: 'ID', width: 80},
                {field: 'name', title: '名称', width: 120},
                {field: 'url', title: 'URL', width: 300},
                {field: 'icon', title: '图标', width: 100},
                {field: 'parentId', title: '上级ID', width: 100},
                {field: 'orderNo', title: '排序号', width: 100},
                {field: 'updateTime', title: '更新时间', width: 200},
                {field: 'createTime', title: '创建时间', width: 200}
            ]]
        });

        $(".btn-add").on("click", function () {
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
                content: '/admin/menu/editMenuView?id=' + id,
                title: '编辑菜单',
                area: ['600px', '400px'],
                end: function () {
                    location.reload();
                }
            });
        });

        $(".btn-delete").on("click", function () {
            var checkData = table.checkStatus('menu').data; //test即为基础参数id对应的值
            if (checkData.length < 1) {
                layer.msg("请选中要删除的数据！");
                return;
            }
            layer.confirm('您确定要删除这' + checkData.length + '条数据吗？', {
                btn: ['确认', '取消']
            }, function (index, layero) {
                layer.close(index);
                var ids = new Array();
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
        });


    });
</script>
</body>
</html>