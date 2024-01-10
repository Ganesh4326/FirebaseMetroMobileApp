package com.example.firebasemymetro.fragments;

import static com.example.firebasemymetro.activities.ShortestRouteActivity.findIndex;
import static com.example.firebasemymetro.activities.ShortestTimeActivity.minTimeDistance;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasemymetro.CouponAdapter;
import com.example.firebasemymetro.data.CitiesArray;
import com.example.firebasemymetro.activities.GraphArray;
import com.example.firebasemymetro.activities.PaymentActivity;
import com.example.firebasemymetro.R;
import com.example.firebasemymetro.data.TimesArray;
import com.example.firebasemymetro.UserManager.UserManager;
import com.example.firebasemymetro.models.TicketData;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


public class TicketFragment extends Fragment implements CouponAdapter.OnCouponClickListener {

    private RecyclerView recyclerView;
    int bookingTotal;
    private TextView textNumber;

    TextView amount;
    private int currentNumber = 0;

    Button btn;

    Button decreaseBtn;

    Spinner sourceSpinner;
    Spinner destSpinner;

    TextView couponName;
    LinearLayout couponLaout;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private RecyclerView recyclerViewCoupons;
    //    private List<String> couponList;
    private CouponAdapter couponAdapter;

    List<String> coupons;
    String rewardId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_ticket, container, false);
        View rootView = inflater.inflate(R.layout.fragment_ticket, container, false);
//
//        couponLaout = rootView.findViewById(R.id.couponsLayout);
//        couponName = rootView.findViewById(R.id.couponName);
        amount = rootView.findViewById(R.id.totalAmountNumber);


        sourceSpinner = rootView.findViewById(R.id.source);
        destSpinner = rootView.findViewById(R.id.destination);
        String[] options = {"Ameerpet", "Assembly", "Begumpet", "Bharatnagar", "Chaitanyapuri", "Chikkadapally", "Dilsukhnagar", "Dr B.R. Ambedkar Balanagar", "Durgam Cheruvu", "Erragadda", "ESI Hospital", "Gandhi Bhavan", "Gandhi Hospital", "Habsiguda", "Hitech City", "Irrum Manjil", "JBS Parade Ground", "JNTU College", "Jubliee Hills Checkpost", "Khairatabad", "KPHB Colony", "Kukatpally", "Lakdi-ka-pul", "LB Nagar", "Madhapur", "Madhuranagar", "Malakpet", "Mettuguda", "MG Bus Station", "Miyapur", "Musapet", "Musarambagh", "Musheerabad", "Nagole", "Nampally", "Narayanguda", "New Market", "NGRI", "Osmania Medical College", "Parade Ground", "Paradise", "Peddamma Gudi", "Prakash Nagar", "Punjagutta", "Rasoolpura", "Raydurg", "Rd no.5 Jubliee Hills", "RTC X Roads", "Secunderabad East", "Secunderabad West", "SR Nagar", "Stadium", "Sultan Bazar", "Tarnaka", "Uppal", "Victoria Mahal", "Yusufguda"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceSpinner.setAdapter(adapter);
        destSpinner.setAdapter(adapter);

        GraphArray ga = new GraphArray();
        int[][] g = ga.graph;

        CitiesArray c = new CitiesArray();
        String[] cities = c.cities;

        textNumber = rootView.findViewById(R.id.textNumber);

        btn = rootView.findViewById(R.id.buttonPlus);

        btn.setEnabled(false);
        btn.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));

        sourceSpinner = rootView.findViewById(R.id.source);
        destSpinner = rootView.findViewById(R.id.destination);

        sourceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                checkAndUpdateButtonState();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        destSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                checkAndUpdateButtonState();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });


        TimesArray t = new TimesArray();
        int[][] times = t.times;

        Button navigateButton = rootView.findViewById(R.id.confirmBtn);
        navigateButton.setEnabled(false);
        navigateButton.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));

        decreaseBtn = rootView.findViewById(R.id.buttonMinus);
        decreaseBtn.setEnabled(false);
        decreaseBtn.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                navigateButton.setEnabled(true);
                navigateButton.setBackgroundColor(getResources().getColor(android.R.color.holo_purple));

                decreaseBtn.setEnabled(true);
                decreaseBtn.setBackgroundColor(getResources().getColor(android.R.color.holo_purple));

                if (currentNumber < 6) {
                    currentNumber++;
                    updateNumber();
                }
                if (currentNumber > 5) {
                    btn.setEnabled(false);
                    btn.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                }

                String sourceStation = sourceSpinner.getSelectedItem().toString();
                String destStation = destSpinner.getSelectedItem().toString();

                int src = findIndex(cities, sourceStation);
                int dest = findIndex(cities, destStation);

                int resultTime = dijkstraForTimes(times, src, dest);

                amount = rootView.findViewById(R.id.totalAmountNumber);
                bookingTotal = resultTime * currentNumber;
                amount.setText(String.valueOf(bookingTotal));

            }
        });

        decreaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentNumber > 0) {
                    currentNumber--;
                    updateNumber();
                }

                if (currentNumber < 6) {
                    btn.setEnabled(true);
                    btn.setBackgroundColor(getResources().getColor(android.R.color.holo_purple));
                }

                if (currentNumber < 1) {
                    decreaseBtn.setEnabled(false);
                    decreaseBtn.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                    navigateButton.setEnabled(false);
                    navigateButton.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                }

                String sourceStation = sourceSpinner.getSelectedItem().toString();
                String destStation = destSpinner.getSelectedItem().toString();

                int src = findIndex(cities, sourceStation);
                int dest = findIndex(cities, destStation);

                int resultTime = dijkstraForTimes(times, src, dest);

                amount = rootView.findViewById(R.id.totalAmountNumber);
                bookingTotal = resultTime * currentNumber;
                amount.setText(String.valueOf(bookingTotal));
            }
        });

        navigateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToPaymentScreen();
            }
        });

        recyclerViewCoupons = rootView.findViewById(R.id.recyclerView);
        recyclerViewCoupons.setLayoutManager(new LinearLayoutManager(getActivity()));

        UserManager userManager = new UserManager(getActivity());

        db.collection("users")
                .document(userManager.getUserId())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            coupons = (List<String>) document.get("coupons");

                            if (coupons != null) {
                                // Do something with the coupons
                                couponAdapter = new CouponAdapter(coupons);
                                couponAdapter.setOnCouponClickListener(this);
                                recyclerViewCoupons.setAdapter(couponAdapter);
                            } else {
                                // Handle the case when coupons are null or empty
                            }
                        } else {
                            // Handle the case when the document doesn't exist
                        }
                    } else {
                        // Handle failures
                    }
                });

        return rootView;

    }

    @Override
    public void onCouponApply(String reward_id, String coupon, String reward_type, int dis_amt, int dis_per) {
        Log.d(String.valueOf(dis_amt), "DISC AMT");
        Log.d(String.valueOf(dis_per), "DISC PER");
        if (Objects.equals(reward_type, "AMOUNT")) {
            Log.d("IN", "AMOUNT");
            if (dis_amt < bookingTotal) {
                bookingTotal = bookingTotal - dis_amt;
                amount.setText(String.valueOf(bookingTotal));
                rewardId = reward_id;

            } else {
                Toast.makeText(requireContext(), "Ticket total is too low", Toast.LENGTH_SHORT).show();
            }
        } else {
            int dis_cal_amt = (dis_per / bookingTotal) * 100;
            Log.d("DISC " + dis_per, String.valueOf(dis_cal_amt));
            bookingTotal = bookingTotal - dis_cal_amt;
            amount.setText(String.valueOf(bookingTotal));
            rewardId = reward_id;
        }
        Toast.makeText(requireContext(), coupon + " applied successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCouponRemove(String reward_id, String coupon, String reward_type, int dis_amt, int dis_per) {
        if (Objects.equals(reward_type, "DISCOUNT")) {
            int dis_cal_amt = (dis_per / bookingTotal) * 100;
            bookingTotal = bookingTotal + dis_cal_amt;
            amount.setText(String.valueOf(bookingTotal));
            rewardId = "";
        } else {
            bookingTotal = bookingTotal + dis_amt;
            amount.setText(String.valueOf(bookingTotal));
            rewardId = "";
        }
        Toast.makeText(requireContext(), coupon + " applied successfully", Toast.LENGTH_SHORT).show();
    }

    private void checkAndUpdateButtonState() {
        String sourceStation = sourceSpinner.getSelectedItem().toString();
        String destStation = destSpinner.getSelectedItem().toString();

        if (sourceStation.equals(destStation) || currentNumber > 5) {
            btn.setEnabled(false);
            btn.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        } else {
            btn.setEnabled(true);
            btn.setBackgroundColor(getResources().getColor(android.R.color.holo_purple));
        }
    }

    private String generateUniqueTicketId() {
        return UUID.randomUUID().toString();
    }

    private void navigateToPaymentScreen() {
        long currentTimeMillis = System.currentTimeMillis();
        UserManager userManager = new UserManager(getActivity());
        TicketData ticketData = new TicketData(userManager.getUserId(), generateUniqueTicketId(), sourceSpinner.getSelectedItem().toString(), destSpinner.getSelectedItem().toString(), rewardId, currentNumber, bookingTotal, String.valueOf(currentTimeMillis));
        Intent intent = new Intent(getActivity(), PaymentActivity.class);
        intent.putExtra("reward_id", rewardId);
        intent.putExtra("ticketData", ticketData);
        startActivity(intent);
    }

    public static int dijkstraForTimes(int[][] graph, int src, int dest) {
        int V = graph.length;
        int[] dist = new int[V];
        boolean[] visited = new boolean[V];

        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        for (int count = 0; count < V - 1; count++) {
            int u = minTimeDistance(dist, visited);
            visited[u] = true;

            for (int v = 0; v < V; v++) {
                if (!visited[v] && graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE
                        && dist[u] + graph[u][v] < dist[v]) {
                    dist[v] = dist[u] + graph[u][v];
                }
            }
        }
        return dist[dest];
    }

    public void increaseNumber(View view) {
        currentNumber++;
        updateNumber();
    }

    public void decreaseNumber(View view) {
        if (currentNumber > 0) {
            currentNumber--;
            updateNumber();
        }
    }

    private void updateNumber() {
        textNumber.setText(String.valueOf(currentNumber));
    }

}