package org.example.travelexpertdesktopapplication.services;

import org.example.travelexpertdesktopapplication.auth.SessionManager;
import org.example.travelexpertdesktopapplication.models.ChatMessage;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.tinylog.Logger;

import java.lang.reflect.Type;
import java.util.function.Consumer;

public class WebSocketService {
    private StompSession stompSession;
    private Consumer<ChatMessage> onMessageReceived;  // Callback for UI updates
    private final String userId;

    public WebSocketService(String serverUri,  Consumer<ChatMessage> onMessageReceived) {
        this.userId = SessionManager.getInstance().getUser().getId().toString();
        Logger.info("User ID: " + userId);
        this.onMessageReceived = onMessageReceived;
        connectToWebSocket(serverUri);
    }

    private void connectToWebSocket(String serverUri) {
        WebSocketClient client = new StandardWebSocketClient();

        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        stompClient.connect(serverUri, new StompSessionHandlerAdapter() {
            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                Logger.info("New session established : " + session.getSessionId());
                stompSession = session;
                String topic = "/user/" + userId + "/queue/messages";
                stompSession.subscribe(topic, this);
            }

            @Override
            public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
                Logger.error(exception, "Error occurred while connecting to the server");
            }

            @Override
            public Type getPayloadType(StompHeaders headers) {
                return ChatMessage.class;
            }


            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                if (payload instanceof ChatMessage) {
                    ChatMessage chatMessage = (ChatMessage) payload;
                    Logger.info("Received: " + chatMessage.getContent() + " from: " + chatMessage.getSenderId());
                    if (onMessageReceived != null) {
                        onMessageReceived.accept(chatMessage);
                    }
                } else {
                    Logger.error("Unexpected payload type: " + payload.getClass());
                }
            }
        });
    }

    public void sendMessage(String receiverId, String message) {
        if (stompSession != null && stompSession.isConnected()) {
            String destination = "/app/chat.send";
            Logger.info("Sending message from " + userId + " to " + receiverId);

            ChatMessage chatMessage = new ChatMessage(userId, receiverId, message);
            stompSession.send(destination, chatMessage);
        } else {
            Logger.warn("WebSocket session is not connected. Cannot send message.");
        }
    }
}
