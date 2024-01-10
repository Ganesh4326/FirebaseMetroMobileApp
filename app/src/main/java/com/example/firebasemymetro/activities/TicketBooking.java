package com.example.firebasemymetro.activities;

import static com.example.firebasemymetro.activities.ShortestRouteActivity.findIndex;
import static com.example.firebasemymetro.activities.ShortestTimeActivity.minTimeDistance;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firebasemymetro.R;
import com.example.firebasemymetro.activities.GraphArray;
import com.example.firebasemymetro.data.CitiesArray;
import com.example.firebasemymetro.data.TimesArray;

import java.util.Arrays;

public class TicketBooking extends AppCompatActivity {

    private TextView textNumber;

    TextView amount;
    private int currentNumber = 0;

    Button btn;

    Button decreaseBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_booking);


        Spinner sourceSpinner = findViewById(R.id.source);
        Spinner destSpinner = findViewById(R.id.destination);
        String[] options = {"Ameerpet", "Assembly", "Begumpet", "Bharatnagar", "Chaitanyapuri", "Chikkadapally", "Dilsukhnagar", "Dr B.R. Ambedkar Balanagar", "Durgam Cheruvu", "Erragadda", "ESI Hospital", "Gandhi Bhavan", "Gandhi Hospital", "Habsiguda", "Hitech City", "Irrum Manjil", "JBS Parade Ground", "JNTU College", "Jubliee Hills Checkpost", "Khairatabad", "KPHB Colony", "Kukatpally", "Lakdi-ka-pul", "LB Nagar", "Madhapur", "Madhuranagar", "Malakpet", "Mettuguda", "MG Bus Station", "Miyapur", "Musapet", "Musarambagh", "Musheerabad", "Nagole", "Nampally", "Narayanguda", "New Market", "NGRI", "Osmania Medical College", "Parade Ground", "Paradise", "Peddamma Gudi", "Prakash Nagar", "Punjagutta", "Rasoolpura", "Raydurg", "Rd no.5 Jubliee Hills", "RTC X Roads", "Secunderabad East", "Secunderabad West", "SR Nagar", "Stadium", "Sultan Bazar", "Tarnaka", "Uppal", "Victoria Mahal", "Yusufguda"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceSpinner.setAdapter(adapter);
        destSpinner.setAdapter(adapter);

        GraphArray ga = new GraphArray();
        int[][] g = ga.graph;

        CitiesArray c = new CitiesArray();
        String[] cities = c.cities;

        textNumber = findViewById(R.id.textNumber);

        btn = findViewById(R.id.buttonPlus);

//        int resultTime = dijkstraForTimes(times, src, dest);

        TimesArray t = new TimesArray();
        int[][] times = t.times;

        decreaseBtn = findViewById(R.id.buttonMinus);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(currentNumber<6){
                    currentNumber++;
                    updateNumber();
                }

                String sourceStation = sourceSpinner.getSelectedItem().toString();
                String destStation = destSpinner.getSelectedItem().toString();

                int src = findIndex(cities, sourceStation);
                int dest = findIndex(cities, destStation);

                int resultTime = dijkstraForTimes(times, src, dest);

                amount = findViewById(R.id.totalAmountNumber);
                amount.setText(String.valueOf(resultTime*currentNumber));

            }
        });

        decreaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentNumber > 0) {
                    currentNumber--;
                    updateNumber();
                }

                String sourceStation = sourceSpinner.getSelectedItem().toString();
                String destStation = destSpinner.getSelectedItem().toString();

                int src = findIndex(cities, sourceStation);
                int dest = findIndex(cities, destStation);

                int resultTime = dijkstraForTimes(times, src, dest);

                amount = findViewById(R.id.totalAmountNumber);
                amount.setText(String.valueOf(resultTime*currentNumber));

            }
        });
    }

    private void resetTotalAmountNumber() {
        amount.setText("0");
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