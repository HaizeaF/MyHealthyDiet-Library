package cryptography;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashMD5 {

    public static String hashText(String texto) {
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
        return hexadecimal(hashedText);
    }

    static String hexadecimal(byte[] hashedText) {
        char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        StringBuffer buf = new StringBuffer();
        for (int j = 0; j < hashedText.length; j++) {
            buf.append(hexDigit[(hashedText[j] >> 4) & 0x0f]);
            buf.append(hexDigit[hashedText[j] & 0x0f]);
        }
        return buf.toString();
    }

}
