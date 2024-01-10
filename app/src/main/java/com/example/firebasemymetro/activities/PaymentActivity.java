package com.example.firebasemymetro.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firebasemymetro.R;
import com.example.firebasemymetro.models.TicketData;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class PaymentActivity extends AppCompatActivity {

    Button btn;
    FirebaseFirestore db;
    private CollectionReference ticketsCollection;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        btn = findViewById(R.id.pay);

        Intent intent = getIntent();
        String reward_id = intent.getStringExtra("reward_id");
        TicketData ticketData = (TicketData) intent.getSerializableExtra("ticketData");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = FirebaseFirestore.getInstance();
                ticketsCollection = db.collection("ticket_data");
                addTicketData(ticketData);
            }
        });
    }

    public void addTicketData(TicketData ticketData) {
        // Generate a unique ID for the ticket
        String ticketId = ticketsCollection.document().getId();

        ticketData.setTicket_id(ticketId);
        Log.d("IN", "TICKET DATA");

        ticketsCollection.document(ticketId)
                .set(ticketData)
                .addOnSuccessListener(documentReference -> {
                    Log.d("IN ", "SUCCESS");
                    Intent i = new Intent(PaymentActivity.this, PaymentSuccessActivity.class);
                    startActivity(i);
                })
                .addOnFailureListener(e -> {
                    Log.d("in failure", e.getMessage());
                });
    }

}