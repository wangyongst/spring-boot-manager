$(function () {
    $('#project-list-table').bootstrapTable('hideLoading');
    $('#resource-list-table').bootstrapTable('hideLoading');
    $('#supplier-list-table').bootstrapTable('hideLoading');
    $('#matiral-list-table').bootstrapTable('hideLoading');

    $.get("admin/project/list?type=3",
        function (result) {
            $('#projectnameselect').html("");
            $('#projectnameselect').append("<option value=\"\">请选择项目名称</option>");
            $.each(result, function (key, val) {
                $('#projectnameselect').append("<option value=\"" + val + "\">" + val + "</option>");
            });
        });

    $.get("admin/material/list?type=3",
        function (result) {
            $('#materialnameselect').html("");
            $('#materialnameselect').append("<option value=\"\">请选择耗材类型</option>");
            $.each(result, function (key, val) {
                $('#materialnameselect').append("<option value=\"" + val + "\">" + val + "</option>");
            });
        });

    $.get("admin/project/list",
        function (result) {
            $('#projectnameselect2').html("");
            $('#projectnameselect2').append("<option value=\"\">请选择项目名称</option>");
            $.each(result, function (key, val) {
                $('#projectnameselect2').append("<option value=\"" + val.id + "\">" + val.name + "</option>");
            });
        });

    $.get("admin/material/list?type=1",
        function (result) {
            $('#materialcodeselect').html("");
            $('#materialcodeselect').append("<option value=\"\">请选择耗材编号</option>");
            $.each(result, function (key, val) {
                $('#materialcodeselect').append("<option value=\"" + val + "\">" + val + "</option>");
            });
        });

    $.get("admin/material/list",
        function (result) {
            $.each(result, function (key, val) {
                $('#productselect').append("<option value=\"" + val.id + "\">" + val.name + "</option>");
            });
            multiSelect();
        });


    $("#uploadfile").change(function () {
        var formData = new FormData();
        formData.append('uploadfile', $('#uploadfile')[0].files[0]);
        formData.append("resourceid", $('#uploadresouceid').val())
        $.ajax({
            url: 'admin/upload',
            type: 'POST',
            cache: false,
            data: formData,
            processData: false,
            contentType: false,
            success: function (result) {
                if (result.status == 1) {
                    $('#resource-list-table').bootstrapTable("refresh").bootstrapTable('hideLoading');
                } else {
                    $('#alertmessage').text(result.message);
                    $('#alertModal').modal('toggle');
                }
            }
        });
    });


    $("#materialcodeselect").change(function () {
        $.get("admin/material/list?type=2&code=" + $('#materialcodeselect').val(),
            function (result) {
                $('#materialnameselect2').html("");
                $('#materialnameselect2').append("<option value=\"\">请选择耗材类型</option>");
                $.each(result, function (key, val) {
                    $('#materialnameselect2').append("<option value=\"" + val.id + "\">" + val.name + "</option>");
                });
            });
    });

    $("#searchprojectButton").click(function () {
        $('#project-list-table').bootstrapTable("destroy");
        $('#project-list-table').bootstrapTable({url: "/admin/project/list?" + $('#searchprojectForm').serialize()}).bootstrapTable('hideLoading');
    });

    $("#searchresourceButton").click(function () {
        $('#resource-list-table').bootstrapTable("destroy");
        $('#resource-list-table').bootstrapTable({url: "/admin/resource/list?" + $('#searchresourceForm').serialize()}).bootstrapTable('hideLoading');
    });

    $("#searchsupplierButton").click(function () {
        $('#supplier-list-table').bootstrapTable("destroy");
        $('#supplier-list-table').bootstrapTable({url: "/admin/supplier/list?" + $('#searchsupplierForm').serialize()}).bootstrapTable('hideLoading');
    });

    $("#materialButton").click(function () {
        $('#materialModal').modal('toggle');
    });

    $("#projectnewButton").click(function () {
        clearForm($('#projectForm'));
        $('#projectidhidden').val(0);
        $('#projectModal').modal('toggle');
    });

    $("#resourcenewButton").click(function () {
        clearForm($('#resourceForm'));
        $('#resourceidhidden').val(0);
        $('#resourceModal').modal('toggle');
    });

    $("#suppliernewButton").click(function () {
        $('#productselect_chosen li.search-choice').remove();
        clearForm($('#supplierForm'));
        $('#supplieridhidden').val(0);
        $('#supplierModal').modal('toggle');
    });

    $("#projectsaveButton").click(function () {
        $.post("admin/project/sud", $('#projectForm').serialize(),
            function (result) {
                if (result.status == 1) {
                    $('#project-list-table').bootstrapTable("refresh").bootstrapTable('hideLoading');
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
})


function updateproject(value) {
    clearForm($('#projectForm'));
    $.get("admin/project?projectid=" + value,
        function (result) {
            if (result.status == 1) {
                $('#projectidhidden').val(result.data.id);
                $("#projectForm [name='customer']").val(result.data.customer);
                $("#projectForm [name='name']").val(result.data.name)
                $("#projectForm [name='zimu']").val(result.data.zimu)
            }
        });
    $('#projectModal').modal('toggle');
}

function delproject(value) {
    $('#deletetype').val(1);
    $('#deletevalue').val(value);
    $('#deletealertmessage').text("确定要删除这个项目吗？");
    $('#deletealertModal').modal('toggle');
}

function updateresource(value) {
    clearForm($('#resourceForm'));
    $.get("admin/resource?resourceid=" + value,
        function (result) {
            if (result.status == 1) {
                $('#resourceidhidden').val(result.data.id);
                $("#projectnameselect2").val(result.data.project.id);
                $("#materialcodeselect").val(result.data.material.code)
                $.ajaxSettings.async = false;
                $.get("admin/material/list?type=2&code=" + $('#materialcodeselect').val(),
                    function (result) {
                        $('#materialnameselect2').html("");
                        $('#materialnameselect2').append("<option value=\"\">请选择耗材类型</option>");
                        $.each(result, function (key, val) {
                            $('#materialnameselect2').append("<option value=\"" + val.id + "\">" + val.name + "</option>");
                        });
                    });
                $.ajaxSettings.async = true;
                $("#materialnameselect2").val(result.data.material.id)
                $("#resourceForm [name='size']").val(result.data.size)
                $("#resourceForm [name='special']").val(result.data.special)
                $("#resourceForm [name='model']").val(result.data.model)
            }
        });
    $('#resourceModal').modal('toggle');
}

function delresource(value) {
    $('#deletetype').val(2);
    $('#deletevalue').val(value);
    $('#deletealertmessage').text("确定要删除这个资源配置吗？");
    $('#deletealertModal').modal('toggle');
}


function updatesupplier(value) {
    $('#productselect_chosen li.search-choice').remove();
    clearForm($('#supplierForm'));
    $.get("admin/supplier?supplierid=" + value,
        function (result) {
            if (result.status == 1) {
                $('#supplieridhidden').val(result.data.id);
                $("#supplierForm [name='name']").val(result.data.name);
                $("#supplierForm [name='contacts']").val(result.data.contacts)
                $("#supplierForm [name='mobile']").val(result.data.mobile)
                result.data.products.forEach(e => {
                    $('#productselect option').each(function () {
                        debugger;
                        if (this.value == e.material.id) this.selected = true;
                    });
                })
                $("#productselect").trigger("chosen:updated");
                $("#supplierForm [name='fapiao']").val(result.data.fapiao);
                $("#supplierForm [name='zhanghu']").val(result.data.zhanghu)
                $("#supplierForm [name='shoukuan']").val(result.data.shoukuan)
                $("#supplierForm [name='kaihu']").val(result.data.kaihu)
            }
        });
    $('#supplierModal').modal('toggle');
}

function delsupplier(value) {
    $('#deletetype').val(3);
    $('#deletevalue').val(value);
    $('#deletealertmessage').text("确定要删除这个供应商吗？");
    $('#deletealertModal').modal('toggle');
}

function newmaterial() {
    var data = {
        code: '<input id="newmaterialcode" class="form-control">',
        name: '<input id="newmaterialname" class="form-control">'
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
}

function cancelmaterial() {
    $("#matiral-list-table").bootstrapTable('refresh').bootstrapTable('hideLoading');
}

function uploadfile(value) {
    $('#uploadresouceid').val(value);
    $("#uploadfile").click();
}


function materialformatter(value, row, index) {
    if (value == undefined) {
        return "<button type=\"button\" class=\"btn btn-link\" onclick= \"savematerial()\"> 保存</button><button type=\"button\" class=\"btn btn-link\" onclick= \"cancelmaterial()\"> 取消</button>";
    } else {
        return "<button type=\"button\" class=\"btn btn-link\" onclick= \"newmaterial()\"> 新增</button><button type=\"button\" class=\"btn btn-link\" onclick= \"updatematerial(" + value + "," + index + ")\"> 修改</button><button type=\"button\" class=\"btn btn-link\" onclick=\"delmaterial(" + value + ")\"> 删除</button>";
    }
}


function fileformatter(value, row, index, field) {
    if (value == null) {
        return "<button type=\"button\" class=\"btn btn-link\" onclick= \"uploadfile(" + row.id + ")\">上传</button>";
    } else {
        return "<button type=\"button\" class=\"btn btn-link\" onclick= \"uploadfile(" + row.id + ")\">" + value + "</button>";
    }
}

function productsformatter(value, row, index) {
    var products = "";
    for (var i of value) {
        products += "," + i.material.name;
    }
    if (products.length > 1) products = products.substr(1);
    return products;
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