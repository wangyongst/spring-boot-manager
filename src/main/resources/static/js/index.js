$(function () {
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


function projectformatter(value, row, index) {
    return "<button type=\"button\" class=\"btn btn-link\" onclick= \"updateproject(" + value + ")\"> 修改</button><button type=\"button\" class=\"btn btn-link\" onclick=\"delproject(" + value + ")\"> 删除</button>";
}