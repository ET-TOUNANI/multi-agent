package org.example.agents;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import javafx.application.Platform;
import org.example.containers.AgentClientGui;

public class ConsumerAgent extends GuiAgent {
    private AgentClientGui clientGui;
    @Override
    protected void setup() {
        clientGui=(AgentClientGui) getArguments()[0];
        clientGui.setConsumerAgent(this);
        System.out.println("initialisation"+this.getAID().getName());
        ParallelBehaviour parallelBehaviour=new ParallelBehaviour();
        addBehaviour(parallelBehaviour);
        parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage message=receive();
                if(message!=null){
                    System.out.println("Sender: "+message.getSender().getLocalName());
                    System.out.println("Content: "+message.getContent());
                    Platform.runLater(() -> {
                        clientGui.getConversationView().receiveMessage(message.getContent());
                    });
                    System.out.println("SpeechAct: "+ACLMessage.getPerformative(message.getPerformative()));
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

    @Override
    public void onGuiEvent(GuiEvent guiEvent) {
        String msg= (String) guiEvent.getParameter(0);
        ACLMessage message=new ACLMessage(ACLMessage.INFORM);
        message.addReceiver(new AID("Server",AID.ISLOCALNAME));
        message.setContent(msg);
        send(message);
    }
}
