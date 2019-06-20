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