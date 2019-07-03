$(function () {

    $('#finance-list-table').bootstrapTable('hideLoading');

    $("#searchaskButton").click(function () {
        $('#purch-list-table').bootstrapTable("destroy");
        $('#purch-list-table').bootstrapTable({url: "/admin/purch/list?" + $('#searchaskForm').serialize()}).bootstrapTable('hideLoading');
    });

    $("#settingButting").click(function () {
        $('#settingModal').modal('toggle');
    });

    $("#exportpurchButton").click(function () {
        $('#settingModal').modal('toggle');
    });

});

function confirm(value) {
   $.post("/admin/purch/coc?purchid="+value,function (result) {
       $('#finance-list-table').bootstrapTable('refresh').bootstrapTable('hideLoading');
   });

}



function financeformatter(value, row, index) {
    $("#rowoperator [name='updateoperator']").attr("onclick", "confirm(" + row["id"]+");");
    $("#rowoperator2 [name='updateoperator']").attr("onclick", "confirm(" + row["id"]+");");
    if (value != null) {
        if (value == 3) return $('#rowoperator').html();
        else if (value == 4) {
            return $('#rowoperator2').html();
        }
        else return null;
    }
}

function yingshouformatter(value, row, index) {
    var price = row["ask"]["request"]["price"];
    var sellnum = row["ask"]["request"]["sellnum"];
    var out = price * sellnum;
    if (isNaN(out)) return 0;
    else return out;
}

function yingfuformatter(value, row, index) {
    debugger;
    var acceptprice = row["acceptprice"];
    var acceptnum = row["acceptnum"];
    var out = acceptprice * acceptnum;
    if (isNaN(out)) return 0;
    else return out;
}

function statusformatter(value, row, index) {
    if (value == 1) return "待接单";
    else if (value == 2) return "待报价";
    else if (value == 3) return "待审核";
    else if (value == 4) return "待收货";
    else if (value == 5) return "待收货";
    else if (value == 6) return "待确定";
    else if (value == 7) return "待出账";
    else if (value == 8) return "已出账";
    else if (value == 9) return "完结";
    else return null;
}