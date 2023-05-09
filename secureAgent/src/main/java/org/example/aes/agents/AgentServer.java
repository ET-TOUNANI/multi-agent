package org.example.aes.agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AgentServer extends Agent {

    @Override
    protected void setup() {
        String password= (String) getArguments()[0];
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage message=receive();
                if(message!=null){
                    String encryptEncodMsg=message.getContent();
                    byte[] encruptMsg= Base64.getDecoder().decode(encryptEncodMsg);
                    SecretKey secretKey=new SecretKeySpec(password.getBytes(),"AES");
                    try {
                        Cipher cipher=Cipher.getInstance("AES");
                        cipher.init(Cipher.DECRYPT_MODE,secretKey);
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
