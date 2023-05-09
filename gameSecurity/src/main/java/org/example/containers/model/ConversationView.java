package org.example.containers.model;

import jade.gui.GuiEvent;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.example.agents.ConsumerAgent;

public class ConversationView extends VBox {
    private ConsumerAgent consumerAgent;
    private String conversationPartner;
    private ObservableList<Node> speechBubbles = FXCollections.observableArrayList();

    private Label contactHeader;
    private ScrollPane messageScroller;
    private VBox messageContainer;
    private HBox inputContainer;

    public ConversationView(String conversationPartner,  ConsumerAgent consumerAgent){
        super(5);
        this.conversationPartner = conversationPartner;
        this.consumerAgent=consumerAgent;
        setupElements();
    }

    private void setupElements(){
        setupContactHeader();
        setupMessageDisplay();
        setupInputDisplay();
        getChildren().setAll(contactHeader, messageScroller, inputContainer);
        setPadding(new Insets(5));
    }

    private void setupContactHeader(){
        contactHeader = new Label(conversationPartner);
        contactHeader.setAlignment(Pos.CENTER);
        contactHeader.setFont(Font.font("Comic Sans MS", 14));
    }

    private void setupMessageDisplay(){
        messageContainer = new VBox(5);
        Bindings.bindContentBidirectional(speechBubbles, messageContainer.getChildren());

        messageScroller = new ScrollPane(messageContainer);
        messageScroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        messageScroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        messageScroller.setPrefHeight(300);
        messageScroller.prefWidthProperty().bind(messageContainer.prefWidthProperty().subtract(5));
        messageScroller.setFitToWidth(true);
        //Make the scroller scroll to the bottom when a new message is added
        speechBubbles.addListener((ListChangeListener<Node>) change -> {
            while (change.next()) {
                if(change.wasAdded()){
                    messageScroller.setVvalue(messageScroller.getVmax());
                }
            }
        });
    }

    private void setupInputDisplay(){
        inputContainer = new HBox(5);

        TextField userInput = new TextField();
        userInput.setPromptText("Enter message");

        Button sendMessageButton = new Button("Send");
        sendMessageButton.disableProperty().bind(userInput.lengthProperty().isEqualTo(0));
        sendMessageButton.setOnAction(event-> {
            GuiEvent guiEvent=new GuiEvent(this,1);
            guiEvent.addParameter(userInput.getText());
            consumerAgent.onGuiEvent(guiEvent);
            sendMessage(userInput.getText());
            userInput.setText("");
        });
        inputContainer.getChildren().setAll(userInput, sendMessageButton);
    }

    public void sendMessage(String message){
        speechBubbles.add(new SpeechBox(message, SpeechDirection.RIGHT));
    }

    public void receiveMessage(String message){
        speechBubbles.add(new SpeechBox(message, SpeechDirection.LEFT));
    }
}