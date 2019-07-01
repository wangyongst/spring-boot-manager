$(function () {

    $('#purch-list-table').bootstrapTable('hideLoading');

    $.get("/admin/material/list?type=3",
        function (result) {
            $('#materialnameselect').html("");
            $('#materialnameselect').append("<option value=\"\">请选择耗材类型</option>");
            $.each(result, function (key, val) {
                $('#materialnameselect').append("<option value=\"" + val + "\">" + val + "</option>");
            });
        });

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