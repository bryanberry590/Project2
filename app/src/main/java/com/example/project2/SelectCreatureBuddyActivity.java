package com.example.project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project2.databinding.ActivityMainBinding;
import com.example.project2.databinding.SelectCreatureBuddyBinding;


public class SelectCreatureBuddyActivity extends AppCompatActivity {
    private SelectCreatureBuddyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_select_starters);
        binding = SelectCreatureBuddyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int selectedStarter1 = 1;
        int selectedStarter2 = 2;
        int selectedStarter3 = 3;

        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = mainActivityIntent(getApplicationContext(), selectedStarter1, selectedStarter2, selectedStarter3);
                startActivity(intent);
            }
        });

    }

    static Intent mainActivityIntent(Context context, int id1, int id2, int id3) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("STARTER1", id1);
        intent.putExtra("STARTER2", id2);
        intent.putExtra("STARTER3", id3);
        return intent;
    }
}