package cryptography;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashMD5 {

    public String cifrarTexto(String texto) {
        MessageDigest messageDigest;
        byte hashedText[] = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            byte dataBytes[] = texto.getBytes(); 
            messageDigest.update(dataBytes);
            hashedText = messageDigest.digest(); 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hashedText.toString();
    }
   
}