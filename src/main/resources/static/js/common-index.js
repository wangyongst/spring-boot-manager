$(function () {
    $.get("/admin/me",
        function (result) {
            if (result.status == 1) {
                $("#usernameButton").text(result.data.name);
            }
        });
});

function logout() {
    $.post("/shiro/logout");
    window.location.href = "/page-login";
};

function changepassword() {
    window.location.href = "/view/page-changepassword";
};


function clearForm(form) {
    $('input', form).each(function () {
        this.value = "";
    });

    $('textarea', form).each(function () {
        this.value = "";
    });

    $('select', form).each(function () {
        this.selectedIndex = -1;
    });

    $('checkbox', form).each(function () {
        this.checked = false;
    });
}


function multiSelect() {
    jQuery(".standardSelect").chosen({
        disable_search_threshold: 10,
        no_results_text: "Oops, nothing found!",
        width: "100%"
    });
}

function select() {
    var ids = "";
    $("input[name=btSelectItem]").each(function () {
        if ($(this).prop('checked')) {
            var index = $("table input:checkbox").index(this);
            val = $("table").find("tr").eq(index).find("td").eq(1).text();
            ids += "," + val;
        }
    });
    if (ids.length > 1) ids = ids.substr(1);
    return ids;
}

function getWebRootPath() {
    var href = window.document.location.href;
    var pathname = window.document.location.pathname;
    var pos = href.indexOf(pathname);
    var path = href.substring(0, pos);
    return path;
}

function dateformatter(value, row, index) {
    if(value != null) return value.substr(0,10);
    else return null;
}