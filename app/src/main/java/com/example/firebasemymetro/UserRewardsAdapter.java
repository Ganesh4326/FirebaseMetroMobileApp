package com.example.firebasemymetro;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasemymetro.models.RewardsData;

import java.util.List;
import java.util.Objects;

public class UserRewardsAdapter extends RecyclerView.Adapter<UserRewardsAdapter.UserRewardViewHolder> {

    private static List<RewardsData> userRewardList;

    public UserRewardsAdapter(List<RewardsData> userRewardList) {
        this.userRewardList = userRewardList;
    }

    @NonNull
    @Override
    public UserRewardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_rewards, parent, false);
        return new UserRewardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserRewardViewHolder holder, int position) {
        RewardsData userRewardData = userRewardList.get(position);
        holder.bind(userRewardData);
    }

    @Override
    public int getItemCount() {
        return userRewardList.size();
    }

    public static class UserRewardViewHolder extends RecyclerView.ViewHolder {

        private TextView userCoinsReq;
        private TextView userExpiry;
        private TextView userCodeName;
        private TextView userDescription;

        Button useRewardBtn;

        public UserRewardViewHolder(@NonNull View itemView) {
            super(itemView);
            userCoinsReq = itemView.findViewById(R.id.coinsRequired);
            userExpiry = itemView.findViewById(R.id.expiryDate);
            userCodeName = itemView.findViewById(R.id.codeName);
            userDescription = itemView.findViewById(R.id.description);
        }

        public void bind(RewardsData userRewardData) {
            userCoinsReq.setText(String.valueOf("Coins required: " + userRewardData.getCoins_req()));
            userExpiry.setText(String.valueOf("Expires in: " + userRewardData.getExpires_on()));
            userCodeName.setText("CODE: " + userRewardData.getReward_name());
            if (Objects.equals(userRewardData.getReward_type(), "DISCOUNT")) {
                userDescription.setText(userRewardData.getDiscount_per() + " % discount on next booking");
            } else {
                userDescription.setText("Rs." + userRewardData.getDis_amount() + " off on next booking");
            }
        }
    }
}
