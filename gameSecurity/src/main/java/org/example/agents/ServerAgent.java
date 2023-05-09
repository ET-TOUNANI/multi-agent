package org.example.agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import org.example.containers.AgentClientGui;

public class ServerAgent extends Agent {
    int number;
    @Override
    protected void setup() {
        number= (int) (Math.random()*100);
        ParallelBehaviour parallelBehaviour=new ParallelBehaviour();
        addBehaviour(parallelBehaviour);
        parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage message=receive();
                if (message!=null){
                    ACLMessage reply;
                    int value=checkValue(message.getContent());
                    if(value==0){
                        reply=new ACLMessage(ACLMessage.CONFIRM);
                        reply.setContent("Bravo le nombre magique est "+number);
                        reply.addReceiver(message.getSender());
                        send(reply);
                    }
                    else if(value==1) {
                        reply=new ACLMessage(ACLMessage.DISCONFIRM);
                        reply.setContent("Oops! en dessus du nombre magique");
                        reply.addReceiver(message.getSender());
                        send(reply);
                    }
                    else {
                        reply=new ACLMessage(ACLMessage.DISCONFIRM);
                        reply.setContent("Oops! au-dessous du nombre magique ");
                        reply.addReceiver(message.getSender());
                        send(reply);
                    }
                }
                else block();
            }
        });
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
    public int checkValue(String value){
        int val=Integer.parseInt(value);
        if(val==number)
            return 0;   // 0 good
        else if (val<number) {
            return -1; // -1 au-dessous
        }
        else return 1;


    }

}
