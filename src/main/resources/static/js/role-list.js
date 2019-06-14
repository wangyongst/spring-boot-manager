$(function () {
    $.get("admin/role/list",
        function (result) {
            if (result.status == 1) {
                $('#user-list-table').bootstrapTable({
                    data: result.data,
                    pagination: true,
                    columns: [{
                        field: 'id',
                        title: 'ID'
                    }, {
                        field: 'name',
                        title: '名称'
                    }, {
                        field: 'name',
                        title: '权限'
                    }, {
                        field: 'id',
                        title: '操作',
                        formatter: function (value, row, index) {
                            return "<button type=\"button\" class=\"btn btn-link\" onclick=\"alert(" + value + ")\"> 修改</button><button type=\"button\" class=\"btn btn-link\" onclick=\"alert(" + value + ")\"> 删除</button>";
                        },
                        align: 'center'
                    }]
                }).bootstrapTable('hideLoading');
            }
        });

    $("#create").click(function () {
        $('#adminUserModal').modal('toggle');
    });


    $("#delete").click(function () {
        var selected = select();
        if (selected == "") {
            alert("请先选择你要删除的记录");
            return;
        }
        var ids = selected.split(",");
        if (ids.length > 2) {
            alert("请选择一条记录");
            return;
        }
        $.ajax({
            type: "POST",
            cache: "false",
            url: "/admin/user/admin",
            data: {
                userid: ids[1],
                type: 2
            },
            dataType: "json",
            success: function (result) {
                if (result.status == 1) {
                    alert("删除记录成功");
                    $("#adminUserTable").bootstrapTable('refresh');
                }
            }
        });
    });

    $("#update").click(function () {
        var selected = select();
        if (selected == "") {
            alert("请先选择你要修改的记录");
            return;
        }
        var ids = selected.split(",");
        if (ids.length > 2) {
            alert("请选择一条记录");
            return;
        }
        $.ajax({
            type: "GET",
            cache: "false",
            url: "/admin/user/admin",
            data: {
                userid: ids[1]
            },
            dataType: "json",
            success: function (result) {
                if (result.status == 1) {
                    $('#userid').val(result.data.id);
                    $('#username').val(result.data.username);
                    $('#password').val(result.data.password);
                    $('#roleid').val(result.data.adminRole.id);
                }
            }
        });
        $('#adminUserModal').modal('toggle');
    });

});
