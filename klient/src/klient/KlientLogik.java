/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klient;

import brugerautorisation.data.Bruger;
import brugerautorisation.transport.rmi.Brugeradmin;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

/**
 *
 * @author mick19955
 */
public class KlientLogik {
    
    Scanner keyboard = new Scanner(System.in);  
    String Input, Username, Password;
            
    
    public Bruger BrugerLogin() throws NotBoundException, RemoteException, MalformedURLException{
        
             
        Brugeradmin ba = (Brugeradmin) Naming.lookup("rmi://javabog.dk/brugeradmin"); //connecting to server
        System.out.println("Du skal nu logge ind på Jakobs linux server");
        System.out.println("Skriv dit studienummer");
        Username = keyboard.nextLine();
        System.out.println("Skriv dit password");
        Password = keyboard.nextLine();
        Bruger b = null;
        try{
            b = ba.hentBruger(Username, Password);//login with user information
            System.out.println("Du er logget ind som " + b);
        }catch(Exception ex){
            System.out.println("Der skete en fejl med login");
            System.out.println("Du er ikke logget ind");
            System.out.println(ex);
        }
        return b;
    }
    
    public void spil(){
        
    }
}