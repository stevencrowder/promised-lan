/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.promisedlan;

import com.lukaspradel.steamapi.core.exception.SteamApiException;
import com.lukaspradel.steamapi.data.json.ownedgames.GetOwnedGames;
import com.lukaspradel.steamapi.data.json.playersummaries.GetPlayerSummaries;
import com.lukaspradel.steamapi.data.json.playersummaries.Response;
import com.lukaspradel.steamapi.webapi.client.*;
import com.lukaspradel.steamapi.webapi.request.GetOwnedGamesRequest;
import com.lukaspradel.steamapi.webapi.request.GetOwnedGamesRequest.GetOwnedGamesRequestBuilder;
import com.lukaspradel.steamapi.webapi.request.GetPlayerSummariesRequest;
import com.lukaspradel.steamapi.webapi.request.GetPlayerSummariesRequest.GetPlayerSummariesRequestBuilder;
import com.lukaspradel.steamapi.webapi.request.builders.SteamWebApiRequestFactory;
import java.util.List;
import java.util.ArrayList;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author steven
 */
public class steamgames {
    // local steam id
	private String steamId;
    private GetOwnedGamesRequest ownedGamesRequest;
    private GetPlayerSummariesRequest playerSummariesRequest;
    private GetOwnedGames ownedGames;
    private GetPlayerSummaries playerSummaries;
    private com.lukaspradel.steamapi.data.json.playersummaries.Response playerSummariesResponse;
    private com.lukaspradel.steamapi.data.json.ownedgames.Response ownedGamesResponse;
    private com.lukaspradel.steamapi.data.json.ownedgames.Game myGame;
    private String apiKey = "8B17D2D94AB4AF4F607E25CD81F990A2";
    private List<com.lukaspradel.steamapi.data.json.ownedgames.Game> ownedGamesList;
    private List<com.lukaspradel.steamapi.data.json.playersummaries.Player> playerSummariesList;
    private List myGamesList;
    private List<String> steamIds;
    private Integer appId;
    private Integer[] appIds;
    private String gameName;
    SteamWebApiClient client;
    GetOwnedGamesRequest request;
    private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String DB_CONNECTION = "jdbc:oracle:thin:@localhost:1521:orcl";
	private static final String DB_USER = "promised_lan_svc";
	private static final String DB_PASSWORD = "steamService";
    private String gamerName = null;
    public steamgames() throws SteamApiException {
        steamId = null;
        appId = null;
        gameName = "";
        buildClient();
       
        // Builds a steam web api client
        
        //request = SteamWebApiRequestFactory.createGetOwnedGamesRequest("76561197969398673");
        
        //GetNewsForApp getNewsForApp;
        
        //getNewsForApp = client.<GetNewsForApp> processRequest(request);  
    }

    /**
     * @return the steamId
     */
    public String getSteamId() {
        return steamId;
    }

    /**
     * @param steamId the steamId to set
     */
    public void setSteamId(String pSteamId) {
        steamId = pSteamId;
    }

    /**
     * @return the ownedGames
     * @throws SQLException 
     */
    /*public List<Game> getOwnedGamesList() throws SteamApiException {
        //buildClient();
        ownedGamesRequest = buildOwnedGamesRequest();
        ownedGames = client.<GetOwnedGames> processRequest(this.ownedGamesRequest);
        ownedGamesResponse = ownedGames.getResponse();
        ownedGamesList = ownedGamesResponse.getGames();
        //for (int i = 0; i < this.ownedGamesList.size(); i++) {
        //    this.myGamesList.set(i, this.ownedGamesList;
        //}
        myGame = ownedGamesList.get(0);
        return this.ownedGamesList;
        //return myGame.getAppid();
    }*/
    
    
    public String getGameName() throws SteamApiException, SQLException {
        //buildClient();
    	// Add steam ID to steamIds list
    	steamIds.add(steamId);
    	// build request for getting games owned by steamid
        ownedGamesRequest = new GetOwnedGamesRequest.GetOwnedGamesRequestBuilder(this.steamId).includeAppInfo(true).buildRequest();
        // build request for getting player summaries
        playerSummariesRequest = SteamWebApiRequestFactory.createGetPlayerSummariesRequest(steamIds);
        //ownedGames = client.<GetOwnedGames> processRequest(this.ownedGamesRequest);
        // process owned games request
        ownedGames = client.<GetOwnedGames> processRequest(this.request);
        // get the owned games response.
        ownedGamesResponse = ownedGames.getResponse();
        // get the list of owned games from the response.
        ownedGamesList = ownedGamesResponse.getGames();
        // process player summaries for list of steamids
        playerSummaries = client.<GetPlayerSummaries> processRequest(playerSummariesRequest);
        // get the list of players from the player summaries.
        playerSummariesList = playerSummariesResponse.getPlayers();
        //for (int i = 0; i < this.ownedGamesList.size(); i++) {
        //    this.myGamesList.set(i, this.ownedGamesList;
        //}
        for (int i = 0; i < ownedGamesList.size(); i++) {
        	myGame = ownedGamesList.get(i);
        	gameName = gameName + " <br/> " + myGame.getName();
        }
        //myGame = ownedGamesList.get(1);
        //gameName = myGame.getName();
        
        //return this.ownedGamesList;
        insertGamer(steamId, gamerName);
        return gameName;
        //return ownedGamesResponse;
    }
    
    private void insertGamer(String steam_id, String gamer) throws SQLException{
    	Connection conn = null;  
    	Statement statement = null;
    	String sqlStmt = "insert into steam_user vlues(";
    	sqlStmt += steam_id + ",";
    	sqlStmt += gamer;
    	try {
    		conn = getDBConnection();
    		statement = conn.createStatement();
			// execute select SQL stetement
			ResultSet rs = statement.executeQuery(sqlStmt);
			while (rs.next()) {
				String userid = rs.getString("USER_ID");
				String username = rs.getString("USERNAME");
				System.out.println("userid : " + userid);
				System.out.println("username : " + username);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (conn != null) {
    			conn.close();
    		}
		}
    }
    
    public com.lukaspradel.steamapi.data.json.ownedgames.Response getOwnedGamesResponse() throws SteamApiException {
        //buildClient();
        ownedGamesRequest = buildOwnedGamesRequest();
        ownedGames = client.<GetOwnedGames> processRequest(this.ownedGamesRequest);
        ownedGamesResponse = ownedGames.getResponse();
        ownedGamesList = ownedGamesResponse.getGames();
        //for (int i = 0; i < this.ownedGamesList.size(); i++) {
        //    this.myGamesList.set(i, this.ownedGamesList;
        //}
        myGame = ownedGamesList.get(0);
        gameName = myGame.getName();
        //return this.ownedGamesList;
        return ownedGamesResponse;
    }
    
    private GetOwnedGamesRequest buildOwnedGamesRequest() {
        
        return SteamWebApiRequestFactory.createGetOwnedGamesRequest(steamId);
        //return SteamWebApiRequestFactory.createGetOwnedGamesRequest(this.steamId, true, true);
    }
        
    private void buildClient() {
        client = new SteamWebApiClient.SteamWebApiClientBuilder(this.apiKey).build();
    }
    
    private static Connection getDBConnection() {
		Connection dbConnection = null;
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}

		try {
			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER,
					DB_PASSWORD);
			return dbConnection;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return dbConnection;
	}

}
