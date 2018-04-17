/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import brugerautorisation.data.Bruger;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Hashtable;
import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 *
 * @author artorias
 */
@WebService
public interface GameControllerI {    
    @WebMethod public void NewGame(String PlayerName) throws RemoteException;
    @WebMethod public void JoinGame(String GameStarter, String NewPlayer);
    @WebMethod public Boolean BrugerLogin(String Username, String Password)throws NotBoundException, RemoteException, MalformedURLException;
}
