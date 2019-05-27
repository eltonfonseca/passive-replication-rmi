import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;


/**
 * @author Elton Fonseca
 */

public class Client {

    private static Item item;
    private static final Scanner scanner = new Scanner(System.in);
    private static int option;
    private static InterfaceServer remoteMainServer;
    private static InterfaceServer remoteServerBackupOne;
    private static InterfaceServer remoteServerBackupTwo;

    /**
     * Execução Principal
     * @param args Argumentos do Client
     */
    public static void main(String[] args) {
        while(option != 3) {
            option = menu();
            loadServers();
            switch(option) {
                case 1: // Enviar Item
                    if(serverIsUp(remoteMainServer)) {
                        System.out.println("####### Connected in Main Server! #######");
                        buildItem(remoteMainServer);
                    }
                    else if(serverIsUp(remoteServerBackupOne)) {
                        System.out.println("####### Connected in Server Backup One! #######");
                        buildItem(remoteServerBackupOne);
                    }
                    else if(serverIsUp(remoteServerBackupTwo)) {
                        System.out.println("####### Connected in Server Backup Two! #######");
                        buildItem(remoteServerBackupTwo);
                    }
                    else {
                        System.out.println("Servers is Down!");
                    }
                    break;
                case 2: // Recuperar Item
                    break;
            }
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
     * Constroi o Objeto Item e envia ao servidor remoto
     * @param server Servidor Remoto
     */
    private static void buildItem(InterfaceServer server) {
        item = new Item();
        System.out.print("Input the code: ");
        item.setId(scanner.nextInt());
        System.out.print("Input the Description: ");
        item.setDescription(scanner.next());
        try {
            server.saveItem(item);
        } catch (Exception e) {
            System.err.println("Error: Not save Item on the server");
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
        remoteMainServer = serverConnect("main_server", 7000);
        remoteServerBackupOne = serverConnect("server_backup_one", 8000);
        remoteServerBackupTwo = serverConnect("server_backup_two", 9000);  
    }

    /**
     * Menu de Itens
     * @return retorna a opção
     */
    private static int menu() {
        System.out.println("========== Options Menu ===========");
        System.out.println("| 1 - Send Item                   |");
        System.out.println("| 2 - Search Item                 |");
        System.out.println("| 3 - Exit                        |");
        System.out.println("==================================="); 
        System.out.print("Option: ");
        return scanner.nextInt();
    }
}