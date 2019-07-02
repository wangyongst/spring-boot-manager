$(function () {

    var userid = window.location.search.substr(1);

    $.get("/admin/user?" + userid,
        function (result) {
            if (result.status == 1) {
                $("#name").val(result.data.name);
                $("#mobile").val(result.data.mobile);
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
