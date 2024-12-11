package com.example.project2;

import com.example.project2.database.entities.Buddies;

public class BattleLogic {
    private Buddies player;
    private Buddies enemy;
    private boolean playerIsDefending;
    private boolean enemyIsDefending;
    private int playerInitialHealth;
    private int enemyInitialHealth;

    public BattleLogic(Buddies player, Buddies enemy) {
        this.player = player;
        this.enemy = enemy;
        this.playerInitialHealth = player.getHealth();
        this.enemyInitialHealth = enemy.getHealth();
        this.playerIsDefending = false;
        this.enemyIsDefending = false;
    }

    public void setPlayerDefending(boolean defending) {
        this.playerIsDefending = defending;
    }

    public void setEnemyDefending(boolean defending) {
        this.enemyIsDefending = defending;
    }

    public int getPlayerHealth() {
        return player.getHealth();
    }

    public int getEnemyHealth() {
        return enemy.getHealth();
    }

    public int getPlayerInitialHealth() {
        return playerInitialHealth;
    }

    public int getEnemyInitialHealth() {
        return enemyInitialHealth;
    }

    public void resetPlayerHealth() {
        player.setHealth(playerInitialHealth);
    }

    public void resetEnemyHealth() {
        enemy.setHealth(enemyInitialHealth);
    }

    public int attack(Buddies attacker, Buddies defender) {
        int damage;
        if (playerIsDefending || enemyIsDefending) {
            damage = attacker.getAttack() - 2 * defender.getDefense();
        } else {
            damage = attacker.getAttack() - defender.getDefense();
        }

        if (damage <= 0) {
            damage = 1;
        }

        defender.setHealth(Math.max(defender.getHealth() - damage, 0));
        return damage;
    }

    public boolean isPlayerDead() {
        return player.getHealth() <= 0;
    }

    public boolean isEnemyDead() {
        return enemy.getHealth() <= 0;
    }

    // Simulate enemy turn logic here without UI code:
    // Returns true if player died, false otherwise.
    public boolean enemyTurn() {
        int choice = (int) (Math.random() * 4 + 1);
        if (choice <= 3) {
            attack(enemy, player);
            return isPlayerDead();
        } else {
            setEnemyDefending(true);
            return false;
        }
    }
}