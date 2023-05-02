package org.example.containers;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.agents.ConsumerAgent;
import org.example.containers.model.ConversationView;

public class AgentClientGui extends Application {
    private ConsumerAgent consumerAgent;
    ConversationView conversationView;
    static Stage stage;
    String player="Abderrahmane";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        startContainer();
        conversationView = new ConversationView(player,consumerAgent);
        stage = primaryStage;
        Scene scene = new Scene(conversationView);
        stage.setTitle(player);
        stage.setScene(scene);
        stage.show();
    }
    public void startContainer() throws StaleProxyException {
        Runtime runtime=Runtime.instance();
        ProfileImpl profile=new ProfileImpl();
        profile.setParameter(Profile.MAIN_HOST,"localhost");
        AgentContainer agentContainer=runtime.createAgentContainer(profile);
        AgentController agentController=agentContainer.createNewAgent("Consumer","org.example.agents.ConsumerAgent",new Object[]{this});
        agentController.start();
    }

    public ConversationView getConversationView() {
        return conversationView;
    }

    public void setConsumerAgent(ConsumerAgent consumerAgent) {
        this.consumerAgent = consumerAgent;
    }
}
