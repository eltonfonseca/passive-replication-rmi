import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author Elton Fonseca
 */

 public class ServerBackupTwo extends UnicastRemoteObject implements InterfaceServer {

    private static final long serialVersionUID = 1L;
    private static final int port = 9000;
    private static Registry registry;
    private static final String name = "server_backup_two";
    private static InetAddress address;

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
            System.out.println("Main Server running on: " + address.getHostAddress() + ":" + port);
            System.out.println("Main Server is ready!");
        } catch (Exception e) {
            System.err.println("Error: Main Server is not ready! " + e.getMessage());
        }
    }
    
    @Override
    public void saveItem(Item item) throws RemoteException {
        System.out.println(item.getId());
        System.out.println(item.getDescription());
    }

    @Override
    public Item searchItem(int id) throws RemoteException {
        System.out.println("Retorna Item");
        Item item = new Item(1, "GariGari");
        return item;
    }
 }