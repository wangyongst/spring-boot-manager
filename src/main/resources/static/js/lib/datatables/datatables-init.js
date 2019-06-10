$(document).ready(function () {
    $('#example23').DataTable({
        dom: 'Bfrtip',
        buttons: [
            'selectAll',
            'selectNone'
        ],
        language: {
            buttons: {
                selectAll: "全选",
                selectNone: "全不选"
            }
        }
    });
});

