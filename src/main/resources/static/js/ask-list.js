$(function () {

    $('#ask-list-table').bootstrapTable('hideLoading');
    $("#searchaskButton").click(function () {
        $('#ask-list-table').bootstrapTable("destroy");
        $('#ask-list-table').bootstrapTable({url: "/admin/ask/list?" + $('#searchaskForm').serialize()}).bootstrapTable('hideLoading');
    });

    $("#allButton").click(function () {
        $('#ask-list-table').bootstrapTable("destroy");
        $('#ask-list-table').bootstrapTable({url: "/admin/ask/list?" + $('#searchaskForm').serialize()}).bootstrapTable('hideLoading');
    });

    $("#notcompleteButton").click(function () {
        $('#ask-list-table').bootstrapTable("destroy");
        $('#ask-list-table').bootstrapTable({url: "/admin/ask/list?status=99&" + $('#searchaskForm').serialize()}).bootstrapTable('hideLoading');
    });

    $("#completeButton").click(function () {
        $('#ask-list-table').bootstrapTable("destroy");
        $('#ask-list-table').bootstrapTable({url: "/admin/ask/list?status=100&" + $('#searchaskForm').serialize()}).bootstrapTable('hideLoading');
    });

    $('#ask-list-table').on('click-row.bs.table', function (e, row, element, field) {
        if (field != "id") {
            window.location.href = "/view/purch-list?askid=" + row["id"];
        }
    });

    $("#deleteConfirmButton").click(function () {
        var deleteid = $('#deletevalue').val();
        $.post("/admin/ask/sud",
            {
                askid: deleteid,
                delete: 1
            }, function (result) {
                $('#deletealertModal').modal('toggle');
                $('#ask-list-table').bootstrapTable("refresh");
            });
    });

});

function del(value) {
    $('#deletevalue').val(value);
    $('#deletealertmessage').text("确定要撤回这个记录吗？");
    $('#deletealertModal').modal('toggle');
}


function askformatter(value, row, index) {
    $("#rowoperator [name='deleteoperator']").attr("onclick", "del(" + value + ");");
    return $('#rowoperator').html();
}


function typeformatter(value, row, index) {
    if (value == 1) return "询价";
    if (value == 2) return "打样";
    else if (value == 3) return "采购";
    else return null;
}

function statusformatter(value, row, index) {
    if (value == 1) return "未完结";
    else if (value >= 2) return "已完结";
    else return null;
}
