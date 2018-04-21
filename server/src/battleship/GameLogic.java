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
    
    int height = 10;
    int width = 10;
    String player1;
    String player2;
    Boolean ready_to_start;
    private int map[][] = new int[height*2][width];
    private Boolean gameOver;
    Scanner keyboard = new Scanner(System.in);  
    String Input, Username, Password;
    
    private Boolean playerturn; 
                            //false = player one
                            //true = player two
    
    public GameLogic() { //constructor
        playerturn = true;
        ready_to_start = false;
        gameOver = false;
    }
    
    public GameLogic(String PlayerName) throws java.rmi.RemoteException {
        player1 = PlayerName;
    }
    
    public void PlayerJoin(String PlayerName) {
        player2 = PlayerName;
        ready_to_start = true; //Theres two players in lobby, we are ready to start
    }
    
    public void Reset(){
        EmptyMap();
        gameOver = false;
        playerturn = true;
    }
    
    public void EmptyMap(){
        for(int i = 0; i < height*2; i++){
            for(int j = 0; j < width; j++){
                map[i][j] = 0; //fills whole map with zero's
            }
        }
    }
    
    public int PlaceShip(int shipsize, int x, int y, Boolean horizontal){
        if(playerturn){ //== 1 == true
            //validate
            if((shipsize + x < width) && horizontal){
                return 0;
            }else if((shipsize + y < height) && !horizontal){
                return 0;
            }
            else{ //Ship can now be placed into map
                if(horizontal){
                    for(int i = x; i < shipsize; i++){
                        map[i][y] = 2; //placing ship, moving right
                    }
                }else{
                    for(int i = y; i < shipsize; i++){
                        map[x][i] = 2; //placing ship, moving up
                    }
                } 
                return 1; //ship placed
            }
        }else{ //playerturn == 2
            //validate
            if((shipsize + x < width) && horizontal){
                return 0;
            }else if((shipsize + y+10 < (height*2)) && !horizontal){
                return 0;
            }
            else{ //Ship can now be placed into map
                if(horizontal){
                    for(int i = x; i < shipsize; i++){
                        map[i][y+10] = 2; //placing ship, moving right
                    }
                }else{
                    for(int i = y; i < shipsize; i++){
                        map[x][i+10] = 2; //placing ship, moving up
                    }
                } 
                return 1; //ship placed
            }
        }
    }
    
    public int Shoot(int x, int y){
        if(playerturn){//== 1 == true
            if(getMap()[x][y] != 2){
                return 0; //didnt hit undamaged ship
            }else{
                map[x][y] = 3; //hit undamaged ship
                return 1;
            }
        }else{ //playerturn == 2
            if(getMap()[x][y+10] != 2){
                return 0; //didnt hit undamaged ship
            }else{
                map[x][y+10] = 3; //hit undamaged ship
                return 1;
            }
        }
    }
    
    public int IsGameOver(){
        
        if(playerturn){
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
        return map;
    }

    /**
     * @return the gameOver
     */
    public Boolean getGameOver() {
        return gameOver;
    }
    
    /**
     * @return the ready_to_start
     */
    public Boolean getReadyToStart() {
        return ready_to_start;
    }

    /**
     * @return the playerturn
     */
    public Boolean getPlayerturn() {
        return playerturn;
    }

    /**
     * @param playerturn the playerturn to set
     */
    public void togglePlayerturn() {
        this.playerturn = !playerturn;
    }
}