$(function () {
    $('#send').hide();
    $('#userstable').hide();

    $.ajax({
        type: "GET",
        cache: "false",
        url: "/admin/user/role/15",
        dataType: "json",
        success: function (result) {
            if (result.status == 1) {
                $.each(result.data, function (key, val) {
                    $('#adminuserid').append("<option value='" + val.id + "'>" + val.username + "</option>");
                });
            }
        }
    });

    $.ajax({
        type: "GET",
        cache: "false",
        url: "/admin/count/study",
        dataType: "json",
        success: function (result) {
            if (result.status == 1) {
                $("#countStudy").text(result.data);
            }
        }
    });

    $("#look").click(function () {
        var selected = select();
        if (selected == "") {
            alert("请先选择你要查看的记录");
            return;
        }
        var ids = selected.split(",");
        if (ids.length > 2) {
            alert("请选择一条记录");
            return;
        }

        $.ajax({
            type: "GET",
            cache: "false",
            url: "/admin/help",
            data: {
                helpid: ids[1]
            },
            dataType: "json",
            success: function (result) {
                if (result.status == 1) {
                    $('#lookImg').attr("src", result.data.image);

                }
            }
        });
        $('#myModal').modal('toggle');
    });

    $("#person").click(function () {
        var selected = select();
        if (selected == "") {
            alert("请先选择你要查看的记录");
            return;
        }
        var ids = selected.split(",");
        if (ids.length > 2) {
            alert("请选择一条记录");
            return;
        }
        $('#studyhelpid').val(ids[1])
        $('#helpstable').hide();
        $('#look').hide();
        $('#person').hide();
        $('#send').show();
        $('#myTable2').bootstrapTable('destroy');
        $('#myTable2').bootstrapTable({url:"/admin/study/help/list?helpid="+ids[1]});
        $('#userstable').show();
    });

    $("#send").click(function () {
        $('#myModal2').modal('toggle');
    });

    $("#close").click(function () {
        $('#myModal').modal('toggle');
    });

    $("#close2").click(function () {
        $('#myModal2').modal('toggle');
    });

    $("#submit2").click(function () {
        $.ajax({
            type: "POST",
            cache: "false",
            url: "/admin/user/send/message",
            data: {
                helpid: $('#studyhelpid').val(),
                text: $('#content').val(),
                adminuserid:$('#adminuserid').val()
            },
            dataType: "json",
            success: function (result) {
                if (result.status == 1) {
                    alert("发送成功")
                }
            }
        });
        $('#myModal2').modal('toggle');
    });

    $("#myModal").on("hidden.bs.modal", function () {
        clearForm(this);
    });

    $("#myModal2").on("hidden.bs.modal", function () {
        clearForm(this);
    });
});

function select() {
    var ids = "";
    $("input[name=btSelectItem]").each(function () {
        if ($(this).prop('checked')) {
            var index = $("table input:checkbox").index(this);
            val = $("table").find("tr").eq(index).find("td").eq(1).text();
            ids += "," + val;
        }
    });
    return ids;
}
