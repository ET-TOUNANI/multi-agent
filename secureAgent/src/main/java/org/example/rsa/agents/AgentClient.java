package org.example.rsa.agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class AgentClient extends Agent {
    @Override
    protected void setup() {
        String msg="hi fro enset";
        String encodePbkey=(String) getArguments()[0];
        byte[] decodePbkey=Base64.getDecoder().decode(encodePbkey);

        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                try {
                    KeyFactory keyFactory=KeyFactory.getInstance("RSA");
                    PublicKey publicKey=keyFactory.generatePublic(new X509EncodedKeySpec(decodePbkey));
                    Cipher cipher=Cipher.getInstance("RSA");
                    cipher.init(Cipher.ENCRYPT_MODE,publicKey);
                    byte[] encryptMsg=cipher.doFinal(msg.getBytes());
                    String encryptEncodMsg= Base64.getEncoder().encodeToString(encryptMsg);
                    ACLMessage aclMessage=new ACLMessage(ACLMessage.INFORM);
                    aclMessage.setContent(encryptEncodMsg);
                    aclMessage.addReceiver(new AID("server",AID.ISLOCALNAME));
                    send(aclMessage);
                }catch (Exception e){
                    e.printStackTrace();
                }
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
