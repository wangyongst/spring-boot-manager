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
    window.location.href = "/view/role-update?roleid=" + value;
};

function del(value) {
    $('#deletevalue').val(value);
    $('#deletealertModal').modal('toggle');
};

function roleformatter(value, row, index) {
    $("#rowoperator [name='updateoperator']").attr("onclick", "update(" + value + ");");
    $("#rowoperator [name='deleteoperator']").attr("onclick", "del(" + value + ");");
    return $('#rowoperator').html();
}

function permissionformatter(value, row, index) {
    var html = "";
    if (value == null || value.length == 0) return html;
    else {
        for (let i in value) {
            if (value[i].permission.parentid == 0) {
                html = html + "," + value[i].permission.cname;
            }
        }
        return html.substr(1);
    }
}