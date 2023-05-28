package ma.enset.bddc;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;

import java.util.concurrent.TimeUnit;

public class SimpleContainer {
    public static void main(String[] args) throws ControllerException {
        Runtime runtime=Runtime.instance();
        ProfileImpl profile=new ProfileImpl();
        profile.setParameter(Profile.MAIN_HOST,"localhost");
        AgentContainer agentContainer=runtime.createAgentContainer(profile);

        AgentController master=agentContainer.createNewAgent("master", MasterAgent.class.getName(),new Object[]{});
        master.start();

        for (int i = 1; i < 5; i++) {
            AgentController island=agentContainer.createNewAgent("island"+i, IslandAgent.class.getName(),new Object[]{});
            island.start();
        }
    }
}
