$(function () {

    var userid = window.location.search.substr(1);

    $.get("/admin/user?" + userid,
        function (result) {
            if (result.status == 1) {
                $("#name").val(result.data.name);
                $("#mobile").val(result.data.mobile);
                // $("#password").val(result.data.password);
                $.get("/admin/role/list",
                    function (result2) {
                        if (result2.status == 1) {
                            $.each(result2.data, function (key, val) {
                                if (result.data.role != null && result.data.role.id == val.id) {
                                    $('#roles').append("<label class=\"form-check-label \">\n" +
                                        "                    <input type=\"radio\" name=\"roleid\" value=\"" + val.id + "\" class=\"form-check-input\" checked=\"checked\" >" + val.name +
                                        "               </label>");
                                } else {
                                    $('#roles').append("<label class=\"form-check-label \">\n" +
                                        "                    <input type=\"radio\" name=\"roleid\" value=\"" + val.id + "\" class=\"form-check-input\">" + val.name +
                                        "               </label>");
                                }
                            });
                        }
                    });
            }
        });

    $("#saveButton").click(function () {
        $.post("/admin/user/sud?userid=" + userid, $('#usernewForm').serialize(),
            function (result) {
                if (result.status == 1) {
                    window.location.href = "/view/user-list"
                } else {
                    $('#alertmessage').text(result.message);
                    $('#alertModal').modal('toggle');
                }
            });
    });
});
