package org.example.travelexpertdesktopapplication.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ChatMessage {
    private String senderId;
    private String receiverId;
    private String content;

    // Required default constructor for Jackson deserialization
    public ChatMessage() {
    }

    @JsonCreator
    public ChatMessage(
            @JsonProperty("senderId") String senderId,
            @JsonProperty("receiverId") String receiverId,
            @JsonProperty("content") String content) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
    }

    public String getSenderId() { return senderId; }
    public void setSenderId(String senderId) { this.senderId = senderId; }

    public String getReceiverId() { return receiverId; }
    public void setReceiverId(String receiverId) { this.receiverId = receiverId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
