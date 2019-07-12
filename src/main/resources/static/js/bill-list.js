$(function () {
    $('#bill-list-table').bootstrapTable('hideLoading');

    $('#bill-list-table').on('click-row.bs.table', function (e, row, element, field) {
            window.location.href = "/view/billdetail-list?billid=" + row["id"];
    });
});