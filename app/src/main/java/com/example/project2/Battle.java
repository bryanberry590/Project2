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
    /*private static int  MAIN_ACTIVITY_USER_ID;
    static final String SHARED_PREFERENCE_USERID_KEY = "com.example.project2.SHARED_PREFERENCE_USERID_KEY";
    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(String.valueOf(MAIN_ACTIVITY_USER_ID),
            Context.MODE_PRIVATE);
    int userId = sharedPreferences.getInt(ProfileActivity.SHARED_PREFERENCE_USERID_KEY, -1);
    private CreatureBuddyRepository repository;*/
    private BattleBinding binding;
    public boolean playerIsDefending = false;
    public boolean enemyIsDefending = false;
    //Buddies player = new Buddies("test Player", 20, 5, 2, 0, "@drawable/bulbasaur");
    //Buddies enemy = new Buddies("test Enemy", 15, 5, 2, 0, "@drawable/charizard");
    private int playerHp;
    private int enemyHp;

    private int currUserId;
    private int buddyId;
    private CreatureBuddyRepository repository;
    Buddies player;
    Buddies enemy;
    boolean playerLoaded = false;
    boolean enemyLoaded = false;

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
        //Log.d("BATTLE SCREEN", "BuddyId: " + buddyId + " UserId: " + currUserId);
        //startPlayerTurn(binding);
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
            startPlayerTurn(binding);
        }
    }

    public void startPlayerTurn(BattleBinding binding) {
        binding.health2.setText(String.format("%d/%d", player.getHealth(), playerHp));
        binding.health.setText(String.format("%d/%d", enemy.getHealth(), enemyHp));
        binding.Attack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                attack(player, enemy);
                enemyIsDefending = false;
                if(enemy.getHealth() <= 0){
                    playerWin();
                }
                enemyTurnBegin(enemy, player, binding);
            }
        });
        binding.Defend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(Battle.this, player.getName() + " is defending!", Toast.LENGTH_SHORT).show();
                playerIsDefending = true;
                enemyIsDefending = false;
                enemyTurnBegin(enemy, player, binding);
            }
        });
    }

    public void attack(Buddies attacker, Buddies defender) {
        int damage;
        if (playerIsDefending || enemyIsDefending) {
            damage = attacker.getAttack() - 2 * defender.getDefense();
            if (damage<=0){
                damage = 1;
            }
            defender.setHealth(defender.getHealth() -damage);

        } else {
            damage = attacker.getAttack() - defender.getDefense();
            if (damage<=0){
                damage = 1;
            }
            defender.setHealth(defender.getHealth() - damage);
            if (defender.getHealth()<0){
                defender.setHealth(0);
            }
        }
        Toast.makeText(Battle.this, defender.getName()+" took " + damage + " damage!", Toast.LENGTH_SHORT).show();
    }

    public void enemyTurnBegin(Buddies enemy, Buddies player, BattleBinding binding) {
        int choice = (int) (Math.random() * 4 + 1);
        if (choice <= 3) {
            attack(enemy, player);
            if(player.getHealth() <= 0){
                playerLose();
            }
        } else {
            Toast.makeText(Battle.this, enemy.getName() + " is defending!", Toast.LENGTH_SHORT).show();
            enemyIsDefending = true;
        }
        playerIsDefending = false;
        startPlayerTurn(binding);
    }
    public void playerWin(){
        Toast.makeText(Battle.this, "You WON", Toast.LENGTH_SHORT).show();
        player.setHealth(playerHp);
        Intent newIntent = characterInfoIntent(getApplicationContext(), buddyId, currUserId);
        startActivity(newIntent);
    }
    public void playerLose(){
        Toast.makeText(Battle.this, "You LOST", Toast.LENGTH_SHORT).show();
        enemy.setHealth(enemyHp);
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