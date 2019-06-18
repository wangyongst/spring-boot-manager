$(function () {
    $.get("admin/role/list",
        function (result) {
                $.each(result, function (key, val) {
                    $('#roles').append("<label class=\"form-check-label \">\n" +
                        "                    <input type=\"radio\" name=\"roleid\" value=\"" + val.id + "\" class=\"form-check-input\">" + val.name +
                        "               </label>");
                });
        });

    $("#createuserButton").click(function () {
        $.post("admin/user/sud", $('#usernewForm').serialize(),
            function (result) {
                if (result.status == 1) {
                    window.location.href = "user-list.html";
                } else {
                    $('#alertmessage').text(result.message);
                    $('#alertModal').modal('toggle');
                }
            });
    });
});
