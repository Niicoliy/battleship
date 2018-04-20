/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klient;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 *
 * @author artorias
 */
public class Klient {

    /**
     * @param args the command line arguments
     * @throws java.rmi.NotBoundException
     * @throws java.net.MalformedURLException
     * @throws java.rmi.RemoteException
     */
    public static void main(String[] args) throws NotBoundException, MalformedURLException, RemoteException {
     
       KlientLogik KLogik = new KlientLogik();
              
       KLogik.spil();
    }
    
}
