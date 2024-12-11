package com.example.project2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.example.project2.database.CreatureBuddyRepository;
import com.example.project2.database.entities.User;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project2.databinding.SelectCreatureBuddyBinding;


public class SelectCreatureBuddyActivity extends AppCompatActivity {
    private SelectCreatureBuddyBinding binding;



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
        setContentView(R.layout.select_creature_buddy);
        binding = SelectCreatureBuddyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = CreatureBuddyRepository.getRepository(getApplication());

        currUserId = getIntent().getIntExtra("USER_ID", -1);

        loginUser(savedInstanceState);

        Log.d("SELECT SCREEN LOGGED IN USER ID", "LOGGED IN USER ID IN SELECT SCREEN IS " + currUserId);
        int selectedStarter1 = 1;
        int selectedStarter2 = 2;
        int selectedStarter3 = 3;

        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = mainActivityIntent(getApplicationContext(), selectedStarter1, selectedStarter2, selectedStarter3, currUserId);
                startActivity(intent);
            }
        });

    }

    static Intent mainActivityIntent(Context context, int id1, int id2, int id3, int userId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("STARTER1", id1);
        intent.putExtra("STARTER2", id2);
        intent.putExtra("STARTER3", id3);
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
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(SelectCreatureBuddyActivity.this); //TODO: change to currentClass.this
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