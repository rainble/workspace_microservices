
/*********************************************************************/
/********************Function For Travel Service**********************/
/********************Used For Travel Service Single Microservice Test*/

// $("#travel_create_button").click(function(){
//     var travelCreateInfo = new Object();
// //     var travelCreateInfo = {
// //         tripId : $("#travel_create_tripId").val();
// // }
//     travelCreateInfo.tripId = $("#travel_create_tripId").val();
//     travelCreateInfo.trainTypeId = $("#travel_create_trainTypeId").val();
//     travelCreateInfo.startingStation = $("#travel_create_startingStation").val();
//     travelCreateInfo.stations = $("#travel_create_stations").val();
//     travelCreateInfo.terminalStation = $("#travel_create_terminalStation").val();
//
//     travelCreateInfo.startingTime = "1970-01-01T" +  $("#travel_create_startingTime").val() +":00Z";
//     travelCreateInfo.endTime = "1970-01-01T" +  $("#travel_create_endTime").val() +":00Z";
//
//     var travelCreateData = JSON.stringify(travelCreateInfo);
//     $.ajax({
//         type: "post",
//         url: "/travel/create",
//         contentType: "application/json",
//         dataType: "text",
//         data: travelCreateData,
//         xhrFields: {
//             withCredentials: true
//         },
//         success: function(result){
//             $("#travel_result").html(result);
//         }
//     });
// });

//------For Trip retrieve------------

$("#travel_queryAll_button").click(function(){
    $.ajax({
        type: "get",
        url: "/travel/queryAll",
        contentType: "application/json",
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            var size = result.length;
            $("#query_travel_list_table").find("tbody").html("");
            for(var i = 0;i < size;i++){
                $("#query_travel_list_table").find("tbody").append(
                    "<tr>" +
                    "<td>" + result[i]["tripId"]["type"] + result[i]["tripId"]["number"] + "</td>" +
                    "<td>" + result[i]["trainTypeId"]     + "</td>" +
                    "<td>" + result[i]["startingStationId"]     + "</td>" +
                    "<td>" + result[i]["stationsId"]     + "</td>" +
                    "<td>" + result[i]["terminalStationId"]     + "</td>" +
                    "<td>" + convertNumberToTimeString(result[i]["startingTime"]) + "</td>" +
                    "<td>" + convertNumberToTimeString(result[i]["endTime"]) + "</td>" +
                    "</tr>"
                );
            }
        }
    });
});

$("#travel2_queryAll_button").click(function(){
    $.ajax({
        type: "get",
        url: "/travel2/queryAll",
        contentType: "application/json",
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            var size = result.length;
            $("#query_travel2_list_table").find("tbody").html("");
            for(var i = 0;i < size;i++){
                $("#query_travel2_list_table_result").append(
                    "<tr>" +
                    "<td>" + result[i]["tripId"]["type"] + result[i]["tripId"]["number"] + "</td>" +
                    "<td>" + result[i]["trainTypeId"]     + "</td>" +
                    "<td>" + result[i]["startingStationId"]     + "</td>" +
                    "<td>" + result[i]["stationsId"]     + "</td>" +
                    "<td>" + result[i]["terminalStationId"]     + "</td>" +
                    "<td>" + convertNumberToTimeString(result[i]["startingTime"]) + "</td>" +
                    "<td>" + convertNumberToTimeString(result[i]["endTime"]) + "</td>" +
                    "</tr>"
                );
            }
        }
    });
});

//------For Trip update------------
$("#travel_update_button").click(function(){
    var travelInfo = new Object();
    travelInfo.tripId = $("#travel_update_tripId").val();
    travelInfo.trainTypeId = $("#travel_update_trainTypeId").val();
    travelInfo.startingStationId =  $("#travel_update_startingStationId").val();
    travelInfo.stationsId = $("#travel_update_stationsId").val();
    travelInfo.terminalStationId = $("#travel_update_terminalStationId").val();
    travelInfo.startingTime = convertStringToTime($("#travel_update_startingTime").val());
    travelInfo.endTime = convertStringToTime($("#travel_update_endTime").val());
    var data = JSON.stringify(travelInfo);
    $.ajax({
        type: "post",
        url: "/travel/update",
        contentType: "application/json",
        dataType: "json",
        data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            $("#travel_result").html(JSON.stringify(result));
        },
    });
});

$("#travel2_update_button").click(function(){
    var travelInfo = new Object();
    travelInfo.tripId = $("#travel2_update_tripId").val();
    travelInfo.trainTypeId = $("#travel2_update_trainTypeId").val();
    travelInfo.startingStationId =  $("#travel2_update_startingStationId").val();
    travelInfo.stationsId = $("#travel2_update_stationsId").val();
    travelInfo.terminalStationId = $("#travel2_update_terminalStationId").val();
    travelInfo.startingTime = convertStringToTime($("#travel2_update_startingTime").val());
    travelInfo.endTime = convertStringToTime($("#travel2_update_endTime").val());
    var data = JSON.stringify(travelInfo);
    $.ajax({
        type: "post",
        url: "/travel2/update",
        contentType: "application/json",
        dataType: "json",
        data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            $("#travel2_result").html(JSON.stringify(result));
        },
    });
});

//------For Trip delete------------
// $("#travel_delete_button").click(function(){
//     var travelInfo = new Object();
//     travelInfo.tripId = $("#travel_delete_tripId").val();
//     var data = JSON.stringify(travelInfo);
//     $.ajax({
//         type: "post",
//         url: "/travel/delete",
//         contentType: "application/json",
//         dataType: "json",
//         data:data,
//         xhrFields: {
//             withCredentials: true
//         },
//         success: function(result){
//             $("#travel_result").html(JSON.stringify(result));
//         }
//     });
// });

/********************************************************************/
/*******************Function For Travel Query************************/

function convertNumberToTimeString(timeNumber) {
    var str = new Date(timeNumber);
    var newStr = str.getHours() + ":" + str.getMinutes() + "";
    return newStr;
}

function convertStringToTime(string){
    var date = new Date();
    var s = string.toString();
    var index = s.indexOf(':');
    var hour = s.substring(0,index).valueOf();
    var minute = s.substring(index+1,s.length).valueOf();
    date.setHours(hour);
    date.setMinutes(minute);
    return date;
}