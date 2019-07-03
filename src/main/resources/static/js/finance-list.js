$(function () {

    $('#finance-list-table').bootstrapTable('hideLoading');

    $("#searchaskButton").click(function () {
        $('#purch-list-table').bootstrapTable("destroy");
        $('#purch-list-table').bootstrapTable({url: "/admin/purch/list?" + $('#searchaskForm').serialize()}).bootstrapTable('hideLoading');
    });
});


function ok(value) {
    alert(value);
};

function askformatter(value, row, index) {
    $("#rowoperator [name='updateoperator']").attr("onclick", "ok(" + value + ");");
    return $('#rowoperator').html();
}