package com.example.firebasemymetro.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.firebasemymetro.R;
import com.example.firebasemymetro.TicketAdapter;
import com.example.firebasemymetro.UserManager.UserManager;
import com.example.firebasemymetro.models.TicketData;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class HistoryActivity extends AppCompatActivity {

    private TicketAdapter adapter;
    private List<TicketData> ticketList = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        setUpRecyclerView();
        UserManager userManager = new UserManager(this);
        loadTicketData(userManager.getUserId());
    }

    private void setUpRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerViewTicket);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new TicketAdapter(ticketList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((ticketData, position) -> {
            // Handle item click if needed
        });
    }

    private void loadTicketData(String userId) {
        db.collection("ticket_data")
                .whereEqualTo("user_id", userId) // replace "userId" with the actual field name in TicketData
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        ticketList.clear();
                        for (DocumentSnapshot document : task.getResult()) {
                            TicketData ticketData = document.toObject(TicketData.class);
                            Log.d("TAG", "Document data: " + document.getData());
                            if (ticketData != null) {
                                ticketList.add(ticketData);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        // Handle the error
                    }
                });
    }

}