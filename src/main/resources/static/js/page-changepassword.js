$(function () {
    $("#alert").hide();

    $("#loginButton").click(function () {
        $.post("/admin/changepassword",
            {
                password: $("#password").val(),
                newpassword: $("#newpassword").val(),
                newpassword2: $("#newpassword2").val(),
            },
            function (result) {
                if (result.status == 1) {
                    window.location.href = "/";
                } else {
                    $("#alert").text(result.message);
                    $("#alert").show();
                }
            });
    });
    $("#cancelButton").click(function () {
        window.history.back()
    });
});

