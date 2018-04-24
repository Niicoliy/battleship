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
    
    public GameController() throws RemoteException {
        games=new Hashtable<>();
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
    
    public void getPrintBoard(String GameKey){
        games.get(GameKey).printBoard();
    }
    
    public void getPlaceShip(String GameKey, int shipsize, int x, int y, Boolean horizontalBool){
        games.get(GameKey).PlaceShip(shipsize, x, y, horizontalBool);
    }
    
    /* Realised this was not needed withing the controller, as all these are simply
    copies of functions from GameLogic
    
    public Boolean getPlayerturn(String GameKey){
        return games.get(GameKey).getPlayerturn();
    }
    
    public void togglePlayerturn(String GameKey){
        games.get(GameKey).togglePlayerturn(); //if true then false, if false then ture
    }
    
    public void Reset(String GameKey){
        games.get(GameKey).Reset();
    }
    
    
    public Boolean getReady_to_start(String GameKey) {
        return games.get(GameKey).getReady_to_start();
    }


    public void setReady_to_start(String GameKey, Boolean ready_to_start) {
        games.get(GameKey).setReady_to_start(ready_to_start);
    }
    */

}
