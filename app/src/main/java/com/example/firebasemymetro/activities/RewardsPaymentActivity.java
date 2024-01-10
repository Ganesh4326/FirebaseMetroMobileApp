package com.example.firebasemymetro.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.firebasemymetro.R;
import com.example.firebasemymetro.UserManager.UserManager;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class RewardsPaymentActivity extends AppCompatActivity {

    Button payBtn;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards_payment);

        payBtn = findViewById(R.id.pay);

        UserManager userManager = new UserManager(RewardsPaymentActivity.this);

        Intent intent = getIntent();
        String reward_id = intent.getStringExtra("reward_id");
        Log.d("ID", reward_id);

        CollectionReference usersCollection = db.collection("users");

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usersCollection.document(userManager.getUserId())
                        .update("coupons", FieldValue.arrayUnion(reward_id))
                        .addOnSuccessListener(aVoid -> {
                            Log.d("TAG", "Coupon ID added to user's coupons successfully!");
                        })
                        .addOnFailureListener(e -> {
                            Log.e("TAG", "Error adding coupon ID to user's coupons", e);
                        });
            }
        });
    }
}