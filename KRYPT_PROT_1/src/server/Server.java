//Autor: Robert Reininger, c1210537020

package server;

import java.io.*;
import java.net.ServerSocket;
 
public class Server {
    protected static boolean listening = true;
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;

        short port = Short.parseShort(args[0]);
 
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Fehler mit Port: " + port);
            System.exit(-1);
        }
        while (listening) {
        	new ServerThread(serverSocket.accept()).start();
        }
    	serverSocket.close();
    }

}
