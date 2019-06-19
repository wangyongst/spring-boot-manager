$(function () {

    $.get("admin/project/search?type=1&customer=11",
        function (result) {
            $.each(result, function (key, val) {
                $('#projectcustomerselect').append("<option value=\"" + val + "\">" + val + "</option>");
            });
        });


    $('#request-list-table').bootstrapTable('hideLoading');

    $("#createrequestButton").click(function () {
        $('#requestModal').modal('toggle');
    });

    $("#rolelistButton").click(function () {
        window.location.href = "role-list.html";
    });

    $("#deleteConfirmButton").click(function () {
        var deleteid = $('#deletevalue').val();
        $.post("admin/user/sud",
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
    window.location.href = "user-update.html?" + value;
};

function del(value) {
    $('#deletevalue').val(value);
    $('#deletealertModal').modal('toggle');
};

function userformatter(value, row, index) {
    return "<button type=\"button\" class=\"btn btn-link\" onclick= \"update(" + value + ")\"> 修改</button><button type=\"button\" class=\"btn btn-link\" onclick=\"del(" + value + ")\"> 删除</button>";
}