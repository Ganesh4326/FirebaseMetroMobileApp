package com.example.firebasemymetro;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasemymetro.models.RewardsData;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Objects;

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.ViewHolder> {

    private static List<String> couponList;
    static RewardsData rewards;
    static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public CouponAdapter(List<String> couponList) {
        this.couponList = couponList;
    }

    private static OnCouponClickListener onCouponClickListener;

    public interface OnCouponClickListener {
        void onCouponApply(String reward_id, String coupon, String reward_type, int dis_amount, int dis_per);

        void onCouponRemove(String reward_id, String coupon, String reward_type, int dis_amt, int dis_per);

//        void onCouponRemove(String coupon, String reward_type, int dis_amt, int dis_per);
    }

    public void setOnCouponClickListener(OnCouponClickListener listener) {
        this.onCouponClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coupon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String coupon = couponList.get(position);
        holder.bind(coupon);
    }

    @Override
    public int getItemCount() {
        return couponList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView couponTextView;
        TextView desc;
        TextView applyBtn;
        TextView appliedBtn;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            couponTextView = itemView.findViewById(R.id.couponTextView);
            desc = itemView.findViewById(R.id.desc);
            appliedBtn = itemView.findViewById(R.id.appliedBtn);
            applyBtn = itemView.findViewById(R.id.applyBtn);
        }

        public void bind(String coupon) {
            db.collection("rewards")
                    .document(coupon)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d("GTOT", "RWARDS");
                                Log.d("TAG", "Document data: " + document.getData());
                                RewardsData rewards = document.toObject(RewardsData.class);
                                Log.d(rewards.getReward_name(), "NAME");
                                couponTextView.setText(rewards.getReward_name());
                                if (Objects.equals(rewards.getReward_type(), "DISCOUNT")) {
                                    desc.setText(rewards.getDiscount_per() + " % discount");
                                } else {
                                    desc.setText(rewards.getDis_amount() + "rs off");
                                }
                                applyBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        appliedBtn.setVisibility(View.VISIBLE);
                                        applyBtn.setVisibility(View.GONE);
                                        if (onCouponClickListener != null) {
                                            onCouponClickListener.onCouponApply(
                                                    rewards.getReward_id(),
                                                    rewards.getReward_name(),
                                                    rewards.getReward_type(),
                                                    rewards.getDis_amount(),
                                                    rewards.getDiscount_per()
                                            );
                                        }
                                    }
                                });
                                appliedBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        applyBtn.setVisibility(View.VISIBLE);
                                        appliedBtn.setVisibility(View.GONE);
                                        if (onCouponClickListener != null) {
                                            onCouponClickListener.onCouponRemove(
                                                    rewards.getReward_id(),
                                                    rewards.getReward_name(),
                                                    rewards.getReward_type(),
                                                    rewards.getDis_amount(),
                                                    rewards.getDiscount_per()
                                            );
                                        }
                                    }
                                });
                            }
                        }
                    });
        }

    }
}
