$(function () {
    $('#bill-list-table').bootstrapTable('hideLoading');

    $('#bill-list-table').on('click-row.bs.table', function (e, row, element, field) {
        window.location.href = "/view/billdetail-list?billid=" + row["id"];
    });

    $("#searchbillButton").click(function () {
        $('#bill-list-table').bootstrapTable("destroy");
        $('#bill-list-table').bootstrapTable({url: "/admin/bill/list?" + $('#searchaskForm').serialize()}).bootstrapTable('hideLoading');
    });
});


function statusformatter(value, row, index) {
    if (value == 1) return "未出账";
    else if (value == 2) return "已出账";
    else if (value == 3) return "已完结";
    else return null;
}