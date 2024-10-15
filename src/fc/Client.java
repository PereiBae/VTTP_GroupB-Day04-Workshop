package fc;

import java.io.*;
import java.net.*;

public class Client {

    public static void main (String[] args) throws IOException{

        if (args.length != 1){
            System.err.println("Usage: java -cp fortunecookie.jar fc.Client <host>:<port>");
            System.exit(1);
        }

        String[] hostport = args[0].split(":");
        if (hostport.length != 2){
            System.err.println("Invalid format. Please provide <host>:<port>.");
            System.exit(1);
        }

        System.out.println("Connecting to the server");
        Socket sock = new Socket(hostport[0],Integer.parseInt(hostport[1]));

        System.out.println("Connected");

        Console cons = System.console();

        String theMessage = cons.readLine("input: ");

        OutputStream os = sock.getOutputStream();
        PrintWriter printWriter = new PrintWriter(os,true);
        Writer writer = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(writer);

        InputStream is = sock.getInputStream();
        Reader reader = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(reader);

        bw.write(theMessage);
        bw.newLine();
        bw.flush();
        os.flush();

        String fromServer = br.readLine();
        System.out.println(">>> Server: " + fromServer + "\n");

        os.close();
        is.close();
        sock.close();

    }
    
}
