$(function () {
       $.get("admin/me",
        function (result) {
            if (result.status == 1) {
                $("#usernameButton").text(result.data.name);
            }
        });
});

function logout() {
    $.post("admin/logout",
        function (result) {
            if (result.status == 1) {
                window.location.href = "page-login.html";
            }
        });
};

function changepassword() {
    window.location.href = "page-changepassword.html";
};
