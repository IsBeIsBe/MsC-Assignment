<html>

	<head>
		<!-- Web page title -->
    	<title>Top Trumps</title>
    	
    	<!-- Import JQuery, as it provides functions you will probably find useful (see https://jquery.com/) -->
    	<script src="https://code.jquery.com/jquery-2.1.1.js"></script>
    	<script src="https://code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
        <link rel="stylesheet" href="https://code.jquery.com/ui/1.11.1/themes/flick/jquery-ui.css">
		<link rel="stylesheet" href="https://raw.githack.com/IsBeIsBe/MsC-Assignment/master/resources/online/dwViews/toptrumpsCSS.css">


	</head>

    <body onload="initalize()">
    	
	<div class="container">

		You made it to the Game Screen!

	<div>
		<a href="http://localhost:7777/toptrumps/">Go Back</a>
	</div>
		
	<div>
		<button id=myButton onclick="playGame()">Play Game</button>
	</div>
	
	<div>
		<p id="playerIndex"></p>
	</div>
	
	<div>
		<p id="test"></p>
	</div>
		
	<div class="card" style="background-color:pink"
		<div class="cardInfo">
		
			<p id="cardName"></p>
			
	        <img id="sandwich" src="https://raw.githack.com/IsBeIsBe/MsC-Assignment/master/resources/online/dwViews/oscar.jpg"
            width="200" height="200">
	        
	        <div class="radio-labels">
            <div class="this-label">
                <label for="Size">Size</label>
                <input type="radio" id="Size" name="choices" value="Size">
                <label for="Size" id="attribute1"></label>
            </div>
            <br>
	        
            <div class="radio-labels">
            <div class="this-label">
                <label for="Rarity">Rarity</label>
                <input type="radio" id="Rarity" name="choices" value="Rarity">
                <label for="Rarity" id="attribute2"></label>
            </div>
            <br>
            
            <div class="radio-labels">
            <div class="this-label">
                <label for="Good Temper">Good Temper</label>
                <input type="radio" id="Good Temper" name="choices" value="Good Temper">
                <label for="Good Temper" id="attribute3"></label>
            </div>
            <br>
	        
            <div class="radio-labels">
            <div class="this-label">
                <label for="Cuteness">Cuteness</label>
                <input type="radio" id="Cuteness" name="choices" value="Cuteness">
                <label for="Cuteness" id="attribute4"></label>
            </div>
            <br>
            
            <div class="radio-labels">
            <div class="this-label">
                <label for="Mischief Rating">Mischief Rating</label>
                <input type="radio" id="Mischief Rating" name="choices" value="Mischief Rating">
                <label for="Mischief Rating" id="attribute5"></label>
            </div>
            <br>
         </div>

	<div>
		<p id="chosenAttribute"></p>
	</div>

	<div>
		<p id="checkingMessage"></p>
	</div>
	
	<div>
		<p id="endOfRoundMessage"></p>
	</div>

		<script type="text/javascript">
		
			function initalize() {
				
			}

			function playGame() {
			 
				document.getElementById("myButton").style.visibility = "hidden";
				startUp();
				
				


			}
			
			function startUp() {
				
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/startAndSelect");
				
				if (!xhr) {
					alert("CORS not supported");
				}
				
				xhr.onload = function(e){
					var responseText = xhr.response;
					playerIndex = JSON.parse(responseText);					
					document.getElementById("playerIndex").innerHTML = "It's Player " + playerIndex + "'s turn to play!";
					chooseAttribute();
					getCardInfo();



				}

				xhr.send();	

			}

			function chooseAttribute() {
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/chosenAttribute");
				if(!xhr) {
					alert("CORS not supported");
				}

				xhr.onload = function(e) {
					var responseText = xhr.response;
					chosenAttribute = JSON.parse(responseText);
					document.getElementById("chosenAttribute").innerHTML = chosenAttribute;
					findWinner();

				}
				xhr.send();
			}
			function findWinner() {
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/findWinner");
				if(!xhr) {
					alert("CORS not supported");
				}

				xhr.onload = function(e) {
					var responseText = xhr.response;
					roundMessage = JSON.parse(responseText);
					document.getElementById("checkingMessage").innerHTML = roundMessage;
					endtheRound();
				}
				xhr.send();
			}


			function endtheRound() {
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/checkForDraws");
				if(!xhr) {
					alert("CORS not supported");
				}

				xhr.onload = function(e) {
					var responseText = xhr.response;
					roundMessage = JSON.parse(responseText);
					document.getElementById("endOfRoundMessage").innerHTML = roundMessage;
				}
				xhr.send();
			}
			
			function getSelector() {
				
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/getSelector");
				
				if (!xhr) {
					alert("CORS not supported");
				}
				
				xhr.onload = function(e){
					var responseText = xhr.response;
					selector = JSON.parse(responseText);					
					document.getElementById("test").innerHTML = selector;
					return selector;
					
					
				}
				
				xhr.send();	
				
				
			}
			
			function setSelector(selectorValue) {
				
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/setSelector"+selector);
				
				if (!xhr) {
					alert("CORS not supported");
				}
				
				xhr.onload = function(e){
					var responseText = xhr.response;
					selectorIs = JSON.parse(responseText);					
					document.getElementById("test").innerHTML = selectorIs + " IS THE SELECTOR.";


				}

				xhr.send();	
				
				
				
			}
						
			
			function getCardInfo() {
				
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/getCard?player"+Player);
				
				if (!xhr) {
					alert("CORS not supported");
				}
				
				xhr.onload = function(e){
					var responseText = xhr.response;
					playerCard = JSON.parse(responseText);					
					document.getElementById("cardName").innerHTML = playerCard;

					
					
				}
				
				xhr.send();	
			}
			
			function createCORSRequest(method, url) {
  				var xhr = new XMLHttpRequest();
  				if ("withCredentials" in xhr) {

    				// Check if the XMLHttpRequest object has a "withCredentials" property.
    				// "withCredentials" only exists on XMLHTTPRequest2 objects.
    				xhr.open(method, url, true);

  				} else if (typeof XDomainRequest != "undefined") {

    				// Otherwise, check if XDomainRequest.
    				// XDomainRequest only exists in IE, and is IE's way of making CORS requests.
    				xhr = new XDomainRequest();
    				xhr.open(method, url);

 				 } else {

    				// Otherwise, CORS is not supported by the browser.
    				xhr = null;

  				 }
  				 return xhr;
			}
		
		</script>
		
		<!-- Here are examples of how to call REST API Methods -->
		<script type="text/javascript">
		
			// This calls the helloJSONList REST method from TopTrumpsRESTAPI
			function helloJSONList() {
			
				// First create a CORS request, this is the message we are going to send (a get request in this case)
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/helloJSONList"); // Request type and URL
				
				// Message is not sent yet, but we can check that the browser supports CORS
				if (!xhr) {
  					alert("CORS not supported");
				}

				// CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
				// to do when the response arrives 
				xhr.onload = function(e) {
 					var responseText = xhr.response; // the text of the response
					alert(responseText); // lets produce an alert
				};
				
				// We have done everything we need to prepare the CORS request, so send it
				xhr.send();		
			}
			
			// This calls the helloJSONList REST method from TopTrumpsRESTAPI
			function helloWord(word) {
			
				// First create a CORS request, this is the message we are going to send (a get request in this case)
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/helloWord?Word="+word); // Request type and URL+parameters
				
				// Message is not sent yet, but we can check that the browser supports CORS
				if (!xhr) {
  					alert("CORS not supported");
				}

				// CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
				// to do when the response arrives 
				xhr.onload = function(e) {
 					var responseText = xhr.response; // the text of the response
					alert(responseText); // lets produce an alert
				};
				
				// We have done everything we need to prepare the CORS request, so send it
				xhr.send();		
			}

		</script>
		
		</body>
</html>