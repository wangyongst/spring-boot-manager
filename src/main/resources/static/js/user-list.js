$(function () {

    $.get("admin/user/list?page=0",
        function (result) {
            if (result.status == 1) {
                $('#user-list-table').bootstrapTable({
                    data: result.data.content,
                    columns: [{
                        field: 'id',
                        title: 'ID'
                    }, {
                        field: 'username',
                        title: '名称'
                    }, {
                        field: 'mobile',
                        title: '电话'
                    }, {
                        field: 'role.name',
                        title: '角色'
                    }, {
                        field: 'createusername',
                        title: '创建人'
                    }, {
                        field: 'createtime',
                        title: '创建时间'
                    }, {
                        title: '操作'
                    }]
                }).bootstrapTable('hideLoading');;
            }
        });

    $("#create").click(function () {
        $('#adminUserModal').modal('toggle');
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
            url: "/admin/user/admin",
            data: {
                userid: ids[1],
                type: 2
            },
            dataType: "json",
            success: function (result) {
                if (result.status == 1) {
                    alert("删除记录成功");
                    $("#adminUserTable").bootstrapTable('refresh');
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
            url: "/admin/user/admin",
            data: {
                userid: ids[1]
            },
            dataType: "json",
            success: function (result) {
                if (result.status == 1) {
                    $('#userid').val(result.data.id);
                    $('#username').val(result.data.username);
                    $('#password').val(result.data.password);
                    $('#roleid').val(result.data.adminRole.id);
                }
            }
        });
        $('#adminUserModal').modal('toggle');
    });

    $("#close").click(function () {
        $('#adminUserModal').modal('toggle');
    });

    $("#submit").click(function () {
        $.ajax({
            type: "POST",
            cache: "false",
            url: "/admin/user/admin",
            data: $('#adminUserForm').serialize() + "&type=1",
            dataType: "json",
            success: function (result) {
                if (result.status == 1) {
                    $("#adminUserTable").bootstrapTable('refresh');
                }
            }
        });
        $('#adminUserModal').modal('toggle');
    });

    $("#adminUserModal").on("hidden.bs.modal", function () {
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
        } else if (tag == 'select') {
            this.selectedIndex = -1;
        }
    });
    var boxes = $("input[type=checkbox]", form);
    for (i = 0; i < boxes.length; i++) {
        boxes[i].checked = false;
    }
}
