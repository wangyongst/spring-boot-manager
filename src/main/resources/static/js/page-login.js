$(function () {
    $("#alert").hide();

    $("#loginButton").click(function () {
        $.post("admin/login",
            {
                mobile: $("#mobile").val(),
                password: $("#password").val()
            },
            function (result) {
                if (result.status == 1) {
                    if (result.data.ischange == 1) {
                        window.location.href = "index.html";
                    } else {
                        window.location.href = "page-changepassword.html";
                    }
                } else {
                    $("#alert").text(result.message);
                    $("#alert").show();
                }
            });
    });
});

