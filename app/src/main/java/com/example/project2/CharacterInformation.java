package com.example.project2;

import android.os.Bundle;

import com.example.project2.databinding.CharacterInformationBinding;

import androidx.appcompat.app.AppCompatActivity;


public class CharacterInformation extends AppCompatActivity{

    private CharacterInformationBinding binding;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_information);
        binding = CharacterInformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
