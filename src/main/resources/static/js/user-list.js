$(function () {

    $('#user-list-table').bootstrapTable('hideLoading');

    $("#createuserButton").click(function () {
        window.location.href = "/view/user-new";
    });

    $("#rolelistButton").click(function () {
        window.location.href = "/view/role-list";
    });

    $("#deleteConfirmButton").click(function () {
        var deleteid = $('#deletevalue').val();
        $.post("/admin/user/sud",
            {
                userid: deleteid,
                delete: 1,
            },
            function (result) {
                $('#deletealertModal').modal('toggle');
                $('#user-list-table').bootstrapTable("refresh").bootstrapTable('hideLoading');
                ;
            });
    });

    $("#searchuserButton").click(function () {
        $('#user-list-table').bootstrapTable("destroy");
        $('#user-list-table').bootstrapTable({url: "/admin/user/list?" + $('#searchuserForm').serialize()}).bootstrapTable('hideLoading');
        ;
    });
});

function update(value) {
    window.location.href = "/view/user-update?userid=" + value;
};

function del(value) {
    $('#deletevalue').val(value);
    $('#deletealertModal').modal('toggle');
};

function userformatter(value, row, index) {
    $("#updateoperator").attr("onclick", "update(" + value + ");");
    $('#deleteoperator').attr("onclick", "del(" + value + ");");
    return $('#rowoperator').html();
}