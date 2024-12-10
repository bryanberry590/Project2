package com.example.project2;

import android.os.Bundle;
import android.util.Log;

import com.example.project2.databinding.CharacterInformationBinding;

import androidx.appcompat.app.AppCompatActivity;


public class CharacterInformation extends AppCompatActivity{

    private CharacterInformationBinding binding;
    private int currUserId;
    private int buddyId;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_information);
        binding = CharacterInformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        buddyId = getIntent().getIntExtra("BUDDY_ID", -1);
        currUserId = getIntent().getIntExtra("USER_ID", -1);
        Log.d("CharacterInformation", "Selected Buddy Id: " + buddyId);
        Log.d("CURRENT USER ID", "THE CURRENT USER ID IS: " + currUserId);

    }
}
