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
import java.util.Scanner;
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
       games.put(PlayerName, new GameLogic(PlayerName));
    }
    
    public void JoinGame(String GameStarter, String NewPlayer) {
        if(games.containsKey(GameStarter)) {
            games.get(GameStarter).PlayerJoin(NewPlayer);
        }
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
}
