package com.example.project2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.project2.database.CreatureBuddyRepository;
import com.example.project2.database.entities.User;
import android.util.Log;
import android.view.View;

import com.example.project2.database.entities.User;
import com.example.project2.databinding.ActivityMainBinding;
import com.example.project2.databinding.EditCreatureBuddyBinding;
import com.example.project2.databinding.SelectCreatureBuddyBinding;

public class EditCreatureBuddyActivity extends AppCompatActivity {

    private EditCreatureBuddyBinding binding;

    private int loggedInUserId = -1;
    private static final String MAIN_ACTIVITY_USER_ID = "com.example.project2.MAIN_ACTIVITY_USER_ID";
    static final String SHARED_PREFERENCE_USERID_VALUE = "com.example.project2.SHARED_PREFERENCE_USERID_VALUE";
    static final String SHARED_PREFERENCE_USERID_KEY = "com.example.project2.SHARED_PREFERENCE_USERID_KEY";
    private static final int LOGGED_OUT = -1;
    private User user;
    private CreatureBuddyRepository repository;
    private int currUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_creature_buddy);
        binding = EditCreatureBuddyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        currUserId = getIntent().getIntExtra("USER_ID", -1);
        repository = CreatureBuddyRepository.getRepository(getApplication());
        loginUser(savedInstanceState);

        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = mainActivityIntent(getApplicationContext(), currUserId);
                startActivity(intent);
            }
        });
    }

    static Intent mainActivityIntent(Context context, int userId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }

    private void loginUser(Bundle savedInstanceState) {
        LiveData<User> userObserver = repository.getUserByUserId(currUserId);
        userObserver.observe(this, retrievedUser -> {
            if (retrievedUser != null) {
                user = retrievedUser;
                invalidateOptionsMenu();
            }else {
                Log.d("CharacterInformation Error MSG", "No user found with id: " + loggedInUserId);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.logoutMenuItem);
        item.setVisible(true);
        if (user == null) {
            return false;
        }
        item.setTitle(user.getUsername());
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                showLogoutDialog();
                return false;
            }
        });


        MenuItem profile = menu.findItem(R.id.profileMenuItem);
        profile.setVisible(true);
        profile.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                Intent newIntent = profileIntent(getApplicationContext());
                startActivity(newIntent);
                return false;
            }
        });
        return true;
    }


    // from alert_dialog slides on canvas
    private void showLogoutDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(EditCreatureBuddyActivity.this); //TODO: change to currentClass.this
        final AlertDialog alertDialog = alertBuilder.create();


        alertBuilder.setMessage("Do you want to Logout?");
        alertBuilder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                logout();
            }
        });
        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                alertDialog.dismiss();
            }
        });
        alertBuilder.create().show();
    }


    private void logout() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREFERENCE_USERID_KEY,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor SharedPrefEditor = sharedPreferences.edit();
        SharedPrefEditor.putInt(SHARED_PREFERENCE_USERID_KEY, LOGGED_OUT);
        SharedPrefEditor.apply();
        getIntent().putExtra(SHARED_PREFERENCE_USERID_VALUE, LOGGED_OUT);
        startActivity(LoginActivity.loginIntent(getApplicationContext()));
    }


    static Intent profileIntent(Context context) {
        return new Intent(context, ProfileActivity.class);
    }

}