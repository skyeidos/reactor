var socket = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    socket = new SockJS('/channel');
    socket.onopen = function() {
        console.log('open');
        setConnected(true)
    };

    socket.onmessage = function(e) {
        showGreeting(e)
    };

    socket.onclose = function() {
       disconnect()
    };
}

function disconnect() {
    if (socket !== null) {
        socket.close();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    socket.send($("#name").val());
}

function showGreeting(message) {
    console.log(message)
    $("#greetings").append("<tr><td>" + message.data + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});