package com.example.firebasemymetro.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class NearbyTourismActivity extends AppCompatActivity {

    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;

    ImageView placeLink1;
    ImageView placeLink2;
    ImageView placeLink3;
    ImageView placeLink4;
    ImageView placeLink5;

    Button getDataBtn;

    private StationData stationData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_tourism);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        imageView5 = findViewById(R.id.imageView5);
        getDataBtn = findViewById(R.id.getDataBtn);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        textView5 = findViewById(R.id.textView5);
        placeLink1 = findViewById(R.id.placeLink1);
        placeLink2 = findViewById(R.id.placeLink2);
        placeLink3 = findViewById(R.id.placeLink3);
        placeLink4 = findViewById(R.id.placeLink4);
        placeLink5 = findViewById(R.id.placeLink5);
        Spinner sourceSpinner = findViewById(R.id.source);

        String[] options = {"Ameerpet", "Assembly", "Begumpet", "Bharatnagar", "Chaitanyapuri", "Chikkadapally", "Dilsukhnagar", "Dr B.R. Ambedkar Balanagar", "Durgam Cheruvu", "Erragadda", "ESI Hospital", "Gandhi Bhavan", "Gandhi Hospital", "Habsiguda", "Hitech City", "Irrum Manjil", "JBS Parade Ground", "JNTU College", "Jubliee Hills Checkpost", "Khairatabad", "KPHB Colony", "Kukatpally", "Lakdi-ka-pul", "LB Nagar", "Madhapur", "Madhuranagar", "Malakpet", "Mettuguda", "MG Bus Station", "Miyapur", "Musapet", "Musarambagh", "Musheerabad", "Nagole", "Nampally", "Narayanguda", "New Market", "NGRI", "Osmania Medical College", "Parade Ground", "Paradise", "Peddamma Gudi", "Prakash Nagar", "Punjagutta", "Rasoolpura", "Raydurg", "Rd no.5 Jubliee Hills", "RTC X Roads", "Secunderabad East", "Secunderabad West", "SR Nagar", "Stadium", "Sultan Bazar", "Tarnaka", "Uppal", "Victoria Mahal", "Yusufguda"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceSpinner.setAdapter(adapter);

        String imageUrl = "https://lh5.googleusercontent.com/p/AF1QipM_T3kGpsn3mNpvY-RXhWnLX4u4Q76JHhMXY_6B=w204-h114-n-k-no";
        new LoadImageTask(imageView1).execute(imageUrl);


        getDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("stations_data")
                        .document("HITECHCITYSTATION")
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    stationData = documentSnapshot.toObject(StationData.class);
                                    Log.d("TAG", "Document data: " + documentSnapshot.getData());
                                    if (stationData != null) {
                                        List<String> stationLinks = stationData.getPlaces_images();
                                        List<String> placesName = stationData.getPlaces_names();
                                        List<String> placesLinks = stationData.getPlaces_links();
                                        Log.d("LINK", Arrays.toString(stationLinks.toArray(new String[0])));
                                        new LoadImageTask(imageView1).execute(stationLinks.get(0));
                                        new LoadImageTask(imageView2).execute(stationLinks.get(1));
                                        new LoadImageTask(imageView3).execute(stationLinks.get(2));
                                        new LoadImageTask(imageView4).execute(stationLinks.get(3));
                                        new LoadImageTask(imageView5).execute(stationLinks.get(4));
                                        textView1.setText(placesName.get(0));
                                        textView2.setText(placesName.get(1));
                                        textView3.setText(placesName.get(2));
                                        textView4.setText(placesName.get(3));
                                        textView5.setText(placesName.get(4));
                                    }
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

        placeLink1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> placesLinks = stationData.getPlaces_links();
                goToUrl(placesLinks.get(0));
            }
        });
        placeLink2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> placesLinks = stationData.getPlaces_links();
                goToUrl(placesLinks.get(1));
            }
        });
        placeLink3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> placesLinks = stationData.getPlaces_links();
                goToUrl(placesLinks.get(2));
            }
        });
        placeLink4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> placesLinks = stationData.getPlaces_links();
                goToUrl(placesLinks.get(3));
            }
        });
        placeLink5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> placesLinks = stationData.getPlaces_links();
                goToUrl(placesLinks.get(4));
            }
        });
    }

    void goToUrl(String url) {
//        String url = "https://www.example.com";
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage("com.android.chrome");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            intent.setPackage(null);
            startActivity(intent);
        }
    }

    private class LoadImageTask extends AsyncTask<String, Void, Bitmap> {

        private ImageView imageView;

        public LoadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

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
}
