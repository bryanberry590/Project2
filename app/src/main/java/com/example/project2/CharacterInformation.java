package com.example.project2;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project2.database.entities.Buddies;
import com.example.project2.databinding.CharacterInformationBinding;

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


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_information);
        binding = CharacterInformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = CreatureBuddyRepository.getRepository(getApplication());

        buddyId = getIntent().getIntExtra("BUDDY_ID", -1);
        currUserId = getIntent().getIntExtra("USER_ID", -1);
        Log.d("CharacterInformation", "Selected Buddy Id: " + buddyId);
        Log.d("CURRENT USER ID", "THE CURRENT USER ID IS: " + currUserId);

        LiveData<Buddies> selectedBuddyLiveData = repository.getBuddiesById(buddyId);
        setBuddyInfo(selectedBuddyLiveData, buddyId);

    }

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
}
