$(document).ready(function(){

    var table = $(".vacancies").DataTable({
        ajax: { url: "/vacancies", dataSrc: "" },
        columns: [
            { "data": "id" },
            { "data": "result_text" },
            { "data": "reply_checkbox" },
            { "data": "keyword_decoded" },
            { "data": "position_link" },
            { "data": "salary" },
            { "data": "company" },
            { "data": "location" }
        ],
        order: [[ 0, "desc" ]],
        pageLength: 100,
        lengthChange: false,
        info: false
    });

    $(".search").on( "keyup", function() {
        table.search( $(this).val() ).draw();
    });

    $(".vacancies").on("change", ".reply-checkbox", function() {
        console.log(1)
        var el = $(this);
        $.getJSON("/vacancies/vacancy-check/" + el.data("id") + "/" + el.prop("checked"), function(data){
            if (data.reply == true) {
                el.prop("checked", true);
            }

            if (data.replied == true) {
                el.prop("disabled", true);
            }
        })
    });

    $(".navbar").on("click", ".show-user", function() {
        var block = $(".user")

        if (block.is(':hidden')) {
            block.show();
            $("#icon").attr("class", "glyphicon glyphicon-chevron-left")
        } else {
            block.hide();
            $("#icon").attr("class", "glyphicon glyphicon-chevron-right")
        }
    });
});