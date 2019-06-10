$(function () {
    $("#create").click(function () {
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
            type: "POST",
            cache: "false",
            url: "/admin/setting",
            data: {
                settingid: ids[1],
                operation: 2
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

    $("#update").click(function () {
        var selected = select();
        if (selected == "") {
            alert("请先选择你要修改的记录");
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
            url: "/admin/setting",
            data: {
                settingid: ids[1]
            },
            dataType: "json",
            success: function (result) {
                if (result.status == 1) {
                    $('#settingid').val(result.data.id);
                    $('#name').val(result.data.name);
                    $('#content').val(result.data.content);
                    $('#type').val(result.data.type);
                }
            }
        });
        $('#myModal').modal('toggle');
    });


    $("#close").click(function () {
        $('#myModal').modal('toggle');
    });

    $("#submit").click(function () {
        $.ajax({
            type: "POST",
            cache: "false",
            url: "/admin/setting",
            data: $('#myForm').serialize() + "&operation=1",
            dataType: "json",
            success: function (result) {
                if (result.status == 1) {
                    $("#myTable").bootstrapTable('refresh');
                }
            }
        });
        $('#myModal').modal('toggle');
    });

    $("#myModal").on("hidden.bs.modal", function () {
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

function clearForm(form) {
    // input清空
    $(':input', form).each(function () {
        var type = this.type;
        var tag = this.tagName.toLowerCase(); // normalize case
        if (type == 'text' || type == 'password' || tag == 'textarea' || tag == "tel") {
            this.value = "";
        }
        else if (tag == 'select') {
            this.selectedIndex = -1;
        }
    });
    var boxes = $("input[type=checkbox]", form);
    for (i = 0; i < boxes.length; i++) {
        boxes[i].checked = false;
    }
}


function gettype(value, row, index) {
    if(value.type ==2) return "友情链接";
    if(value.type ==3) return "推荐关键字";
    else return "设计分类";
}
