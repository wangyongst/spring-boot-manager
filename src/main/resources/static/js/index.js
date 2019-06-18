$(function () {
    $("#searchprojectButton").click(function () {
        $('#project-list-table').bootstrapTable("destroy");
        $('#project-list-table').bootstrapTable({url: "/admin/project/list?" + $('#searchprojectForm').serialize()});
    });

    $("#projectnewButton").click(function () {
        $('#projectModal').modal('toggle');
    });

    $("#resourcenewButton").click(function () {
        $('#resourceModal').modal('toggle');
    });

    $("#suppliernewButton").click(function () {
        $('#supplierModal').modal('toggle');
    });

    $("#projectsaveButton").click(function () {
        $.post("admin/project/sud", $('#projectForm').serialize(),
            function (result) {
                if (result.status == 1) {
                    $('#project-list-table').bootstrapTable("destroy");
                    $('#project-list-table').bootstrapTable({url: "/admin/project/list?" + $('#searchprojectForm').serialize()});
                    $('#projectModal').modal('toggle');
                } else {
                    $('#alertmessage').text(result.message);
                    $('#alertModal').modal('toggle');
                }
            });
    });


    $("#deleteConfirmButton").click(function () {
        var deleteid = $('#deletevalue').val();
        var deletetype = $('#deletetype').val();
        if (deletetype == 1) {
            $.post("admin/project/sud",
                {
                    projectid: deleteid,
                    delete: 1,
                },
                function (result) {
                    $('#deletealertModal').modal('toggle');
                    $('#project-list-table').bootstrapTable("destroy");
                    $('#project-list-table').bootstrapTable({url: "/admin/project/list?" + $('#searchprojectForm').serialize()});
                });
        } else if (deletetype == 2) {
            $.post("admin/resource/sud",
                {
                    resourceid: deleteid,
                    delete: 1,
                },
                function (result) {
                    $('#deletealertModal').modal('toggle');
                    $('#resource-list-table').bootstrapTable("destroy");
                    $('#resource-list-table').bootstrapTable({url: "/admin/source/list?" + $('#searchresourceForm').serialize()});
                });
        } else if (deletetype == 3) {
            $.post("admin/supplier/sud",
                {
                    supplierid: deleteid,
                    delete: 1,
                },
                function (result) {
                    $('#deletealertModal').modal('toggle');
                    $('#supplier-list-table').bootstrapTable("destroy");
                    $('#supplier-list-table').bootstrapTable({url: "/admin/supplier/list?" + $('#searchsupplierForm').serialize()});
                });
        }


    });


    $("#searchresourceButton").click(function () {
        $('#resource-list-table').bootstrapTable("destroy");
        $('#resource-list-table').bootstrapTable({url: "/admin/resource/list?" + $('#searchresourceForm').serialize()});
    });

    $("#searchsupplierButton").click(function () {
        $('#supplier-list-table').bootstrapTable("destroy");
        $('#supplier-list-table').bootstrapTable({url: "/admin/supplier/list?" + $('#searchsupplierForm').serialize()});
    });
})


function updateproject(value) {
    window.location.href = "user-update.html?" + value;
}

function delproject(value) {
    $('#deletetype').val(1);
    $('#deletevalue').val(value);
    $('#deletealertmessage').text("确定要删除这个项目吗？");
    $('#deletealertModal').modal('toggle');
}

function updateresource(value) {
    window.location.href = "user-update.html?" + value;
}

function delresource(value) {
    $('#deletetype').val(2);
    $('#deletevalue').val(value);
    $('#deletealertmessage').text("确定要删除这个资源配置吗？");
    $('#deletealertModal').modal('toggle');
}


function updatesupplier(value) {
    window.location.href = "user-update.html?" + value;
}

function delsupplier(value) {
    $('#deletetype').val(3);
    $('#deletevalue').val(value);
    $('#deletealertmessage').text("确定要删除这个供应商吗？");
    $('#deletealertModal').modal('toggle');
}


function projectformatter(value, row, index) {
    return "<button type=\"button\" class=\"btn btn-link\" onclick= \"updateproject(" + value + ")\"> 修改</button><button type=\"button\" class=\"btn btn-link\" onclick=\"delproject(" + value + ")\"> 删除</button>";
}

function resourceformatter(value, row, index) {
    return "<button type=\"button\" class=\"btn btn-link\" onclick= \"updateresource(" + value + ")\"> 修改</button><button type=\"button\" class=\"btn btn-link\" onclick=\"delresource(" + value + ")\"> 删除</button>";
}

function supplierformatter(value, row, index) {
    return "<button type=\"button\" class=\"btn btn-link\" onclick= \"updatesupplier(" + value + ")\"> 修改</button><button type=\"button\" class=\"btn btn-link\" onclick=\"delsupplier(" + value + ")\"> 删除</button>";
}