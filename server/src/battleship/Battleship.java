/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import java.rmi.RemoteException;
import javax.xml.ws.Endpoint;
/**
 *
 * @author mick19955
 */
public class Battleship {
    
    /**
     * @param args the command line arguments
     * @throws java.rmi.RemoteException
     */
    public static void main(String[] args) throws RemoteException {
        GameControllerI spil = new GameController();
        Endpoint.publish("http://[::]:47713/battleship", spil);
        System.out.println("Battleship started.\n");
    }
    
}