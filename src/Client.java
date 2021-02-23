import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final String SERVER_ADDR = "localhost";
    private static final int SERVER_PORT = 8189;

    public static void main(String[] args) {
        System.out.println("Client started");
        try(Socket socket = new Socket(SERVER_ADDR, SERVER_PORT)){
                System.out.println("Client connected");
                Scanner socket_in = new Scanner(socket.getInputStream());
                Scanner console_in = new Scanner(System.in);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            while(true){
                                String fromServer = socket_in.nextLine();
                                if (fromServer.equals("/end")){
                                    System.out.println("Server disconnected");
                                    break;
                                }
                                System.out.println("Server: " + fromServer);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();
                while (true){
                    System.out.println("Введите сообщение для сервера");
                    String s = console_in.nextLine();
                    if (s.equals("/end")){
                        System.out.println("Client disconnected");
                        out.println(s);
                        break;
                    }
                    out.println(s);
                }
               new Thread(new Runnable() {
                   @Override
                   public void run() {
                       try {
                           while(true){
                               String fromServer = socket_in.nextLine();
                               if (fromServer.equals("/end")){
                                   System.out.println("Server disconnected");
                                   break;
                               }
                               System.out.println("Server: " + fromServer);
                           }
                       }catch (Exception e){
                           e.printStackTrace();
                       }
                   }
               }).start();
        }catch (IOException e) {
                e.printStackTrace();
        }
    }
}
