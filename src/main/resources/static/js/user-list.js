$(function () {
    $.get("admin/user/list",
        function (result) {
            if (result.status == 1) {
                $('#user-list-table').bootstrapTable({
                    data: result.data,
                    pagination: true,
                    search: true,
                    refresh: true,
                    columns: [{
                        field: 'id',
                        title: 'ID'
                    }, {
                        field: 'name',
                        title: '名称'
                    }, {
                        field: 'mobile',
                        title: '电话'
                    }, {
                        field: 'role.name',
                        title: '角色'
                    }, {
                        field: 'createusername',
                        title: '创建人'
                    }, {
                        field: 'createtime',
                        title: '创建时间'
                    }, {
                        field: 'id',
                        title: '操作',
                        formatter: function (value, row, index) {
                            return "<button type=\"button\" class=\"btn btn-link\" onclick= \"update(" + value + ")\"> 修改</button><button type=\"button\" class=\"btn btn-link\" onclick=\"del(" + value + ")\"> 删除</button>";
                        },
                        align: 'center'
                    }]
                }).bootstrapTable('hideLoading');
            }
        });

    $("#createuserButton").click(function () {
        window.location.href = "user-new.html";
    });

    $("#rolelistButton").click(function () {
        window.location.href = "role-list.html";
    });

    $("#deleteConfirmButton").click(function () {
        var deleteid = $('#deletevalue').val();
        $('#deletealertModal').modal('toggle');
        $.post("admin/user/sud",
            {
                userid: deleteid,
                delete: 1,
            },
            function (result) {
                window.location.reload();
            });
    });

});

function update(value) {
    window.location.href = "user-update.html?" + value;
};

function del(value) {
    $('#deletevalue').val(value);
    $('#deletealertModal').modal('toggle');
};