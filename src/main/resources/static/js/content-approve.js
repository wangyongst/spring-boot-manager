$(function () {
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
    $.ajax({
        type: "GET",
        cache: "false",
        url: "/admin/count/click",
        dataType: "json",
        success: function (result) {
            if (result.status == 1) {
                $("#countClick").text(result.data);
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


    $("#lookyes").click(function () {
        var selected = select();
        if (selected == "") {
            alert("请先选择你要审核的记录");
            return;
        }
        var ids = selected.split(",");
        if (ids.length > 2) {
            alert("请选择一条记录");
            return;
        }

        $.ajax({
            type: "Post",
            cache: "false",
            url: "/admin/help",
            data: {
                helpid: ids[1],
                type:1,
                draft:4
            },
            dataType: "json",
            success: function (result) {
                if (result.status == 1) {
                    alert("审核成功");
                    $("#myTable").bootstrapTable('refresh');
                }
            }
        });
    });


    $("#lookno").click(function () {
        var selected = select();
        if (selected == "") {
            alert("请先选择你要审核的记录");
            return;
        }
        var ids = selected.split(",");
        if (ids.length > 2) {
            alert("请选择一条记录");
            return;
        }

        $.ajax({
            type: "Post",
            cache: "false",
            url: "/admin/help",
            data: {
                helpid: ids[1],
                type:1,
                draft:3
            },
            dataType: "json",
            success: function (result) {
                if (result.status == 1) {
                    alert("审核成功");
                    $("#myTable").bootstrapTable('refresh');
                }
            }
        });
    });

    $("#close").click(function () {
        $('#myModal').modal('toggle');
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
