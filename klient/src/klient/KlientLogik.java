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
    int Choice, shipsize, x, y;
    //String directon;
    Boolean horizontalBool;
    Boolean in_lobby = true;
    Boolean playing = true;
    Boolean ship_placing;
    Set games;
    GameControllerI game;
    //GameLogic currentGame;
    
    public void spil() throws MalformedURLException, RemoteException, NotBoundException, InterruptedException {
        System.out.println("Welcome to BATTLESHIP!");
        
        //Attempt to connect to game server        
        URL url = new URL("http://localhost:2429/battleship?WSDL");        //on local host
        //URL url = new URL("http://ubuntu4.saluton.dk:4443/GalgeTest?WSDL"); //on ubunto server
        QName qname = new QName("http://battleship/", "GameControllerService");
        Service service = Service.create(url, qname);
        QName port_name = new QName("http://battleship/", "GameControllerPort");
        game = service.getPort(port_name, GameControllerI.class);

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
                    keyboard.nextLine();
                    switch(Choice) {
                        case 1:
                            System.out.println("Creating new game");
                            game.NewGame(Username);
                            Gamemaster = Username;
                            in_lobby = false;
                            ship_placing = true;
                            break;
                        case 2:
                            games = game.getAllGames();
                            System.out.println("Type a game you want to join: " + games);
                            Gamemaster = keyboard.nextLine();
                            if(games.contains(Gamemaster)) {
                                game.JoinGame(Gamemaster, Username);
                                in_lobby = false;
                                ship_placing = true;
                                System.out.println("Joining game");
                            }
                            else {
                                System.out.println("Invalid input");
                            }
                            break;
                        case 0:
                            playing=false;
                            break;
                    }
                }
                else {
                    //if both players are in the game
                    if(game.getGame(Gamemaster).getReady_to_start()) {
                        System.out.println("The turn belongs to "+game.getGame(Gamemaster).getPlayerturn());
                        //Wait while loop
                        while(!((game.getGame(Gamemaster).getPlayerturn()).equals(Username))) { //Not your turn
                            TimeUnit.SECONDS.sleep(1);
                        }
                        if(ship_placing) {//Place ships
                            System.out.println("Place your carrier (5)");
                            PlaceShip(5);
                            printMap();
                            System.out.println("Place your battleship (4)");
                            PlaceShip(4);
                            printMap();
                            System.out.println("Place your cruiser (3)");
                            PlaceShip(3);
                            printMap();
                            System.out.println("Place your destroyers (2)");
                            for(int i = 0; i < 2; i += 1) {
                                PlaceShip(2);
                                printMap();
                            }
                            System.out.println("Place your submarines (1)");
                            for(int i = 0; i < 3; i += 1) {
                                PlaceShip(1);
                                printMap();
                            }
                            ship_placing = false;
                        }
                        else { //Play the game
                            printMap();
                        }
                        game.togglePlayerturn(Gamemaster);
                        System.out.println("Turn has changed, it is no longer your turn");
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
    
    public void PlaceShip(int ShipSize) {
        Boolean ShipPlaced = false;
        while(!ShipPlaced) {
            System.out.print("Choose x-coordinate: ");
            x = keyboard.nextInt();
            System.out.print("Choose y-coordinate: ");
            y = keyboard.nextInt();
            keyboard.nextLine();
            System.out.print("(V)ertical or (H)orizontal: ");
            String direction = keyboard.nextLine();
            //keyboard.nextLine();
            ShipPlaced = game.PlaceShip(Username, Gamemaster, ShipSize, x, y, direction);
            if(!ShipPlaced) {
                System.out.println("Placing of ship failed try again");
            }
        }
    }
    
    public void printMap() {
        int[][] OwnMap = game.getOwnMap(Gamemaster, Username);
        int[][] OpponentMap = game.getOpponentMap(Gamemaster, Username);
        for(int i = 0; i < 10; i += 1) {
            System.out.print(i + " ");
            for(int j = 0; j < 10; j += 1) {
                System.out.print(OwnMap[j][i] + " ");
            }
            System.out.print("| ");
            for(int j = 0; j < 10; j += 1) {
                System.out.print(OpponentMap[j][i] + " ");
            }
            System.out.print("\n");
        }
        System.out.println("  0 1 2 3 4 5 6 7 8 9   0 1 2 3 4 5 6 7 8 9");
    }
}