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
    });
});

function update(value) {
    window.location.href = "/view/user-update?userid=" + value;
};

function del(value) {
    $('#deletevalue').val(value);
    $('#deletealertModal').modal('toggle');
};

function roleformatter(value, row, index) {
    if(row["role"] == null){
        if(row["deliver"] != null) return "收货员";
        if(row["supplier"] != null ) return "供应商";
    }else{
        return value;
    }
}

function userformatter(value, row, index) {
    $("#rowoperator [name='updateoperator']").attr("onclick", "update(" + value+");");
    $("#rowoperator [name='deleteoperator']").attr("onclick", "del(" + value+");");
    return $('#rowoperator').html();
}