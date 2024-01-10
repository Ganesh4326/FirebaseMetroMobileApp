package com.example.firebasemymetro.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.firebasemymetro.activities.AllPathsActivity;
import com.example.firebasemymetro.activities.NearbyTourismActivity;
import com.example.firebasemymetro.activities.PlatformInfoActivity;
import com.example.firebasemymetro.R;
import com.example.firebasemymetro.activities.ShortestTimeActivity;
import com.example.firebasemymetro.UserManager.UserManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeFragment extends Fragment {

    Button navigateToShortestRoute;
    Button navigateToShortestTime;
    Button navigateToAllPaths;
    Button navigateToPlatformInfo;

    private DatabaseReference databaseReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        TextView userName = rootView.findViewById(R.id.userName);
        UserManager userManager = new UserManager(getActivity());
        userName.setText("Hey, welcome " + userManager.getUserName());

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("data");

        navigateToShortestRoute = rootView.findViewById(R.id.shortestRouteButton);
        navigateToShortestRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ShortestTimeActivity.class);
                startActivity(i);
            }
        });

        navigateToShortestTime = rootView.findViewById(R.id.shortestTimeButton);
        navigateToShortestTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), NearbyTourismActivity.class);
                startActivity(i);
            }
        });

        navigateToAllPaths = rootView.findViewById(R.id.allPathsButton);
        navigateToAllPaths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AllPathsActivity.class);
                startActivity(i);
            }
        });

        navigateToPlatformInfo = rootView.findViewById(R.id.platformInfoButton);
        navigateToPlatformInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), PlatformInfoActivity.class);
                startActivity(i);
            }
        });

        return rootView;
    }

}