$(document).ready(function(){

    var table = $(".professions").DataTable({
        ajax: { url: "/professions", dataSrc: "" },
        columns: [
            { "data": "id" },
            { "data": "keyword_decoded" },
            { "data": "message" },
            { "data": "actions" },
        ],
        pageLength: 100,
        lengthChange: false,
        info: false
    });

    $(".search").on( "keyup", function() {
        table.search( $(this).val() ).draw();
    });

});