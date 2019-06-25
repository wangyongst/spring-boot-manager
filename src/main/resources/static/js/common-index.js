$(function () {
    $.get("admin/me",
        function (result) {
            if (result.status == 1) {
                $("#usernameButton").text(result.data.name);
            }
        });
});

function logout() {
    $.post("shiro/logout",
        function (result) {
            if (result.status == 3) {
                window.location.href = "page-login.html";
            }
        });
};

function changepassword() {
    window.location.href = "page-changepassword.html";
};


function clearForm(form) {
    // input清空
    $(':input', form).each(function () {
        var type = this.type;
        var tag = this.tagName.toLowerCase(); // normalize case
        if (type == 'text' || type == 'password' || tag == 'textarea' || tag == "tel") {
            this.value = "";
        } else if (tag == 'select') {
            this.selectedIndex = -1;
        }
    });
    var boxes = $("input[type=checkbox]", form);
    for (i = 0; i < boxes.length; i++) {
        boxes[i].checked = false;
    }
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