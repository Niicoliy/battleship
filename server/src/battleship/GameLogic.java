/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import java.util.Scanner;


/**
 *
 * @author mick19955
 */

/**
 * # 	Class of ship 	Size    Ships/player
 * 1 	Carrier 	5       1
 * 2 	Battleship 	4       1
 * 3 	Cruiser 	3       1
 * 4 	Destroyer 	2       2
 * 5 	Submarine 	1       3
 * @author sand
 */
public class GameLogic {
    /*
    map layout:
    0: empty
    1: empty hit
    2: ship
    3: ship hit
    4: ship destroyed
    */
    
    int height;
    int width;
    String player1;
    String player2;
    private Boolean ready_to_start;
    private int map1[][];
    private int map2[][];
    private Boolean gameOver;
    Scanner keyboard = new Scanner(System.in);  
    String Input, Username, Password;
    
    private String playerturn; 
                            //false = player one
                            //true = player two
    
    public GameLogic() { //constructor
        height = 10;
        width = 10;
        map1 = new int[height][width];
        map2 = new int[height][width];
        ready_to_start = false;
        gameOver = false;
    }
    
    public void PlayerJoin(String Gamemaster, String Joiner) {
        setPlayer1(""+Gamemaster);
        setPlayer2(""+Joiner);
        setPlayerturn(""+Gamemaster);
        setReady_to_start((Boolean) true); //Theres two players in lobby, we are ready to start
    }
    
    public void Reset(){
        EmptyMap();
        gameOver = false;
        playerturn = player1;
    }
    
    public void EmptyMap(){
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                map1[i][j] = 0; //fills whole map with zero's
                map2[i][j] = 0;
            }
        }
    }
    
    public int PlaceShip(int shipsize, int x, int y, Boolean horizontal){
        if(playerturn.equals(player1)){ //== 1 == true
            //validate
            if(((shipsize + x > width) && horizontal) || x < 0 || x > width-1){
                System.out.println("Player1: Width error");
                return 0;
            }else if(((shipsize + y > height) && !horizontal) || y < 0 || y > height-1){
                System.out.println("Player1: Height error");
                return 0;
            }
            else{ //Ship can now be placed into map
                if(horizontal){
                    for(int i = x; i < x+shipsize; i++){
                        map1[i][y] = 2; //placing ship, moving right
                    }
                }else{
                    for(int i = y; i < y+shipsize; i++){
                        map1[x][i] = 2; //placing ship, moving up
                    }
                }
                System.out.println("Ship being placed player 1");
                return 1; //ship placed
            }
        }else{ //playerturn == 2
            //validate
            if(((shipsize + x > width) && horizontal) || x < 0 || x > width-1){
                System.out.println("Player2: Width error");
                return 0;
            }else if(((shipsize + y > height) && !horizontal) || y < 0 || y > height-1){
                System.out.println("Player2: Height error");
                return 0;
            }
            else{ //Ship can now be placed into map
                if(horizontal){
                    for(int i = x; i < x+shipsize; i++){
                        map2[i][y] = 2; //placing ship, moving right
                    }
                }else{
                    for(int i = y; i < y+shipsize; i++){
                        map2[x][i] = 2; //placing ship, moving up
                    }
                } 
                System.out.println("Ship being placed player 2");
                return 1; //ship placed
            }
        }
    }
    
    public int Shoot(int x, int y){
        if(playerturn.equals(player1)){//== 1 == true
            if(getMap()[x][y] != 2){
                return 0; //didnt hit undamaged ship
            }else{
                map1[x][y] = 3; //hit undamaged ship
                return 1;
            }
        }else{ //playerturn == 2
            if(getMap()[x][y+10] != 2){
                return 0; //didnt hit undamaged ship
            }else{
                map1[x][y+10] = 3; //hit undamaged ship
                return 1;
            }
        }
    }
    
    public int IsGameOver(){
        if(playerturn.equals(player1)){
            for(int i = 0; i < width; i++){
                for(int j = 0; j < height; j++){
                    if(getMap()[i][j] == 2){
                        return 0; //There are still ships to be shot
                    }
                }
            }
        }else{ //playerturn == 2
            for(int i = 0; i < width; i++){
                for(int j = 10; j < height*2; j++){
                    if(getMap()[i][j] == 2){
                        return 0; //There are still ships to be shot
                    }
                }
            }
        }
        gameOver = true;
        return 1; //no more ships, game is now over
    }

    /**
     * @return the map
     */
    public int[][] getMap() {
        if(playerturn.equals(player1)) {
            return map1;
        }
        else {
            return map2;
        }
    }

    /**
     * @return the gameOver
     */
    public Boolean getGameOver() {
        return gameOver;
    }

    /**
     * @return the playerturn
     */
    public String getPlayerturn() {
        return playerturn;
    }

    /**
     * @param playerturn the playerturn to set
     */
    public void setPlayerturn(String playerturn) {
        this.playerturn = playerturn;
    }
    
    public void togglePlayerturn() {
        if(playerturn.equals(player1)) {
            setPlayerturn(""+player2);
        }
        else {
            setPlayerturn(""+player1);
        }
    }

    /**
     * @return the ready_to_start
     */
    public Boolean getReady_to_start() {
        return ready_to_start;
    }

    /**
     * @param ready_to_start the ready_to_start to set
     */
    public void setReady_to_start(Boolean ready_to_start) {
        this.ready_to_start = ready_to_start;
    }
    
    /**
     * @param player1 the player1 to set
     */
    public void setPlayer1(String player1) {
        this.player1 = player1;
    }
    
    /**
     * @param player2 the player2 to set
     */
    public void setPlayer2(String player2) {
        this.player2 = player2;
    }
}