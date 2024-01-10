package com.example.firebasemymetro.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasemymetro.R;
import com.example.firebasemymetro.activities.RewardsPaymentActivity;
import com.example.firebasemymetro.models.RewardsData;

import java.util.List;
import java.util.Objects;

public class RewardsAdapter extends RecyclerView.Adapter<RewardsAdapter.RewardViewHolder> {

    private static List<RewardsData> rewardList;

    public RewardsAdapter(List<RewardsData> rewardList) {
        this.rewardList = rewardList;
    }

    @NonNull
    @Override
    public RewardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rewards, parent, false);
        return new RewardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RewardViewHolder holder, int position) {
        RewardsData rewardData = rewardList.get(position);
        holder.bind(rewardData);
    }

    @Override
    public int getItemCount() {
        return rewardList.size();
    }

    public static class RewardViewHolder extends RecyclerView.ViewHolder {

        private TextView coinsReq;
        private TextView expiry;
        private TextView codeName;
        private TextView description;
        private TextView rewardDescriptionTextView;

        Button payBtn;

        public RewardViewHolder(@NonNull View itemView) {
            super(itemView);
            coinsReq = itemView.findViewById(R.id.coinsRequired);
            expiry = itemView.findViewById(R.id.expiryDate);
            codeName = itemView.findViewById(R.id.codeName);
            description = itemView.findViewById(R.id.description);
            payBtn = itemView.findViewById(R.id.btnBuyCoupon);
//            rewardDescriptionTextView = itemView.findViewById(R.id.rewardDescriptionTextView);

            payBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        RewardsData selectedReward = rewardList.get(position);

                        Context context = v.getContext();

                        Intent intent = new Intent(context, RewardsPaymentActivity.class);

                        intent.putExtra("reward_id", selectedReward.getReward_id());

                        context.startActivity(intent);
                    }
                }
            });

        }

        public void bind(RewardsData rewardData) {
            coinsReq.setText(String.valueOf("Coins required: " + rewardData.getCoins_req()));
            expiry.setText(String.valueOf("Expires in: " + rewardData.getExpires_on()));
            codeName.setText("CODE: " + rewardData.getReward_name());
            if (Objects.equals(rewardData.getReward_type(), "DISCOUNT")) {
                description.setText(rewardData.getDiscount_per() + " % discount on next booking");
            } else {
                description.setText("Rs." + rewardData.getDis_amount() + " off on next booking");
            }
            // rewardDescriptionTextView.setText(rewardData.getRewardDescription());
        }

    }
}
