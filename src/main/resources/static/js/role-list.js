$(function () {

    $('#role-list-table').bootstrapTable('hideLoading');

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

function roleformatter(value, row, index) {
    return "<button type=\"button\" class=\"btn btn-link\" onclick= \"update(" + value + ")\"> 修改</button><button type=\"button\" class=\"btn btn-link\" onclick=\"del(" + value + ")\"> 删除</button>";
}