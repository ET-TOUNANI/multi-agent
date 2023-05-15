import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.ControllerException;
import jade.core.Runtime;

public class MainContainer {
    public static void main(String[] args) throws ControllerException {
        Runtime runtime=Runtime.instance();
        ProfileImpl profile=new ProfileImpl();
        profile.setParameter("gui","true");
        AgentContainer agentContainer=runtime.createMainContainer(profile);
        agentContainer.start();
    }
}
