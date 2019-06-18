$(function () {
    $('#project-list-table').bootstrapTable('hideLoading');
    $('#resource-list-table').bootstrapTable('hideLoading');
    $('#supplier-list-table').bootstrapTable('hideLoading');
    $('#matiral-list-table').bootstrapTable('hideLoading');


    $("#searchprojectButton").click(function () {
        $('#project-list-table').bootstrapTable("destroy");
        $('#project-list-table').bootstrapTable({url: "/admin/project/list?" + $('#searchprojectForm').serialize()}).bootstrapTable('hideLoading');
        ;
    });

    $("#materialButton").click(function () {
        $('#materialModal').modal('toggle');
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
                    $('#project-list-table').bootstrapTable("refresh").bootstrapTable('hideLoading');
                    ;
                    $('#projectModal').modal('toggle');
                } else {
                    $('#alertmessage').text(result.message);
                    $('#alertModal').modal('toggle');
                }
            });
    });


    $("#resourcesaveButton").click(function () {
        $.post("admin/resource/sud", $('#resourceForm').serialize(),
            function (result) {
                if (result.status == 1) {
                    $('#resource-list-table').bootstrapTable("refresh").bootstrapTable('hideLoading');
                    ;
                    $('#resourceModal').modal('toggle');
                } else {
                    $('#alertmessage').text(result.message);
                    $('#alertModal').modal('toggle');
                }
            });
    });

    $("#suppliersaveButton").click(function () {
        $.post("admin/supplier/sud", $('#supplierForm').serialize(),
            function (result) {
                if (result.status == 1) {
                    $('#supplier-list-table').bootstrapTable("refresh").bootstrapTable('hideLoading');
                    ;
                    $('#supplierModal').modal('toggle');
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
                    $('#project-list-table').bootstrapTable("refresh").bootstrapTable('hideLoading');
                    ;
                });
        } else if (deletetype == 2) {
            $.post("admin/resource/sud",
                {
                    resourceid: deleteid,
                    delete: 1,
                },
                function (result) {
                    $('#deletealertModal').modal('toggle');
                    $('#resource-list-table').bootstrapTable("refresh").bootstrapTable('hideLoading');
                    ;
                });
        } else if (deletetype == 3) {
            $.post("admin/supplier/sud",
                {
                    supplierid: deleteid,
                    delete: 1,
                },
                function (result) {
                    $('#deletealertModal').modal('toggle');
                    $('#supplier-list-table').bootstrapTable("refresh").bootstrapTable('hideLoading');
                    ;
                });
        } else if (deletetype == 4) {
            $.post("admin/material/sud",
                {
                    materialid: deleteid,
                    delete: 1,
                },
                function (result) {
                    $('#deletealertModal').modal('toggle');
                    $("#matiral-list-table").bootstrapTable('refresh').bootstrapTable('hideLoading');
                    ;
                });
        }
    })


    $("#searchresourceButton").click(function () {
        $('#resource-list-table').bootstrapTable("destroy");
        $('#resource-list-table').bootstrapTable({url: "/admin/resource/list?" + $('#searchresourceForm').serialize()}).bootstrapTable('hideLoading');
        ;
    });

    $("#searchsupplierButton").click(function () {
        $('#supplier-list-table').bootstrapTable("destroy");
        $('#supplier-list-table').bootstrapTable({url: "/admin/supplier/list?" + $('#searchsupplierForm').serialize()}).bootstrapTable('hideLoading');
        ;
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

function newmaterial() {
    var data = {
        code: '<input id="newmaterialcode">',
        name: '<input id="newmaterialname">'
    };
    $("#matiral-list-table").bootstrapTable('append', data);
}

function updatematerial(value, index) {
    $("#table tr:nth-child(" + (index + 1) + ") td.editable").each(function () {
        var value = $(this).text();
        $(this).html("<input value='" + value + "'>");
    });
}

function savematerial() {
    $.post("admin/material/sud",
        {
            code: $('#newmaterialcode').val(),
            name: $('#newmaterialname').val(),
        },
        function (result) {
            if (result.status == 1) {
                $("#matiral-list-table").bootstrapTable('refresh').bootstrapTable('hideLoading');
                ;
            } else {
                $('#alertmessage').text(result.message);
                $('#alertModal').modal('toggle');
            }
        });
}


function delmaterial(value) {
    $('#deletetype').val(4);
    $('#deletevalue').val(value);
    $('#deletealertmessage').text("确定要删除这个耗材吗？");
    $('#deletealertModal').modal('toggle');
}

function cancelmaterial() {
    $("#matiral-list-table").bootstrapTable('refresh').bootstrapTable('hideLoading');
    ;
}

function materialformatter(value, row, index) {
    if (value == undefined) {
        return "<button type=\"button\" class=\"btn btn-link\" onclick= \"savematerial()\"> 保存</button><button type=\"button\" class=\"btn btn-link\" onclick= \"cancelmaterial()\"> 取消</button>";
    } else {
        return "<button type=\"button\" class=\"btn btn-link\" onclick= \"newmaterial()\"> 新增</button><button type=\"button\" class=\"btn btn-link\" onclick= \"updatematerial(" + value + "," + index + ")\"> 修改</button><button type=\"button\" class=\"btn btn-link\" onclick=\"delmaterial(" + value + ")\"> 删除</button>";
    }
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