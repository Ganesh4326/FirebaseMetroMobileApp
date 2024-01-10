package com.example.firebasemymetro.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firebasemymetro.CustomAdapter;
import com.example.firebasemymetro.data.CitiesArray;
import com.example.firebasemymetro.R;
import com.example.firebasemymetro.data.TimesArray;

import java.util.ArrayList;
import java.util.Arrays;

public class ShortestTimeActivity extends AppCompatActivity {

    Button submitBtn;
    private ArrayList<String> itemList;

    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shortest_time);

        TextView textViewScrolling = (TextView) this.findViewById(R.id.scrollingText);
        textViewScrolling.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        textViewScrolling.setText("Select source and destination and get the shortest route and time");
        textViewScrolling.setSelected(true);
        textViewScrolling.setSingleLine(true);

        Spinner sourceSpinner = findViewById(R.id.source);
        Spinner destSpinner = findViewById(R.id.destination);
        String[] options = {"Ameerpet", "Assembly", "Begumpet", "Bharatnagar", "Chaitanyapuri", "Chikkadapally", "Dilsukhnagar", "Dr B.R. Ambedkar Balanagar", "Durgam Cheruvu", "Erragadda", "ESI Hospital", "Gandhi Bhavan", "Gandhi Hospital", "Habsiguda", "Hitech City", "Irrum Manjil", "JBS Parade Ground", "JNTU College", "Jubliee Hills Checkpost", "Khairatabad", "KPHB Colony", "Kukatpally", "Lakdi-ka-pul", "LB Nagar", "Madhapur", "Madhuranagar", "Malakpet", "Mettuguda", "MG Bus Station", "Miyapur", "Musapet", "Musarambagh", "Musheerabad", "Nagole", "Nampally", "Narayanguda", "New Market", "NGRI", "Osmania Medical College", "Parade Ground", "Paradise", "Peddamma Gudi", "Prakash Nagar", "Punjagutta", "Rasoolpura", "Raydurg", "Rd no.5 Jubliee Hills", "RTC X Roads", "Secunderabad East", "Secunderabad West", "SR Nagar", "Stadium", "Sultan Bazar", "Tarnaka", "Uppal", "Victoria Mahal", "Yusufguda"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceSpinner.setAdapter(adapter);
        destSpinner.setAdapter(adapter);

//        String[] myArray = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5"};

        GraphArray ga = new GraphArray();
        int[][] g = ga.graph;

        CitiesArray c = new CitiesArray();
        String[] cities = c.cities;

        TimesArray t = new TimesArray();
        int[][] times = t.times;

        submitBtn = findViewById(R.id.button);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sourceStation = sourceSpinner.getSelectedItem().toString();
                String destStation = destSpinner.getSelectedItem().toString();
                if(sourceStation==destStation){
                    Toast.makeText(ShortestTimeActivity.this, "Source and destination can't be same", Toast.LENGTH_SHORT).show();
                    return;
                }

                int src = findIndex(cities, sourceStation);
                int dest = findIndex(cities, destStation);

                int resultTime = dijkstraForTimes(times, src, dest);

                textView = findViewById(R.id.timeText);
                textView.setText(String.valueOf(resultTime)+" Minutes");

                ArrayList<Integer> l = new ArrayList<>();
                l = dijkstra(g, src, dest);

                ArrayList<String> resultRoute = new ArrayList<>();

                String[] resultArray = new String[l.size()];

                for (int i = 0; i < l.size(); i++) {
                    int index = l.get(i);
                    if (index >= 0 && index < options.length) {
                        resultRoute.add(options[index]);
                    }
                }

                ArrayAdapter<String> resultPath = new ArrayAdapter<>(ShortestTimeActivity.this, android.R.layout.simple_spinner_item, resultArray);
                ListView listView = findViewById(R.id.listView);
                itemList = new ArrayList<>();
                itemList.add("Item 1");
                itemList.add("Item 2");
                itemList.add("Item 3");
                CustomAdapter adapter = new CustomAdapter(ShortestTimeActivity.this, resultRoute);
                listView.setAdapter(adapter);
            }
        });

    }

    public static int findIndex(String[] arr, String target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                Log.d("INDEX", arr[i]);
                return i;
            }
        }
        return -1;
    }


    public ArrayList<Integer> dijkstra(int[][] graph, int src, int dest) {
        int V = graph.length;
        boolean[] visited = new boolean[V];
        int[] dist = new int[V];
        int[] prev = new int[V];

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(prev, -1);

        dist[src] = 0;

        for (int count = 0; count < V - 1; count++) {
            int u = minDistance(dist, visited);
            visited[u] = true;

            for (int v = 0; v < V; v++) {
                if (!visited[v] && graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE &&
                        dist[u] + graph[u][v] < dist[v]) {
                    dist[v] = dist[u] + graph[u][v];
                    prev[v] = u;
                }
            }
        }


        return constructPath(prev, src, dest);
    }

    private int minDistance(int[] dist, boolean[] visited) {
        int min = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int v = 0; v < dist.length; v++) {
            if (!visited[v] && dist[v] <= min) {
                min = dist[v];
                minIndex = v;
            }
        }

        return minIndex;
    }

    private ArrayList<Integer> constructPath(int[] prev, int src, int dest) {
        ArrayList<Integer> path = new ArrayList<>();
        int current = dest;

        while (current != -1) {
            path.add(0, current);
            current = prev[current];
        }
        return path;
    }

    //Algorithm for time
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

    public static int minTimeDistance(int[] dist, boolean[] visited) {
        int min = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int v = 0; v < dist.length; v++) {
            if (!visited[v] && dist[v] <= min) {
                min = dist[v];
                minIndex = v;
            }
        }
        return minIndex;
    }

    public static void dijkstraTimesAlgo(int[][] times, String[] cities, String src, String dest) {
        if (src.equals(dest)) {
            // Handle the case when src and dest are the same
            System.out.println("Shortest distance is 0.");
        } else {
            int srcIndex = Arrays.asList(cities).indexOf(src);
            int destIndex = Arrays.asList(cities).indexOf(dest);
            int shortestDistance = dijkstraForTimes(times, srcIndex, destIndex);
        }
    }

}