package cryptography;

import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class Asymmetric_KeyGenerator {

    public void generatePrivateKey() {

        KeyPairGenerator generator;
        try {
            generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(1024); 
            KeyPair keypair = generator.generateKeyPair();
            PublicKey publicKey = keypair.getPublic();
            PrivateKey privateKey = keypair.getPrivate();

            // Public
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
            FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\2dam\\Desktop\\reto2\\MyHealthyDiet-Client\\src\\businessLogic\\Public.key");
            fileOutputStream.write(x509EncodedKeySpec.getEncoded());
            fileOutputStream.close();

            // Private
            PKCS8EncodedKeySpec pKCS8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
            fileOutputStream = new FileOutputStream("C:\\Users\\2dam\\Desktop\\reto2\\MyHealthyDiet-Server\\src\\java\\files\\Private.key");
            fileOutputStream.write(pKCS8EncodedKeySpec.getEncoded());
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        Asymmetric_KeyGenerator asimetricoRSA_KeyGenerator = new Asymmetric_KeyGenerator();
        asimetricoRSA_KeyGenerator.generatePrivateKey();
        System.out.println("Ficheros de Clave Generados!");
    }
}