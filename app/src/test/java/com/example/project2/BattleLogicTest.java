package com.example.project2;

import static org.junit.Assert.*;

import com.example.project2.database.entities.Buddies;

import org.junit.Before;
import org.junit.Test;

public class BattleLogicTest {
    private Buddies player;
    private Buddies enemy;
    private BattleLogic logic;

    @Before
    public void setUp(){
        player = new Buddies("Player", 20, 5, 2, 0, "@drawable/bulbasaur");
        enemy = new Buddies("Enemy", 15, 5, 2, 0, "@drawable/charizard");
        logic = new BattleLogic(player, enemy);
    }

    //BRYAN
    @Test
    public void testAttack() {
        int damage = logic.attack(player, enemy);
        //test the the attack function is working properly
        assertEquals(12, enemy.getHealth()); // 15 - 3 = 12
        assertEquals(3, damage);
    }

    //BRYAN
    @Test
    public void testDefending() {
        logic.setEnemyDefending(true);
        int damage = logic.attack(player, enemy);
        // damage = 1
        // check that the enemy only takes 1 damage
        assertEquals(14, enemy.getHealth());
        assertEquals(1, damage);
    }

    //CHRIS
    @Test
    public void testPlayerWinCondition() {
        enemy.setHealth(1);
        logic.attack(player, enemy);
        //this will make sure the enemy dies correctly
        assertTrue(logic.isEnemyDead());
    }

    //CHRIS
    @Test
    public void testMultipleAttacksReduceHealth() {
        // Enemy is at 15 health to begin
        logic.attack(player, enemy); // First attack, enemy should lose 3 health, now 12
        logic.attack(player, enemy); // Second attack, another should lose 3 health, now 9

        assertEquals(9, logic.getEnemyHealth());
    }

    //SERGIO
    @Test
    public void testAttackMinimumDamageIsOne() {
        // Increase enemy's defense
        enemy.setDefense(10); // Big defense to reduce damage
        int damage = logic.attack(player, enemy);
        // this will check that the minimum damage that can be done is 1
        assertEquals(1, damage);
        assertEquals(14, logic.getEnemyHealth()); // 15 - 1 = 14
    }

    //SERGIO
    @Test
    public void testResetHealthRestoresHealth() {
        logic.attack(player, enemy); // Deal damage
        assertNotEquals(enemy.getHealth(), logic.getEnemyInitialHealth());

        logic.resetEnemyHealth();
        // This is to make sure that the enemy health gets reset with resetEnemyHealth
        assertEquals(logic.getEnemyInitialHealth(), enemy.getHealth());
    }

    //HUGO
    @Test
    public void testPlayerDefendingReducesDamage() {
        logic.setPlayerDefending(true);
        // make the enemy attack the player
        int damage = logic.attack(enemy, player);
        // defending should double the defense for that turn
        assertEquals(1, damage);
        assertEquals(19, logic.getPlayerHealth());
    }

    //HUGO
    @Test
    public void testEnemyDefendingReducesDamage() {
        logic.setEnemyDefending(true); //next have the player attack enemy
        int damage = logic.attack(player, enemy);
        // 5 - 2 = 3 damage
        // when the enemy defending: 5 - (2*2) = 5 - 4 = 1 damage
        assertEquals(1, damage);
        assertEquals(14, logic.getEnemyHealth());
    }
}