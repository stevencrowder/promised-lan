/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java.org.promisedlan;

import com.lukaspradel.steamapi.core.exception.SteamApiException;
import com.lukaspradel.steamapi.data.json.ownedgames.*;
import com.lukaspradel.steamapi.webapi.client.SteamWebApiClient;
import com.lukaspradel.steamapi.webapi.request.GetOwnedGamesRequest;
import com.lukaspradel.steamapi.webapi.request.builders.SteamWebApiRequestFactory;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author steven
 */
public class steamgames {
    private String l_steam_id;
    private GetOwnedGamesRequest ownedGamesRequest;
    private GetOwnedGames ownedGames;
    private Response ownedGamesResponse;
    private Game myGame;
    private String apiKey = "8B17D2D94AB4AF4F607E25CD81F990A2";
    private List<Game> ownedGamesList;
    private List myGamesList;
    private Integer l_appid;
    private String l_game_name;
    SteamWebApiClient client;
    GetOwnedGamesRequest request;
    public steamgames() throws SteamApiException {
        l_steam_id = null;
        l_appid = null;
        l_game_name = "";
        // Builds a steam web api client
        
        //request = SteamWebApiRequestFactory.createGetOwnedGamesRequest("76561197969398673");
        
        //GetNewsForApp getNewsForApp;
        
        //getNewsForApp = client.<GetNewsForApp> processRequest(request);
       
               
    }

    /**
     * @return the l_steam_id
     */
    public String getL_steam_id() {
        return l_steam_id;
    }

    /**
     * @param l_steam_id the l_steam_id to set
     */
    public void setL_steam_id(String l_steam_id) {
        this.l_steam_id = l_steam_id;
    }

    /**
     * @return the ownedGames
     */
    public Integer getOwnedGamesList() throws SteamApiException {
        this.buildClient();
        this.ownedGamesRequest = this.buildRequest();
        this.ownedGames = this.client.<GetOwnedGames> processRequest(this.ownedGamesRequest);
        this.ownedGamesResponse = this.ownedGames.getResponse();
        this.ownedGamesList = this.ownedGamesResponse.getGames();
        //for (int i = 0; i < this.ownedGamesList.size(); i++) {
        //    this.myGamesList.set(i, this.ownedGamesList;
        //}
        this.myGame = this.ownedGamesList.get(0);
        //return this.ownedGamesList;
        return this.myGame.getAppid();
        
    }
    
    public Integer getL_appid() throws SteamApiException {
        this.buildClient();
        this.ownedGamesRequest = this.buildRequest();
        this.ownedGames = this.client.<GetOwnedGames> processRequest(this.ownedGamesRequest);
        this.ownedGamesResponse = this.ownedGames.getResponse();
        this.ownedGamesList = this.ownedGamesResponse.getGames();
        //for (int i = 0; i < this.ownedGamesList.size(); i++) {
        //    this.myGamesList.set(i, this.ownedGamesList;
        //}
        this.myGame = this.ownedGamesList.get(0);
        this.l_appid = this.myGame.getAppid();
        //return this.ownedGamesList;
        return this.l_appid;
    }
    
    public String getL_game_name() throws SteamApiException {
        this.buildClient();
        this.ownedGamesRequest = this.buildRequest();
        this.ownedGames = this.client.<GetOwnedGames> processRequest(this.ownedGamesRequest);
        this.ownedGamesResponse = this.ownedGames.getResponse();
        this.ownedGamesList = this.ownedGamesResponse.getGames();
        //for (int i = 0; i < this.ownedGamesList.size(); i++) {
        //    this.myGamesList.set(i, this.ownedGamesList;
        //}
        this.myGame = this.ownedGamesList.get(0);
        this.l_game_name = this.myGame.getName();
        //return this.ownedGamesList;
        return this.l_game_name;
    }
    
    private GetOwnedGamesRequest buildRequest() {
        return SteamWebApiRequestFactory.createGetOwnedGamesRequest(l_steam_id);
        //return SteamWebApiRequestFactory.createGetOwnedGamesRequest(this.l_steam_id, true, true);
    }
        
    private void buildClient() {
        this.client = new SteamWebApiClient.SteamWebApiClientBuilder(this.apiKey).build();
    }

    
}
