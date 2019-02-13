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

    <body onload="initalize()">
    <h1>Top Trumps</h1>

    <hr />

    <div>
		<a href="http://localhost:7777/toptrumps/">Go Back</a>
	</div>

    <hr />

    <div>
        <button id="myButton" onclick="playGame()">Play Game</button>
    </div>

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
        <button id="gameButton" onlick="buttons" value="buttonValue"></button>
    </div>

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

    <script=text/javascript">
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
				document.getElementById("gameInfo").innerHTML = "It's Player " + playerIndex + "'s turn to play!";
				document.getElementById("gameInfo").style.visibility = "visible";
                document.getElementById("gameButton").innerHTML = "Okay! Let's see what they choose!";
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
				document.getElementById("gameButton").innerHTML = "Okay! Let's see who won!";

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
				document.getElementById("gameMessage").innerHTML = roundMessage;
			//	document.getElementById("gameButton").style.visibility = "visible";
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
				document.getElementById("gameMessage").innerHTML = roundMessage;
				document.getElementById("gameButton").innerHTML = "Great! Let's update the scores!";
			//	document.getElementById("endRoundButton").style.visibility = "visible";
				
			}
			xhr.send();
		}

		function getCardCounts(){
			//document.getElementById("getCardCountButton").style.visibility = "hidden";
			getCardCount(0);
			getCardCount(1);
			getCardCount(2);
			getCardCount(3);
			getCardCount(4);
			document.getElementById("gameInfo").innerHTML = "Great! Let's play another round!";
            document.getElementById("gameButton").innerHTML = "Play next Round";
		}

		function startNewRound() {

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
				document.getElementById("gameInfo").innerHTML = "It's Player " + selector + "'s turn to play!"
				document.getElementById("gameButton").style.visibility = "Okay! Let's see what they chose!";
				getRounds();
				getCardInfo();
				chooseAttribute();
					
					
					
			}
				
			xhr.send();	
				
				
		}






		function getCardCount(playerIndex){
			var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/getCardCount?playerIndex");
			
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



			

            //not sure if this needs an argument?
			function getCardInfo() {
				
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/getCard?player");
				
				if (!xhr) {
					alert("CORS not supported");
				}
				
				xhr.onload = function(e){
					var responseText = xhr.response;
					playerCard = JSON.parse(responseText);					
					document.getElementById("playerCardInfo").innerHTML = "Your cards is: "+ playerCard;

					
					
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

    </body>
</html>