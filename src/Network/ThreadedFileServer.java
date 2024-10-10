package Network;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadedFileServer {

    public static void main(String[] args) throws IOException {

        // Create a thread pool = Create the worker
        ExecutorService thrPool = Executors.newFixedThreadPool(2);

        int port = 3000;

        System.out.println("Waiting for connection");
        ServerSocket server = new ServerSocket(port);

        while (true) {
            Socket sock = server.accept();
            System.out.println("Got a new connection");

            // Create the work
            FileClientHandler handler = new FileClientHandler(sock);

            // Pass the work to the worker
            thrPool.submit(handler);
        }

    }
}
