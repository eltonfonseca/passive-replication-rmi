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

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        
        InterfaceServer remoteMainServer = serverConnect("main_server", 7000);
        InterfaceServer remoteServerBackupOne = serverConnect("server_backup_one", 8000);
        InterfaceServer remoteServerBackupTwo = serverConnect("server_backup_two", 9000);

        while(true) {
            menu();
            item = new Item();
            System.out.print("Digite o Código: ");
            item.setId(scanner.nextInt());
            System.out.print("Digite a Descrição: ");
            item.setDescription(scanner.next());
            System.out.println();
            System.out.println("Código: " + item.getId());
            System.out.println("Descrição: " + item.getDescription());
        }
    }

    private static InterfaceServer serverConnect(String nameServer, int port) {
        try {
            InterfaceServer remoteServer = (InterfaceServer) Naming.lookup("rmi://localhost:" + port + "/"+ nameServer);
            return remoteServer;
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            return null;       
        }
    }

    private static int menu() {
        System.out.println("========== Menu de Opções ==========");
        System.out.println("| 1 - Enviar Item                   |");
        System.out.println("| 2 - Recuperar Item                |");
        System.out.println("| 3 - Sair                          |");
        System.out.println("====================================="); 
        System.out.print("Opção: ");
        return scanner.nextInt();
    }
}