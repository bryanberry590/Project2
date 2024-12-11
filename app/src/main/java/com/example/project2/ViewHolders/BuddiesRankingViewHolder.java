package com.example.project2.ViewHolders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.project2.R;
import com.example.project2.database.entities.BuddyRanking;
import com.example.project2.database.entities.Buddies;

public class BuddiesRankingViewHolder extends RecyclerView.ViewHolder {
    private final ImageView buddyImageView;
    private final TextView buddyNameView;
    private final TextView winPercentageView;
    private final TextView winsLossesView;

    private BuddiesRankingViewHolder(View itemView) {
        super(itemView);
        buddyImageView = itemView.findViewById(R.id.buddyImage);
        buddyNameView = itemView.findViewById(R.id.buddyName);
        winPercentageView = itemView.findViewById(R.id.winPercentage);
        winsLossesView = itemView.findViewById(R.id.winsLosses);
    }

    public void bind(BuddyRanking ranking, Buddies buddy) {
        // Set buddy name
        buddyNameView.setText(buddy.getName());
        
        // Set win percentage
        String winPercentageText = String.format("%.1f%%", ranking.getWinPercentage());
        winPercentageView.setText(winPercentageText);
        
        // Set wins/losses
        String winsLossesText = String.format("Wins: %d | Losses: %d", 
            ranking.getWins(), ranking.getLosses());
        winsLossesView.setText(winsLossesText);
        
        // Set buddy image - assuming the imageSource is a drawable resource name
        try {
            Context context = itemView.getContext();
            String imageName = buddy.getImageSource().replace("@drawable/", "");
            int resourceId = context.getResources().getIdentifier(
                imageName, "drawable", context.getPackageName());
            buddyImageView.setImageResource(resourceId);
        } catch (Exception e) {
            // If image loading fails, we could set a default image or leave it empty
            e.printStackTrace();
        }
    }

    public static BuddiesRankingViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.buddy_ranking_item, parent, false);
        return new BuddiesRankingViewHolder(view);
    }
}
