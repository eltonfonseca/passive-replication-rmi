import java.net.InetAddress;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import com.sun.org.apache.xpath.internal.SourceTree;

/**
 * @author Elton Fonseca, Felipe Hercules, Gabriel Castelo, Gean Matos
 */

 public class ServerBackupTwo extends UnicastRemoteObject implements InterfaceServer {

    private static final long serialVersionUID = 1L;
    private static final int port = 9000;
    private static Registry registry;
    private static final String name = "server_backup_two";
    private static InetAddress address;
    private static Log log;

    /**
     * 
     * @throws RemoteException
     */
    public ServerBackupTwo() throws RemoteException {};

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        try {
            InterfaceServer remoteServerBackupTwo = new ServerBackupTwo();
            address = InetAddress.getLocalHost();
            registry = LocateRegistry.createRegistry(port);
            registry.bind(name, remoteServerBackupTwo);
            log = new Log("ServerBackupTwo.log");
            System.out.println("Main Server running on: " + address.getHostAddress() + ":" + port);
            System.out.println("Main Server is ready!");
        } catch (Exception e) {
            System.err.println("Error: Main Server is not ready! " + e.getMessage());
        }
    }

    @Override
    public void saveItem(Item item) throws RemoteException {
        log.save(item.getId() + ";" + item.getDescription());
        System.out.println("Item Saved!");
    }

    @Override
    public Item searchItem(int id) throws RemoteException {
        Item item;
        String description = log.search(id);
        if(!description.equals(null)){
            item = new Item(id, description);
            System.out.println("Item Retrieved!");
            return item;
        }
        System.out.println("Item not Found!");
        return null;
    }
 }