/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klient;

import battleship.GameControllerI;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

/**
 *
 * @author mick19955
 */
public class KlientLogik {
    Scanner keyboard = new Scanner(System.in);
    String Input, Username, Password, Gamemaster, Gamename;
    int Choice;
    Boolean in_lobby = true;
    Boolean playing = true;
    Set games;
    //GameLogic currentGame;
    
    public void spil() throws MalformedURLException, RemoteException, NotBoundException, InterruptedException {
        System.out.println("Welcome to BATTLESHIP!");
        
        //Attempt to connect to game server        
        URL url = new URL("http://localhost:2429/battleship?WSDL");        //on local host
        //URL url = new URL("http://ubuntu4.saluton.dk:4443/GalgeTest?WSDL"); //on ubunto server
        QName qname = new QName("http://battleship/", "GameControllerService");
        Service service = Service.create(url, qname);
        QName port_name = new QName("http://battleship/", "GameControllerPort");
        GameControllerI game = service.getPort(port_name, GameControllerI.class);

        //spil.nulstil();
        
        System.out.println("Log in to Jakobs linux server");
        System.out.println("Write your username");
        Username = keyboard.nextLine();
        System.out.println("Write your password");
        Password = keyboard.nextLine();
        
        if(game.BrugerLogin(Username,Password)) {
            System.out.println("Youre logged in");
            while(playing) {
                if(in_lobby) {
                    System.out.println("1. Create new game");
                    System.out.println("2. Join existing game");
                    Choice = keyboard.nextInt();
                    switch(Choice) {
                        case 1:
                            System.out.println("Creating new game");
                            game.NewGame(Username);
                            Gamemaster = Username;
                            //currentGame = game.getGame(Gamemaster);
                            in_lobby = false;
                            break;
                        case 2:
                            games = game.getAllGames();
                            System.out.println("Type a game you want to join: " + games);
                            keyboard.nextLine();
                            Gamemaster = keyboard.nextLine();
                            if(games.contains(Gamemaster)) {
                                game.JoinGame(Gamemaster, Username);
                                in_lobby = false;
                                System.out.println("Joining game");
                            }
                            else {
                                System.out.println("Invalid input");
                            }
                            break;
                        case 'q':
                            playing=false;
                            break;
                    }
                }
                else {
                    //if both players are in the game
                    if(game.getGame(Gamemaster).getReady_to_start()) {
                        System.out.println("Started game");
                        System.out.println(game.getGame(Gamemaster).getPlayerturn());
                        //Wait while loop
                        while(!((game.getGame(Gamemaster).getPlayerturn()).equals(Username))) {
                            TimeUnit.SECONDS.sleep(1);
                        }
                        //Do turn
                        keyboard.nextLine();
                        game.togglePlayerturn(Gamemaster);
                        TimeUnit.SECONDS.sleep(1);
                    }
                    else {
                        System.out.println("Waiting for other players");
                        TimeUnit.SECONDS.sleep(1);
                    }
                }
            }
        }
        else {
            System.out.println("Youre not logged in");
        }
    }
}