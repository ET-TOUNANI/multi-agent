package org.example.aes;

import jade.wrapper.AgentContainer;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class SaimpleContainer1 {
    public static void main(String[] args) throws StaleProxyException {
        Runtime runtime=Runtime.instance();
        ProfileImpl profile=new ProfileImpl();
        profile.setParameter("host","localhost");
        AgentContainer container =runtime.createAgentContainer(profile);
        String password="1234567812345678";
        AgentController agentClient=container.createNewAgent("client","org.example.aes.agents.AgentClient",new  Object[]{password});
        agentClient.start();
    }

}
