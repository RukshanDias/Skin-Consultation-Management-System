package UI;

import Console.Consultation;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class Encryptor {
    private static SecretKeySpec secretKey;

    public Encryptor(Consultation consultation) {
        String key = generateKey();
        setKey(key, consultation);
    }
    public static void setKey(final String myKey, Consultation consultation) {
        MessageDigest sha;
        try {
            byte[] key = myKey.getBytes(StandardCharsets.UTF_8);
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
            consultation.setSecretKey(secretKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to encrypt data
     * @param data - String data that want to encrypt
     * @return encrypted String message
     */
    public static String encryptData(String data){
        String encryptedMsg = "";
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            encryptedMsg = Base64.getEncoder()
                    .encodeToString(cipher.doFinal(data.getBytes(StandardCharsets.UTF_8)));

        }catch (Exception e){
            System.out.println("Error occur when encrypting data "+e);
        }
        return encryptedMsg;
    }

    /***
     * This method is used to decrypt encrypted message
     * @param encryptedMessage - encrypted message
     * @param key - key that used to encrypt
     * @return decrypted String message
     */
    public static String decryptData(String encryptedMessage, SecretKeySpec key) {
        String decryptedMsg = "";
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);

            decryptedMsg = new String(cipher.doFinal(Base64.getDecoder()
                    .decode(encryptedMessage)));
            System.out.println(decryptedMsg);
        } catch (Exception e){
            System.out.println("Error occur when decrypting data "+e);
        }
        return decryptedMsg;
    }

    /***
     * This method is used to generate a secret key
     * @return secret key in String format
     */
    private static String generateKey(){
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            return keyGenerator.generateKey().toString();

        }catch (Exception e){
            System.out.println("Error occur when generating key");
        }
        return null;
    }

}
