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

    $("#projectcustomerselect").change(function () {
        $.get("/admin/project/list?type=2&customer=" + $('#projectcustomerselect').val(),
            function (result) {
                $('#projectnameselect').html("");
                $('#projectnameselect').append("<option value=\"\">请选择项目名称</option>");
                $.each(result, function (key, val) {
                    $('#projectnameselect').append("<option value=\"" + val + "\">" + val + "</option>");
                });
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
                ids: selected
            },
            function (result) {
                $('#request-list-table').bootstrapTable('hideLoading');
                $('#alertmessage').text("发起询价成功，等待供应商报价");
                $('#alertModal').modal('toggle');
            });
    });


    $("#projectnameselect").change(function () {
        $.get("/admin/resource/list?name2=" + $('#projectnameselect').val(),
            function (result) {
                $('#materialnameselect').html("");
                $('#materialnameselect').append("<option value=\"0\">请选择耗材类型</option>");
                $.each(result, function (key, val) {
                    $('#materialnameselect').append("<option value=\"" + val.id + "\">" + val.material.name + "</option>");
                });
            });
    });

    $("#materialnameselect").change(function () {
        $.get("/admin/resource?resourceid=" + $('#materialnameselect').val(),
            function (result) {
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
                $('#projectcustomerselect').val(result.data.resource.project.customer);
                $('#projectnameselect').html("<option selected>" + result.data.resource.project.name + "</option>");
                $('#materialnameselect').html("<option value=\"" + result.data.resource.id + "\">" + result.data.resource.material.name + "</option>");
                $('#size').val(result.data.resource.size);
                $('#special').val(result.data.resource.special);
                $('#model').val(result.data.resource.model);
                $('#num').val(result.data.num);
                $('#sellnum').val(result.data.sellnum);
                $('#price').val(result.data.price);
            }
        });
    $('#requestModal').modal('toggle');
};

function del(value) {
    $('#deletevalue').val(value);
    $('#deletealertmessage').text("确定要删除这个申请吗？");
    $('#deletealertModal').modal('toggle');
}

function showfile(value) {
    window.open(getWebRootPath() + "/file/" + value);
}

function requestformatter(value, row, index) {
    $("#rowoperator [name='updateoperator']").attr("onclick", "update(" + value + ");");
    $("#rowoperator [name='deleteoperator']").attr("onclick", "del(" + value + ");");
    return $('#rowoperator').html();
    // return "<button type=\"button\" class=\"btn btn-link\" onclick= \"update(" + value + ")\"> 修改</button><button type=\"button\" class=\"btn btn-link\" onclick=\"del(" + value + ")\"> 删除</button>";
}

function fileformatter(value, row, index) {
    if(value == null) return value;
    return "<button type=\"button\" class=\"btn btn-link\" onclick= \"showfile('" + value + "')\">" + value + "</button>";
}

function initPage() {

    $('#request-list-table').bootstrapTable('hideLoading');

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

    $.get("/admin/project/list?type=1",
        function (result) {
            $('#projectcustomerselect').html("");
            $('#projectcustomerselect').append("<option value=\"\">请选择客户名称</option>");
            $.each(result, function (key, val) {
                $('#projectcustomerselect').append("<option value=\"" + val + "\">" + val + "</option>");
            });
        });
}