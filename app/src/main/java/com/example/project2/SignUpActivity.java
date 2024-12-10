package com.example.project2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import androidx.appcompat.app.AppCompatActivity;
import com.example.project2.database.CreatureBuddyRepository;
import com.example.project2.database.entities.User;
import com.example.project2.databinding.SignupBinding;



public class SignUpActivity extends AppCompatActivity {

    private SignupBinding binding;
    private CreatureBuddyRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = CreatureBuddyRepository.getRepository(getApplication());


        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });
    }

    private void createUser(){
        String username = binding.usernameLoginEditText.getText().toString();
        String password = binding.passwordLoginEditText.getText().toString();
        String passwordCheck = binding.passwordCheckEditText.getText().toString();

        if(username.isEmpty() || password.isEmpty() || passwordCheck.isEmpty()){
            toastMaker("Text fields cannot be blank");
            return;
        } else {
            if(!password.equals(passwordCheck)){
                toastMaker("Passwords do not match");
                return;
            }

            repository.getUserByUsername(username).observe(this, existingUser ->{
                if(existingUser != null){
                    Log.d("SignUpActivity", "User already exists: " + existingUser.getUsername());
                    toastMaker("This username already exists");
                } else{
                    User user = new User(username, password);
                    insertUser(user);
                    Log.d("SignUpActivity", "New user created: " + username);
                }
            });
        }
    }

    private void insertUser(User user){
        repository.insertUser(user);
        Log.d("SignUpActivity", "User inserted: " + user.getUsername());
        toastMaker("User Created Successfully");
        finish();
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
