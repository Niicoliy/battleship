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
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.lang.Integer;


/**
 *
 * @author mick19955
 */
public class KlientLogik {
    Scanner keyboard = new Scanner(System.in);
    String Input, Username, Password, Gamemaster;
    int Choice, shipsize, x, y;
    int ShootX, ShootY;
    Integer Score = 0;
    //String directon;
    Boolean horizontalBool;
    Boolean in_lobby = true;
    Boolean playing = true;
    Boolean ship_placing;
    Boolean not_shot;
    Set games;
    GameControllerI game;
    //GameLogic currentGame;
    
    public void spil() throws MalformedURLException, RemoteException, NotBoundException, InterruptedException {
        System.out.println("Welcome to BATTLESHIP!");
        
        //Attempt to connect to game server        
        URL url = new URL("http://localhost:47713/battleship?WSDL");        //on local host
        //URL url = new URL("http://ubuntu4.saluton.dk:47713/battleship?WSDL"); //on ubunto server
        QName qname = new QName("http://battleship/", "GameControllerService");
        Service service = Service.create(url, qname);
        QName port_name = new QName("http://battleship/", "GameControllerPort");
        game = service.getPort(port_name, GameControllerI.class);
        
        System.out.println("Log in to Jakobs linux server");
        System.out.println("Write your username");
        Username = keyboard.nextLine();
        System.out.println("Write your password");
        Password = keyboard.nextLine();
        
        if(game.BrugerLogin(Username,Password)) {
            System.out.println("Youre logged in");
            while(playing) {
                if(in_lobby) {
                    System.out.println("0. Quit");
                    System.out.println("1. Create new game");
                    System.out.println("2. Join existing game");
                    System.out.println("3. Print highscore list");
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
                                System.out.println("Game was not found!");
                            }
                            break;
                        case 3:
                            System.out.println("Printing highscore");
                            System.out.println(game.getHighScore());
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
                            if(game.getGame(Gamemaster).getGameOver()) {
                                System.out.println("You lost!");
                                in_lobby = true;
                                game.RemoveGame(Gamemaster);
                                //Remove game
                                break;
                            }
                            TimeUnit.SECONDS.sleep(1);
                        }
                        if(!game.getGame(Gamemaster).getGameOver()) { //Put so losing player doesn't call any functions
                            if(ship_placing) {//Place ships
                                System.out.println("Its your turn to place your ships!");
                                PlaceShips();
                                ship_placing = false;
                            }
                            else { //Play the game
                                System.out.println("Its your turn to shoot!");
                                Shoot();
                                printMap();
                                if(game.IsGameOver(Username, Gamemaster)) {
                                    System.out.println("The game is now over!");
                                    game.addHighScore(Username, Score);
                                    in_lobby = true;
                                }
                            }
                            game.togglePlayerturn(Gamemaster);
                            System.out.println("Turn has changed, it is no longer your turn");
                            TimeUnit.SECONDS.sleep(1);
                        }
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
    
    public void Shoot() {
        not_shot = true;
        while(not_shot){
            try { 
                System.out.print("Choose x-coordinate: ");
                ShootX = keyboard.nextInt();
                System.out.print("Choose y-coordinate: ");
                ShootY = keyboard.nextInt();
                keyboard.nextLine();
                int response = game.Shoot(Username, Gamemaster, ShootX, ShootY);
                switch(response) {
                    case 1:
                        System.out.println("Hit water");
                        Score-=1;
                        not_shot = false;
                        break;
                    case 2:
                        System.out.println("Hit ship");
                        Score+=5;
                        not_shot = false;
                        break;
                    case 3:
                        System.out.println("You already shot here, shoot somewhere else");
                        break;
                }
            }
            catch(InputMismatchException ex) {
                keyboard.nextLine(); //clears line read
            }
            if(not_shot){
                System.out.println("Failed to shoot, try again");
            }
        }
    }
    
    public void PlaceShips() {
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
    }
    
    public void PlaceShip(int ShipSize) {
        Boolean ShipPlaced = false;
        while(!ShipPlaced) {
            try{
                System.out.print("Choose x-coordinate: ");
                x = keyboard.nextInt();
                System.out.print("Choose y-coordinate: ");
                y = keyboard.nextInt();
                keyboard.nextLine();
                System.out.print("(V)ertical or (H)orizontal: ");
                String direction = keyboard.nextLine();
                //keyboard.nextLine();
                ShipPlaced = game.PlaceShip(Username, Gamemaster, ShipSize, x, y, direction);
            }
            catch(InputMismatchException ex) {
                keyboard.nextLine(); //clears line read
            }
            if(!ShipPlaced) {
                    System.out.println("Placing of ship failed try again");
                }
        }
    }
    
    public void printMap() {
        int[][] OwnMap = game.getOwnMap(Gamemaster, Username);
        int[][] OpponentMap = game.getHiddenOpponentMap(Gamemaster, Username);
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