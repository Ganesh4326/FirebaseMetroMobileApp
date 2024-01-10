package com.example.firebasemymetro.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.firebasemymetro.R;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<String> {

    private Context context;
    private ArrayList<String> itemList;

    public CustomAdapter(Context context, ArrayList<String> itemList) {
        super(context, R.layout.activity_custom_adapter, itemList);
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_custom_adapter, parent, false);
        }

        TextView itemText = convertView.findViewById(R.id.itemText);
        ImageView itemArrow = convertView.findViewById(R.id.itemArrow);

        String item = itemList.get(position);
        itemText.setText(item);

        // Set the background color based on the item's content
//        if ("Ameerpet".equals(item)) {
//            itemText.setBackgroundColor(ContextCompat.getColor(context, R.color.));
//        } else if ("Item 2".equals(item)) {
//            itemText.setBackgroundColor(ContextCompat.getColor(context, R.color.black));
//        } else if ("Item 3".equals(item)) {
//            itemText.setBackgroundColor(ContextCompat.getColor(context, R.color.green));
//        }
        // Add more conditions for other items

        return convertView;
    }

//    private void setItemBackgroundColor(View view, String itemName) {
//        int backgroundColor = Color.WHITE; // Default background color
//
//        if (itemName != null) {
//            if (itemName.equals("Item1")) {
//                backgroundColor = Color.RED;
//            } else if (itemName.equals("Ameerpet")) {
//                backgroundColor = Color.GREEN;
//            }
//        }
//
//        view.setBackgroundColor(backgroundColor);
//    }
}
