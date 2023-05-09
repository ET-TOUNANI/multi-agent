package org.example.rsa.agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class AgentServer extends Agent {

    @Override
    protected void setup() {
        String encodePrk= (String) getArguments()[0];
        byte[] decoderPrk =Base64.getDecoder().decode(encodePrk);
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage message=receive();
                if(message!=null){
                    String encryptEncodMsg=message.getContent();
                    byte[] encruptMsg= Base64.getDecoder().decode(encryptEncodMsg);
                    try {
                        KeyFactory keyFactory=KeyFactory.getInstance("RSA");
                       PrivateKey privateKey= keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decoderPrk));

                        Cipher cipher=Cipher.getInstance("RSA");
                        cipher.init(Cipher.DECRYPT_MODE,privateKey);
                        byte[] decryptMessage=cipher.doFinal(encruptMsg);
                        System.out.println(new String(decryptMessage));
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }else block();
            }
        });
    }

    @Override
    protected void afterMove() {
        System.out.println("after move");
    }

    @Override
    protected void beforeMove() {
        System.out.println("beforeMove");
    }

    @Override
    public void doDelete() {
        System.out.println("delete");
    }
}
