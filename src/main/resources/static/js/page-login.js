$(function () {
    $("#loginButton").click(function () {
        debugger;
        $.ajax({
            type: "POST",
            cache: "false",
            url: "admin/login",
            data: {
                mobile: $("#mobile").val(),
                password: $("#password").val()
            },
            dataType: "json",
            success: function (result) {
                if (result.status == 1) {
                    if(result.data.ischange  == 1){
                        window.location.href = "home.html";
                    }
                    else{
                        window.location.href = "page-changepassword.html";
                    }
                } else {
                    alert(result.message);
                }
            }
        });
    });
});

