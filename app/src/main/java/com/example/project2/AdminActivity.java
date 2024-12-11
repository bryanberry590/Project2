package com.example.project2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class adminActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_welcome);

        // Initialize buttons
        Button editBaseStatButton = findViewById(R.id.editBaseStat);
        Button selectStarterButton = findViewById(R.id.selectStarter);

        // Set click listener for editBaseStat button
        editBaseStatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to EditCreatureBuddyActivity
                Intent intent = new Intent(adminActivity.this, editCreatureBuddyActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for selectStarter button
        selectStarterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to SelectCreatureBuddyActivity
                Intent intent = new Intent(adminActivity.this, selectCreatureBuddyActivity.class);
                startActivity(intent);
            }
        });
    }
}