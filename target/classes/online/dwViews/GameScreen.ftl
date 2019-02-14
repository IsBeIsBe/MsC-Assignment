<html>
<!-- 13/02 Have refactored GameScreen to include just one message and button that is re-written a lot, hopefully making things easier to follo -->
<!-- Still need to include javascript method buttons() for declaring what methods are called based upon the String in "buttonValue" -->
<!-- workiing in costa so also haven't been able to test any of this, but most of it is copy and pasted in -->
	<head>
		<!-- Web page title -->
    	<title>Top Trumps</title>
    	
    	<!-- Import JQuery, as it provides functions you will probably find useful (see https://jquery.com/) -->
    	<script src="https://code.jquery.com/jquery-2.1.1.js"></script>
    	<script src="https://code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
        <link rel="stylesheet" href="https://code.jquery.com/ui/1.11.1/themes/flick/jquery-ui.css">
		<link rel="stylesheet" href="https://raw.githack.com/IsBeIsBe/MsC-Assignment/master/resources/online/dwViews/toptrumpsCSS.css">


	</head>

   <!-- <body onload="initalize()"> -->
    <h1>Top Trumps</h1>

    <hr />

    <div>
		<a href="http://localhost:7777/toptrumps/">Go Back</a>
	</div>

    <hr />

    <div>
        <button id=myButton onclick="playGame()" value="Play Game"></button>
    </div>
	<script type="text/javascript">
		document.getElementById("myButton").innerHTML = myButton.value;
	</script>

	<div>
		<p id="rounds"></p>
	</div>

    <hr />

    <div>
        <p id="playerCardInfo"></p>
        <p id="playerCardCountInfo"></p>
    </div>
    <script type="text/javascript">
        document.getElementById("playerCardInfo").style.visibility = "hidden";
        document.getElementById("playerCardCountInfo").style.visibility = "hidden";
    </script>


    <div>
        <p id="gameInfo"></p>
    </div>
    <script type="text/javascript">
        document.getElementById("gameInfo").style.visibility = "hidden";
    </script>

    <hr />

    <div>
        <button id=gameButton onclick="buttonsFunctions()" value=""></button>
    </div>
	<script type="text/javascript">
		document.getElementById("gameButton").innerHTML = gameButton.value;
	</script>

    <hr />

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

	<div>
		<p id="commonPileCount"></p>
	</div>
	<script type="text/javascript">
		document.getElementById("commonPileCount").style.visibility = "hidden";
	</script>

    <script type="text/javascript">

		function playGame() {
	//	document.getElementById("myButton").innerHTML = "Okay!";	 
		document.getElementById("myButton").style.visibility = "hidden";
		getRounds();
		startUp();
		}

		function buttonsFunctions() {

			var buttonValue = document.getElementById("gameButton").getAttribute("value");
			//document.getElementById("gameInfo").innerHTML = buttonValue;

			if (buttonValue === "Okay! Let's see what they choose!") {
				chooseAttribute();

			} else if (buttonValue === "Okay! Let's see who won!") {
				findWinner();

			} else if (buttonValue === "Great! Let's update the scores!") {
				getCardCounts();

			} else if (buttonValue === "Play next Round") {
				//alert("Button Pressed!");
				startNewRound();

			} else if (buttonValue === "Click to Play Again!") {
				startUp();

			} else if (buttonValue === "Click to see final scores!") {
				endTheGame();

			}

		}

        function startUp() {
				
			var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/startAndSelect");
				
			if (!xhr) {
				alert("CORS not supported");
			}
				
			xhr.onload = function(e){
				var responseText = xhr.response;
				playerIndex = JSON.parse(responseText);					
				document.getElementById("gameInfo").innerHTML = "It's Player " + playerIndex + "'s turn to play!";
				document.getElementById("gameInfo").style.visibility = "visible";
                document.getElementById("gameButton").value = "Okay! Let's see what they choose!";
				document.getElementById("gameButton").innerHTML = gameButton.value;
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
				document.getElementById("gameInfo").innerHTML = chosenAttribute;
			//	document.getElementById("chosenAttribute").style.visibility = "visible";
				document.getElementById("gameButton").value = "Okay! Let's see who won!";
				document.getElementById("gameButton").innerHTML = gameButton.value;

			}
			xhr.send();
		}

		function findWinner() {
			//document.getElementById("attributeButton").style.visibility = "hidden";
			var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/findWinner");
			if(!xhr) {
				alert("CORS not supported");
			}

			xhr.onload = function(e) {
				var responseText = xhr.response;
				roundMessage = JSON.parse(responseText);
				document.getElementById("gameInfo").innerHTML = roundMessage;
			//	document.getElementById("gameButton").style.visibility = "visible";
				endTheRound();
			}
			xhr.send();
		}

		function endTheRound() {
			var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/checkForDraws");
			if(!xhr) {
    			alert("CORS not supported");
			}

			xhr.onload = function(e) {
			
				var responseText = xhr.response;
				roundMessage = JSON.parse(responseText);
				document.getElementById("gameInfo").innerHTML = roundMessage;
				if (roundMessage === "There was a draw! All cards have been moved to the common pile! Now let's play again!") {
					commonPileCount();
				} else {
					document.getElementById("commonPileCount").style.visibility = "hidden";
				}

				document.getElementById("gameButton").value = "Great! Let's update the scores!";
				document.getElementById("gameButton").innerHTML = gameButton.value;
				
			}
			xhr.send();
		}

		function getCardCounts(){
			//document.getElementById("getCardCountButton").style.visibility = "hidden";
			getCardCount();
			getCardCountforAI1();
			getCardCountforAI2();
			getCardCountforAI3();
			getCardCountforAI4();
			document.getElementById("gameInfo").innerHTML = "Great! Let's play another round!";
            document.getElementById("gameButton").value = "Play next Round";
			document.getElementById("gameButton").innerHTML = gameButton.value;

		}

		function commonPileCount() {
			var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/getCommonPile");
			
			if (!xhr) {
				alert("CORS not supported");
			}
				
			xhr.onload = function(e){
				var responseText = xhr.response;
				commonPileCountValue = JSON.parse(responseText);
				document.getElementById("commonPileCount").innerHTML = commonPileCountValue;
				document.getElementById("commonPileCount").style.visibility = "visible";
			}
			xhr.send()
		}

		function startNewRound() {

			var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/winnerCheck");
			if(!xhr) {
				alert("CORS not supported");
			}
			xhr.onload = function(e) {
				var responseText = xhr.response;
				winnerCheck = JSON.parse(responseText);

				if (winnerCheck === "winner") {
					alert("winner?");
					document.getElementById("gameInfo").innerHTML = "The Game has Ended!";
					document.getElementById("gameButton").value = "Click to see final scores!";
					document.getElementById("gameButton").innerHTML = gameButton.value;

				} else if (winnerCheck === "noWinner"){
					alert("no winner");
					getSelector();
				}
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
				document.getElementById("gameInfo").innerHTML = "It's Player " + selector + "'s turn to play!"
				document.getElementById("gameButton").value = "Okay! Let's see what they choose!";
				document.getElementById("gameButton").innerHTML = gameButton.value;
				getRounds();
				getCardInfo();
				chooseAttribute();
					
					
					
			}
				
			xhr.send();	
				
				
		}
		function getCardCount() {
				
			var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/getCardCount");
				
			if (!xhr) {
				alert("CORS not supported");
			}
				
			xhr.onload = function(e){
			var responseText = xhr.response;
			count = JSON.parse(responseText);

			document.getElementById("humanCardCount").innerHTML = "Human Player's Card Count: " + count;
			document.getElementById("humanCardCount").style.visibility = "visible";

			}				
			xhr.send();	
		}
		function getCardCountforAI1() {
				
			var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/getCardCountForAI1");
				
			if (!xhr) {
				alert("CORS not supported");
			}
				
			xhr.onload = function(e){
			var responseText = xhr.response;
			count = JSON.parse(responseText);

			document.getElementById("AI1CardCount").innerHTML = "AI Player One's Card Count: " + count;
			document.getElementById("AI1CardCount").style.visibility = "visible";

			}				
			xhr.send();	
		}			
		function getCardCountforAI2() {
				
			var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/getCardCountForAI2");
			
			if (!xhr) {
				alert("CORS not supported");
			}
				
			xhr.onload = function(e){
			var responseText = xhr.response;
			count = JSON.parse(responseText);

			document.getElementById("AI2CardCount").innerHTML = "AI Player Two's Card Count: " + count;
			document.getElementById("AI2CardCount").style.visibility = "visible";

			}				
			xhr.send();	
		}

		function getCardCountforAI3() {
				
			var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/getCardCountForAI3");
				
			if (!xhr) {
				alert("CORS not supported");
			}
				
			xhr.onload = function(e){
			var responseText = xhr.response;
			count = JSON.parse(responseText);

			document.getElementById("AI3CardCount").innerHTML = "AI Player Three's Card Count: " + count;
			document.getElementById("AI3CardCount").style.visibility = "visible";

			}				
			xhr.send();	
		}
		function getCardCountforAI4() {
				
			var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/getCardCountForAI4");
			
			if (!xhr) {
				alert("CORS not supported");
			}
				
			xhr.onload = function(e){
			var responseText = xhr.response;
			count = JSON.parse(responseText);

			document.getElementById("AI4CardCount").innerHTML = "AI Player Four's Card Count: " + count;
			document.getElementById("AI4CardCount").style.visibility = "visible";
			}				
			xhr.send();	
		}


			

            //not sure if this needs an argument?
			function getCardInfo() {
				
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/getCard");
				
				if (!xhr) {
					alert("CORS not supported");
				}
				
				xhr.onload = function(e){
					var responseText = xhr.response;
					playerCard = JSON.parse(responseText);					
					document.getElementById("playerCardInfo").innerHTML = "Your cards is: "+ playerCard;
					document.getElementById("playerCardInfo").style.visibility = "visible";

					
					
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
                    document.getElementById("rounds").style.visibility = "visible";
				}

				xhr.send();
			}

			function endTheGame() {
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/endGame");

				if (!xhr) {
					alert("CORS not supported");
				}

				xhr.onload = function(e){
					var responseText = xhr.response;
					finalScores = JSON.parse(responseText);
					document.getElementById("gameInfo").innerHTML = finalScores;
					document.getElementById("gameButton").getAttribute.value = "Click to Play Again!";
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


    </body>
</html>