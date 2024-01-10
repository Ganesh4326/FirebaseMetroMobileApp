package com.example.firebasemymetro.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasemymetro.R;
import com.example.firebasemymetro.UserManager.UserManager;
import com.example.firebasemymetro.adapters.RewardsAdapter;
import com.example.firebasemymetro.models.RewardsData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class RewardsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RewardsAdapter rewardAdapter;
    private List<RewardsData> rewardList = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    TextView coins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);

        coins = findViewById(R.id.coins);
        UserManager userManager = new UserManager(this);

        coins.setText(userManager.getUserCoins());

        recyclerView = findViewById(R.id.rewardsRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        rewardAdapter = new RewardsAdapter(rewardList);
        recyclerView.setAdapter(rewardAdapter);

        fetchRewardsData();
    }

    private void fetchRewardsData() {
        db.collection("rewards")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            rewardList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                RewardsData rewards = document.toObject(RewardsData.class);
                                Log.d("TAG", "Document data: " + document.getData());
                                rewardList.add(rewards);
                            }
                            rewardAdapter.notifyDataSetChanged();
                        } else {
                            Log.e("RewardsActivity", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
