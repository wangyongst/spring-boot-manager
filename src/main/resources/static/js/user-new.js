$(function () {

    $.ajax({
        url: 'http://2504od2888.wicp.vip:17742/api/usercheck/checkUser',
        type: 'POST',
        headers: {'token': "88cbc33c-d81d-49d4-b281-df348f7b4b6b"},
        data: {
            id: "iugfhye-szrqX5L-8fs7XVb-dwdfeaqe",
            status: 1,
            checkDescription: ""
        },
        crossDomain:true,
        xhrFields: {  withCredentials: true  },
        success: function (result) {
            alert(result);
        }
    });


    $.get("/admin/role/list",
        function (result) {
            var html = "";
            $.each(result, function (key, val) {
                if (key % 3 == 0) {
                    if (html != "") html += "</div></div>";
                    html += "<div class=\"col-12 col-md-9\"><div class=\"form-check-inline form-check col-12 col-md-12\">";
                }
                html += "  <div class=\"col-md-4\"><label class=\"form-check-label \"><input type=\"radio\" name=\"roleid\" value=\"" + val.id + "\" class=\"form-check-input\">" + val.name + "</label></div>";
            });
            html += "</div></div>";
            $('#roles').append(html);
        });


    $("#createuserButton").click(function () {
        $.post("/admin/user/sud", $('#usernewForm').serialize(),
            function (result) {
                if (result.status == 1) {
                    window.location.href = "/view/user-list";
                } else {
                    $('#alertmessage').text(result.message);
                    $('#alertModal').modal('toggle');
                }
            });
    });
});
