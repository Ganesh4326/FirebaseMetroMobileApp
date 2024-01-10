package com.example.firebasemymetro.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasemymetro.R;
import com.example.firebasemymetro.models.TicketData;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {

    private List<TicketData> ticketList;
    private OnItemClickListener listener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public TicketAdapter(List<TicketData> ticketList) {
        this.ticketList = ticketList;
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket, parent, false);
        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        TicketData ticketData = ticketList.get(position);
        holder.bind(ticketData);
    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }

    public class TicketViewHolder extends RecyclerView.ViewHolder {

        private TextView quantity;
        private TextView sourceAndDest;
        private TextView amount;
        private TextView time;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            quantity = itemView.findViewById(R.id.ticketQuantityTextView);
            sourceAndDest = itemView.findViewById(R.id.sourceDestinationTextView);
            amount = itemView.findViewById(R.id.amountTextView);
            time = itemView.findViewById(R.id.timeTextView);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onItemClick(ticketList.get(position), position);
                }
            });
        }

        public void bind(TicketData ticketData) {
            sourceAndDest.setText(ticketData.getSource()+" --> "+ticketData.getDestination());
            quantity.setText("x "+ticketData.getQuantity());
            amount.setText("Rs."+ticketData.getAmount());
            time.setText("10-12-2023");
        }
    }

    public interface OnItemClickListener {
        void onItemClick(TicketData ticketData, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
