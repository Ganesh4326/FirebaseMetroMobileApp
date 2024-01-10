package com.example.firebasemymetro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firebasemymetro.R;
import com.example.firebasemymetro.UserManager.UserManager;
import com.example.firebasemymetro.models.UserData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth auth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    Button loginBtn;

    EditText emailText;
    EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView signupTextView = findViewById(R.id.signUpNav);
        loginBtn = findViewById(R.id.loginButton);
//        auth = FirebaseAuth.getInstance();
        emailText = findViewById(R.id.emailEdit);
        passwordText = findViewById(R.id.passwordEdit);

        signupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the SignupActivity
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailText.getText().toString();
                String password = passwordText.getText().toString();

//                auth.signInWithEmailAndPassword(email, password)
//                        .addOnCompleteListener(LoginActivity.this, task -> {
//                            if (task.isSuccessful()) {
//                                // Sign-in success
//                                FirebaseUser user = auth.getCurrentUser();
//                                Toast.makeText(LoginActivity.this, "Login successful.", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                                startActivity(intent);
//                            } else {
//                                // Sign-in failed
//                                Toast.makeText(LoginActivity.this, "Login failed... try again!.", Toast.LENGTH_SHORT).show();
//                            }
//                        });
                loginUser(email, password);
            }
        });
    }

    private void loginUser(String email, String password) {
        Log.d(email, password);
        db.collection("users")
                .whereEqualTo("email_id", email)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            for (QueryDocumentSnapshot document : querySnapshot) {
                                UserData userData = document.toObject(UserData.class);
                                if (userData.getPassword().equals(password)) {
                                    // Passwords match, login successful
                                    Log.d("SUCCCESS", "JKDHSKJ");
                                    handleSuccessfulLogin(userData);
                                } else {
                                    // Passwords do not match
                                    handleFailedLogin();
                                }
                            }
                        } else {
                            // No user found with the provided email
                            handleFailedLogin();
                        }
                    } else {
                        // Error in Firestore query
                        Log.d("TAG", "Error getting user document", task.getException());
                        handleFailedLogin();
                    }
                });
    }

    // Method to handle successful login
    private void handleSuccessfulLogin(UserData userData) {
        UserManager userManager = new UserManager(LoginActivity.this);
        userManager.saveUserData(userData.getUser_id(), userData.getUser_name(), userData.getNo_of_coins());
        Toast.makeText(LoginActivity.this, "Login successful for "+userManager.getUserName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    private void handleFailedLogin() {

        Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
    }
}