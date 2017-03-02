package source;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class Crypt {

	
	public void MD5_Hash()
	{
		
		try {
			String plaintext = "your text here";
			MessageDigest m;
			m = MessageDigest.getInstance("MD5");
			byte[] bytesOfMessage = plaintext.getBytes("UTF-8");
			m.reset();
			m.update(plaintext.getBytes());
			byte[] digest = m.digest();
			
			BigInteger bigInt = new BigInteger(1,digest);
			
			String hashtext = bigInt.toString(16);
			// Now we need to zero pad it if you actually want the full 32 chars.
			while(hashtext.length() < 32 ){
			  hashtext = "0"+hashtext;
			  
			}
			System.out.println(hashtext);
			
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void Encrypt()
	{
		try {
			 String FileName = "encryptedtext.txt";
		     String FileName2 = "decryptedtext.txt";

			Cipher desCipher;
			
			KeyGenerator keygen = KeyGenerator.getInstance("AES");
			desCipher = Cipher.getInstance("AES");
			
			keygen.init(128);
			SecretKey secretKey = keygen.generateKey();
			
			byte[] text = "No body can see me.".getBytes("UTF-8");
			
			
			desCipher.init(Cipher.ENCRYPT_MODE,secretKey);
			byte[] texte= desCipher.doFinal(text);
			
			String s = new String(texte);
			System.out.println(s);
			
			 desCipher.init(Cipher.DECRYPT_MODE, secretKey);
	         byte[] textd = desCipher.doFinal(texte);
			
			s = new String (textd);
			System.out.println(s);
		
			
			
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void Decrypt()
	{
		
	}

}

	
