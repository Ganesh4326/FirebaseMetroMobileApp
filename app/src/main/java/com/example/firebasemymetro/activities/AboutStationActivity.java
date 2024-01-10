package com.example.firebasemymetro.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.firebasemymetro.R;
import com.example.firebasemymetro.models.StationData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class AboutStationActivity extends AppCompatActivity {
    ImageView imageView;
    Button getDataBtn;
    TextView nextTrainIn;
    TextView crowdLevels;
    TextView stationName;
    TextView parkingAvail;
    TextView rtcAvail;
    RelativeLayout relativeLayout;

    ImageView iconImageView;
    ImageView checkImageView;

    ImageView iconImageView2;
    ImageView checkImageView2;
    NumberPicker numberPicker;
    Spinner crowdLevelsEdit;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    StationData stationData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_station);


        Spinner sourceSpinner = findViewById(R.id.source);
        getDataBtn = findViewById(R.id.getDataBtn);
        nextTrainIn = findViewById(R.id.nextTrainIn);
        crowdLevels = findViewById(R.id.crowdLevels);
        stationName = findViewById(R.id.stationName);
        parkingAvail = findViewById(R.id.parkingAvail);
        rtcAvail = findViewById(R.id.rtcAvail);
        relativeLayout = findViewById(R.id.relaLay);
        iconImageView = findViewById(R.id.iconImageView);
        numberPicker = findViewById(R.id.numberPicker);
        checkImageView = findViewById(R.id.checkImageView);
        iconImageView2 = findViewById(R.id.iconImageView2);
        checkImageView2 = findViewById(R.id.checkImageView2);
//        imageView = findViewById(R.id.imageView);
        crowdLevelsEdit = findViewById(R.id.crowdLevelsEdit);
        numberPicker.setMaxValue(15);
        numberPicker.setMinValue(1);

        String[] crowdOptions = {"low", "high", "med"};
        String[] options = {"Ameerpet", "Assembly", "Begumpet", "Bharatnagar", "Chaitanyapuri", "Chikkadapally", "Dilsukhnagar", "Dr B.R. Ambedkar Balanagar", "Durgam Cheruvu", "Erragadda", "ESI Hospital", "Gandhi Bhavan", "Gandhi Hospital", "Habsiguda", "Hitech City", "Irrum Manjil", "JBS Parade Ground", "JNTU College", "Jubliee Hills Checkpost", "Khairatabad", "KPHB Colony", "Kukatpally", "Lakdi-ka-pul", "LB Nagar", "Madhapur", "Madhuranagar", "Malakpet", "Mettuguda", "MG Bus Station", "Miyapur", "Musapet", "Musarambagh", "Musheerabad", "Nagole", "Nampally", "Narayanguda", "New Market", "NGRI", "Osmania Medical College", "Parade Ground", "Paradise", "Peddamma Gudi", "Prakash Nagar", "Punjagutta", "Rasoolpura", "Raydurg", "Rd no.5 Jubliee Hills", "RTC X Roads", "Secunderabad East", "Secunderabad West", "SR Nagar", "Stadium", "Sultan Bazar", "Tarnaka", "Uppal", "Victoria Mahal", "Yusufguda"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        ArrayAdapter<String> adapterTwo = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, crowdOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterTwo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceSpinner.setAdapter(adapter);
        crowdLevelsEdit.setAdapter(adapterTwo);

        String imageUrl = "https://lh5.googleusercontent.com/p/AF1QipM_T3kGpsn3mNpvY-RXhWnLX4u4Q76JHhMXY_6B=w204-h114-n-k-no";
//        new LoadImageTask().execute(imageUrl);

        stationName.setBackgroundColor(Color.parseColor("#FFC107"));

        getDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeLayout.setVisibility(View.VISIBLE);
                db.collection("stations_data")
                        .document("HITECHCITYSTATION")
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
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

        iconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberPicker.setVisibility(View.VISIBLE);
                iconImageView.setVisibility(View.GONE);
                nextTrainIn.setVisibility(View.GONE);
                checkImageView.setVisibility(View.VISIBLE);
            }
        });

        iconImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crowdLevelsEdit.setVisibility(View.VISIBLE);
                iconImageView2.setVisibility(View.GONE);
                crowdLevels.setVisibility(View.GONE);
                checkImageView2.setVisibility(View.VISIBLE);
            }
        });

        checkImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("HCECK", "CHECK");
                updateNextTrain(stationData);
            }
        });
        checkImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCrowdLevels(stationData);
            }
        });
    }

    private void displayData(StationData stationData) {
        nextTrainIn.setText(stationData.getNext_train_in() + " min");
        crowdLevels.setText(stationData.getCrowd_levels());
        stationName.setText(stationData.getFull_name());
        parkingAvail.setText(stationData.getParking_avail());
        rtcAvail.setText(stationData.getRtc_avail());
    }


    private class LoadImageTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            String imageUrl = params[0];
            try {
                // Open a connection to the URL
                InputStream inputStream = new URL(imageUrl).openStream();

                // Read the input stream into a byte array
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, length);
                }

                // Convert the byte array to a Bitmap
                byte[] data = byteArrayOutputStream.toByteArray();
                return BitmapFactory.decodeByteArray(data, 0, data.length);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                // Set the Bitmap to the ImageView
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    private void updateCrowdLevels(StationData stationData) {
        if (stationData != null) {
            Log.d("CROWD", crowdLevelsEdit.getSelectedItem().toString());
            stationData.setCrowd_levels(crowdLevelsEdit.getSelectedItem().toString());
            updateDataInFirestore(stationData);
            displayData(stationData);
            crowdLevelsEdit.setVisibility(View.GONE);
            iconImageView2.setVisibility(View.VISIBLE);
            crowdLevels.setVisibility(View.VISIBLE);
            checkImageView2.setVisibility(View.GONE);
        }
    }

    private void updateDataInFirestore(StationData updatedStationData) {
        db.collection("stations_data")
                .document("HITECHCITYSTATION")
                .set(updatedStationData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "Data updated successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TAG", "Error updating data", e);
                    }
                });
    }

    private void updateNextTrain(StationData stationData) {
        if (stationData != null) {
            Log.d("NEXT", "TRAIN1");
            stationData.setNext_train_in(numberPicker.getValue());
            updateNextTrainInFirestore(stationData);
            displayData(stationData);
            Log.d("NEXT", "TRAIN");
            numberPicker.setVisibility(View.GONE);
            iconImageView.setVisibility(View.VISIBLE);
            nextTrainIn.setVisibility(View.VISIBLE);
            checkImageView.setVisibility(View.GONE);
        }
    }

    private void updateNextTrainInFirestore(StationData updatedStationData) {
        db.collection("stations_data")
                .document("HITECHCITYSTATION")
                .set(updatedStationData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "Data updated successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TAG", "Error updating data", e);
                    }
                });
    }
}