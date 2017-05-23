
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * A utility class that encrypts or decrypts a file.
 * 
 * @author www.codejava.net
 *
 */
public class Encryption {
	private static final String ALGORITHM = "AES";
	private static final String TRANSFORMATION = "AES";

	public static void encryptFile(String key, File inputFile, File outputFile) throws CryptoException {
		cryptFile(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
	}

	public static void decryptFile(String key, File inputFile, File outputFile) throws CryptoException {
		cryptFile(Cipher.DECRYPT_MODE, key, inputFile, outputFile);
	}
	
	private static void cryptFile(int cipherMode, String key, File inputFile, File outputFile) throws CryptoException {
		try {
			Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(cipherMode, secretKey);

			FileInputStream inputStream = new FileInputStream(inputFile);
			byte[] inputBytes = new byte[(int) inputFile.length()];
			inputStream.read(inputBytes);

			byte[] outputBytes = cipher.doFinal(inputBytes);

			FileOutputStream outputStream = new FileOutputStream(outputFile);
			outputStream.write(outputBytes);

			inputStream.close();
			outputStream.close();

		} catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException
				| IllegalBlockSizeException | IOException ex) {
			throw new CryptoException("Error encrypting=", ex);
		}
	}

	public static String encryptString(String key, String input) throws CryptoException {
		return cryptString(Cipher.ENCRYPT_MODE, key, input);
	}

	public static String decryptString(String key, String input) throws CryptoException {
		return cryptString(Cipher.DECRYPT_MODE, key, input);
	}

	private static String cryptString(int cipherMode, String key, String input) throws CryptoException {
		try {
			Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(cipherMode, secretKey);

			byte[] inputBytes = cipher.doFinal(input.getBytes());

			String output = new String(inputBytes);
			return output;
		} catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException
				| IllegalBlockSizeException ex) {
			throw new CryptoException("Error encrypting=", ex);
		}
	}

}
