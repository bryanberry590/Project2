package com.example.project2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.project2.database.CreatureBuddyRepository;
import com.example.project2.database.entities.User;
import com.example.project2.databinding.ActivityLoginBinding;


public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private CreatureBuddyRepository repository;
    private User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = CreatureBuddyRepository.getRepository(getApplication());

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyUser();
            }
        });

        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = signUpIntent(getApplicationContext());
                startActivity(newIntent);
            }
        });
    }

    private void verifyUser() {
        String username = binding.usernameLoginEditText.getText().toString();
        if (username.isEmpty()) {
            toastMaker("Username cannot be blank");
            return;
        }
        LiveData<User> userObserver = repository.getUserByUsername(username);
        userObserver.observe(this, user -> {
            if (user != null) {
                String password = binding.passwordLoginEditText.getText().toString();
                if (password.equals(user.getPassword())) {
                    SharedPreferences sharedPreferences = getApplicationContext().
                            getSharedPreferences(MainActivity.SHARED_PREFERENCE_USERID_KEY,
                                    Context.MODE_PRIVATE);
                    SharedPreferences.Editor SharedPrefEditor = sharedPreferences.edit();
                    SharedPrefEditor.putInt(MainActivity.SHARED_PREFERENCE_USERID_KEY, user.getId());
                    SharedPrefEditor.apply();

                    startActivity(MainActivity.mainActivityIntent(getApplicationContext(), user.getId()));
                } else {
                    toastMaker("Invalid password");
                    binding.passwordLoginEditText.setSelection(0);
                }
            } else {
                toastMaker(String.format("User %s not found", username));
                binding.usernameLoginEditText.setSelection(0);
            }
        });
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    static Intent loginIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    static Intent signUpIntent(Context context) { return new Intent(context, SignUpActivity.class); }
}