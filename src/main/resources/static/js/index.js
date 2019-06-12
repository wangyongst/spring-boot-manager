$(function () {
       $.get("admin/me",
        function (result) {
            if (result.status == 1) {
                $("#usernameButton").text(result.data.username);
            }
        });


    $("#projectButton").click(function () {
        $.get("admin/me",
            function (result) {
                if (result.status == 1) {
                    $("#projectButton").addClass("active");
                    $("#resourceButton").removeClass("active");
                    $("#supplierButton").removeClass("active");
                }
            });
    });

    $("#resourceButton").click(function () {
        $.get("admin/me",
            function (result) {
                if (result.status == 1) {
                    $("#projectButton").removeClass("active");
                    $("#resourceButton").addClass("active");
                    $("#supplierButton").removeClass("active");
                }
            });
    });

    $("#supplierButton").click(function () {
        $.get("admin/me",
            function (result) {
                if (result.status == 1) {
                    $("#projectButton").removeClass("active");
                    $("#resourceButton").removeClass("active");
                    $("#supplierButton").addClass("active");
                }
            });
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
