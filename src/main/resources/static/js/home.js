$(function () {
    $.ajax({
        type: "GET",
        cache: "false",
        url: "/admin/user/me",
        dataType: "json",
        success: function (result) {
            if (result.status == 1) {
                var mapParent = {};
                var mapChild = {};
                $.each(result.data.adminRole.adminPrivileges, function (key, val) {
                    if (val.adminMenu.type == 0) {
                        mapParent[val.adminMenu.id] = "<li class='active'> <a class=\"has-arrow  \" href=\"#\" aria-expanded=\"true\"><i class=\"fa fa-tachometer\"></i><span class=\"hide-menu\">" + val.adminMenu.name + "</span></a><ul aria-expanded=\"true\" class=\"collapse in\">";
                    } else if (val.adminMenu.type == 1) {
                        if (mapChild[val.adminMenu.parent] == undefined) mapChild[val.adminMenu.parent] = "<li><a href=\"javascript:void(0);\" onclick=\"goto('" + val.adminMenu.url + "')\">" + val.adminMenu.name + " </a></li>";
                        else mapChild[val.adminMenu.parent] = mapChild[val.adminMenu.parent] + "<li><a href=\"javascript:void(0);\" onclick=\"goto('" + val.adminMenu.url + "')\">" + val.adminMenu.name + " </a></li>";
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
        url: "admin/user/logout",
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

