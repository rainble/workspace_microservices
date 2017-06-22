/********************************************************************/
/********************Function For Inside Payment Service***************/
$("#inside_payment_query_account_button").click(function(){
    $.ajax({
        type: "get",
        url: "/inside_payment/queryAccount",
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            var size = result.length;
            $("#query_inside_payment_account_list_table").find("tbody").html("");
            $("#query_inside_payment_account_list_table").find("tbody").css('height','200px');
            for (var i = 0; i < size; i++) {
                $("#query_inside_payment_account_list_table").find("tbody").append(
                    "<tr>" +
                    "<td>" + result[i]["accountId"] + "</td>" +
                    "<td>" + result[i]["balance"] + "</td>" +
                    "</tr>"
                );
            }
        }
    });
});

$("#inside_payment_query_payment_button").click(function(){
    $.ajax({
        type: "get",
        url: "/inside_payment/queryPayment",
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            var size = result.length;
            $("#query_inside_payment_payment_list_table").find("tbody").html("");
            $("#query_inside_payment_payment_list_table").find("tbody").css('height','200px');
            for (var i = 0; i < size; i++) {
                $("#query_inside_payment_payment_list_table").find("tbody").append(
                    "<tr>" +
                    "<td>" + result[i]["paymentId"] + "</td>" +
                    "<td>" + result[i]["orderId"] + "</td>" +
                    "<td>" + result[i]["userId"] + "</td>" +
                    "<td>" + result[i]["price"] + "</td>" +
                    "</tr>"
                );
            }
        }
    });
});

$("#inside_payment_pay_button").click(function(){
    var info = new Object();
    info.orderId = $("#inside_payment_orderId").val();
    info.tripId = $("#inside_payment_tripId").val();
    var data = JSON.stringify(info);
    $.ajax({
        type: "post",
        url: "/inside_payment/pay",
        contentType: "application/json",
        data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            $("#inside_payment_result").html(result);
        }
    });
});