$(function () {

    $("#updateroleButton").click(function () {
        $.post("/admin/role/sud", $('#rolenewForm').serialize(),
            function (result) {
                if (result.status == 1) {
                    window.location.href = "/view/role-list";
                } else {
                    $('#alertmessage').text(result.message);
                    $('#alertModal').modal('toggle');
                }
            });
    });

    // $("[name='permission']").change(function () {
    //     alert("checked");
    // });

});