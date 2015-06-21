<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

	<script type="text/javascript">
		var wsUrl = "ws://localhost:8080/WebSocket/ws/contact/BF1/SDG?p=v";
		var webSocket = new WebSocket(wsUrl);
		
		webSocket.onopen = function(){
		    //ws.send("Message to send");
		    console.log("Client > Connection is onopen...");
		};
		
		webSocket.onmessage = function(evt) {
			console.log("Client > Connection is onmessage...");
			console.log(evt);
		};
		
		webSocket.onclose = function(){ 
			console.log("Client > Connection is closed...");
		};
		
	</script>
	
</head>

<body>
	<h1>Hello WebSocket :)</h1>
</body>

</html>