package com.example.websocketapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.java_websocket.enums.ReadyState;

import java.net.URI;
import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SocketChatInterface {

    Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.laheta).setOnClickListener(this);


        try {
            client = new Client(new URI("ws://obscure-waters-98157.herokuapp.com"), this);
            client.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.laheta){
            if(client != null && client.getReadyState() == ReadyState.OPEN){
                EditText editText = findViewById(R.id.input);
                client.send(editText.getText().toString());
            }else{
                Toast.makeText(this,"Ei yhteytta, viesti ei kulje",Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    public void onMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView textView = findViewById(R.id.chatField);
                textView.setText(message);
                Log.d("websocket:", "Viesti kulkee");
            }
        });
    }

    @Override
    public void onStatusChange(final String newStatus) {

    }
}
