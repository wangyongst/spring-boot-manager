$(function () {

    $('#role-list-table').bootstrapTable('hideLoading');

    $("#createroleButton").click(function () {
        window.location.href = "/view/role-new";
    });


    $("#deleteConfirmButton").click(function () {
        $('#deletealertModal').modal('toggle');
        $.post("/admin/role/sud",
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
    window.location.href = "/view/role-update?" + value;
};

function del(value) {
    $('#deletevalue').val(value);
    $('#deletealertModal').modal('toggle');
};

function roleformatter(value, row, index) {
    $("#updateoperator").attr("onclick", "update(" + value + ");");
    $('#deleteoperator').attr("onclick", "del(" + value + ");");
    return $('#rowoperator').html();
    // return "<button type=\"button\" class=\"btn btn-link\" onclick= \"update(" + value + ")\"> 修改</button><button type=\"button\" class=\"btn btn-link\" onclick=\"del(" + value + ")\"> 删除</button>";
}