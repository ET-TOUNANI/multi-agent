package org.example.agents;

import jade.core.Agent;

public class ConsumerAgent extends Agent {
    @Override
    protected void setup() {
        System.out.println("initialisation"+this.getAID().getName());
    }

    @Override
    protected void beforeMove() {
        System.out.println("beforeMove");
    }

    @Override
    protected void afterMove() {
        System.out.println("afterMove");
    }

    @Override
    protected void takeDown() {
        System.out.println("I am going to die");
    }
}
