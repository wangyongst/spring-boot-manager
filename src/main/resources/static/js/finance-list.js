$(function () {

    $('#finance-list-table').bootstrapTable('hideLoading');

    $("#searchfinanceButton").click(function () {
        $('#finance-list-table').bootstrapTable("destroy");
        $('#finance-list-table').bootstrapTable({url: "/admin/finance/history?" + $('#searchfinanceForm').serialize()}).bootstrapTable('hideLoading');
    });

    $("#settingsaveButton").click(function () {
        $.post("/admin/setting/sud?" + $('#settingForm').serialize() + "&type=2",
            function (result) {
                if (result.status == 1) {
                    $('#settingModal').modal('toggle');
                }
            });
    });

    $("#settingButting").click(function () {
        $('#settingModal').modal('toggle');
    });

    $("#exportpurchButton").click(function () {
        var formData = $('#searchfinanceForm');
        formData.attr("action", "/admin/finance/export").attr("method", "get");
        formData.submit();
    });

});

function confirm(value) {
    $.post("/admin/purch/coc?purchid=" + value,
        function (result) {
            if (result.status == 1) {
                $('#finance-list-table').bootstrapTable('refresh').bootstrapTable('hideLoading');
            } else {
                $('#alertmessage').text(result.message);
                $('#alertModal').modal('toggle');
            }
        });
}


function financeformatter(value, row, index) {
    $("#rowoperator [name='updateoperator']").attr("onclick", "confirm(" + row["id"] + ");");
    if (value == 4) {
        return $('#rowoperator').html();
    }
}

function yingshouformatter(value, row, index) {
    var price = row["ask"]["request"]["price"];
    var sellnum = row["ask"]["request"]["sellnum"];
    var out = price * sellnum;
    if (isNaN(out)) return 0;
    else return out.toFixed(2);
}

function yingfuformatter(value, row, index) {
    debugger;
    var acceptprice = row["acceptprice"];
    var acceptnum = row["acceptnum"];
    var out = acceptprice * acceptnum;
    if (isNaN(out)) return 0;
    else return out.toFixed(2);
}

function statusformatter(value, row, index) {
    if (value == 1) return "待报价";
    else if (value == 2) return "待审核";
    else if (value == 3) return "待接单";
    else if (value == 4) return "待接单（已过期）";
    else if (value == 5) return "生产中";
    else if (value == 6) return "送货中";
    else if (value == 7) return "已完成";
    else if (value == 8) return "待出账";
    else if (value == 9) return "完结";
    else return null;
}

function sizeformatter(value, row, index) {
    return "<div style='width:250px;'>" + value + "</div>";
}