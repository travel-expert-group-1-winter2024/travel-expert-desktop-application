package org.example.travelexpertdesktopapplication.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.example.travelexpertdesktopapplication.auth.SessionManager;
import org.example.travelexpertdesktopapplication.models.ChatMessage;
import org.example.travelexpertdesktopapplication.services.WebSocketService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ChatController {
    @FXML
    private TextArea chatArea;
    @FXML
    private TextField messageField;
    @FXML
    private ListView<String> customerListView;

    private WebSocketService webSocketService;
    private final Map<String, StringBuilder> chatRooms = new HashMap<>();  // stores chat history per customer
    private String selectedCustomerId = null;

    public void initialize() {
        webSocketService = new WebSocketService("ws://localhost:8080/chat", this::updateChat);
        customerListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                selectedCustomerId = newVal;
                displayChatHistory(selectedCustomerId);
            }
        });
    }

    @FXML
    private void sendMessage() {
        String message = messageField.getText();
        webSocketService.sendMessage(selectedCustomerId, message);

        String formattedMessage = formatMessage("Me", message);

        chatRooms.putIfAbsent(selectedCustomerId, new StringBuilder());
        chatRooms.get(selectedCustomerId).append(formattedMessage).append("\n");

        displayChatHistory(selectedCustomerId);
        messageField.clear();
    }

    private void updateChat(ChatMessage message) {
        Platform.runLater(() -> {
            String senderLabel = message.getSenderId().equals(SessionManager.getInstance().getUser().getId().toString())
                    ? "Me" : message.getSenderId();

            String formattedMessage = formatMessage(senderLabel, message.getContent());

            chatRooms.putIfAbsent(message.getSenderId(), new StringBuilder());
            chatRooms.get(message.getSenderId()).append(formattedMessage).append("\n");

            if (message.getSenderId().equals(selectedCustomerId)) {
                displayChatHistory(message.getSenderId());
            }

            if (!customerListView.getItems().contains(message.getSenderId())) {
                customerListView.getItems().add(message.getSenderId());
            }
        });
    }

    private String formatMessage(String senderLabel, String content) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return String.format("[%s] [%s]: %s", now.format(formatter), senderLabel, content);
    }

    private void displayChatHistory(String customerId) {
        chatArea.clear();
        chatArea.appendText(chatRooms.getOrDefault(customerId, new StringBuilder()).toString());
    }
}
