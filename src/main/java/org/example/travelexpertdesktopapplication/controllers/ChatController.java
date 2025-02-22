package org.example.travelexpertdesktopapplication.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.travelexpertdesktopapplication.services.WebSocketService;
import org.tinylog.Logger;

public class ChatController {
    @FXML
    private TextArea chatArea;
    @FXML
    private TextField messageField;

    private WebSocketService webSocketService;

    public void initialize() {
        webSocketService = new WebSocketService("ws://localhost:8080/gs-guide-websocket", this::updateChat);
    }

    @FXML
    private void sendMessage() {
        String message = messageField.getText();
        if (!message.isEmpty()) {
            webSocketService.sendMessage(message);
            messageField.clear();
        }
    }

    private void updateChat(String message) {
        Logger.info("Received message: {}", message);
        chatArea.appendText(message + "\n");
    }
}
