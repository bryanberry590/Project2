package com.example.project2;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class editCreatureBuddyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_creature_buddy);

        Spinner creatureTypeSpinner = findViewById(R.id.creatureTypeSpinner);

        // Create an ArrayAdapter using a simple spinner layout and your list of options
        String[] creatureTypes = {"Fire", "Water", "Grass", "Electric"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                creatureTypes
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        creatureTypeSpinner.setAdapter(adapter);

        // Set a listener to handle selection
        creatureTypeSpinner.setOnItemSelectedListener(new itemSelectedListener());
    }

}