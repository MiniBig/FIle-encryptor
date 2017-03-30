package source;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JFileChooser;

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
	
	public void Encrypt(String filename,String path,String dir) 
	{

		filename = filename.substring(0,filename.indexOf('.'));

		
		System.err.println("filename "+filename);
		System.err.println(path);
		System.err.println(dir);
		
		
		
		// file to be encrypted
		FileInputStream inFile = null;
		try {
			System.out.println("Encrypting...");
			inFile = new FileInputStream(path);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// encrypted file
		FileOutputStream outFile = null;
		try {
			outFile = new FileOutputStream(dir+"\\"+filename+".des");
			System.out.println(dir+"\\"+filename+".des");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// password to encrypt the file
		String password = "javapapers";

		// password, iv and salt should be transferred to the other end
		// in a secure manner

		// salt is used for encoding
		// writing it to a file
		// salt should be transferred to the recipient securely
		// for decryption
		byte[] salt = new byte[8];
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.nextBytes(salt);
		FileOutputStream saltOutFile;
		try {
			saltOutFile = new FileOutputStream(dir+"\\"+filename+".enc");
			System.out.println(dir+"salt.enc");
			saltOutFile.write(salt);
			saltOutFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		SecretKeyFactory factory;
		try {
			factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			
			KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536,
					256);
			SecretKey secretKey = factory.generateSecret(keySpec);
			SecretKey secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
			
			//
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secret);
			AlgorithmParameters params = cipher.getParameters();
			

			// iv adds randomness to the text and just makes the mechanism more
			// secure
			// used while initializing the cipher
			// file to store the iv
			FileOutputStream ivOutFile = new FileOutputStream(dir+"\\"+filename+"_iv.enc");
			byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
			ivOutFile.write(iv);
			ivOutFile.close();

			//file encryption
			byte[] input = new byte[64];
			int bytesRead;

			while ((bytesRead = inFile.read(input)) != -1) {
				byte[] output = cipher.update(input, 0, bytesRead);
				if (output != null)
					outFile.write(output);
			}

			byte[] output = cipher.doFinal();
			if (output != null)
				outFile.write(output);

			inFile.close();
			outFile.flush();
			outFile.close();

			
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidParameterSpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	

		System.out.println("File Encrypted.");
	}
	
	public void Decrypt(String path,String filename,String dir){

		
		filename = filename.substring(0,filename.indexOf('.'));
		
		System.err.println("name: "+filename);
		System.err.println("path: "+path);
		System.err.println("dir: "+dir);
		
		
		String password = "javapapers";

		// reading the salt
		// user should have secure mechanism to transfer the
		// salt, iv and password to the recipient
		FileInputStream saltFis;
		try {
			saltFis = new FileInputStream(filename+".enc");
			byte[] salt = new byte[8];
			saltFis.read(salt);
			saltFis.close();
			// reading the iv
			FileInputStream ivFis = new FileInputStream(filename+"_iv.enc");
			byte[] iv = new byte[16];
			ivFis.read(iv);
			ivFis.close();

			SecretKeyFactory factory = SecretKeyFactory
					.getInstance("PBKDF2WithHmacSHA1");
			KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536,
					256);
			SecretKey tmp = factory.generateSecret(keySpec);
			SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");

			// file decryption
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));
			System.out.println("path: "+path);
			FileInputStream fis = new FileInputStream(filename+".des");
			FileOutputStream fos = new FileOutputStream(filename+"_decrypted");
			byte[] in = new byte[64];
			int read;
			while ((read = fis.read(in)) != -1) {
				byte[] output = cipher.update(in, 0, read);
				if (output != null)
					fos.write(output);
			}

			byte[] output = cipher.doFinal();
			if (output != null)
				fos.write(output);
			fis.close();
			fos.flush();
			fos.close();
			System.out.println("File Decrypted.");
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
	}
	
	public String chooseFolder() {
		String path = null;
		
		JFileChooser f = new JFileChooser();
		f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int result = f.showSaveDialog(null);
		
		System.out.println(f.getSelectedFile());

		switch (result) {
		case JFileChooser.APPROVE_OPTION:
			System.out.println("Approve (Open or Save) was clicked");
			path = f.getSelectedFile().toString();
			return path;
		case JFileChooser.CANCEL_OPTION:
			System.out.println("Cancel or the close-dialog icon was clicked");

			path = "";
			break;
		case JFileChooser.ERROR_OPTION:
			System.out.println("Error");

			path = "";
			break;
		}
		return path;
		
		
	}
	
}

	
