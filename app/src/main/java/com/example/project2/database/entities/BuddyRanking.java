package com.example.project2.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;
import androidx.room.Ignore;

import com.example.project2.database.CreatureBuddyDatabase;

import java.util.Objects;

// BuddyRanking entity class, and link to Buddies table
@Entity(tableName = CreatureBuddyDatabase.BUDDY_RANKING_TABLE,
        foreignKeys = @ForeignKey(
            entity = Buddies.class,
            parentColumns = "id",
            childColumns = "buddyId",
            onDelete = ForeignKey.CASCADE
        ))
public class BuddyRanking {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int buddyId;
    private int wins;
    private int losses;
    private float winPercentage;

    @Ignore
    public BuddyRanking(int buddyId) {
        this.buddyId = buddyId;
        this.wins = 0;
        this.losses = 0;
        this.winPercentage = 0.0f;
    }

    public BuddyRanking() {
    }


    public void updateWinPercentage() {
        if (wins + losses > 0) { // will calculate win percentage if there are wins or losses
            this.winPercentage = (float) wins / (wins + losses) * 100;
        } else { // if there are no wins or losses, set the win percentage to 0
            this.winPercentage = 0.0f;
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuddyRanking that = (BuddyRanking) o;
        return id == that.id && buddyId == that.buddyId && wins == that.wins && losses == that.losses && Float.compare(winPercentage, that.winPercentage) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, buddyId, wins, losses, winPercentage);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBuddyId() {
        return buddyId;
    }

    public void setBuddyId(int buddyId) {
        this.buddyId = buddyId;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public float getWinPercentage() {
        return winPercentage;
    }

    public void setWinPercentage(float winPercentage) {
        this.winPercentage = winPercentage;
    }
}