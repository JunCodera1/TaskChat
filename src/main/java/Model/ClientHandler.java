package Model;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private Socket socket;
    private Server server;
    private InputStream inputStream;
    private OutputStream outputStream;


    public ClientHandler(Socket socket, Server server){
        this.socket =  socket;
        this.server = server;
        try{
            this.inputStream = socket.getInputStream();
            this.outputStream = socket.getOutputStream();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void run(){
        try{
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1){
                String message = new String(buffer, 0, bytesRead);
                server.broadcastMessage(message);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void sendMessage(String message){
        try{
            outputStream.write(message.getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
