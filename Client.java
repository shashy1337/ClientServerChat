package ClientServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable{

    Server server;

    Socket socket;
    String name;

    Scanner in;
    PrintStream out;

    public Client(Socket socket, Server server){
        this.socket = socket;
        this.server = server;
        new Thread(this).start();
    }

    public void reciveAll(String message){
        out.println(message);
    }


    @Override
    public void run() {
        try {
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            in = new Scanner(is);
            out = new PrintStream(os);

           out.print("Введите ваше имя: ");

           name = in.nextLine();

            out.println("Начините ваше общение!");
            String input = in.nextLine();

            while (!input.equals("bye")){
                server.receive(name + ":" + input);
                input = in.nextLine();
            }
            socket.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
