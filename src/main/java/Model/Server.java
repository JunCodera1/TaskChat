package Model;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final int PORT = 7000;

    private List<ClientHandler> clients = new ArrayList<>();

    public void startServer(){
        try{
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Chat room started. Listening on Port: " + PORT);
            while (true){
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());
                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void broadcastMessage(String message){
        for(ClientHandler clientHandler : clients){
            clientHandler.sendMessage(message);
        }
    }
}
