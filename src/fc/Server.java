package fc;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.*;

public class Server{

    public static void main (String[] args) throws IOException {

        int port = 3000;
        System.out.println("Waiting for connection");
        ServerSocket server = new ServerSocket(port);

        while (true){
            Socket sock = server.accept();
        }

    }

}