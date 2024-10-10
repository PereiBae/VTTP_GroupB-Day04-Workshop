package Network;

import java.io.*;
import java.net.*;

public class FileCopy {

    public static void main(String[] args) throws IOException {

        int port = 3000;
        String filepath = args[0];

        if(args.length <= 0){
            System.err.println("Missing File Name");
            System.exit(-1);
        }

        System.out.println("Connecting to the server");
        Socket sock = new Socket("localhost", port);

        System.out.println("Connected");

        File file = new File(filepath);

        if (!file.exists()) {
            System.out.println("File does not exist: " + filepath);
            return;
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
        DataInputStream dis = new DataInputStream(sock.getInputStream());

        OutputStream os = sock.getOutputStream();
        Writer writer = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(writer);

        InputStream is = sock.getInputStream();
        Reader reader = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(reader);

        dos.writeUTF(file.getName());
        dos.writeLong(file.length());
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
            dos.write(buffer, 0, bytesRead);
        }

        String fromServer = "x";
        while ((fromServer = br.readLine()) != null){
            System.out.println(">>> SERVER: " + fromServer + " \n");
        }

        System.out.println("File sent to server successfully!");

        dis.close();
        is.close();
        sock.close();
    }
}
