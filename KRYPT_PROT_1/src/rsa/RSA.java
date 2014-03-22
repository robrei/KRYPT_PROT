//Autor: Robert Reininger, c1210537020

package rsa;

import java.math.BigInteger;
import java.util.Random;

public class RSA {
    public static String encryptRsa(String plaintext) {
        Random rand = new Random();
        BigInteger n = BigInteger.ZERO;
        BigInteger phi = BigInteger.ZERO;
        BigInteger e = BigInteger.ZERO;
        
        //generate p, q, n, phi, e
        do {
            BigInteger p = BigInteger.probablePrime(512, rand);
            BigInteger q = BigInteger.probablePrime(512, rand);
            n = p.multiply(q);
            phi = p.subtract(BigInteger.ONE);
            phi = phi.multiply(q.subtract(BigInteger.ONE));
            e = BigInteger.valueOf(3);
        } while (phi.gcd(e).compareTo(BigInteger.ONE) != 0);

        //generate d, ciphertext, m2, decrypted
        BigInteger d = e.modInverse(phi);       
        BigInteger m = new BigInteger(plaintext.getBytes());
        	//System.out.println("Debug: Plain als BigInt " + m);
        BigInteger ciphertext = m.modPow(e, n);
        	//System.out.println("Debug Print: Ciphertext als BigInt " + ciphertext);
        BigInteger m2 = ciphertext.modPow(d, n);
        	//System.out.println("Debug Print: Ciphertext decrypted als BigInt " + m2);
        String ciphertextStr = new String(ciphertext.toByteArray());
        String decryptedStr = new String(m2.toByteArray());
        return ("Verschluesselt als String:  " + ciphertextStr + "  Wieder entschluesselt als String:  " + decryptedStr);
    } 
}
