package com.example.firebasemymetro.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.firebasemymetro.R;
import com.example.firebasemymetro.UserManager.UserManager;
import com.example.firebasemymetro.UserRewardsAdapter;
import com.example.firebasemymetro.models.RewardsData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UserRewardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserRewardsAdapter rewardAdapter;
    private List<RewardsData> rewardList = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    TextView coins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reward);

        coins = findViewById(R.id.coins);
        UserManager userManager = new UserManager(this);

//        TextView textView = (TextView) this.findViewById(R.id.scrollingText);
//        textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
//        textView.setText("General Information... general information... General Information");
//        textView.setSelected(true);
//        textView.setSingleLine(true);

        coins.setText(userManager.getUserCoins());

        recyclerView = findViewById(R.id.rewardsRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        rewardAdapter = new UserRewardsAdapter(rewardList);
        recyclerView.setAdapter(rewardAdapter);

        fetchRewardsData(userManager.getUserId());
    }

    private void fetchRewardsData(String userId) {
        db.collection("rewards")
                .whereEqualTo("user_id", userId)
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