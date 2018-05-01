package rest;

import battleship.GameControllerI;
import java.net.MalformedURLException;
import java.net.URL;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;


@Path("/game")
public class GameResource {
    GameControllerI game;
    
    public GameResource() throws MalformedURLException {
        URL url = new URL("http://ubuntu4.saluton.dk:47713/battleship?WSDL");
        QName qname = new QName("http://battleship/", "GameControllerService");
        Service service = Service.create(url, qname);
        QName port_name = new QName("http://battleship/", "GameControllerPort");
        game = service.getPort(port_name, GameControllerI.class);
    }
    /**
     * Retrieves representation of an instance of game.GameResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/html")
    public String getGreeting() {
        return "<html><body><h1>Hello "+game.getAllGames()+"!</h1></body></html>";
    }

}
