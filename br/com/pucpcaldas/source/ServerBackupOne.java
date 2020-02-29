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

 public class ServerBackupOne extends UnicastRemoteObject implements InterfaceServer {

    private static final long serialVersionUID = 1L;
    private static final int port = 8000;
    private static Registry registry;
    private static final String name = "server_backup_one";
    private static InetAddress address;
    private static Log log;
    private static InterfaceServer remoteServerBackupTwo;

    /**
     * 
     * @throws RemoteException
     */
    public ServerBackupOne() throws RemoteException {};

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        try {
            InterfaceServer remoteServerBackupOne = new ServerBackupOne();
            address = InetAddress.getLocalHost();
            registry = LocateRegistry.createRegistry(port);
            registry.bind(name, remoteServerBackupOne);
            log = new Log("ServerBackupOne.log");
            System.out.println("Main Server running on: " + address.getHostAddress() + ":" + port);
            System.out.println("Main Server is ready!");
        } catch (Exception e) {
            System.err.println("Error: Main Server is not ready! " + e.getMessage());
        }
    }

    /**
     * Estabelece conexão ao servidor via RMI
     * @param nameServer nome do servidor
     * @param port porta do servidor
     * @return retorna o objeto remoto ou null
     */
    private static InterfaceServer serverConnect(String nameServer, int port) {
        try {
            InterfaceServer remoteServer = (InterfaceServer) Naming.lookup("rmi://localhost:" + port + "/"+ nameServer);
            return remoteServer;
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            return null;       
        }
    }

    /**
     * Verifica se o servidor está UP ou DOWN
     * @param server servidor
     * @return retorna true se o servidor estiver UP ou retorna false se o servidor estiver DOWN 
     */
    private static boolean serverIsUp(InterfaceServer server) {
        if(server == null)
            return false;
        return true;
    }

    /**
     * Carrega os servidores
     * @return void
     */
    private static void loadServers() {
        remoteServerBackupTwo = serverConnect("server_backup_two", 9000);  
    }

    @Override
    public void saveItem(Item item) throws RemoteException {
        loadServers();
        log.save(item.getId() + ";" + item.getDescription());
        if(serverIsUp(remoteServerBackupTwo)) {
            remoteServerBackupTwo.saveItem(item);
        }
        else {
            System.out.println("ServerBackupTwo is Down!");
        }
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