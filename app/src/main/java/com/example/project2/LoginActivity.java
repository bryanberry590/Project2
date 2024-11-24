package com.example.project2;

import android.os.Bundle;

import com.example.project2.databinding.ActivityMainBinding;
import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {

    private activityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = activityLoginBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_login);

    }
}