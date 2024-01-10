package com.example.firebasemymetro.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firebasemymetro.R;
import com.example.firebasemymetro.models.StationData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PlatformInfoActivity extends AppCompatActivity {

    TextView textView;

    StationData stationData;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platform_info);
        textView1=findViewById(R.id.textView1);
        textView2=findViewById(R.id.textView2);
        textView3=findViewById(R.id.textView3);
        textView4=findViewById(R.id.textView4);

        TextView textViewScrolling = (TextView) this.findViewById(R.id.scrollingText);
        textViewScrolling.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        textViewScrolling.setText("Select station and get the platform details and save the time");
        textViewScrolling.setSelected(true);
        textViewScrolling.setSingleLine(true);

        Spinner sourceSpinner = findViewById(R.id.source);
        String[] options = {"Ameerpet", "Assembly", "Begumpet", "Bharatnagar", "Chaitanyapuri", "Chikkadapally", "Dilsukhnagar", "Dr B.R. Ambedkar Balanagar", "Durgam Cheruvu", "Erragadda", "ESI Hospital", "Gandhi Bhavan", "Gandhi Hospital", "Habsiguda", "Hitech City", "Irrum Manjil", "JBS Parade Ground", "JNTU College", "Jubliee Hills Checkpost", "Khairatabad", "KPHB Colony", "Kukatpally", "Lakdi-ka-pul", "LB Nagar", "Madhapur", "Madhuranagar", "Malakpet", "Mettuguda", "MG Bus Station", "Miyapur", "Musapet", "Musarambagh", "Musheerabad", "Nagole", "Nampally", "Narayanguda", "New Market", "NGRI", "Osmania Medical College", "Parade Ground", "Paradise", "Peddamma Gudi", "Prakash Nagar", "Punjagutta", "Rasoolpura", "Raydurg", "Rd no.5 Jubliee Hills", "RTC X Roads", "Secunderabad East", "Secunderabad West", "SR Nagar", "Stadium", "Sultan Bazar", "Tarnaka", "Uppal", "Victoria Mahal", "Yusufguda"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceSpinner.setAdapter(adapter);

        Button btn = findViewById(R.id.getDataBtn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("stations_data")
                        .document("HITECHCITYSTATION")
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                textView1.setVisibility(View.VISIBLE);
                                textView2.setVisibility(View.VISIBLE);
                                textView3.setVisibility(View.VISIBLE);
                                textView4.setVisibility(View.VISIBLE);
                                if (documentSnapshot.exists()) {
                                    stationData = documentSnapshot.toObject(StationData.class);
                                    Log.d("TAG", "Document data: " + documentSnapshot.getData());
                                    displayData(stationData);
                                } else {
                                    Log.d("TAG", "No such document");
                                }
                            }
                        })
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (!task.isSuccessful()) {
                                    Log.e("TAG", "Error getting documents.", task.getException());
                                }
                            }
                        });
            }
        });
    }

    private void displayData(StationData stationData) {
//        textView1.setText(stationData.getNext_train_in() + " min");
//        textView1.setText(stationData.getCrowd_levels());
        textView2.setText(stationData.getPlatform1());
        if(stationData.getPlatform1().contains("Blue")){
            textView2.setBackgroundColor(Color.BLUE);
        }
        if(stationData.getPlatform1().contains("Green")){
            textView2.setBackgroundColor(Color.GREEN);
        }
        if(stationData.getPlatform1().contains("Red")){
            textView2.setBackgroundColor(Color.RED);
        }
        textView4.setText(stationData.getPlatform2());
        if(stationData.getPlatform2().contains("Blue")){
            textView4.setBackgroundColor(Color.RED);
        }
        if(stationData.getPlatform2().contains("Green")){
            textView4.setBackgroundColor(Color.GREEN);
        }
        if(stationData.getPlatform2().contains("Red")){
            textView4.setBackgroundColor(Color.RED);
        }
    }
}