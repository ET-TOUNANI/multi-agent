import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

public class SellerAgent extends Agent {
    private String price;
    @Override
    protected void setup() {
        price=(String) getArguments()[0];
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                ParallelBehaviour parallelBehaviour= new ParallelBehaviour();
                DFAgentDescription dfAgentDescription = new DFAgentDescription();
                ServiceDescription serviceDescription = new ServiceDescription();

                //Définir le service à etre publié ;
                serviceDescription.setName("HP");
                serviceDescription.setType("PC");
                //Agouter le service à notre df
                dfAgentDescription.addServices(serviceDescription);
                try {
                    //Publier le service
                    DFService.register(this.getAgent() , dfAgentDescription);
                } catch (FIPAException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage receierMessage = receive();
                if (receierMessage!=null)
                {
                    switch (receierMessage.getPerformative())
                    {
                        case ACLMessage.CFP : {
                            ACLMessage aclMessage = new ACLMessage(ACLMessage.PROPOSE);
                            aclMessage.setContent(price);
                            aclMessage.addReceiver(receierMessage.getSender());
                            send(aclMessage);
                            break;
                        }
                        case ACLMessage.ACCEPT_PROPOSAL: {
                            ACLMessage aclMessage1 = new ACLMessage(ACLMessage.AGREE);
                            aclMessage1.setContent("I can sell you the pc");
                            aclMessage1.addReceiver(receierMessage.getSender());
                            send(aclMessage1);
                            break;
                        }
                        case ACLMessage.REQUEST: {
                            ACLMessage aclMessage2 = new ACLMessage(ACLMessage.CONFIRM);
                            aclMessage2.setContent(" I will send you the pc !!");
                            aclMessage2.addReceiver(receierMessage.getSender());
                            send(aclMessage2);
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

    @Override
    protected void takeDown() {
        try {
            DFService.deregister(this);
        }catch (FIPAException e){
            e.printStackTrace();
        }
    }
}
