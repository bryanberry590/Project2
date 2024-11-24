package com.example.project2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import androidx.appcompat.app.AppCompatActivity;
import com.example.project2.database.CreatureBuddyRepository;
import com.example.project2.database.entities.User;
import com.example.project2.databinding.SignupBinding;



public class SignUpActivity extends AppCompatActivity {

    private SignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
