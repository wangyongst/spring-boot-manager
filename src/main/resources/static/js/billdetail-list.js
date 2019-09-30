$(function () {
    $('#billdetail-list-table').bootstrapTable('hideLoading');

    $("#billnosaveButton").click(function () {
        $.post("/admin/billdetail/sud", $('#billdetailForm').serialize()
            , function (result) {
                $('#billdetailModal').modal('toggle');
                $('#billdetail-list-table').bootstrapTable("refresh").bootstrapTable('hideLoading');
            });
    });

    $("#allButton").click(function () {
        $('#billdetail-list-table').bootstrapTable("destroy");
        $('#billdetail-list-table').bootstrapTable({url: "/admin/billdetail/list?billid=" + $('#billidvalue').val() }).bootstrapTable('hideLoading');
    });

    $("#notbilldetailButton").click(function () {
        $('#billdetail-list-table').bootstrapTable("destroy");
        $('#billdetail-list-table').bootstrapTable({url: "/admin/billdetail/list?billid=" + $('#billidvalue').val() + "&status=2"}).bootstrapTable('hideLoading');
    });

    $("#billdetailButton").click(function () {
        $('#billdetail-list-table').bootstrapTable("destroy");
        $('#billdetail-list-table').bootstrapTable({url: "/admin/billdetail/list?billid=" + $('#billidvalue').val() + "&status=1"}).bootstrapTable('hideLoading');
    });

    $("#completeButton").click(function () {
        $('#billdetail-list-table').bootstrapTable("destroy");
        $('#billdetail-list-table').bootstrapTable({url: "/admin/billdetail/list?billid=" + $('#billidvalue').val() + "&status=3"}).bootstrapTable('hideLoading');
    });


    $("#searchbillButton").click(function () {
        $('#billdetail-list-table').bootstrapTable("destroy");
        $('#billdetail-list-table').bootstrapTable({url: "/admin/billdetail/list?"+ $('#searchaskForm').serialize() + "&billid=" + $('#billidvalue').val() }).bootstrapTable('hideLoading');
    });
});


function billdetailformatter(value, row, index) {
    if (row["status"] == 2) {
        $("#rowoperator [name='updateoperator']").attr("onclick", "update(" + value + ");");
        return $('#rowoperator').html();
    }else return null;
}

function moneyformatter(value, row, index) {
    var acceptprice = row["purch"]["acceptprice"];
    var acceptnum = row["purch"]["acceptnum"];
    var out = acceptprice * acceptnum;
    if (isNaN(out)) return 0;
    else return out.toFixed(2);
}


function update(value) {
    $("#billdetailid").val(value);
    $('#billdetailModal').modal('toggle');
}

function statusformatter(value, row, index) {
    if (value == 1) return "未出账";
    else if (value == 2) return "已出账";
    else if (value == 3) return "已完结";
    else return null;
}