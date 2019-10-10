$(function () {

    initPage();

    $("#requestsaveButton").click(function () {
        $.post("/admin/request/sud", $('#requestForm').serialize(),
            function (result) {
                if (result.status == 1) {
                    $('#requestModal').modal('toggle');
                    $('#request-list-table').bootstrapTable("refresh").bootstrapTable('hideLoading');
                } else {
                    $('#alertmessage').text(result.message);
                    $('#alertModal').modal('toggle');
                }
            });
    });

    $("#createrequestButton").click(function () {
        clearForm($('#requestForm'));
        $('#requestidhidden').val(0);
        $('#requestModal').modal('toggle');
    });

    $("#settingButton").click(function () {
        $('#settingModal').modal('toggle');
    });

    $("#settingsaveButton").click(function () {
        $.post("/admin/setting/sud?type=1", $('#settingForm').serialize(),
            function (result) {
                if (result.status == 1) {
                    $('#settingModal').modal('toggle');
                } else {
                    $('#alertmessage').text(result.message);
                    $('#alertModal').modal('toggle');
                }
            });
    });

    $("#askPriceButton").click(function () {
        var selected = select();
        if (selected.length == 0) {
            $('#alertmessage').text("请先选择你要发起的记录");
            $('#alertModal').modal('toggle');
            return;
        }
        $.post("/admin/request/ask", {
                ids: selected,
                type: 1
            },
            function (result) {
                if (result.status == 1) {
                    $('#alertmessage').text("发起询价成功，等待供应商报价");
                    $('#alertModal').modal('toggle');
                    $('#request-list-table').bootstrapTable("refresh").bootstrapTable('hideLoading');
                } else {
                    $('#alertmessage').text(result.message);
                    $('#alertModal').modal('toggle');
                }
            });
    });


    $("#askExampleButton").click(function () {
        var selected = select();
        if (selected.length == 0) {
            $('#alertmessage').text("请先选择你要发起的记录");
            $('#alertModal').modal('toggle');
            return;
        }
        $.post("/admin/request/ask", {
                ids: selected,
                type: 2
            },
            function (result) {
                if (result.status == 1) {
                    $('#alertmessage').text("发起打样成功，等待供应商送货");
                    $('#alertModal').modal('toggle');
                    $('#request-list-table').bootstrapTable("refresh").bootstrapTable('hideLoading');
                } else {
                    $('#alertmessage').text(result.message);
                    $('#alertModal').modal('toggle');
                }
            });
    });


    $("#askButton").click(function () {
        var selected = select();
        if (selected.length == 0) {
            $('#alertmessage').text("请先选择你要发起的记录");
            $('#alertModal').modal('toggle');
            return;
        }
        $.post("/admin/request/ask", {
                ids: selected,
                type: 3
            },
            function (result) {
                if (result.status == 1) {
                    $('#alertmessage').text("发起采购成功，等待供应商报价");
                    $('#alertModal').modal('toggle');
                    $('#request-list-table').bootstrapTable("refresh").bootstrapTable('hideLoading');
                } else {
                    $('#alertmessage').text(result.message);
                    $('#alertModal').modal('toggle');
                }
            });
    });


    $("#projectnameselect").change(function () {
        if ($('#projectnameselect').val() == "未选择") {
            $('#projectcustomer').val("");
            $('#materialcodeselect').html("");
            $('#materialname').val("")
            $('#size').val("");
            $('#special').val("");
            $('#model').val("");
        }
        $.get("/admin/resource/list?name2=" + $('#projectnameselect').val(),
            function (result) {
                $('#projectcustomer').val(result[0].project.customer)
                $('#materialcodeselect').html("");
                $('#materialcodeselect').append("<option value=\"0\">请选择耗材编号</option>");
                $.each(result, function (key, val) {
                    $('#materialcodeselect').append("<option value=\"" + val.id + "\">" + val.code + "</option>");
                });
            });
    });

    $("#materialcodeselect").change(function () {
        $.get("/admin/resource?resourceid=" + $('#materialcodeselect').val(),
            function (result) {
                $('#materialname').val(result.data.material.name)
                $('#size').val(result.data.size);
                $('#special').val(result.data.special);
                $('#model').val(result.data.model);
            });
    });

    $("#deleteConfirmButton").click(function () {
        var deleteid = $('#deletevalue').val();
        $.post("/admin/request/sud",
            {
                requestid: deleteid,
                delete: 1,
            },
            function (result) {
                $('#deletealertModal').modal('toggle');
                $('#request-list-table').bootstrapTable("refresh").bootstrapTable('hideLoading');
            });
    });

    $("#price").change(function () {
        var sellnum = $("#sellnum").val();
        var price = $("#price").val();
        $("#total").val(sellnum * price);
    });

    $("#sellnum").change(function () {
        var sellnum = $("#sellnum").val();
        var price = $("#price").val();
        $("#total").val(sellnum * price);
    });


    $("#searchrequestButton").click(function () {
        $('#request-list-table').bootstrapTable("destroy");
        $('#request-list-table').bootstrapTable({url: "/admin/request/list?" + $('#searchrequestForm').serialize()}).bootstrapTable('hideLoading');
    });
});

function update(value) {
    clearForm($('#requestForm'));
    $.get("/admin/request?requestid=" + value,
        function (result) {
            if (result.status == 1) {
                $('#requestidhidden').val(result.data.id);
                $('#projectnameselect').val(result.data.resource.project.name);
                $('#projectcustomer').val(result.data.resource.project.customer);
                $('#materialcodeselect').html("<option value=\"" + result.data.resource.id + "\">" + result.data.resource.material.code + "</option>");
                $('#materialname').val(result.data.resource.material.name);
                $('#size').val(result.data.resource.size);
                $('#special').val(result.data.resource.special);
                $('#model').val(result.data.resource.model);
                $('#num').val(result.data.num);
                $('#sellnum').val(result.data.sellnum);
                $('#price').val(result.data.price);
                $('#total').val(result.data.total);
            }
        });
    $('#requestModal').modal('toggle');
};

function del(value) {
    $('#deletevalue').val(value);
    $('#deletealertmessage').text("确定要取消这个申请吗？");
    $('#deletealertModal').modal('toggle');
}

function showfile(value) {
    window.open(getWebRootPath() + "/file/" + value);
}

function requestformatter(value, row, index) {
    if (row["status"] < 4) {
        $("#rowoperator [name='deleteoperator']").attr("onclick", "del(" + value + ");");
        return $('#rowoperator').html();
    }
}

function typeformatter(value, row, index) {
    if (value == 1) return "询价";
    if (value == 2) return "打样";
    else if (value == 3) return "采购";
    else return null;
}

function statusformatter(value, row, index) {
    if (value == 1) return "待报价";
    else if (value == 2 && row["type"] == 1) return "已报价";
    else if (value == 2 && row["type"] != 1) return "待审核";
    else if (value == 3) return "待接单";
    else if (value == 4) return "下单失败";
    else if (value == 0) return "取消订单";
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

function fileformatter(value, row, index) {
    if (value == null) return value;
    return "<button type=\"button\" class=\"btn btn-link\" onclick= \"showfile('" + value + "')\">" + value + "</button>";
}

function initPage() {

    $('#request-list-table').bootstrapTable('hideLoading');

    $.get("/admin/resource/list?type=3",
        function (result) {
            $('#projectnameselect').html("");
            $('#projectnameselect').append("<option value=\"未选择\">请选择项目名称</option>");
            $.each(result, function (key, val) {
                $('#projectnameselect').append("<option value=\"" + val + "\">" + val + "</option>");
            });
        });

    $.get("/admin/project/list?type=3",
        function (result) {
            $('#projectnameselect2').html("");
            $('#projectnameselect2').append("<option value=\"\">请选择项目名称</option>");
            $.each(result, function (key, val) {
                $('#projectnameselect2').append("<option value=\"" + val + "\">" + val + "</option>");
            });
        });


    $.get("/admin/material/list?type=3",
        function (result) {
            $('#materialnameselect2').html("");
            $('#materialnameselect2').append("<option value=\"\">请选择耗材类型</option>");
            $.each(result, function (key, val) {
                $('#materialnameselect2').append("<option value=\"" + val + "\">" + val + "</option>");
            });
        });
}
