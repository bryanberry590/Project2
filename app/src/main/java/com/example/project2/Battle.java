package com.example.project2;


import static com.example.project2.MainActivity.characterInfoIntent;
import static com.example.project2.MainActivity.mainActivityIntent;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.project2.database.CreatureBuddyRepository;
import com.example.project2.database.entities.User;
import com.example.project2.databinding.BattleBinding;
import com.example.project2.database.CreatureBuddyRepository;
import com.example.project2.database.entities.Buddies;

public class Battle extends AppCompatActivity {

    private BattleBinding binding;
    private CreatureBuddyRepository repository;
    private int currUserId;
    private int buddyId;

    // Remove these fields since they’re now in BattleLogic
    // public boolean playerIsDefending = false;
    // public boolean enemyIsDefending = false;

    private int playerHp;
    private int enemyHp;

    Buddies player;
    Buddies enemy;

    boolean playerLoaded = false;
    boolean enemyLoaded = false;

    private BattleLogic battleLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.battle);
        binding = BattleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = CreatureBuddyRepository.getRepository(getApplication());
        buddyId = getIntent().getIntExtra("BUDDY_ID", -1);
        currUserId = getIntent().getIntExtra("USER_ID", -1);

        getPlayerBuddy(buddyId);
        getEnemyBuddy(getRandomNum());
    }

    private int getRandomNum(){
        int randomInt = (int)(Math.random() * 9 + 1);
        return randomInt;
    }

    private void getEnemyBuddy(int buddyId){
        LiveData<Buddies> currEnemyBuddy = repository.getBuddiesById(buddyId);
        currEnemyBuddy.observe(this, buddy -> {
            if(buddy != null){
                enemy = buddy;
                enemyHp = enemy.getHealth();

                binding.character1.setImageResource(
                        getResources().getIdentifier(enemy.getImageSource(), "drawable", getPackageName())
                );
                enemyLoaded = true;
                startBattleIfReady();
            }
        });
    }

    private void getPlayerBuddy(int buddyId){
        LiveData<Buddies> currBuddy = repository.getBuddiesById(buddyId);
        currBuddy.observe(this, buddy -> {
            if(buddy != null){
                player = buddy;
                playerHp = player.getHealth();

                binding.character2.setImageResource(
                        getResources().getIdentifier(player.getImageSource(), "drawable", getPackageName())
                );
                playerLoaded = true;
                startBattleIfReady();
            }
        });
    }

    private void startBattleIfReady() {
        if (playerLoaded && enemyLoaded) {
            battleLogic = new BattleLogic(player, enemy);
            startPlayerTurn();
        }
    }

    public void startPlayerTurn() {
        binding.health2.setText(String.format("%d/%d", battleLogic.getPlayerHealth(), battleLogic.getPlayerInitialHealth()));
        binding.health.setText(String.format("%d/%d", battleLogic.getEnemyHealth(), battleLogic.getEnemyInitialHealth()));

        binding.Attack.setOnClickListener(view -> {
            int damage = battleLogic.attack(player, enemy);
            battleLogic.setEnemyDefending(false);
            Toast.makeText(Battle.this, enemy.getName()+" took " + damage + " damage!", Toast.LENGTH_SHORT).show();

            if(battleLogic.isEnemyDead()){
                playerWin();
            } else {
                // enemy turn
                boolean playerDied = battleLogic.enemyTurn();
                if (playerDied) {
                    Toast.makeText(Battle.this, player.getName()+" took damage!", Toast.LENGTH_SHORT).show();
                    playerLose();
                } else {
                    Toast.makeText(Battle.this, enemy.getName()+" took damage!", Toast.LENGTH_SHORT).show();
                }
                battleLogic.setPlayerDefending(false);
                startPlayerTurn();
            }
        });

        binding.Defend.setOnClickListener(view -> {
            Toast.makeText(Battle.this, player.getName() + " is defending!", Toast.LENGTH_SHORT).show();
            battleLogic.setPlayerDefending(true);
            battleLogic.setEnemyDefending(false);

            boolean playerDied = battleLogic.enemyTurn();
            if (playerDied) {
                playerLose();
            }
            battleLogic.setPlayerDefending(false);
            startPlayerTurn();
        });
    }

    public void playerWin(){
        Toast.makeText(Battle.this, "You WON", Toast.LENGTH_SHORT).show();
        battleLogic.resetPlayerHealth();
        repository.incrementWins(buddyId);
        Intent newIntent = mainActivityIntent(getApplicationContext(), buddyId, currUserId);
        startActivity(newIntent);
    }

    public void playerLose(){
        Toast.makeText(Battle.this, "You LOST", Toast.LENGTH_SHORT).show();
        battleLogic.resetEnemyHealth();
        repository.incrementLosses(buddyId);
        Intent newIntent = mainActivityIntent(getApplicationContext(), buddyId, currUserId);
        startActivity(newIntent);
    }

    static Intent mainActivityIntent(Context context, int buddyId, int currUserId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("BUDDY_ID", buddyId);
        intent.putExtra("USER_ID", currUserId);
        return intent;
    }

    static Intent characterInfoIntent(Context context, int buddyId, int currUserId) {
        Intent intent = new Intent(context, CharacterInformation.class);
        intent.putExtra("BUDDY_ID", buddyId);
        intent.putExtra("USER_ID", currUserId);
        return intent;
    }

}