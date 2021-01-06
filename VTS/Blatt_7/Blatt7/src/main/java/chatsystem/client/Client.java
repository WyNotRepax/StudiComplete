/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsystem.client;

import chatsystem.ChatProxy;
import chatsystem.ChatServer;
import chatsystem.Main;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 *
 * @author benno
 */
public class Client {
    

    public static void main(String[] args) {
        try {
            Registry registry;
            String host;
            if (args.length < 2) {
                host = "localhost";
                registry = LocateRegistry.getRegistry(Main.PORT);
            } else {
                host = args[1];
                registry = LocateRegistry.getRegistry(host, Main.PORT);
            }

            try {
                ChatServer chatServer = (ChatServer) registry.lookup(Main.NAME);
                mainLoop(chatServer);
            } catch (UnknownHostException e) {
                System.err.printf("Unknown Host: %s\n", host);
                System.exit(1);
            } catch (NotBoundException e){
                System.err.printf("Service not bound on Host %s\n", host);
                System.exit(1);
            } catch (ConnectException e){
                System.err.printf("Could not Connect to Host: %s \n",e.getCause());
                System.exit(1);
            }

        }catch (RemoteException e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void mainLoop(ChatServer chatServer) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Username: ");
        String username = scanner.nextLine();

        try {
            ChatProxy chatProxy = chatServer.subscribeUser(username, new ClientProxyImpl());

            while (true) {
                String input = scanner.nextLine();
                if (input.equals("/exit")) {
                    chatServer.unsubscribeUser(username);
                    System.out.println("Bye");
                    System.exit(0);
                }
                chatProxy.sendMessage(input);
            }

        } catch (RemoteException e) {
            System.err.println("Could not get ChatProxy");
            System.exit(1);
        }
    }
}
