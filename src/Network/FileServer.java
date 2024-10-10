package Network;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.*;

public class FileServer {

    public static void main(String[] args) throws IOException {

        int port = 3000;
        System.out.println("Waiting for connection");
        ServerSocket server = new ServerSocket(port);
        while (true) {
            Socket sock = server.accept();
            System.out.println("Got a new connection");

            InputStream is = sock.getInputStream();
            Reader reader = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(reader);

            OutputStream os = sock.getOutputStream();
            Writer writer = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(writer);

            DataInputStream dis = new DataInputStream(sock.getInputStream());
            DataOutputStream dos = new DataOutputStream(sock.getOutputStream());

            String fileName = dis.readUTF();
            System.out.println(">>> Receiving file from Client at" + (new Date()).toString() + " : " + fileName);
            bw.write(">>> Receiving file from Client at" + (new Date()).toString() + " : " + fileName);
            bw.newLine();

            long fileSize = dis.readLong();
            System.out.println(">>> Size of file received: " + fileSize + " bytes");
            bw.write(">>> Size of file received: " + fileSize + " bytes");
            bw.newLine();

            File file = new File("received_" + fileName); // Save the file with a prefix 'received_'
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            byte[] buffer = new byte[4096]; // Buffer for receiving file data
            int bytesRead;
            long bytesReceived = 0;

            while (bytesReceived < fileSize) {
                bytesRead = dis.read(buffer, 0, buffer.length);
                fileOutputStream.write(buffer, 0, bytesRead);
                bytesReceived += bytesRead;
            }
            System.out.println(">>> File received successfully at " + (new Date()).toString());
            bw.write(">>> File received successfully at " + (new Date()).toString());

            bw.flush();

            fileOutputStream.close();
            dis.close();
            dos.close();
            sock.close();
        }
    }
}
