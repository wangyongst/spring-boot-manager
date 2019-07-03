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

function dateformatter(value, row, index) {
   if(value != null) return value.substr(0,10);
   else return null;
}

function financeformatter(value, row, index) {
    if(value != null){
        if(value == 3) return "确定";
        else if(value ==4 ) return "取消";
    };
}

function statusformatter(value, row, index) {
    if (value == 1) return "待接单";
    else if (value == 2) return "待报价";
    else if (value == 3) return "待审核";
    else if (value == 4) return "生产中";
    else if (value == 5) return "待收货";
    else if (value == 6) return "待确定";
    else if (value == 7) return "待出账";
    else if (value == 8) return "已出账";
    else if (value == 9) return "完结";
    else return null;
}