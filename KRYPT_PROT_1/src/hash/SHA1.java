//Autor: Robert Reininger, c1210537020

package hash;

import java.io.UnsupportedEncodingException;
//import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class SHA1 {
	//generate HASH - return byte[]
	public static byte[] generateHash(String text) {
	    try {
	        byte[] Message = text.getBytes("UTF-8");
	        MessageDigest md = MessageDigest.getInstance("SHA1");
	        byte[] hash = md.digest(Message);
	        return hash;
	    } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
	        return null;
	    }
	}
	//convert byte[] to HexString
	public static String outputHash(String text) {
		byte[] temp = generateHash(text);
		String hashed="";
        for (int i =  0; i<=temp.length-1; i++) {
        	hashed = hashed + Integer.toHexString(temp[i] & 0xFF);
        }
        return hashed;
	}
}