$(function () {
    $.ajax({
        type: "GET",
        cache: "false",
        url: "/admin/menu/list",
        dataType: "json",
        success: function (result) {
            if (result.status == 1) {
                $.each(result.data, function (key, val) {
                    $('#myForm2').append("<label class=\"checkbox-inline\"><input type=\"checkbox\" name=\"menuids\" value=\"" + val.id + "\"> " + val.name + "</label>");
                });
            }
        }
    });

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
            url: "/admin/user/role",
            data: {
                roleid: ids[1],
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
            url: "/admin/user/role",
            data: {
                roleid: ids[1]
            },
            dataType: "json",
            success: function (result) {
                if (result.status == 1) {
                    $('#roleid').val(result.data.id);
                    $('#name').val(result.data.name);
                }
            }
        });
        $('#myModal').modal('toggle');
    });


    $("#update2").click(function () {
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
        $('#roleid2').val(ids[1]);
        $.ajax({
            type: "GET",
            cache: "false",
            url: "/admin/user/privilege",
            data: {
                roleid: ids[1]
            },
            dataType: "json",
            success: function (result) {
                if (result.status == 1) {
                    debugger;
                    var boxes = $("input[type=checkbox]", $('#myForm2'));
                    $.each(result.data, function (key, val) {
                        for (i = 0; i < boxes.length; i++) {
                            if(val.adminMenu.id == boxes[i].value){
                                boxes[i].checked = true;
                            }
                        }
                    });
                }
            }
        });
        $('#myModal2').modal('toggle');
    });


    $("#close").click(function () {
        $('#myModal').modal('toggle');
    });

    $("#close2").click(function () {
        $('#myModal2').modal('toggle');
    });

    $("#submit").click(function () {
        $.ajax({
            type: "POST",
            cache: "false",
            url: "/admin/user/role",
            data: $('#myForm').serialize() + "&type=1",
            dataType: "json",
            success: function (result) {
                if (result.status == 1) {
                    $("#myTable").bootstrapTable('refresh');
                }
            }
        });
        $('#myModal').modal('toggle');
    });

    $("#submit2").click(function () {
        $.ajax({
            type: "POST",
            cache: "false",
            url: "/admin/user/privilege",
            data: $('#myForm2').serialize(),
            dataType: "json",
            success: function (result) {
                if (result.status == 1) {
                    $("#myTable").bootstrapTable('refresh');
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
