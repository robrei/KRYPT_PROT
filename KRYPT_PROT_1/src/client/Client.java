//Autor: Robert Reininger, c1210537020

package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.*;
 
public class Client {
    public static void main(String[] args) throws IOException { 
    	Socket clientSocket = null;        
        PrintWriter outStr = null;        
        //ObjectOutputStream outObj = null;        
        BufferedReader inStr = null;
        //ObjectInputStream inObj = null;
        
        short port = Short.parseShort(args[0]); 
        
        try {
	            clientSocket = new Socket("localhost", port);
	            outStr = new PrintWriter(clientSocket.getOutputStream(), true);
	            //outObj = new ObjectOutputStream(clientSocket.getOutputStream());
	            inStr = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //von Socket
	            //inObj = new ObjectInputStream(clientSocket.getInputStream());
	        } catch (UnknownHostException e) {
	            System.err.println("Don't know about host: localhost.");
	            System.exit(-1);
	        } catch (IOException e) {
	            System.err.println("Couldn't get I/O for the connection to: localhost.");
	            System.exit(-1);
        } 
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in)); //von Console
        
        String fromServerString;
        String fromUserString;
 
        while ((fromServerString = inStr.readLine()) != null) {
            if (fromServerString.equals("#")) {
            	fromUserString = stdIn.readLine();
            	if (fromUserString != null) {
            		outStr.println(fromUserString);
            	}
            } else {
            	System.out.println(fromServerString);
            }
        }
        //Verbindung trennen
        outStr.close();
        inStr.close();
        //outObj.close();
        //inObj.close();
        stdIn.close();
        clientSocket.close();
    }
}