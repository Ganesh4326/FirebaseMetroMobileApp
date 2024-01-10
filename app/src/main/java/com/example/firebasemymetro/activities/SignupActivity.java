package com.example.firebasemymetro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firebasemymetro.R;
import com.example.firebasemymetro.UserManager.UserManager;
import com.example.firebasemymetro.models.UserData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SignupActivity extends AppCompatActivity {

    int l = 0;

    Button submit;
    EditText email;
    EditText password;
    EditText username;

    FirebaseAuth auth;
    SignupActivity binding;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference usersCollection = db.collection("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        submit = findViewById(R.id.SignupButton);
        email = findViewById(R.id.emailEditText);
        password = findViewById(R.id.passwordEditText);
        username = findViewById(R.id.usernameText);

        auth = FirebaseAuth.getInstance();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();
                String usernameText = username.getText().toString();

                List<String> sampleCoupons = Arrays.asList();
                List<String> sampleBookings = Arrays.asList();

                UserData user = new UserData();
                user.setUser_id(generateUniqueUserId());
                user.setUser_name(username.getText().toString());
                user.setEmail_id(email.getText().toString());
                user.setPassword(password.getText().toString());
                user.setNo_of_coins(5);
                user.setCoupons(sampleCoupons);
                user.setBookings(sampleBookings);

                Map<String, Object> userDataMap = user.toMap();

                if (isEmailValid(user.getEmail_id()) && isPasswordValid(user.getPassword())) {
                    usersCollection.document(user.getUser_id()).set(userDataMap)
                            .addOnSuccessListener(aVoid -> {
                                Log.d("TAG", "User added successfully!");
                                UserManager userManager = new UserManager(SignupActivity.this);
                                userManager.saveUserData(user.getUser_id(), user.getUser_name(), user.getNo_of_coins());
                                Toast.makeText(SignupActivity.this, "Signup successful for " + userManager.getUserName(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignupActivity.this, HomeActivity.class);
                                startActivity(intent);
                            })
                            .addOnFailureListener(e -> {
                                Log.e("TAG", "Error adding user", e);
                            });
                }
            }
        });
    }

    private String generateUniqueUserId() {
        return UUID.randomUUID().toString();
    }

    private boolean isEmailValid(String email) {

        final boolean[] val = {false};

        db.collection("users")
                .whereEqualTo("email_id", email)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult() != null && !task.getResult().isEmpty()) {
                            val[0] = false;
                            Toast.makeText(SignupActivity.this, "Email is already registered", Toast.LENGTH_LONG).show();
                        } else {
                            Log.d("EMAIL ", "NOT");
                            l = 1;
                            val[0] = true;
                        }
                    } else {
                        Toast.makeText(SignupActivity.this, "Error checking email registration", Toast.LENGTH_LONG).show();
                        Log.e("TAG", "Error checking email registration", task.getException());
                    }
                });

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(SignupActivity.this, "Email is required", Toast.LENGTH_LONG).show();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(SignupActivity.this, "Invalid email", Toast.LENGTH_LONG).show();
            return false;
        }
        Log.d("RETURN ", String.valueOf(val[0]));
        if(l==1){
            return true;
        }
        return false;
//        return val[0];
    }

    private boolean isPasswordValid(String password) {
        Log.d("In ", "PAD");
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(SignupActivity.this, "Password is required", Toast.LENGTH_LONG).show();
            return false;
        } else if (password.length() < 6) {
            Toast.makeText(SignupActivity.this, "Password must be at least 6 characters", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}

