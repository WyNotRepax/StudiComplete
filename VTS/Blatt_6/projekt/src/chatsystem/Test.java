package chatsystem;

import chatsystem.server.ChatServerImpl;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Test {
    public static void main(String[] args) {
        try {
            ChatServer stub = new ChatServerImpl();
            Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            registry.rebind("OTHERNAME", stub);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
