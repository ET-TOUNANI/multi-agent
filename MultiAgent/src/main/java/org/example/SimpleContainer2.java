package org.example;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class SimpleContainer2 {
    public static void main(String[] args) throws StaleProxyException {
        Runtime runtime=Runtime.instance();
        ProfileImpl profile=new ProfileImpl();
        profile.setParameter("host","localhost");
        AgentContainer container =runtime.createAgentContainer(profile);
        AgentController agentClient=container.createNewAgent("server","org.example.agents.AgentServer",new  Object[]{});
        agentClient.start();
    }
}
