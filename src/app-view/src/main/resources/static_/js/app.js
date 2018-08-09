//var's
search_host = "http://localhost:8089/api/v1/search/";
client_host = "http://localhost:8089/api/v1/client/";
var order;

//init UI component's
$('.date input').each(function() {
 $(this).datepicker();
});
$(".date-input").datepicker({
 // options
});

function isEmail(email) {
    var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    return regex.test(email);
}

function removeRows(){
    $("#orderTable tbody tr").each(function(){
        $(this).remove();
    });
}

function addRows(value){
    $("#orderTable tbody").append(value);
}

function openModel(){
//orders[x].items + ", " + orders[x].client
    console.log(order.items);
    console.log(order.client.name);
    $("#spanClientName").html(order.client.name);
    $("#spanPhone").html(order.client.phone);
    $("#spanEmail").html(order.client.email);
    $("#modalId").modal('show');
}

$( document ).ready(function() {

    removeRows();
    //openModel(null,null);

    $("#btnSearch").click(function(event) {
        event.preventDefault();
        searchOrder();
    });

    function searchOrder() {

        //validing form request
        if ($("#eMail").val() != "" && !isEmail(eMail)){
            alert("E-mail invalid");
            return false;
        }

        if (Date.parse($("#startDate").val()) > Date.parse($("#endDate").val())){
            alert("Start date is smaller than end date.");
            return false;
        }

        if ($("#startDate").val() != "" && $("#endDate").val() == "") {
            alert("End date is blank.");
            return false;
        }

        if ($("#endDate").val() != "" && $("#startEnd").val() == "" ){
            alert("Start date is blank.");
            return false;
        }


        //alert(1); get("http://localhost:8089/api/v1/client/name/alisson", "");
        alert(2); get("http://localhost:8089/api/integ/orders?eMail=" + $("#eMail").val() + "&phone=" + $("#phone").val() + "&clientName=" + $("#clientName").val() + "&startDate=" + $("#startDate").val() + "&endDate=" + $("#endDate").val(), "");

    }

    function get(url, data){
        $.ajax({
            type : "GET",
            contentType : "application/json",
            url : url,
            data : data,
            dataType : 'json',
            timeout : 100000,
            success : function(data) {
                console.log("SUCCESS: ", data);
                orders = data;
                displaySearch(orders)
            },
            error : function(e) {
                console.log("ERROR: ", e);
                alert("ERROR: " + e);
            }
        });
    }

    function displaySearch(orders) {
        console.log("json: " + JSON.stringify(orders));
        console.log("orders items: " + orders[0].items );
        console.log("orders client: " + orders[0].client.id );
        //orders = JSON.stringify(orders);

        removeRows();
        for (x = 0; x < orders.length; x++){

            total = 0;
            order = orders[x];

            for (y = 0; y < orders[x].items; y++){
                total += orders[x].items[y].quantity * orders[x].items[y].price;
            }

            row = "<tr onclick=\"openModel();\"><td>" + new Date(orders[x].createdAt) +   "</td>"+
            "<td>" + orders[x].client.name + "</td>" +
            "<td>" + orders[x].client.phone +  "</td>" +
            "<td>" + orders[x].client.email +  "</td>" +
            "<td>" + total + "</td></tr>";
            addRows(row);
        }

    }

})



