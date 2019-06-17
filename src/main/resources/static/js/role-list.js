$(function () {
    $.get("admin/role/list",
        function (result) {
            if (result.status == 1) {
                $('#user-list-table').bootstrapTable({
                    data: result.data,
                    pagination: true,
                    pageSize: 8,
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
                            return "<button type=\"button\" class=\"btn btn-link\" onclick= \"update(" + value + ")\"> 修改</button><button type=\"button\" class=\"btn btn-link\" onclick=\"del(" + value + ")\"> 删除</button>";
                        },
                        align: 'center'
                    }]
                }).bootstrapTable('hideLoading');
            }
        });

    $("#createroleButton").click(function () {
        window.location.href = "role-new.html";
    });


    $("#deleteConfirmButton").click(function () {
        $('#deletealertModal').modal('toggle');
        $.post("admin/role/sud",
            {
                roleid: $('#deletevalue').val(),
                delete: 1,
            },
            function (result) {
                window.location.reload();
            });
    });

});

function update(value) {
    window.location.href = "role-update.html?" + value;
};

function del(value) {
    $('#deletevalue').val(value);
    $('#deletealertModal').modal('toggle');
};