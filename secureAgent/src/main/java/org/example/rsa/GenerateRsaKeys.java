package org.example.rsa;

import java.security.*;
import java.util.Base64;

public class GenerateRsaKeys {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize( 512);
        KeyPair keyPair=keyPairGenerator.generateKeyPair();
        PrivateKey privateKey=keyPair.getPrivate();
        PublicKey publicKey=keyPair.getPublic();
        String encodePRK= Base64.getEncoder().encodeToString(privateKey.getEncoded());
        String encodePbK= Base64.getEncoder().encodeToString(publicKey.getEncoded());
        System.out.println("**********private*********");
        System.out.println(encodePRK);
        System.out.println("*********public**********");
        System.out.println(encodePbK);
        System.out.println("*******************");
    }
}
