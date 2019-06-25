$(function () {
    $.get("/admin/role/list",
        function (result) {
            if (result.status == 1) {
                $.each(result.data, function (key, val) {
                    $('#roles').append("<label class=\"form-check-label \">\n" +
                        "                    <input type=\"radio\" name=\"roleid\" value=\"" + val.id + "\" class=\"form-check-input\">" + val.name +
                        "               </label>");
                });
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
