package org.example.aes;

import jade.wrapper.AgentContainer;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.ControllerException;

public class Main {
    public static void main(String[] args) throws ControllerException {
        Runtime runtime=Runtime.instance();
        ProfileImpl profile=new ProfileImpl();
        profile.setParameter("gui","true");
       AgentContainer mainContainer = runtime.createMainContainer(profile);
       mainContainer.start();

    }
}