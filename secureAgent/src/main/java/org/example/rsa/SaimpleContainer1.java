package org.example.rsa;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class SaimpleContainer1 {
    public static void main(String[] args) throws StaleProxyException {
        Runtime runtime=Runtime.instance();
        ProfileImpl profile=new ProfileImpl();
        profile.setParameter("host","localhost");
        AgentContainer container =runtime.createAgentContainer(profile);
        String pbKey="MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIdwRUDZtqd7XA0/Mje3JJxMO53r9a/HHk1ZeqLQzEsIqoSIffU7KCTGEDilz26TTjsTJ8KkcyXv4F6/DDbf4bsCAwEAAQ==";
        AgentController agentClient=container.createNewAgent("client","org.example.rsa.agents.AgentClient",new  Object[]{pbKey});
        agentClient.start();
    }

}
