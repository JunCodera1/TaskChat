package Model;

import java.io.InputStream;
import java.net.Socket;

public class ClientListener implements Runnable{
    private Socket socket;
    private InputStream inputStream;
    public ClientListener(Socket socket){
        this.socket = socket;
        try{
            this.inputStream = socket.getInputStream();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void run(){
        try{
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1){
                String message = new String(buffer, 0, bytesRead);
                System.out.println(message);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
