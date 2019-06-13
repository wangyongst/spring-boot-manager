$(function () {

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

