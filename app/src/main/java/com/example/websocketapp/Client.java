package com.example.websocketapp;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

interface SocketChatInterface{
    void onMessage(String message);
    void onStatusChange (String newStatus);
}

public class Client extends WebSocketClient {

    SocketChatInterface observer;

    public Client(URI serverUri, SocketChatInterface observer) {
        super(serverUri);
        this.observer = observer;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        observer.onStatusChange("Connection Open");
    }

    @Override
    public void onMessage(String message) {
        observer.onStatusChange("New Message: "+ message);
        observer.onMessage((message));
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        observer.onStatusChange("Connection closed:" + reason);
    }

    @Override
    public void onError(Exception ex) {
        observer.onStatusChange(("Error:"+ ex.toString()));
    }
}
