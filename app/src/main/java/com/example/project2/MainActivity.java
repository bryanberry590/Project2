package com.example.project2;

import java.util.Random;
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
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.project2.database.entities.Buddies;
import com.example.project2.databinding.ActivityMainBinding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.project2.database.CreatureBuddyRepository;
import com.example.project2.database.entities.User;

public class MainActivity extends AppCompatActivity {

    private static final String MAIN_ACTIVITY_USER_ID = "com.example.project2.MAIN_ACTIVITY_USER_ID";
    static final String SHARED_PREFERENCE_USERID_VALUE = "com.example.project2.SHARED_PREFERENCE_USERID_VALUE";
    static final String SHARED_PREFERENCE_USERID_KEY = "com.example.project2.SHARED_PREFERENCE_USERID_KEY";
    private static final int LOGGED_OUT = -1;

    private CreatureBuddyRepository repository;
    public static final String TAG = "Project_2";
    private int loggedInUserId = -1;
    private User user;
    private int clickedBuddyId;

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = CreatureBuddyRepository.getRepository(getApplication());

        //fetch the first 3 creaturebuddies
        int rand1 = getRandomNum(0, 0, 0);
        int rand2 = getRandomNum(rand1, 0, 0);
        int rand3 = getRandomNum(rand1, rand2, 0);
        Log.d("RANDOM BUDDY NUMBER GENERATOR", "Rand numbers are " + rand1 + ", " + rand2 + ", " + rand3);

        LiveData<Buddies> buddy1LiveData = repository.getBuddiesById(rand1);
        LiveData<Buddies> buddy2LiveData = repository.getBuddiesById(rand2);
        LiveData<Buddies> buddy3LiveData = repository.getBuddiesById(rand3);
        //System.out.println("Buddy 1 live data is : " + buddy1LiveData.toString());

        //Retrieves the Image Buttons by ID
        ImageButton creature1ImageButton = (ImageButton)findViewById(R.id.creature1);
        ImageButton creature2ImageButton = (ImageButton)findViewById(R.id.creature2);
        ImageButton creature3ImageButton = (ImageButton)findViewById(R.id.creature3);

        //these 3 lines set each of the images for the buttons for the creature buddies
        buddyImgObserver(buddy1LiveData, creature1ImageButton);
        buddyImgObserver(buddy2LiveData, creature2ImageButton);
        buddyImgObserver(buddy3LiveData, creature3ImageButton);


        loginUser(savedInstanceState);
        if (loggedInUserId == -1) {
            Intent intent = LoginActivity.loginIntent(getApplicationContext());
            startActivity(intent);
        }
        binding.hiddenBtn.setVisibility(View.VISIBLE);

        binding.hiddenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "You are an admin", Toast.LENGTH_SHORT).show();
                Intent intent = adminActivityIntent(getApplicationContext(), loggedInUserId);
                startActivity(intent);
            }
        });

        binding.creature1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this, "You selected creature 1", Toast.LENGTH_SHORT).show();
                passIdForBuddy(buddy1LiveData);
            }
        });

        binding.creature2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this, "You selected creature 2", Toast.LENGTH_SHORT).show();
                passIdForBuddy(buddy2LiveData);
            }
        });

        binding.creature3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this, "You selected creature 3", Toast.LENGTH_SHORT).show();
                passIdForBuddy(buddy3LiveData);
            }
        });
    }

    private int getRandomNum(int param1, int param2, int param3){
        int randomInt = (int)(Math.random() * 9 + 1);
        while(randomInt == param1 || randomInt == param2 || randomInt == param3){
            randomInt = (int)(Math.random() * 9 + 1);
        }
        return randomInt;
    }

    private void passIdForBuddy(LiveData<Buddies> buddy){
        buddy.observe(this, currBuddy -> {
            if(currBuddy != null) {
                clickedBuddyId = currBuddy.getId();
                //now pass the id to the character info activity
                Toast.makeText(MainActivity.this, "You selected creature " + clickedBuddyId, Toast.LENGTH_SHORT).show();
                Intent newIntent = characterInfoIntent(getApplicationContext(), clickedBuddyId, loggedInUserId);
                startActivity(newIntent);
            }
        });
    }

    private void buddyImgObserver(LiveData<Buddies> buddy, ImageButton creatureBtn){
        buddy.observe(this, currBuddy -> {
            if(currBuddy != null){
                creatureBtn.setImageResource(
                        getResources().getIdentifier(currBuddy.getImageSource(), "drawable", getPackageName())
                );
            }
        });
    }


    static Intent mainActivityIntent(Context context, int userId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }

    static Intent adminActivityIntent(Context context, int userId) {
        Intent intent = new Intent(context, AdminActivity.class);
        intent.putExtra("USER_ID", userId);
        return intent;
    }

    private void loginUser(Bundle savedInstanceState) {
        //check shared reference for logged in user / Dr.C goes over this in video 11 @ around 22 min
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREFERENCE_USERID_KEY,
                Context.MODE_PRIVATE); // making it private makes it only accessible by the app and not system wide

        loggedInUserId = sharedPreferences.getInt(SHARED_PREFERENCE_USERID_VALUE, LOGGED_OUT);
        if (loggedInUserId != LOGGED_OUT) {
            return;
        }
        //check intent for logged in user
        loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);
        Log.i(TAG, "Logged in user id: " + loggedInUserId);
        if (loggedInUserId == LOGGED_OUT) {
            return;
        }
        LiveData<User> userObserver = repository.getUserByUserId(loggedInUserId);
        userObserver.observe(this, retrievedUser -> {
            if (retrievedUser != null) {
                user = retrievedUser;

                String text = binding.welcomeMessage.getText().toString().toUpperCase() + " " + user.getUsername().toUpperCase();
                binding.welcomeMessage.setText(text);
                //System.out.println("The new user is: " + this.user);
                if(loggedInUserId == 1){
                    binding.hiddenBtn.setVisibility(View.VISIBLE);
                } else {
                    binding.hiddenBtn.setVisibility(View.INVISIBLE);
                }
                invalidateOptionsMenu();
            }else {
                Log.d(TAG, "No user found with id: " + loggedInUserId);
            }
        });
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
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
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

    static Intent characterInfoIntent(Context context, int clickedBuddyId, int userId) {
        Intent intent = new Intent(context, CharacterInformation.class);
        intent.putExtra("BUDDY_ID", clickedBuddyId);
        intent.putExtra("USER_ID", userId);
        return intent;
    }
}