package rest;

import battleship.GameControllerI;
import battleship.GameLogic;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Set;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;


@Path("/game")
public class GameResource {
    GameControllerI game;
    
    public GameResource() throws MalformedURLException {
        //URL url = new URL("http://localhost:47713/battleship?WSDL");        //on local host
        URL url = new URL("http://ubuntu4.saluton.dk:47713/battleship?WSDL");
        QName qname = new QName("http://battleship/", "GameControllerService");
        Service service = Service.create(url, qname);
        QName port_name = new QName("http://battleship/", "GameControllerPort");
        game = service.getPort(port_name, GameControllerI.class);
    }
    
    @Path("/test")
    @GET
    @Produces("text/html")
    public String Test() {
        return "test";
    }
    
    @Path("/lobby")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response BrugerLogin(@QueryParam("Username") String Username, @QueryParam("Password") String Password) throws NotBoundException, RemoteException, MalformedURLException{
        Boolean BrugerLogin = game.BrugerLogin(Username, Password);
        return Response.ok()
                        .entity(BrugerLogin)
                        .header("Access-Control-Allow-Origin", "*")
                        .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                        .header("Access-Control-Allow-Credentials", "true")
                        .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                        .build();
    }
    
    @Path("/lobby/newgame")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response NewGame(@QueryParam("GameKey") String GameKey) throws RemoteException {
        game.NewGame(GameKey);
        return Response.ok()
                        .entity(Response.Status.OK)
                        .header("Access-Control-Allow-Origin", "*")
                        .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                        .header("Access-Control-Allow-Credentials", "true")
                        .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                        .build();
    }
    
    @Path("/lobby/joingame")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response JoinGame(@QueryParam("GameKey") String GameKey, @QueryParam("NewPlayer") String NewPlayer) {
        game.JoinGame(GameKey, NewPlayer);
        return Response.ok()
                        .entity(Response.Status.OK)
                        .header("Access-Control-Allow-Origin", "*")
                        .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                        .header("Access-Control-Allow-Credentials", "true")
                        .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                        .build();
    }
    
    @Path("/lobby/gethighscore")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHighscore() {
        //return Highscore_table.toString(); //returnerer hashtablen som en string
        String highscoretable = game.getHighScore();
        return Response.ok()
                        .entity(highscoretable)
                        .header("Access-Control-Allow-Origin", "*")
                        .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                        .header("Access-Control-Allow-Credentials", "true")
                        .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                        .build();
    }
    
    @Path("/game")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response togglePlayerturn(@QueryParam("GameKey") String GameKey) {
        game.togglePlayerturn(GameKey);
        return Response.ok()
                        .entity(Response.Status.OK)
                        .header("Access-Control-Allow-Origin", "*")
                        .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                        .header("Access-Control-Allow-Credentials", "true")
                        .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                        .build();
    }
    
    @Path("/game/game")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGame(@QueryParam("GameKey") String GameKey) {
        GameLogic localgame = game.getGame(GameKey);
        return Response.ok()
                        .entity(localgame)
                        .header("Access-Control-Allow-Origin", "*")
                        .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                        .header("Access-Control-Allow-Credentials", "true")
                        .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                        .build();
    }
                       
    @Path("/game/games")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllGames() {
        Set games = game.getAllGames();
        return Response.ok()
                        .entity(games)
                        .header("Access-Control-Allow-Origin", "*")
                        .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                        .header("Access-Control-Allow-Credentials", "true")
                        .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                        .build();
    }
    
    @Path("/game/ownmap")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOwnMap(@QueryParam("GameKey") String GameKey, @QueryParam("Username") String Username) {
        int[][] map = game.getOwnMap(GameKey, Username);
        return Response.ok()
                        .entity(map)
                        .header("Access-Control-Allow-Origin", "*")
                        .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                        .header("Access-Control-Allow-Credentials", "true")
                        .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                        .build();
    }
    
    @Path("/game/oppmap")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHiddenOpponentMap(@QueryParam("GameKey") String GameKey, @QueryParam("Username") String Username) {
        int[][] map = game.getHiddenOpponentMap(GameKey, Username);
        return Response.ok()
                        .entity(map)
                        .header("Access-Control-Allow-Origin", "*")
                        .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                        .header("Access-Control-Allow-Credentials", "true")
                        .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                        .build();
    }
    
    @Path("/game/placeship")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response PlaceShip(  @QueryParam("Username") String Username, 
                                @QueryParam("GameKey") String GameKey, 
                                @QueryParam("ShipSize") int ShipSize,
                                @QueryParam("X") int X,
                                @QueryParam("Y") int Y,
                                @QueryParam("Direction") String Direction) {
        Boolean PlaceShipResult = game.PlaceShip(Username, GameKey, ShipSize, X, Y, Direction);
        return Response.ok()
                        .entity(PlaceShipResult)
                        .header("Access-Control-Allow-Origin", "*")
                        .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                        .header("Access-Control-Allow-Credentials", "true")
                        .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                        .build();
    }
    
    @Path("/game/shoot")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response Shoot(  @QueryParam("Username") String Username, 
                            @QueryParam("GameKey") String GameKey,
                            @QueryParam("X") int X,
                            @QueryParam("Y") int Y) {
        int ShootResult = game.Shoot(Username, GameKey, X, Y);
        return Response.ok()
                        .entity(ShootResult)
                        .header("Access-Control-Allow-Origin", "*")
                        .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                        .header("Access-Control-Allow-Credentials", "true")
                        .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                        .build();
    }
    
    @Path("/game/gameover")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response IsGameOver(@QueryParam("Username") String Username, @QueryParam("GameKey") String GameKey) {
        Boolean GameOver = game.IsGameOver(Username, GameKey);
        return Response.ok()
                        .entity(GameOver)
                        .header("Access-Control-Allow-Origin", "*")
                        .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                        .header("Access-Control-Allow-Credentials", "true")
                        .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                        .build();
    }
    
    @Path("/game/remove")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response RemoveGame(@QueryParam("GameKey") String GameKey) {
        game.RemoveGame(GameKey);
        return Response.ok()
                        .header("Access-Control-Allow-Origin", "*")
                        .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                        .header("Access-Control-Allow-Credentials", "true")
                        .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                        .build();
    }
    
    @Path("/game/addhighscore")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addHighScore(@QueryParam("Playername") String Playername, @QueryParam("Score") Integer Score) {
        game.addHighScore(Playername, Score);
        return Response.ok()
                        .header("Access-Control-Allow-Origin", "*")
                        .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                        .header("Access-Control-Allow-Credentials", "true")
                        .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                        .build();
    }
}
