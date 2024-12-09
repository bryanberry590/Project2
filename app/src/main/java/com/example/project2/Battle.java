package com.example.project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.project2.databinding.BattleBinding;
import com.example.project2.database.CreatureBuddyRepository;
import com.example.project2.database.entities.Buddies;

public class Battle extends AppCompatActivity {
    private BattleBinding binding;
    private CreatureBuddyRepository repository;
    public boolean playerIsDefending = false;
    public boolean enemyIsDefending = false;
    //id, name, health, attack, defense, exp, imageSource, isStarter
    Buddies player = new Buddies("test Player", 20, 5, 4, 0, "@drawable/bulbasaur");
    Buddies enemy = new Buddies("test Enemy", 15, 3, 2, 0, "@drawable/charizard");
    private int playerHp = player.getHealth();
    private int enemyHp = enemy.getHealth();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.battle);
        binding = BattleBinding.inflate(getLayoutInflater());
        startPlayerTurn(binding);
    }

    public void startPlayerTurn(BattleBinding binding) {
        binding.Attack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                attack(player, enemy);
                enemyIsDefending = false;
                if(enemy.getHealth() <= 0){
                    playerWin();
                }
                enemyTurnBegin(enemy, player);
            }
        });
        binding.Defend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                playerIsDefending = true;
                enemyIsDefending = false;
                enemyTurnBegin(enemy, player);
            }
        });
    }

    public void attack(Buddies attacker, Buddies defender) {
        if (playerIsDefending || enemyIsDefending) {
            defender.setHealth(defender.getHealth() - attacker.getAttack() - 2 * defender.getDefense());
        } else {
            defender.setHealth(defender.getHealth() - attacker.getAttack() - defender.getDefense());
        }
    }

    public void enemyTurnBegin(Buddies enemy, Buddies player) {
        int choice = (int) (Math.random() * 4 + 1);
        if (choice < 3) {
            attack(enemy, player);
            if(player.getHealth() <= 0){
                playerLose();
            }
        } else {
            enemyIsDefending = true;
        }
        playerIsDefending = false;
    }
    public void playerWin(){
        Toast.makeText(Battle.this, "You WON", Toast.LENGTH_SHORT).show();
        player.setHealth(playerHp);
    }
    public void playerLose(){
        Toast.makeText(Battle.this, "You LOST", Toast.LENGTH_SHORT).show();
        enemy.setHealth(enemyHp);

    }
    static Intent battleIntent(Context context) {
        Intent intent = new Intent(context,Battle.class);
        //intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }
}