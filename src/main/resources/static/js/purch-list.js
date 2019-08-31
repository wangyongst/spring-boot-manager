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

    $("#deleteConfirmButton").click(function () {
        var deleteid = $('#deletevalue').val();
        $.post("/admin/purch/sud",
            {
                purchid: deleteid,
                delete: 1,
            },
            function (result) {
                $('#deletealertModal').modal('toggle');
                $('#purch-list-table').bootstrapTable("refresh").bootstrapTable('hideLoading');
            });
    });

    $("#searchpurchButton").click(function () {
        $('#purch-list-table').bootstrapTable("destroy");
        $('#purch-list-table').bootstrapTable({url: "/admin/purch/list?" + $('#searchaskForm').serialize() + "&askid=" + $('#myaskid').val()}).bootstrapTable('hideLoading');
    });

});

function del(value) {
    $('#deletevalue').val(value);
    $('#deletealertmessage').text("确定要撤回这个是询价单吗？");
    $('#deletealertModal').modal('toggle');
}

function typeformatter(value, row, index) {
    if (value == 1) return "询价";
    if (value == 2) return "打样";
    else if (value == 3) return "采购";
    else return null;
}

function priceformatter(value, row, index) {
    if (value == 4) return null;
    else if (value < 3) return "未接单";
    else if (value >= 3) return "已接单";
}

function statusformatter(value, row, index) {
    if (value == 1) return "待报价";
    else if (value == 2) return "待审核";
    else if (value == 3) return "待接单";
    else if (value == 4) return "已失效";
    else if (value == 5) return "生产中";
    else if (value == 7) return "已完成";
    else if (value == 8) return "待出账";
    else if (value == 9) return "完结";
    else return null;
}

function purchformatter(value, row, index) {
    $("#rowoperator [name='deleteoperator']").attr("onclick", "del(" + value + ");");
    return $('#rowoperator').html();
}

function priceformatter(value, row, index) {
    if (value != null || value == 0) return row["acceptprice"] * $('#timesvalue').val();
    return value;
}