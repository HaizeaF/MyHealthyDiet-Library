package cryptography;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.spec.KeySpec;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class Symmetric {
    static String sSalt = "abcdefghijklmnop";
    private static byte[] salt = sSalt.getBytes();
    
    public String cifrarTexto(String clave, String mensaje) {
        String ret = null;
        KeySpec keySpec = null;
        SecretKeyFactory secretKeyFactory = null;
        try {
            keySpec = new PBEKeySpec(clave.toCharArray(),salt,65536,128);
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] key = secretKeyFactory.generateSecret(keySpec).getEncoded();
            SecretKey privateKey = new SecretKeySpec(key, "AES"); 
            Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
            c.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] encodedMessage = c.doFinal(mensaje.getBytes());
            byte[] iv = c.getIV();
            byte[] combined = concatArrays(iv, encodedMessage);
            fileWriter("../files/SymmetricMSG.dat", combined);
            ret = new String(encodedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    private String descifrarTexto(String clave) {
        String ret = null;
        byte[] fileContent = fileReader("../files/SymmetricMSG.dat");
        KeySpec keySpec = null;
        SecretKeyFactory secretKeyFactory = null;
        try {
            keySpec = new PBEKeySpec(clave.toCharArray(),salt,65536,128);
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] key = secretKeyFactory.generateSecret(keySpec).getEncoded();
            SecretKey privateKey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); 
            IvParameterSpec ivParam = new IvParameterSpec(Arrays.copyOfRange(fileContent, 0, 16));
            cipher.init(Cipher.DECRYPT_MODE, privateKey, ivParam);
            byte[] decodedMessage = cipher.doFinal(Arrays.copyOfRange(fileContent, 16, fileContent.length));
            ret = new String(decodedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    private byte[] concatArrays(byte[] array1, byte[] array2) {
        byte[] ret = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, ret, 0, array1.length);
        System.arraycopy(array2, 0, ret, array1.length, array2.length);
        return ret;
    }

    private void fileWriter(String path, byte[] text) {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] fileReader(String path) {
        byte ret[] = null;
        File file = new File(path);
        try {
            ret = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }
}