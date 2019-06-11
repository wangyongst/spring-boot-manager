$(function () {
    $.ajax({
        type: "GET",
        cache: "false",
        url: "/admin/me",
        dataType: "json",
        success: function (result) {
            if (result.status == 1) {
                var mapParent = {};
                var mapChild = {};
                $.each(result.data.role.role2Privs, function (key, val) {
                    debugger;
                    if (val.privilege.type == 0) {
                        mapParent[val.privilege.id] = "<li class='active'> <a class=\"has-arrow  \" href=\"#\" aria-expanded=\"true\"><i class=\"fa fa-tachometer\"></i><span class=\"hide-menu\">" + val.privilege.name + "</span></a><ul aria-expanded=\"true\" class=\"collapse in\">";
                    } else if (val.privilege.type == 1) {
                        if (mapChild[val.privilege.parentid] == undefined) mapChild[val.privilege.parentid] = "<li><a href=\"javascript:void(0);\" onclick=\"goto('" + val.privilege.url + "')\">" + val.privilege.name + " </a></li>";
                        else mapChild[val.privilege.parentid] = mapChild[val.privilege.parentid] + "<li><a href=\"javascript:void(0);\" onclick=\"goto('" + val.privilege.url + "')\">" + val.privilege.name + " </a></li>";
                    }
                });
                var html = "";
                for (x in mapParent) {
                    html = html + mapParent[x] + mapChild[x] + "</ul></li>"
                }
                $("#sidebarnav").append(html);
            }
        }
    });
});


function goto(url) {
    $("#right").attr("src", url);
}


function logout() {
    $.ajax({
        type: "POST",
        cache: "false",
        url: "admin/logout",
        dataType: "json",
        success: function (result) {
            if (result.status == 1) {
                window.location.href = "page-login.html";
            } else {
                alert(result.message);
            }
        }
    });
}

