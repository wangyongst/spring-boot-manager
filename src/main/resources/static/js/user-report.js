$(function () {
    $("#lock").click(function () {
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
            type: "POST",
            cache: "false",
            url: "/admin/user/lock",
            data: {
                userid: ids[1]
            },
            dataType: "json",
            success: function (result) {
                if (result.status == 1) {
                    alert("账号冻结三天")
                }
            }
        });
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

