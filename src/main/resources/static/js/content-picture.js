$(function () {

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

    $("#delete").click(function () {
        var selected = select();
        if (selected == "") {
            alert("请先选择你要删除的记录");
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
                type: 2
            },
            dataType: "json",
            success: function (result) {
                if (result.status == 1) {
                    alert("删除记录成功");
                    $("#myTable").bootstrapTable('refresh');
                }
            }
        });
    });


    $("#refer").click(function () {
        var selected = select();
        if (selected == "") {
            alert("请先选择你要推荐的记录");
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
            url: "/admin/help/refer",
            data: {
                helpid: ids[1]
            },
            dataType: "json",
            success: function (result) {
                if (result.status == 1) {
                    alert("推荐成功");
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

function getStatus(value, row, index) {
    if(value ==1) return "草稿";
    else if(value ==2) return "待审核";
    else if(value ==3) return "未通过审核";
    else if(value ==4) return "已经发表";
    else return "其它";
}
