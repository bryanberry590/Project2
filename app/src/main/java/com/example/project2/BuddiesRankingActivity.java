package com.example.project2;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2.ViewHolders.BuddiesRankingAdapter;
import com.example.project2.ViewHolders.BuddiesRankingViewModel;
import com.example.project2.databinding.ActivityBuddiesRankingBinding;

public class BuddiesRankingActivity extends AppCompatActivity {
    private BuddiesRankingViewModel viewModel;
    private ActivityBuddiesRankingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        
        binding = ActivityBuddiesRankingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        // Set up window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set up back button
        binding.backButton.setOnClickListener(v -> {
            finish(); // This will close the current activity and return to MainActivity
        });

        // Set up RecyclerView
        RecyclerView recyclerView = binding.buddiesRankingRecyclerView;
        final BuddiesRankingAdapter adapter = new BuddiesRankingAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set up ViewModel
        viewModel = new ViewModelProvider(this).get(BuddiesRankingViewModel.class);
        
        // Observe buddy rankings
        viewModel.getAllBuddyRankings().observe(this, rankings -> {
            adapter.submitList(rankings);
        });

        // Observe buddies map
        viewModel.getBuddiesMap().observe(this, buddiesMap -> {
            adapter.setBuddies(buddiesMap);
        });

        // Set up test buttons
        binding.addWinButton.setOnClickListener(v -> {
            try {
                String buddyIdStr = binding.buddyIdInput.getText().toString();
                if (!buddyIdStr.isEmpty()) {
                    int buddyId = Integer.parseInt(buddyIdStr);
                    viewModel.incrementWins(buddyId);
                    Toast.makeText(this, "Added win for Buddy " + buddyId, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Please enter a Buddy ID", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, "Error updating wins: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.addLossButton.setOnClickListener(v -> {
            try {
                String buddyIdStr = binding.buddyIdInput.getText().toString();
                if (!buddyIdStr.isEmpty()) {
                    int buddyId = Integer.parseInt(buddyIdStr);
                    viewModel.incrementLosses(buddyId);
                    Toast.makeText(this, "Added loss for Buddy " + buddyId, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Please enter a Buddy ID", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, "Error updating losses: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}