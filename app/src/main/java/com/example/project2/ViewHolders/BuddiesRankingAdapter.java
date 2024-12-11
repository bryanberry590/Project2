package com.example.project2.ViewHolders;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.project2.database.entities.BuddyRanking;
import com.example.project2.database.entities.Buddies;

import java.util.HashMap;
import java.util.Map;

public class BuddiesRankingAdapter extends ListAdapter<BuddyRanking, BuddiesRankingViewHolder> {
    private Map<Integer, Buddies> buddiesMap = new HashMap<>();

    public BuddiesRankingAdapter() {
        super(DIFF_CALLBACK);
    }

    public static final DiffUtil.ItemCallback<BuddyRanking> DIFF_CALLBACK = 
            new DiffUtil.ItemCallback<BuddyRanking>() {
        @Override
        public boolean areItemsTheSame(
                @NonNull BuddyRanking oldItem, @NonNull BuddyRanking newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(
                @NonNull BuddyRanking oldItem, @NonNull BuddyRanking newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public BuddiesRankingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return BuddiesRankingViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull BuddiesRankingViewHolder holder, int position) {
        BuddyRanking current = getItem(position);
        Buddies buddy = buddiesMap.get(current.getBuddyId());
        if (buddy != null) {
            holder.bind(current, buddy);
        }
    }

    public void setBuddies(Map<Integer, Buddies> buddiesMap) {
        this.buddiesMap = buddiesMap;
        notifyDataSetChanged();
    }
}
