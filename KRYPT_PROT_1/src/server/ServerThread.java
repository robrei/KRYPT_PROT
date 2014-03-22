//Autor: Robert Reininger, c1210537020

package server;
//import for client/server
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
import java.io.PrintWriter;
//import java.io.UnsupportedEncodingException;
import java.net.Socket;
//import for Hashing, RSA, ECC
//import java.math.BigInteger;
//import java.security.*;



//import java.util.*;
import ecc.PointOperation;
import dsa.DSA;
 
public class ServerThread extends Thread {
    private Socket socketServer = null;
    public ServerThread(Socket socketServer) {
    	 super("Server Thread");
    	 this.socketServer = socketServer;
    }
 
    public void run() {
    	try {
	        PrintWriter outStr = new PrintWriter(socketServer.getOutputStream(), true);
	        //ObjectOutputStream outObj = new ObjectOutputStream(socketServer.getOutputStream());
	        BufferedReader inStr = new BufferedReader(new InputStreamReader(socketServer.getInputStream()));
	       // ObjectInputStream inObj = new ObjectInputStream(socketServer.getInputStream());
	 
	        String inputLine;
	        //String outputLine;
	        
	        //Menue:
	        outStr.println("1.Hash");
	        outStr.println("2.RSA");
	        outStr.println("3.ECC");
	        outStr.println("4.DSA");
	        outStr.println("Zum Beenden - exit");
	        outStr.println("#");
	        while ((inputLine = inStr.readLine()) != null) {
	        		if(inputLine.equals("#")) {
	        			break;
	        		}
	        		if(inputLine.equals("1")) {
		        		outStr.println("Bitte den Text eingeben! (SHA1)");
		        		outStr.println("#");
		        		outStr.println(hash.SHA1.outputHash(inStr.readLine()));
		        		outStr.println("#");
	        		}
	        		else if(inputLine.equals("2")) {
			        	outStr.println("Bitte den zu verschluesselnden Text eingeben!");
			        	outStr.println("#");
			        		inputLine = inStr.readLine();
			        	outStr.println(rsa.RSA.encryptRsa(inputLine));
			        	outStr.println("#");
	        		}
	        		else if(inputLine.equals("3")) {
		        		outStr.println("Bitte a fuer Kurve angeben:");
		        		outStr.println("#");
		        			double a = Double.parseDouble(inStr.readLine());
		        		outStr.println("Bitte b fuer Kurve angeben:");
		        		outStr.println("#");
		        			double b = Double.parseDouble(inStr.readLine());
		        		outStr.println("Bitte Punkt angeben:");
		        		outStr.println("#");
		        			double punkt= Double.parseDouble(inStr.readLine());
		        		outStr.println("Bitte Anzahl der Multiplikationen angeben:");
		        		outStr.println("#");
		        			int multi = Integer.parseInt(inStr.readLine());
		        			ecc.Point temp = new ecc.Point();
		        			temp = PointOperation.pMultiply(new ecc.Point(a,b,punkt),a,multi);
		        		outStr.println("P: " + punkt + " * " + multi + "=");
		        		outStr.println("X: " + temp.getX() + " Y: " + temp.getY()); //return from ECC
		        		outStr.println("#");
	        		}
	        		else if(inputLine.equals("4")) {
			        	outStr.println("Bitte einen Text eingeben!");
			        	outStr.println("#");
			        		inputLine = inStr.readLine();
			        	outStr.println(DSA.verify(inputLine,DSA.sign(inputLine,DSA.generateKey())));
			        	outStr.println("#");
	        		}
	        		else if(inputLine.equals("exit")) {
	        			break;
	        		}
	        		else {
		        		outStr.println("Ungueltige Eingabe!");
		        		outStr.println("#");
	        	}
	        }
	        //Verbindungen beenden:
	        outStr.println("...Serververbindung beendet");
	        outStr.println(); //sendet NULL an Client zum terminieren
	        outStr.close();
	        inStr.close();
	        socketServer.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    }
}