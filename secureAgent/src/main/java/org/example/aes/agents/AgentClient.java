package org.example.aes.agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AgentClient extends Agent {
    @Override
    protected void setup() {
        String msg="hi fro enset";
        String key=(String) getArguments()[0];
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                SecretKey secretKey=new SecretKeySpec(key.getBytes(),"AES");
                try {
                    Cipher cipher=Cipher.getInstance("AES");
                    cipher.init(Cipher.ENCRYPT_MODE,secretKey);
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
