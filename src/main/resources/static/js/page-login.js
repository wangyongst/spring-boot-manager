$(function () {
    $("#alert").hide();

    $("#loginButton").click(function () {
        $.post("/shiro/login",
            {
                username: $("#mobile").val(),
                password: $("#password").val()
            },
            function (result) {
                if (result.status == 1) {
                    if (result.message == "1") {
                        window.location.href = "/";
                    } else {
                        window.location.href = "/view/page-changepassword";
                    }
                } else {
                    $("#alert").text(result.message);
                    $("#alert").show();
                }
            });
    });
});

