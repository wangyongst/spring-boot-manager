$(function () {
    $('#billdetail-list-table').bootstrapTable('hideLoading');

    $("#billnosaveButton").click(function () {
        $.post("/admin/billdetail/sud", $('#billdetailForm').serialize()
            , function (result) {
                $('#billdetailModal').modal('toggle');
                $('#billdetail-list-table').bootstrapTable("refresh").bootstrapTable('hideLoading');
                ;
            });
    });
});


function billdetailformatter(value, row, index) {
    if (row["status"] == 2) {
        $("#rowoperator [name='updateoperator']").attr("onclick", "update(" + value + ");");
        return $('#rowoperator').html();
    }else return null;
}

function moneyformatter(value, row, index) {
    if (row["status"] == 2) {
        $("#rowoperator [name='updateoperator']").attr("onclick", "update(" + value + ");");
        return $('#rowoperator').html();
    }else return 0;
}


function update(value) {
    $("#billdetailid").val(value);
    $('#billdetailModal').modal('toggle');
}

function statusformatter(value, row, index) {
    if (value == 1) return "已出账";
    else if (value == 2) return "未完结";
    else if (value == 3) return "已完结";
    else return null;
}