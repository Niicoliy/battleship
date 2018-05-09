/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import brugerautorisation.data.Bruger;
import brugerautorisation.transport.rmi.Brugeradmin;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;
import java.util.Set;
import javax.jws.WebService;

/**
 *
 * @author artorias
 */
@WebService(endpointInterface = "battleship.GameController")
public class GameController extends UnicastRemoteObject implements GameControllerI{
    private Hashtable<String, GameLogic> games;
    private Hashtable<String, Integer> Highscore_table;
    
    public GameController() throws RemoteException {
        games = new Hashtable<>();
        Highscore_table = new Hashtable<>();
    }
    
    //TODO: Dont allow these functions with out identifyable user
    public void NewGame(String PlayerName) throws RemoteException {
       games.put(PlayerName, new GameLogic());
    }
    
    public void JoinGame(String GameKey, String NewPlayer) {
        if(games.containsKey(GameKey)) {
            games.get(GameKey).PlayerJoin(GameKey, NewPlayer);
        } 
    }
    
    public void togglePlayerturn(String GameKey) {
        games.get(GameKey).togglePlayerturn();
    }
    
    public GameLogic getGame(String GameKey) {
        return games.get(GameKey);
    }
    
    public Set getAllGames() {
        return games.keySet();
    }
    
    public String getHighScore() {
        return Highscore_table.toString(); //returnerer hashtablen som en string
    }
    
    public void addHighScore(String Playername, Integer Score) {
        if(Highscore_table.containsKey(Playername)){ //check if player already got a recorded highscore
            if(Highscore_table.get(Playername) < Score){ //If new score is higher than old
                Highscore_table.put(Playername, Score);
            }else{
                //Do nothing
            } 
            return;
        }
        Highscore_table.put(Playername, Score);
        return;
    }
   
    public Boolean BrugerLogin(String Username, String Password) throws NotBoundException, RemoteException, MalformedURLException{
             
        Brugeradmin ba = (Brugeradmin) Naming.lookup("rmi://javabog.dk/brugeradmin"); //connecting to server
    
      
        try{
            Bruger b = ba.hentBruger(Username, Password);//login with user information
            System.out.println("Du er logget ind som " + b);
            
            //TODO: add user to identification arraylist
            
            return true;
        }catch(Exception ex){
            System.out.println("Der skete en fejl med login");
            System.out.println("Du er ikke logget ind");
            System.out.println(ex);
            return false;
        }
    }
    
    public int[][] getOwnMap(String GameKey, String Username) {
        return games.get(GameKey).getOwnMap(Username);
    }
    
    public int[][] getHiddenOpponentMap(String GameKey, String Username) {
        return games.get(GameKey).getHiddenOpponentMap(Username);
    }
    
    public Boolean PlaceShip(String Username, String GameKey, int ShipSize, int x, int y, String direction) {
        int[][] map = games.get(GameKey).getOwnMap(Username);
        return games.get(GameKey).PlaceShip(map, ShipSize, x, y, direction);
    }
    
    public int Shoot(String Username, String GameKey, int x, int y) {
        int[][] HiddenMap = games.get(GameKey).getHiddenOpponentMap(Username);
        int[][] OpponentMap = games.get(GameKey).getActualOpponentMap(Username);
        return games.get(GameKey).Shoot(x, y, OpponentMap, HiddenMap);
    }
    
    public Boolean IsGameOver(String Username, String GameKey) {
        int[][] OpponentMap = games.get(GameKey).getActualOpponentMap(Username);
        return games.get(GameKey).IsGameOver(OpponentMap);
    }
    
    public void RemoveGame(String GameKey) {
        games.remove(GameKey);
    }
}
