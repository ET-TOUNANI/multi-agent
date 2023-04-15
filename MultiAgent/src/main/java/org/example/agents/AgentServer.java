package org.example.agents;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public class AgentServer extends Agent {
    @Override
    protected void setup() {
        System.out.println("setup");
        /*ACLMessage receive =receive();
        System.out.println(receive.getContent());*/
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
