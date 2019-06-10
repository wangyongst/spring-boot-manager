$(function () {

    $("#selectNot2").text("上传大图:最大不能超过40M");
    $("#url").hide();


    $.ajax({
        type: "GET",
        cache: "false",
        url: "/admin/user/role/15",
        dataType: "json",
        success: function (result) {
            if (result.status == 1) {
                $.each(result.data, function (key, val) {
                    $('#adminuserid').append("<option value='" + val.id + "'>" + val.username + "</option>");
                });
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
            url: "/admin/advert",
            data: {
                advertid: ids[1]
            },
            dataType: "json",
            success: function (result) {
                if (result.status == 1) {
                    $('#lookImg').attr("src", result.data.image);
                } else {
                    alert(result.message);
                }
            }
        });
        $('#myModal').modal('toggle');
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
            url: "/admin/advert",
            data: {
                advertid: ids[1],
                operation: 2
            },
            dataType: "json",
            success: function (result) {
                if (result.status == 1) {
                    $('#advertid').val(result.data.id);
                    $('#title').val(result.data.title);
                    $('#adminuserid').val(result.data.adminuser.id);
                    $('#type').val(result.data.type);
                    $('#url').val(result.data.url);
                    if ($("#type").val() == 1) {
                        $("#imagesize").text("上传封面:310*310:");
                        $("#selectNot2").text("上传大图:最大不能超过40M");
                        $("#url").hide();
                        $("#upload2").show();
                    } else {
                        $("#imagesize").text("上传封面:310*310:");
                        if ($("#type").val() == 2) {
                            $("#imagesize").text("上传封面:310*350:");
                        }
                        $("#selectNot2").text("跳转链接:");
                        $("#url").show();
                        $("#upload2").hide();
                    }
                } else {
                    alert(result.message);
                }
            }
        });
        $('#myModal2').modal('toggle');
    });


    $("#delete").click(function () {
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
            type: "POST",
            cache: "false",
            url: "/admin/advert",
            data: {
                advertid: ids[1],
                operation: 3
            },
            dataType: "json",
            success: function (result) {
                if (result.status == 1) {
                    $("#myTable").bootstrapTable('refresh');
                } else {
                    alert(result.message);
                }
            }
        });
    });

    $("#create").click(function () {
        $('#myModal2').modal('toggle');
    });

    $("#upload").change(function () {
        var formData = new FormData();
        formData.append('image', this.files[0]);
        $.ajax({
            type: "POST",
            cache: "false",
            contentType: false,
            processData: false,
            url: "/upload/image",
            data: formData,
            dataType: "json",
            success: function (result) {
                if (result.status == 1) {
                    $('#image').val("http://pas99p7vd.bkt.clouddn.com/" + result.data);
                } else {
                    alert(result.message);
                }
            }
        });
    });

    $("#upload2").change(function () {
        var formData = new FormData();
        formData.append('image', this.files[0]);
        $.ajax({
            type: "POST",
            cache: "false",
            contentType: false,
            processData: false,
            url: "/upload/image",
            data: formData,
            dataType: "json",
            success: function (result) {
                if (result.status == 1) {
                    $('#url').val("http://pas99p7vd.bkt.clouddn.com/" + result.data);
                } else {
                    alert(result.message);
                }
            }
        });
    });

    $("#type").change(function () {
        if ($("#type").val() == 1) {
            $("#imagesize").text("上传封面:310*310:");
            $("#selectNot2").text("上传大图:最大不能超过40M");
            $("#url").hide();
            $("#upload2").show();
        } else {
            $("#imagesize").text("上传封面:310*310:");
            if ($("#type").val() == 2) {
                $("#imagesize").text("上传封面:310*350:");
            }
            $("#selectNot2").text("跳转链接:");
            $("#url").show();
            $("#upload2").hide();
        }
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
            type: "POST",
            cache: "false",
            url: "/admin/advert/refer",
            data: {
                advertid: ids[1]
            },
            dataType: "json",
            success: function (result) {
                if (result.status == 1) {
                    $("#myTable").bootstrapTable('refresh');
                } else {
                    alert(result.message);
                }
            }
        });
    });

    $("#down").click(function () {
        var selected = select();
        if (selected == "") {
            alert("请先选择你要下线的记录");
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
            url: "/admin/advert/out",
            data: {
                advertid: ids[1]
            },
            dataType: "json",
            success: function (result) {
                if (result.status == 1) {
                    $("#myTable").bootstrapTable('refresh');
                } else {
                    alert(result.message);
                }
            }
        });
    });

    $("#submit2").click(function () {
        if ($("#adminuserid") == null) {
            alert("广告主必选！！")
            return;
        }
        var oper = 1;
        if($("#advertid").val() != "") oper = 2;
        $.ajax({
            type: "Post",
            cache: "false",
            url: "/admin/advert",
            data: $('#advertForm').serialize() + "&operation="+oper,
            dataType: "json",
            success: function (result) {
                if (result.status == 1) {
                    $('#myModal2').modal('toggle');
                    $("#myTable").bootstrapTable('refresh');
                } else {
                    alert(result.message);
                }
            }
        });
    });

    $("#close").click(function () {
        $('#myModal').modal('toggle');
    });

    $("#close2").click(function () {
        $('#myModal2').modal('toggle');
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

function getType(value, row, index) {
    if (value == 1) return "想买广告";
    else if (value == 2) return "了解详情";
    else if (value == 3) return "立即购买";
    else return "其它";
}

function getStatus(value, row, index) {
    if (value == 1) return "强制推荐";
    if (value == 2) return "已经下线";
    else return "普通广告";
}

