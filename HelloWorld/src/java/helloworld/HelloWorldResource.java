package helloworld;

import battleship.GameController;
import battleship.GameControllerI;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;


@Path("/games")
public class HelloWorldResource {
    GameControllerI game;
    
    public HelloWorldResource() throws MalformedURLException {
        URL url = new URL("http://ubuntu4.saluton.dk:47713/battleship?WSDL");
        //URL url = new URL("http://ubuntu4.saluton.dk:4443/GalgeTest?WSDL"); //on ubunto server
        QName qname = new QName("http://battleship/", "GameControllerService");
        Service service = Service.create(url, qname);
        QName port_name = new QName("http://battleship/", "GameControllerPort");
        game = service.getPort(port_name, GameControllerI.class);
    }
    /**
     * Retrieves representation of an instance of helloworld.HelloWorldResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/html")
    public String getGreeting() {
        return "<html><body><h1>Hello "+game.getAllGames()+"!</h1></body></html>";
    }

}
