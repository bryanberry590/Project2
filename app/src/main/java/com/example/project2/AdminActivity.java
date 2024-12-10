package com.example.project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {
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
                Intent newIntent = editCreatureBuddyIntent(getApplicationContext());
                startActivity(newIntent);
            }
        });

        // Set click listener for selectStarter button
        selectStarterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to SelectCreatureBuddyActivity
                Intent newIntent = selectStartersIntent(getApplicationContext());
                startActivity(newIntent);
            }
        });
    }

    // create intent factories
    static Intent editCreatureBuddyIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }
    static Intent selectStartersIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

}