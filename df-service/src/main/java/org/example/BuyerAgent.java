import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

public class BuyerAgent extends Agent {
     /*
            Chercher les services
            Contacter le service souhaité
    */
    DFAgentDescription[] dfAgentDescriptions;
    private AID bestSaller;
    private double bestPrice=Double.MAX_VALUE;
    private int cpt=0;

    @Override
    protected void setup() {
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                DFAgentDescription dfAgentDescription = new DFAgentDescription();
                ServiceDescription serviceDescription = new ServiceDescription();
                serviceDescription.setType("PC");
                dfAgentDescription.addServices(serviceDescription);
                try {
                    dfAgentDescriptions = DFService.search(this.getAgent(), dfAgentDescription);
                    for (DFAgentDescription dfAD1:dfAgentDescriptions)
                    {
                        AID sallerID=dfAD1.getName();
                        ACLMessage aclMessage=new ACLMessage(ACLMessage.CFP);
                        aclMessage.setContent("can give the price of PCS");
                        aclMessage.addReceiver(sallerID);
                        send(aclMessage);
                    }
                } catch (FIPAException e) {
                   e.printStackTrace();
                }
            }
        });

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage receierMessage = receive();
                if (receierMessage!=null) {
                    switch (receierMessage.getPerformative()) {
                        case ACLMessage.PROPOSE: {
                            cpt++;
                            double price = Double.parseDouble(receierMessage.getContent());
                            if (price < bestPrice) {
                                bestPrice = price;
                                bestSaller = receierMessage.getSender();
                            }
                            if (dfAgentDescriptions.length == cpt) {
                                ACLMessage aclMessage = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                                aclMessage.addReceiver(bestSaller);
                                aclMessage.setContent(" I accept your proposed price!!");
                                send(aclMessage);
                            }
                            break;
                        }
                        case  ACLMessage.AGREE: {
                            ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
                            aclMessage.setContent(" I want the sell the product");
                            aclMessage.addReceiver(receierMessage.getSender());
                            send(aclMessage);
                            break;
                        }
                        case  ACLMessage.CONFIRM: {
                            System.out.println("Le nom de Seller " + receierMessage.getSender().getLocalName() + " Prix :" + receierMessage.getContent());
                            break;
                        }
                    }
                }
                else  {
                    block();
                }
            }
        });
    }
}
