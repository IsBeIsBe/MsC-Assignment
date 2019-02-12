package online.dwResources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import online.configuration.TopTrumpsJSONConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import commandline.DatabaseInteraction;
import commandline.FileReaderClass;
import commandline.Game;
import commandline.NewGame;

@Path("/toptrumps") // Resources specified here should be hosted at http://localhost:7777/toptrumps
@Produces(MediaType.APPLICATION_JSON) // This resource returns JSON content
@Consumes(MediaType.APPLICATION_JSON) // This resource can take JSON content as input
/**
 * This is a Dropwizard Resource that specifies what to provide when a user
 * requests a particular URL. In this case, the URLs are associated to the
 * different REST API methods that you will need to expose the game commands
 * to the Web page.
 * 
 * Below are provided some sample methods that illustrate how to create
 * REST API methods in Dropwizard. You will need to replace these with
 * methods that allow a TopTrumps game to be controled from a Web page.
 */
public class TopTrumpsRESTAPI {

	/** A Jackson Object writer. It allows us to turn Java objects
	 * into JSON strings easily. */
	ObjectWriter oWriter = new ObjectMapper().writerWithDefaultPrettyPrinter();
	NewGame theGame;
	
	/**
	 * Contructor method for the REST API. This is called first. It provides
	 * a TopTrumpsJSONConfiguration from which you can get the location of
	 * the deck file and the number of AI players.
	 * @param conf
	 */
	public TopTrumpsRESTAPI(TopTrumpsJSONConfiguration conf) {
		// ----------------------------------------------------
		// Add relevant initalization here
		// ----------------------------------------------------
		FileReaderClass fr = new FileReaderClass();
		fr.getCardsFromFile();
		boolean writeGameLogsToFile = false;
		theGame = new NewGame(fr.getDeck(), fr.getAttributeNames(), writeGameLogsToFile);
		
	}
	
	
	/**
	 * This is the method which initialises the game, shuffling the deck and allocating cards. 
	 * 
	 * It returns the integer value of the player in the arraylist theGame.players who is to play first. 
	 * 
	 * @return
	 * @throws JsonProcessingException
	 */
	@GET
	@Path("/startAndSelect")
	public String startAndSelectFirstPlayer() throws IOException{
		int firstPlayerIndex = theGame.startAndSelectFirstPlayer();
		
		String firstPlayerIndexAsJSON = oWriter.writeValueAsString(firstPlayerIndex);
		
		return firstPlayerIndexAsJSON;
	}
	
	@GET
	@Path("/getCard")
	public String getCardInfo() throws IOException {
		
		String cardToAPI = theGame.getPlayerCardForAPI(0);
				
		String cardAsJSON =	oWriter.writeValueAsString(cardToAPI);
		System.out.println(cardToAPI);
		System.out.println(cardAsJSON);
		return cardAsJSON;
		
	}
	
	@GET
	@Path("/getSelector")
	/**
	 * Here is an example of how to read parameters provided in an HTML Get request.
	 * @param Word - A word
	 * @return - A String
	 * @throws IOException
	 */
	public String getSelector() throws IOException {
		
		String selectorString = theGame.getSelector();
		
		
		
		return selectorString;
		
	}
	
	
	@GET
	@Path("/setSelector")
	/**
	 * Here is an example of how to read parameters provided in an HTML Get request.
	 * @param Word - A word
	 * @return - A String
	 * @throws IOException
	 */
	public String setSelector(@QueryParam("selector") int selector) throws IOException {
		
		theGame.setSelector(selector);
		String selectorString = String.valueOf(selector);
		return selectorString;
		
	}
	
	
	@GET
	@Path("/helloWord")
	/**
	 * Here is an example of how to read parameters provided in an HTML Get request.
	 * @param Word - A word
	 * @return - A String
	 * @throws IOException
	 */
	public String helloWord(@QueryParam("Word") String Word) throws IOException {
		return "Hello "+Word;
	}
	
	/**
	 * This method takes in the 'selector' value and uses that as an index on the players list to call the select attribute 
	 * method.
	 * 
	 * I think this may have to be re-thought when it comes to input from the human player. 
	 * 
	 * @param selector
	 * @return
	 * @throws IOException
	 */
	@GET
	@Path("/chosenAttribute")
	public String getChosenAttribute() throws IOException{
		String attribute = theGame.returnChosenAttribute();
		
		String attributeAsJSON = oWriter.writeValueAsString(attribute);
		
		return attributeAsJSON;
	}
	
	
	/**
	 * This method calls the condensed version of checking for a winner. 
	 * 
	 * NOTE: This does not account for draws, and will have to be used in conjunction with the checkForDraws method.
	 * @return
	 * @throws IOException
	 */
	@GET
	@Path("/findWinner")
	public String findWinnerOfRound() throws IOException{
		String winnerMessage = theGame.findWinnerOfRound();
		
		String winnerAsJSON = oWriter.writeValueAsString(winnerMessage);
		
		return winnerAsJSON;
	}
	
	
	/**
	 * This method checks for draws. Either way, the card allocation should have been updated accordingly via the 
	 * drawDecisions method, so the return value should be used print if there was a draw or not. 
	 * 
	 * If there was not, the value of the findWinnerOfRound method can be used to declare the winner.
	 * @return
	 * @throws IOException
	 */
	@GET
	@Path("/checkForDraws")
	public String wasThereADraw() throws IOException{
		String draw = theGame.drawDecisions();
		
		String drawAsJSON = oWriter.writeValueAsString(draw);
		
		return drawAsJSON;
	}
	
	
	/**
	 * This returns true if there is an overall winner for the game. It should be used at the end of the 'round' loop.
	 * 
	 * It increments the theGame.rounds variable. 
	 * 
	 * @return
	 * @throws IOException
	 */
	@GET
	@Path("/winnerCheck")
	public String checkOverallWinner() throws IOException {
		boolean overallWinner = theGame.checkForOutRightWinner();
		
		String winnerASJSON = oWriter.writeValueAsString(overallWinner);
		
		return winnerASJSON;
	}
	
	/**
	 * This method allows us to call the endGameMethod from the API, creating and setting the gameStats array, as well as 
	 * creating the final scores String. This string is returned for ease of writing it to the webpage. 
	 * 
	 * Declaring who won will have to be done separately, probably by finding the last value of theGame.roundWinner. 
	 * @return
	 * @throws IOException
	 */
	@GET
	@Path("/endGame")
	public String endOfGame() throws IOException {
		theGame.endGameMethod();
		String finalMessage = theGame.finalScores;
		
		String finalsAsJSON = oWriter.writeValueAsString(finalMessage);
		
		return finalsAsJSON;
	}
	
	/**
	 * This method will be altered to sync with the DatabaseInteraction class's printGameStats method. 
	 * 
	 * Because this requires library computers etc it is currently linked up to a simple String method 
	 * from the same class to test that it is working as intended. 
	 * @return
	 * @throws JsonProcessingException
	 */
	@GET
	@Path("/printGameStats")
	public String printGameStatsOnline() throws JsonProcessingException {
		String results = "";
		DatabaseInteraction db = new DatabaseInteraction();
		
		results += db.onlineTest();		
		String resultsAsJSON = oWriter.writeValueAsString(results);
		
		return resultsAsJSON;
	}
	
	// ----------------------------------------------------
	// Add relevant API methods here
	// ----------------------------------------------------
	
	@GET
	@Path("/helloJSONList")
	/**
	 * Here is an example of a simple REST get request that returns a String.
	 * We also illustrate here how we can convert Java objects to JSON strings.
	 * @return - List of words as JSON
	 * @throws IOException
	 */
	public String helloJSONList() throws IOException {
		
		

		
		List<String> listOfWords = new ArrayList<String>();
		listOfWords.add("Hello");
		listOfWords.add("World!");
		
		// We can turn arbatory Java objects directly into JSON strings using
		// Jackson seralization, assuming that the Java objects are not too complex.
		String listAsJSONString = oWriter.writeValueAsString(listOfWords);
		
		return listAsJSONString;
	}
	

	
}
