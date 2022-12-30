package UI;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class Encryptor {
    private String secret = "ddsdasda";
    private SecretKey key1;
    private static SecretKeySpec secretKey;
    private static byte[] key;

    public void setKey(final String myKey) {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes(StandardCharsets.UTF_8);
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    public String encryptData(String data){
        String encryptedMsg = "";
        generateKey();
        try {
            setKey(secret);
//            generateKey();
            byte[] message = convertToBytes(data);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            encryptedMsg = Base64.getEncoder()
                    .encodeToString(cipher.doFinal(data.getBytes(StandardCharsets.UTF_8)));

//            byte[] messageInBytes = cipher.doFinal(message);
//            encryptedMsg = convertToString(messageInBytes);
            System.out.println("encrypt -> "+encryptedMsg);
        }catch (Exception e){
            System.out.println("Error occur when encrypting data "+e);
        }
        return encryptedMsg;
    }

    public String decryptData(String encryptedMessage) {
        String decryptedMsg = "";
        try {
            setKey(secret);
//            byte[] encryptMsg = convertToBytes(encryptedMessage);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            decryptedMsg = new String(cipher.doFinal(Base64.getDecoder()
                    .decode(encryptedMessage)));
//            byte[] messageInBytes = cipher.doFinal(encryptMsg);
//            decryptedMsg = convertToString(messageInBytes);
            System.out.println("decrypt -> "+decryptedMsg);
        } catch (Exception e){
            System.out.println("Error occur when decrypting data "+e);
        }
        return decryptedMsg;
    }

    private void generateKey(){
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            this.secret = keyGenerator.generateKey().toString();
            System.out.println("secretkey -> "+this.secret);
        }catch (Exception e){
            System.out.println("Error occur when generating key");
        }
    }
    private String convertToString(byte[] data) {
        return new String(data);
    }

    private byte[] convertToBytes(String data) {
        return data.getBytes();
    }
}
