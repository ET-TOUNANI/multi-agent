import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import jade.core.Runtime;

public class SimpleContainer {
    public static void main(String[] args) throws StaleProxyException {
        Runtime runtime=Runtime.instance();
        ProfileImpl profile=new ProfileImpl();
        profile.setParameter(ProfileImpl.MAIN_HOST,"localhost");
        AgentContainer container=runtime.createAgentContainer(profile);
        AgentController seller1=container.createNewAgent("seller1",SellerAgent.class.getName(),new Object[]{"13000"});
        AgentController seller2=container.createNewAgent("seller2",SellerAgent.class.getName(),new Object[]{"1500"});
        AgentController buyer=container.createNewAgent("buyer",BuyerAgent.class.getName(),new Object[]{});
        seller1.start();
        seller2.start();
        buyer.start();
    }
}
