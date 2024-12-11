package com.example.project2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import static com.example.project2.MainActivity.mainActivityIntent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.project2.database.entities.Buddies;
import com.example.project2.databinding.CharacterInformationBinding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.project2.database.CreatureBuddyRepository;
import com.example.project2.database.entities.User;

import org.w3c.dom.Text;

public class CharacterInformation extends AppCompatActivity{

    private CharacterInformationBinding binding;
    private int currUserId;
    private int buddyId;
    private CreatureBuddyRepository repository;

    private int loggedInUserId = -1;
    private static final String MAIN_ACTIVITY_USER_ID = "com.example.project2.MAIN_ACTIVITY_USER_ID";
    static final String SHARED_PREFERENCE_USERID_VALUE = "com.example.project2.SHARED_PREFERENCE_USERID_VALUE";
    static final String SHARED_PREFERENCE_USERID_KEY = "com.example.project2.SHARED_PREFERENCE_USERID_KEY";
    private static final int LOGGED_OUT = -1;
    private User user;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_information);
        binding = CharacterInformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = CreatureBuddyRepository.getRepository(getApplication());


        repository = CreatureBuddyRepository.getRepository(getApplication());

        buddyId = getIntent().getIntExtra("BUDDY_ID", -1);
        currUserId = getIntent().getIntExtra("USER_ID", -1);
//        Log.d("CharacterInformation", "Selected Buddy Id: " + buddyId);
//        Log.d("CURRENT USER ID", "THE CURRENT USER ID IS: " + currUserId);
        loginUser(savedInstanceState);

        LiveData<Buddies> selectedBuddyLiveData = repository.getBuddiesById(buddyId);
        setBuddyInfo(selectedBuddyLiveData, buddyId);

        binding.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = battleIntent(getApplicationContext());
                startActivity(newIntent);
            }
        });
    }

    static Intent battleIntent(Context context) {
        Intent intent = new Intent(context, Battle.class);
        return intent;    }

    private void setBuddyInfo(LiveData<Buddies> selectedBuddy, int buddyId){
        TextView statView = (TextView)findViewById(R.id.statTextViewBox);

        selectedBuddy.observe(this, buddy -> {
            if(buddy != null){
                statView.setText("Name: " + buddy.getName() + "\n\nHealth: " + buddy.getHealth() + "\n\nAttack: " + buddy.getAttack() + "\n\nDefense: " + buddy.getDefense());
                ImageView creatureImageButton = (ImageView)findViewById(R.id.creatureImageBox);
                creatureImageButton.setImageResource(getResources().getIdentifier(buddy.getImageSource(), "drawable", getPackageName()));
            }
        });
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
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(CharacterInformation.this);
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
