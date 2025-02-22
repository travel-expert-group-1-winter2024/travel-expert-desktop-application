package org.example.travelexpertdesktopapplication.services;

import org.example.travelexpertdesktopapplication.models.Message;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.tinylog.Logger;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.function.Consumer;

public class WebSocketService {
    private StompSession stompSession;
    private Consumer<String> onMessageReceived;  // Callback for UI updates

    public WebSocketService(String serverUri, Consumer<String> onMessageReceived) {
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
                stompSession.subscribe("/topic/messages", this);
            }

            @Override
            public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
                Logger.error(exception, "Error occurred while connecting to the server");
            }

            @Override
            public Type getPayloadType(StompHeaders headers) {
                return Message.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                if (payload instanceof Message) {
                    Message msg = (Message) payload;
                    Logger.info("Received : " + msg.getText() + " from : " + msg.getFrom());
                    if (onMessageReceived != null) {
                        onMessageReceived.accept(createDisplayMessage(msg));
                    }
                } else {
                    Logger.error("Unexpected payload type: " + payload.getClass());
                }
            }
        });
    }

    public void sendMessage(String message) {
        if (stompSession != null && stompSession.isConnected()) {
            Message msg = new Message();
            msg.setFrom("Nicky"); // TODO: Replace with user name
            msg.setText(message);
            stompSession.send("/app/chat", msg);
            Logger.info("Message sent to websocket server");
        }
    }

    private String createDisplayMessage(Message message) {
        LocalDateTime now = LocalDateTime.now();
        return now.getHour() + ":" + now.getMinute() + " [" + message.getFrom() + "]: " + message.getText();
    }
}
