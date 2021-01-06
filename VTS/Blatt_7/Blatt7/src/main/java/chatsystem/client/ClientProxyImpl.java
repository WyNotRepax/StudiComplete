package chatsystem.client;

import chatsystem.ClientProxy;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientProxyImpl extends UnicastRemoteObject implements ClientProxy {

    ClientProxyImpl() throws RemoteException {
        super();
    }

    @Override
    public void receiveMessage(String username, String message) throws RemoteException {
        System.out.printf("%s: %s\n", username, message);
    }
}
