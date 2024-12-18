package com.example.project2.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project2.database.CreatureBuddyDatabase;

import java.util.Objects;

@Entity(tableName = CreatureBuddyDatabase.BuddiesTable)
public class Buddies {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private int health;
    private int attack;
    private int defense;
    private int exp;
    private String imageSource;
    private boolean isStarter;

    public Buddies(String name, int health, int attack, int defense, int exp, String imageResource){
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.exp = exp;
        this.imageSource = imageResource;
        isStarter = false;
    }

    public Buddies(){
    }

    @Override
    public String toString() {
        return "Buddies{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", health=" + health +
                ", attack=" + attack +
                ", defense=" + defense +
                ", exp=" + exp +
                ", isStarter=" + isStarter +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Buddies buddies = (Buddies) o;
        return id == buddies.id && health == buddies.health && attack == buddies.attack && defense == buddies.defense && exp == buddies.exp && isStarter == buddies.isStarter && Objects.equals(name, buddies.name) && Objects.equals(imageSource, buddies.imageSource);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, health, attack, defense, exp, imageSource, isStarter);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public boolean isStarter() {
        return isStarter;
    }

    public void setStarter(boolean starter) {
        isStarter = starter;
    }
}
