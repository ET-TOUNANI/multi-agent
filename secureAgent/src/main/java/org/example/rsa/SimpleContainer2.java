package org.example.rsa;

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
        String prKey="MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEAh3BFQNm2p3tcDT8yN7cknEw7nev1r8ceTVl6otDMSwiqhIh99TsoJMYQOKXPbpNOOxMnwqRzJe/gXr8MNt/huwIDAQABAkAqwgT7DkCd61IKVJM0B7ulJGQF3UJ5NfGZFdCjqit03FSw4eEYySo/iyfBuZr0nnZq3UY6jSBg+fGtvcwXvesxAiEA66CTrL5f9QjuhvS039APSXVXmZC2hRE2Oyc5jh62p28CIQCTJhiTaibpDyeMq2NtstbuNk6iN4exFIXxPcOVPOJkdQIhALzNOSckkD0GgCJROBFSZL1msd+Rzj5HsV2qq3qOb6svAiBJMZ7x3NCEBzS+BRaNH3NHxJ2ylPqa+8FwcS+TUVivbQIhAKzEL0xD8+yPtDskVgcAJ0zx63MhYJusV8c8WkDjKwSZ";
        AgentController agentClient=container.createNewAgent("server","org.example.rsa.agents.AgentServer",new  Object[]{prKey});
        agentClient.start();
    }
}
