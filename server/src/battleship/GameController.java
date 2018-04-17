/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;
import javax.jws.WebService;

/**
 *
 * @author artorias
 */
@WebService(endpointInterface = "battleship.GameController")
public class GameController extends UnicastRemoteObject implements GameControllerI{
    private Hashtable<String, GameLogic> games;
    
    public GameController() {
        games=new Hashtable<>();
    }
    
    public void NewGame(String PlayerName) throws RemoteException {
        games.put(PlayerName, new GameLogic(PlayerName));
    }
    
    public void JoinGame(String GameStarter, String NewPlayer) {
        if(games.containsKey(GameStarter)) {
            games.get(GameStarter).PlayerJoin(NewPlayer);
        }
    }
}
