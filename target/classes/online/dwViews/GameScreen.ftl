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
		<p id="rounds"></p>
	</div>
	
	<div>
		<p id="playerIndex"></p>
	</div>
	
	<div>
		<p id="test"></p>
	</div>

	<div>
		<p id="chosenAttribute"></p>
	</div>

	<div>
		<button id="attributeButton" onclick="findWinner()">Okay</button>
	</div>
	<script type="text/javascript">
		document.getElementById("attributeButton").style.visibility = "hidden";
	</script>

	<div>
		<p id="checkingMessage"></p>
	</div>
	
	<div>
		<p id="endOfRoundMessage"></p>
	</div>

	<div>
		<button id="getCardCountButton" onclick="startNewRound()">Let's Go!</button>
	</div>
	<script type="text/javascript">
		document.getElementById("getCardCountButton").style.visibility = "hidden";
	</script>

	<div>
		<p id="humanCardCount"></p>
		<p id="AI1CardCount"></p>
		<p id="AI2CardCount"></p>
		<p id="AI3CardCount"></p>
		<p id="AI4CardCount"></p>
	</div>
	<script type="text/javascript">
		document.getElementById("humanCardCount").style.visibility = "hidden";
		document.getElementById("AI1CardCount").style.visibility = "hidden";
		document.getElementById("AI2CardCount").style.visibility = "hidden";
		document.getElementById("AI3CardCount").style.visibility = "hidden";
		document.getElementById("AI4CardCount").style.visibility = "hidden";
	</script>


<!-- After findWinner() - make checkForOverallWinner a method with an if statement, if overall winner end the game, if not, call chooseAttribute etc -->
	<div>
		<button id="endRoundButton" onclick="getCardCounts()">Get Scores!</button>
	</div>
	<script type="text/javascript">
		document.getElementById("endRoundButton").style.visibility = "hidden";
	</script>


		<script type="text/javascript">
		
			function initalize() {
				
			}

			function playGame() {
			 
				document.getElementById("myButton").style.visibility = "hidden";
				getRounds();
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
					document.getElementById("playerIndex").style.visibility = "visible";
					getCardInfo();
					chooseAttribute();
					



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
					document.getElementById("chosenAttribute").style.visibility = "visible";
					document.getElementById("attributeButton").style.visibility = "visible";

				}
				xhr.send();
			}
			function findWinner() {
				document.getElementById("attributeButton").style.visibility = "hidden";
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/findWinner");
				if(!xhr) {
					alert("CORS not supported");
				}

				xhr.onload = function(e) {
					var responseText = xhr.response;
					roundMessage = JSON.parse(responseText);
					document.getElementById("checkingMessage").innerHTML = roundMessage;
					document.getElementById("checkingMessage").style.visibility = "visible";
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
					document.getElementById("endOfRoundMessage").style.visibility = "visible";
					document.getElementById("endRoundButton").style.visibility = "visible";
					
					
				}
				xhr.send();
			}

			function getCardCounts(){
				document.getElementById("getCardCountButton").style.visibility = "hidden";
				getCardCount(0);
				getCardCount(1);
				getCardCOunt(2);
				getCardCount(3);
				getCardCount(4);
				document.getElementById("endRoundButton").style.visibility = "visible";
			}

			function getCardCount(playerIndex){
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/getCardCount");
				
				if (!xhr) {
					alert("CORS not supported");
				}
				
				xhr.onload = function(e){
					var responseText = xhr.response;
					count = JSON.parse(responseText);					
					
					if(playerIndex == 0){
						document.getElementById("humanCardCount").innerHTML = "Human Player's Card Count: " + count;
						document.getElementById("humanCardCount").style.visibility = "visible";
					}
					
					if(playerIndex == 1){
						document.getElementById("AI1CardCount").innerHTML = "AI Player One's Card Count: " + count;
						document.getElementById("AI1CardCount").style.visibility = "visible";
					}			

					if(playerIndex == 2){
						document.getElementById("AI2CardCount").innerHTML = "AI Player Two's Card Count: " + count;
						document.getElementById("AI2CardCount").style.visibility = "visible";
					}			
					
					if(playerIndex == 3){
						document.getElementById("AI3CardCount").innerHTML = "AI Player Three's Card Count: " + count;
						document.getElementById("AI3CardCount").style.visibility = "visible";
					}	

					if(playerIndex == 4){
						document.getElementById("AI4CardCount").innerHTML = "AI Player Four's Card Count: " + count;
						document.getElementById("AI4CardCount").style.visibility = "visible";
					}	
					
				}
				
				xhr.send();	
				
				
			}

			function startNewRound() {
				document.getElementById("endRoundButton").style.visibility = "hidden";
				document.getElementById("endOfRoundMessage").style.visibility = "hidden";
				document.getElementById("checkingMessage").style.visibility = "hidden";
				document.getElementById("chosenAttribute").style.visibility = "hidden";
				document.getElementById("chosenAttribute").style.visibility = "hidden";
				document.getElementById("humanCardCount").style.visibility = "hidden";
				document.getElementById("AI1CardCount").style.visibility = "hidden";
				document.getElementById("AI2CardCount").style.visibility = "hidden";
				document.getElementById("AI3CardCount").style.visibility = "hidden";
				document.getElementById("AI4CardCount").style.visibility = "hidden";



				getSelector();

			}
			
			function getSelector() {
				
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/getSelector");
				
				if (!xhr) {
					alert("CORS not supported");
				}
				
				xhr.onload = function(e){
					var responseText = xhr.response;
					selector = JSON.parse(responseText);					
					document.getElementById("playerIndex").innerHTML = "It's Player " + selector + "'s turn to play!"
					document.getElementById("playerIndex").style.visibility = "visible";
					getRounds();
					getCardInfo();
					chooseAttribute();
					
					
					
				}
				
				xhr.send();	
				
				
			}

			function getRounds() {
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/getRoundNum");

				if (!xhr) {
					alert("CORS not supported");
				}

				xhr.onload = function(e){
					var responseText = xhr.response;
					roundNum = JSON.parse(responseText);
					document.getElementById("rounds").innerHTML = "Round: " + roundNum;
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
				
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/getCard?player");
				
				if (!xhr) {
					alert("CORS not supported");
				}
				
				xhr.onload = function(e){
					var responseText = xhr.response;
					playerCard = JSON.parse(responseText);					
					document.getElementById("cardName").innerHTML = "Your cards is: "+playerCard;

					
					
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
	</div>
	<script type = "text/javascript">
		document.getElementByClass("card").style.visibility = "hidden";
	</script>
		
		</body>
</html>