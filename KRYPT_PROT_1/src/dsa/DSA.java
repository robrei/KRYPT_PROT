//Autor: Robert Reininger, c1210537020
//FIPS PUB 186-4
package dsa;

import java.math.BigInteger;
import java.util.Random;

import hash.SHA1;

public class DSA {
	
	/*
	//TEST:
	public static void main(String[] args) {
		System.out.println(DSA.verify("test",DSA.sign("test",DSA.generateKey())));
	}
	*/
	
	public static BigInteger[] generateKey() {
		System.out.println("start DSA Key Generation...");
		Random rand = new Random(); 

		//generate prime q (160bit and (p-1) mod q=0)
		BigInteger q = BigInteger.ZERO;
		q = BigInteger.probablePrime(160, rand);
		System.out.println("q= "+ q);
		
		//generate prime p (512-1024bit%64=0)
		BigInteger p = BigInteger.ZERO;
		do {
			/*
			//Zufaellige Laenge von p
			do {
				temp = randInt(512-160,1024-160); //Bitlaenge von p bestimmen zw. 512 u. 1024 u. teilbar durch 64
			} while (temp%64 != 0);
			p = new BigInteger(temp, rand); 
			*/
			
			// feste Laenge von p mit 1024bit
			p = new BigInteger(1024-160, rand); 
			p = p.multiply(q).add(BigInteger.ONE);
			
			//debug:
			//System.out.println("p: "+ p);		
			//System.out.println("p: " + p.bitLength());
			//System.out.println("q: " + q);
			//System.out.println("p gcd q = q ?: " + p.subtract(BigInteger.ONE).gcd(q));
			
		}while(p.isProbablePrime(1)==false && p.bitLength()%64 !=0);
		System.out.println("p= "+ p);
		
		//generate h
		BigInteger h = BigInteger.ZERO;
		do {
			h = new BigInteger(p.bitLength()-1,rand);
		} while (h.compareTo(BigInteger.ONE) != 1 || h.compareTo(p.subtract(BigInteger.ONE)) != -1 || h.modPow((p.subtract(BigInteger.ONE).divide(q)), p).compareTo(BigInteger.ONE) == 0);
		System.out.println("h= "+ h);
		
		//generate g
		BigInteger g = h.modPow((p.subtract(BigInteger.ONE).divide(q)),p);
		System.out.println("g= "+ g);
		
		//generate x
		BigInteger x = BigInteger.ZERO;
		do {
			x = new BigInteger(q.bitLength()-1,rand);
		} while (x.compareTo(BigInteger.ONE) != 1 || x.compareTo(p.subtract(BigInteger.ONE)) != -1 );
		System.out.println("x= "+ x);
		
		//generate y
		BigInteger y = BigInteger.ZERO;
	    y = g.modPow(x,p);
	    System.out.println("y= "+ y);
	    
	    //generate return array
		BigInteger[] pKey = new BigInteger[5];
		pKey[0] = p;
		pKey[1] = q;
		pKey[2] = g;
		pKey[3] = x;
		pKey[4] = y;
		System.out.println("...DSA Keys generated");
		return pKey;

	}
	
	
	public static BigInteger[] sign(String text1, BigInteger[] pKey) {
		System.out.println("DSA Signing starting...");
		BigInteger p = pKey[0];
		BigInteger q = pKey[1];
		BigInteger g = pKey[2];
		BigInteger x = pKey[3];
		BigInteger y = pKey[4];
		Random rand = new Random();
		
		//sha1 of message
		BigInteger hash = new BigInteger(SHA1.generateHash(text1));
		System.out.println("Hash1: " + hash);
		BigInteger s = BigInteger.ZERO;
		BigInteger s1 = BigInteger.ZERO;
		BigInteger s2 = BigInteger.ZERO;
		
		//generate s, s1, s2
		do {
			do {
				do {
					s = new BigInteger(q.bitLength()-1,rand);
				} while (s.compareTo(BigInteger.ONE) != 1 || s.compareTo(p.subtract(BigInteger.ONE)) != -1 );
				s1 = g.modPow(s, p).mod(q);
			}while(s1==BigInteger.ZERO );
			System.out.println("s1= " + s1);
			s2 = s.modInverse(q).multiply(hash.add(s1.multiply(x))).mod(q);
		}while(s2==BigInteger.ZERO);	
		System.out.println("s2= " + s2); 
		
		//generate return array
		BigInteger[] sig = new BigInteger[7];
		sig[0] = s1;
		sig[1] = s2;
		sig[2] = q;
		sig[3] = y;
		sig[4] = g;
		sig[5] = p;
		sig[6] = x;
		
		System.out.println("...DSA Signing finished");
		
		return sig;
	}
	
	public static String verify(String text2, BigInteger[] sig) {
		System.out.println("DSA Verification starting...");
		String ver = "";
		BigInteger s1 = sig[0];
		BigInteger s2 = sig[1];
		BigInteger q = sig[2];
		BigInteger y = sig[3];
		BigInteger g = sig[4];
		BigInteger p = sig[5];
		BigInteger x = sig[6];
		BigInteger hash2 = new BigInteger(SHA1.generateHash(text2)); 
		
		//generate w, u1, u2, v
		//check if sig is valid
		if (s1.compareTo(BigInteger.ZERO) == 1 && s1.compareTo(q) == -1){
			if (s2.compareTo(BigInteger.ZERO) == 1 && s2.compareTo(q) == -1) {
				//calculate w
				BigInteger w = s2.modInverse(q);
				System.out.println("w= " + w);
				//calculate u1
				BigInteger u1 = hash2.multiply(w).mod(q);
				//calculate u2
				BigInteger u2 = s1.multiply(w).mod(q);
				//calculate v
				BigInteger v = ((g.modPow(u1,p).multiply(y.modPow(u2, p))).mod(p)).mod(q);
				System.out.println("v= " + v);
				if (v.compareTo(s1)==0){
				ver="Signatur gueltig!" + "" + "Text2: text2" + "\n" + "Verification: v= " + v + "\n" + "Verification: s1= " + s1;
					
				} else {
					ver = "Oeffentlicher Schluessel: \n";
					ver = ver + "q: " + q + "\n";
					ver = ver + "p: " + p + "\n";
					ver = ver + "g: " + g + "\n";
					ver = ver + "y: " + y + "\n";
					ver = ver + "s1: " + s1 + "\n";
					ver = ver + "s2: " + s2 + "\n";
					ver = ver + "Privater Schluessel: \n";
					ver = ver + "x: " + x + "\n";
					ver = ver + "Signatu ngueltig!";
				}	
			} else {
				ver="Signatur2 ist ungueltig!";
			}
		} else {
			ver="Signatur1 ist ungueltig!";
		}
		System.out.println("...DSA Verification finished...");
		return ver;
	}

	
	public static int randInt(int min, int max) {
	    Random rand = new Random();
	    // nextInt +1 für Obergrenze
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}

}
