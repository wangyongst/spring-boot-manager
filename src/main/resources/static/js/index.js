$(function () {
    initPage();

    $("#uploadfile").change(function () {
        var formData = new FormData();
        formData.append('uploadfile', $('#uploadfile')[0].files[0]);
        formData.append("resourceid", $('#uploadresouceid').val())
        $.ajax({
            url: '/admin/upload',
            type: 'POST',
            cache: false,
            data: formData,
            processData: false,
            contentType: false,
            success: function (result) {
                if (result.status == 1) {
                    initPage()
                } else {
                    $('#alertmessage').text(result.message);
                    $('#alertModal').modal('toggle');
                }
            }
        });
    });

    $("#searchprojectButton").click(function () {
        $('#project-list-table').bootstrapTable("destroy");
        $('#project-list-table').bootstrapTable({url: "/admin/project/list?" + $('#searchprojectForm').serialize()}).bootstrapTable('hideLoading');
    });

    $("#exportprojectButton").click(function () {
        var formData = $('#searchprojectForm');
        formData.attr("action", "/admin/project/export").attr("method", "get");
        formData.submit();
    });

    $("#searchresourceButton").click(function () {
        $('#resource-list-table').bootstrapTable("destroy");
        $('#resource-list-table').bootstrapTable({url: "/admin/resource/list?" + $('#searchresourceForm').serialize()}).bootstrapTable('hideLoading');
    });

    $("#exporresourceButton").click(function () {
        var formData = $('#searchresourceForm');
        formData.attr("action", "/admin/resource/export").attr("method", "get");
        formData.submit();
    });

    $("#searchsupplierButton").click(function () {
        $('#supplier-list-table').bootstrapTable("destroy");
        $('#supplier-list-table').bootstrapTable({url: "/admin/supplier/list?" + $('#searchsupplierForm').serialize()}).bootstrapTable('hideLoading');
    });

    $("#exportsupplierButton").click(function () {
        var formData = $('#searchsupplierForm');
        formData.attr("action", "/admin/supplier/export").attr("method", "get");
        formData.submit();
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
        clearForm($('#supplierForm'));
        $('#productselect option').each(function () {
            this.selected = false;
        })
        $("#productselect").trigger("chosen:updated");
        $('#supplieridhidden').val(0);
        $('#supplierModal').modal('toggle');
    });

    $("#projectsaveButton").click(function () {
        $.post("/admin/project/sud", $('#projectForm').serialize(),
            function (result) {
                if (result.status == 1) {
                    initPage()
                    $('#projectModal').modal('toggle');
                } else {
                    $('#alertmessage').text(result.message);
                    $('#alertModal').modal('toggle');
                }
            });
    });

    $("#resourcesaveButton").click(function () {
        $.post("/admin/resource/sud", $('#resourceForm').serialize(),
            function (result) {
                if (result.status == 1) {
                    initPage()
                    $('#resourceModal').modal('toggle');
                } else {
                    $('#alertmessage').text(result.message);
                    $('#alertModal').modal('toggle');
                }
            });
    });

    $("#suppliersaveButton").click(function () {
        $.post("/admin/supplier/sud", $('#supplierForm').serialize(),
            function (result) {
                if (result.status == 1) {
                    initPage()
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
            $.post("/admin/project/sud",
                {
                    projectid: deleteid,
                    delete: 1
                }, function (result) {
                    initPage()
                    $('#deletealertModal').modal('toggle');
                });
        } else if (deletetype == 2) {
            $.post("/admin/resource/sud",
                {
                    resourceid: deleteid,
                    delete: 1
                }, function (result) {
                    initPage()
                    $('#deletealertModal').modal('toggle');
                });
        } else if (deletetype == 3) {
            $.post("/admin/supplier/sud",
                {
                    supplierid: deleteid,
                    delete: 1
                }, function (result) {
                    initPage()
                    $('#deletealertModal').modal('toggle');
                });
        } else if (deletetype == 4) {
            $.post("/admin/material/sud",
                {
                    materialid: deleteid,
                    delete: 1
                }, function (result) {
                    initPage()
                    $('#deletealertModal').modal('toggle');
                });
        }
    })
})


function updateproject(value) {
    clearForm($('#projectForm'));
    $.get("/admin/project?projectid=" + value,
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
    $.get("/admin/resource?resourceid=" + value,
        function (result) {
            if (result.status == 1) {
                $('#resourceidhidden').val(result.data.id);
                $("#projectnameselect2").val(result.data.project.id);
                $.ajaxSettings.async = false;
                $.get("/admin/material/list",
                    function (result) {
                        $('#materialnameselect2').html("");
                        $('#materialnameselect2').append("<option value=\"\">请选择耗材类型</option>");
                        $.each(result, function (key, val) {
                            $('#materialnameselect2').append("<option value=\"" + val.id + "\">" + val.name + "</option>");
                        });
                    });
                $.ajaxSettings.async = true;
                $("#materialnameselect2").val(result.data.material.id)
                $("#resourceForm [name='code']").val(result.data.code)
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
    clearForm($('#supplierForm'));
    $("#productselect").trigger("chosen:updated");
    $.get("/admin/supplier?supplierid=" + value,
        function (result) {
            if (result.status == 1) {
                $('#supplieridhidden').val(result.data.id);
                $("#supplierForm [name='name']").val(result.data.name);
                $("#supplierForm [name='contacts']").val(result.data.contacts)
                $("#supplierForm [name='mobile']").val(result.data.mobile)
                $('#productselect option').each(function () {
                    this.selected = false;
                    result.data.products.forEach(e => {
                        if (this.value == e.material.id) {
                            this.selected = true;
                        }
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
    if($("#newmaterialcode").length>0){
        return;
    }else{
        $("#matiral-list-table").bootstrapTable('append', data);
    }
}

function updatematerial(value, index) {
    $("#matiral-list-table tbody tr").each(function (i) {
        if (i == index) {
            $(this).find("td").each(function (k) {
                if (k < 2) {
                    $(this).html("<input class=\"form-control\" value='" + $(this).text() + "'>");
                } else {
                    $(this).html("<button type=\"button\" class=\"btn btn-link\" onclick= \"updatesavematerial(" + value + "," + index + ")\"> 保存</button><button type=\"button\" class=\"btn btn-link\" onclick= \"cancelmaterial()\"> 取消</button>");
                }
            })
        }
    });
}

function savematerial() {
    $.post("/admin/material/sud",
        {
            code: $('#newmaterialcode').val(),
            name: $('#newmaterialname').val(),
        },
        function (result) {
            if (result.status == 1) {
                initPage()
            } else {
                $('#alertmessage').text(result.message);
                $('#alertModal').modal('toggle');
            }
        });
}

function updatesavematerial(value,index) {
    var code;
    var name;
    $("#matiral-list-table tbody tr").each(function (i) {
        if (i == index) {
            $(this).find("td").each(function (k) {
                if(k == 0) code = $(this).find("input").val();
                else if(k == 1) name = $(this).find("input").val();
            })
        }
    });

    $.post("/admin/material/sud",
        {
            materialid: value,
            code: code,
            name: name
        },
        function (result) {
            if (result.status == 1) {
                initPage()
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
    initPage()
}

function uploadfile(value) {
    $('#uploadresouceid').val(value);
    $("#uploadfile").val("")
    $("#uploadfile").click();
}


function materialformatter(value, row, index) {
    if (value == undefined) {
        return "<button type=\"button\" class=\"btn btn-link\" onclick= \"savematerial()\"> 保存</button><button type=\"button\" class=\"btn btn-link\" onclick= \"cancelmaterial()\"> 取消</button>";
    } else {
        $("#rowoperator2 [name='newoperator2']").attr("onclick", "newmaterial();");
        $("#rowoperator2 [name='updateoperator2']").attr("onclick", "updatematerial(" + value + "," + index + ");");
        $("#rowoperator2 [name='deleteoperator2']").attr("onclick", "delmaterial(" + value + ");");
        return $('#rowoperator2').html();
    }
}


function fileformatter(value, row, index) {
    $("#rowoperator1 [name='updateoperator1']").attr("onclick", "uploadfile(" + row.id + ");");
    if (value == null) {
        $("#rowoperator1 [name='updateoperator1']").text("上传")
    } else {
        $("#rowoperator1 [name='updateoperator1']").text(value)
    }
    return $('#rowoperator1').html();
}

function productsformatter(value, row, index) {
    var products = "";
    for (var i of value) {
        products += "、" + i.material.name;
    }
    if (products.length > 1) products = products.substr(1);
    return products;
}

function projectformatter(value, row, index) {
    $("#rowoperator [name='updateoperator']").attr("onclick", "updateproject(" + value + ");");
    $("#rowoperator [name='deleteoperator']").attr("onclick", "delproject(" + value + ");");
    return $('#rowoperator').html();
}

function resourceformatter(value, row, index) {
    $("#rowoperator [name='updateoperator']").attr("onclick", "updateresource(" + value + ");");
    $("#rowoperator [name='deleteoperator']").attr("onclick", "delresource(" + value + ");");
    return $('#rowoperator').html();
}

function supplierformatter(value, row, index) {
    $("#rowoperator [name='updateoperator']").attr("onclick", "updatesupplier(" + value + ");");
    $("#rowoperator [name='deleteoperator']").attr("onclick", "delsupplier(" + value + ");");
    return $('#rowoperator').html();
}

function initPage() {
    $('#project-list-table').bootstrapTable("refresh").bootstrapTable('hideLoading');
    $("#matiral-list-table").bootstrapTable('refresh').bootstrapTable('hideLoading');
    $('#supplier-list-table').bootstrapTable("refresh").bootstrapTable('hideLoading');
    $('#resource-list-table').bootstrapTable("refresh").bootstrapTable('hideLoading');

    $.get("/admin/project/list?type=3",
        function (result) {
            $('#projectnameselect').html("");
            $('#projectnameselect').append("<option value=\"\">请选择项目名称</option>");
            $.each(result, function (key, val) {
                $('#projectnameselect').append("<option value=\"" + val + "\">" + val + "</option>");
            });
        });

    $.get("/admin/material/list",
        function (result) {
            $('#materialnameselect').html("");
            $('#materialnameselect2').html("");
            $('#materialnameselect').append("<option value=\"\">请选择耗材类型</option>");
            $('#materialnameselect2').append("<option value=\"\">请选择耗材类型</option>");
            $.each(result, function (key, val) {
                $('#materialnameselect').append("<option value=\"" + val.name + "\">" + val.name + "</option>");
                $('#materialnameselect2').append("<option value=\"" + val.id + "\">" + val.name + "</option>");
            });
        });

    $.get("/admin/project/list",
        function (result) {
            $('#projectnameselect2').html("");
            $('#projectnameselect2').append("<option value=\"\">请选择项目名称</option>");
            $.each(result, function (key, val) {
                $('#projectnameselect2').append("<option value=\"" + val.id + "\">" + val.name + "</option>");
            });
        });

    $.get("/admin/material/list",
        function (result) {
            $('#productselect').html("");
            $.each(result, function (key, val) {
                $('#productselect').append("<option value=\"" + val.id + "\">" + val.name + "</option>");
            });
            multiSelect();
        });
}
