/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Set;
import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 *
 * @author artorias
 */
@WebService
public interface GameControllerI {    
    @WebMethod public void NewGame(String PlayerName) throws RemoteException;
    @WebMethod public void JoinGame(String GameKey, String NewPlayer);
    @WebMethod public void togglePlayerturn(String GameKey);
    @WebMethod public Boolean BrugerLogin(String Username, String Password)throws NotBoundException, RemoteException, MalformedURLException;
    @WebMethod public GameLogic getGame(String GameStarter);
    @WebMethod public Set getAllGames();
}
