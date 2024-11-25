package com.example.project2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.project2.databinding.ActivityProfileBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.project2.database.CreatureBuddyRepository;
import com.example.project2.database.entities.User;

public class ProfileActivity extends AppCompatActivity{
    private static final String MAIN_ACTIVITY_USER_ID = "com.example.project2.MAIN_ACTIVITY_USER_ID";
    static final String SHARED_PREFERENCE_USERID_VALUE = "com.example.project2.SHARED_PREFERENCE_USERID_VALUE";
    static final String SHARED_PREFERENCE_USERID_KEY = "com.example.project2.SHARED_PREFERENCE_USERID_KEY";
    private static final int LOGGED_OUT = -1;

    private CreatureBuddyRepository repository;
    public static final String TAG = "Project_2";
    private User user;
    private ActivityProfileBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = CreatureBuddyRepository.getRepository(getApplication());
        setEditText();

        binding.updateAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: route back to the login page, update the user account information accordingly,
                // logout user, and take back to login

                updateUserAccount();

                Intent intent = loginIntent(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    private void updateUserAccount(){
        return;
    }

    private void setEditText() {
        Log.d(TAG, "setEditText() called");
        //Toast.makeText(this, "setEditText() called", Toast.LENGTH_SHORT).show();

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(
                ProfileActivity.SHARED_PREFERENCE_USERID_KEY, Context.MODE_PRIVATE);

        int userId = sharedPreferences.getInt(ProfileActivity.SHARED_PREFERENCE_USERID_KEY, -1);
        Toast.makeText(this, "userId : " + userId, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "User id is " + userId);

        repository.getUserByUserId(userId).observe(this, retrievedUser -> {
            if (retrievedUser != null) {
                user = retrievedUser;
                binding.usernameLoginEditText.setText(retrievedUser.getUsername());

            } else {
                Log.d(TAG, "User not found in database");
            }
        });
    }

    static Intent loginIntent(Context context) {
        return new Intent(context, LoginActivity.class); //TODO: change to profileActivity.class
    }

    static Intent profileIntent(Context context) {
        return new Intent(context, ProfileActivity.class); //TODO: change to profileActivity.class
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    // after clicking log in username is displayed on top right
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
        profile.setVisible(false);
//        profile.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
//                Intent newIntent = profileIntent(getApplicationContext());
//                startActivity(newIntent);
//                return false;
//            }
//        });
        return true;
    }

    // from alert_dialog slides on canvas
    private void showLogoutDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ProfileActivity.this);
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
}
