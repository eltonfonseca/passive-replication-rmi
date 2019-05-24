/**
 * @author Elton Fonseca
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceServer extends Remote {

    /**
     * 
     * @param item
     * @throws RemoteException
     */
    public void saveItem(Item item) throws RemoteException;

    /**
     * 
     * @param id
     * @return
     * @throws RemoteException
     */
    public Item searchItem(int id) throws RemoteException;
}