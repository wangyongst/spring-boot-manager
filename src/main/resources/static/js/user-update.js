$(function () {

    $("#supplierselecteddiv").hide();

    var userid = window.location.search.substr(1);

    $.get("/admin/user?" + userid,
        function (result) {
            if (result.status == 1) {
                $("#name").val(result.data.name);
                $("#mobile").val(result.data.mobile);
                if(result.data.role != null && result.data.role.name == "系统管理员") {
                    $("#adminrole").prop("checked",true);
                }
                else if(result.data.supplier != null) {
                    $("#supplierrole").prop("checked",true);
                    $("#supplierselecteddiv").show();
                }else if(result.data.deliver == 1){
                    $("#deliverrole").prop("checked",true);
                }
                $.get("/admin/role/list",
                    function (result2) {
                        var html = "";
                        $.each(result2, function (key, val) {
                            if (key % 3 == 0) {
                                if (html != "") html += "</div></div>";
                                html += "<div class=\"col-12 col-md-9\"><div class=\"form-check-inline form-check col-12 col-md-12\">";
                            }
                            if (result.data.role != null && result.data.role.id == val.id) {
                                html += "  <div class=\"col-md-4\"><label class=\"form-check-label \"><input type=\"radio\" name=\"roleid\" value=\"" + val.id + "\" class=\"form-check-input\" checked=\"checked\">" + val.name + "</label></div>";
                            } else {
                                html += "  <div class=\"col-md-4\"><label class=\"form-check-label \"><input type=\"radio\" name=\"roleid\" value=\"" + val.id + "\" class=\"form-check-input\">" + val.name + "</label></div>";
                            }
                        });
                        html += "</div></div>";
                        $('#roles').append(html);
                    });
            }
        });


    $("#roles").change(function () {
        var roleid=$('input:radio[name="roleid"]:checked').val();
        if (roleid == -1) {
            $("#supplierselecteddiv").show();
        } else {
            $("#supplierselecteddiv").hide();
        }
    });


    $("#saveButton").click(function () {
        $.post("/admin/user/sud?" + userid, $('#usernewForm').serialize(),
            function (result) {
                if (result.status == 1) {
                    window.location.href = "/view/user-list";
                } else {
                    $('#alertmessage').text(result.message);
                    $('#alertModal').modal('toggle');
                }
            });
    });
});
