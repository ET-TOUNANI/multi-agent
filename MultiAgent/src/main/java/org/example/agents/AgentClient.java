package org.example.agents;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public class AgentClient extends Agent {
    @Override
    protected void setup() {
        System.out.println("setup");
        //String arg1=(String) getArguments()[0];
        //String arg2=(String) getArguments()[1];
       // System.out.println(arg1 +" "+arg2);
        /*ACLMessage message=new ACLMessage(ACLMessage.REQUEST);
        message.setContent("salam labas 3liiik");
        message.addReceiver(new AID("server",AID.ISLOCALNAME));
        send(message);*/
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
