$(function () {
    $("#loginButton").click(function () {
        if ($("#username").val() == null || $("#username").val() == "" || $("#username").val() == "null") {
            alert("用户名不能为空");
            return;
        }
        if ($("#password").val() == null || $("#password").val() == "" || $("#password").val() == "null") {
            alert("密码不能为空");
            return;
        }
        $.ajax({
            type: "POST",
            cache: "false",
            url: "admin/user/login",
            data: {
                username: $("#username").val(),
                password: $("#password").val()
            },
            dataType: "json",
            success: function (result) {
                if (result.status == 1) {
                    window.location.href = "home.html";
                } else {
                    alert(result.message);
                }
            }
        });
    });
});

