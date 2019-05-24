import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * @author Elton Fonseca
 */

public class Client {

    private static final int portMainServer = 7000;
    private static String hostMainServer;
    private static InterfaceServer remoteMainServer;
    private static Item item;

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        
        hostMainServer = "rmi://localhost:" + portMainServer + "/main_server";
        item = new Item(1, "Paralelepipedo");

        try {
            remoteMainServer = (InterfaceServer) Naming.lookup(hostMainServer);
            remoteMainServer.saveItem(item);
        } catch (NotBoundException e) {
            System.err.println("Error: Not Bound Server error" + e.getMessage());           
        } catch(MalformedURLException e) {
            System.err.println("Error: URL error" + e.getMessage());
        } catch(RemoteException e) {
            System.err.println("Error: Remote Server error" + e.getMessage());
        }
    }
}