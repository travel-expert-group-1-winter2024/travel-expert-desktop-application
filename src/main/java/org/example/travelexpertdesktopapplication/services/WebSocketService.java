package org.example.travelexpertdesktopapplication.services;

import org.example.travelexpertdesktopapplication.models.ChatMessage;
import org.example.travelexpertdesktopapplication.models.Greeting;
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
                stompSession = session;
                Logger.info("Connected to WebSocket server");
                stompSession.subscribe("/topic/greetings", new StompFrameHandler() {
                    @Override
                    public Type getPayloadType(StompHeaders headers) {
                        return Greeting.class;  // Specify message type
                    }

                    @Override
                    public void handleFrame(StompHeaders headers, Object payload) {
                        if (payload instanceof Greeting) {
                            Greeting greeting = (Greeting) payload;
                            Logger.info("Received message from server: " + greeting.getContent());
                            if (onMessageReceived != null) {
                                onMessageReceived.accept(greeting.getContent());
                            }
                        } else {
                            Logger.error("Unexpected payload type: " + payload.getClass());
                        }
                    }
                });
            }

            @Override
            public void handleTransportError(StompSession session, Throwable exception) {
                Logger.error(exception, "Error occurred while connecting to the server");
            }
        });
    }

    public void sendMessage(String message) {
        if (stompSession != null && stompSession.isConnected()) {
            stompSession.send("/app/hello", new ChatMessage(message));
        }
    }
}
