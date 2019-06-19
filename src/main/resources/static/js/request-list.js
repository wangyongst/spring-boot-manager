$(function () {


    $('#request-list-table').bootstrapTable('hideLoading');

    $.get("admin/project/search",
        function (result) {
            $.each(result, function (key, val) {
                $('#projectcustomerselect').append("<option value=\"" + val + "\">" + val + "</option>");
            });
        });

    $("#createrequestButton").click(function () {
        $('#requestModal').modal('toggle');
    });

    $("#projectcustomerselect").change(function () {
        $.get("admin/project/search?type=1&customer=" + $('#projectcustomerselect').val(),
            function (result) {
                $.each(result, function (key, val) {
                    $('#projectnameselect').html("<option value=\"" + val + "\">" + val + "</option>");
                });
            });
    });


    $("#projectnameselect").change(function () {
        $.get("admin/request/search?type=1&name=" + $('#projectnameselect').val(),
            function (result) {
                $.each(result, function (key, val) {
                    $('#materialnameselect').html("<option value=\"" + val + "\">" + val + "</option>");
                });
            });
    });

    $("#projectnameselect").change(function () {
        $.get("admin/project/search?type=1&customer=" + $('#projectcustomerselect').val(),
            function (result) {
                $.each(result, function (key, val) {
                    $('#projectnameselect').append("<option value=\"" + val + "\">" + val + "</option>");
                });
            });
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