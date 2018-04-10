/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;
import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 *
 * @author mick19955
 */
@WebService
public interface GameI {
    @WebMethod void Reset();
    @WebMethod void EmptyMap();
    @WebMethod int PlaceShip(int shipsize, int x, int y, Boolean horizontal,int playerturn);
    @WebMethod int Shoot(int x, int y,int playerturn);
    @WebMethod int IsGameOver(int playerturn);
    
    @WebMethod int[][] getMap();
    @WebMethod Boolean getGameOver();
}
