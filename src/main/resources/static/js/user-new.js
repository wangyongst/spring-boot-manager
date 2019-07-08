$(function () {

    $("#supplierselecteddiv").hide();

    $.get("/admin/role/list",
        function (result) {
            var html = "";
            $.each(result, function (key, val) {
                if (key % 3 == 0) {
                    if (html != "") html += "</div></div>";
                    html += "<div class=\"col-12 col-md-9\"><div class=\"form-check-inline form-check col-12 col-md-12\">";
                }
                html += "  <div class=\"col-md-4\"><label class=\"form-check-label \"><input type=\"radio\" name=\"roleid\" value=\"" + val.id + "\" class=\"form-check-input\">" + val.name + "</label></div>";
            });
            html += "</div></div>";
            $('#roles').append(html);
        });

    $("#roles").change(function () {
        var roleid=$('input:radio[name="roleid"]:checked').val();
        if (roleid == -1) {
            $("#supplierselecteddiv").show();
        } else {
            $("#supplierselecteddiv").hide();
        }
    });


    $("#createuserButton").click(function () {
        $.post("/admin/user/sud", $('#usernewForm').serialize(),
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
