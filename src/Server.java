import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private static final int PORT = 8189;
    public static void main(String[] args) {
        try(ServerSocket server = new ServerSocket(PORT)){
            System.out.println("Server started");
            try(Socket socket = server.accept()) {
                System.out.println("Client connected");
                Scanner scaner = new Scanner(socket.getInputStream());
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Scanner console_in = new Scanner(System.in);
                        while (true) {
                            System.out.println("Введите сообщение для клиента");
                            String fromConsole = console_in.nextLine();
                            if (fromConsole.equals("/end")) {
                                System.out.println("Server disconnected");
                                break;
                            }
                            out.println(fromConsole);
                        }
                    }
                }).start();
                while (true) {
                    String s = scaner.nextLine();
                    if (s.equals("/end")) {
                        System.out.println("Client disconnected");
                        break;
                    }
                    System.out.println("Client: " + s);
                }

            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
