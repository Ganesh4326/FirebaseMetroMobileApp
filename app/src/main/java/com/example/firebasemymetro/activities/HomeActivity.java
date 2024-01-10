package com.example.firebasemymetro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.firebasemymetro.R;
import com.example.firebasemymetro.fragments.HomeFragment;
import com.example.firebasemymetro.fragments.MapFragment;
import com.example.firebasemymetro.fragments.ProfileFragment;
import com.example.firebasemymetro.fragments.TicketFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    TextView userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        replaceFragment(new HomeFragment(), true);

//        userName = findViewById(R.id.userName);
//        UserManager userManager = new UserManager(HomeActivity.this);
//        userName.setText("Hey, welcome "+userManager.getUserName());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        TextView textView = (TextView)toolbar.findViewById(R.id.toolbarTextView);
        textView.setText("Go Metro");

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        FirebaseApp.initializeApp(this);
        // Get a Firestore instance
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        DocumentReference documentRef = db.collection("sample").document("first");

        documentRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String value = document.getString("name"); // Replace "yourFieldName" with the actual field name in your document
                    Log.d("Firestore Read", "Document data: " + value);
                } else {
                    Log.d("Firestore Read fail", "No such document");
                }
            } else {
                Log.e("Firestore Read error", "Error getting document", task.getException());
                task.getException().printStackTrace();
            }
        });



        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                replaceFragment(new HomeFragment(), false);
            } else if (itemId == R.id.nav_ticket) {
                replaceFragment(new TicketFragment(), false);
            } else if (itemId == R.id.nav_map) {
                replaceFragment(new MapFragment(), false);
//                CollectionReference collectionRef = db.collection("example_collection");
//
//                Map<String, Object> data = new HashMap<>();
//                data.put("field1", "value1");
//                data.put("field2", 42);
//                data.put("field3", true);
//
//                collectionRef.add(data)
//                        .addOnSuccessListener(documentReference -> {
//                            Log.d("Firestore", "Document added with ID: " + documentReference.getId());
//                        })
//                        .addOnFailureListener(e -> {
//                            Log.e("Firestore error", "Error adding document", e);
//                        });
            } else if (itemId == R.id.nav_profile) {
                replaceFragment(new ProfileFragment(), false);
            }

            return true;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.about_station) {
            Intent intent = new Intent(HomeActivity.this, AboutStationActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.feedback) {
            Intent intent = new Intent(HomeActivity.this, FeedbackActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.rewards) {
            Intent intent = new Intent(HomeActivity.this, RewardsActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.orders) {
            Intent intent = new Intent(HomeActivity.this, HistoryActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.yourRewards) {
            Intent intent = new Intent(HomeActivity.this, UserRewardActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Here is the share content body";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        }
        return true;
    }

    private void replaceFragment(Fragment fragment, boolean isAppInitialized) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (isAppInitialized) {
            fragmentTransaction.add(R.id.frameLayout, fragment);
        } else {
            fragmentTransaction.replace(R.id.frameLayout, fragment);
        }
        fragmentTransaction.commit();
    }
}
