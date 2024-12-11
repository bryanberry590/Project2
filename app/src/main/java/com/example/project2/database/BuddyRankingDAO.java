package com.example.project2.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.project2.database.entities.BuddyRanking;

import java.util.List;

@Dao
public interface BuddyRankingDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(BuddyRanking... rankings);

    @Query("SELECT * FROM " + CreatureBuddyDatabase.BUDDY_RANKING_TABLE + " ORDER BY winPercentage DESC")
    LiveData<List<BuddyRanking>> getAllRankings();

    @Query("SELECT * FROM " + CreatureBuddyDatabase.BUDDY_RANKING_TABLE + " ORDER BY winPercentage DESC")
    List<BuddyRanking> getAllRankingsSync();

    @Query("SELECT * FROM " + CreatureBuddyDatabase.BUDDY_RANKING_TABLE + " WHERE buddyId = :buddyId")
    LiveData<BuddyRanking> getRankingByBuddyId(int buddyId);

    @Query("SELECT * FROM " + CreatureBuddyDatabase.BUDDY_RANKING_TABLE + " WHERE buddyId = :buddyId")
    BuddyRanking getRankingByBuddyIdSync(int buddyId);

    @Query("UPDATE " + CreatureBuddyDatabase.BUDDY_RANKING_TABLE + 
           " SET wins = wins + 1, " +
           "winPercentage = (CAST((wins + 1) AS FLOAT) / (wins + losses + 1)) * 100 " +
           "WHERE buddyId = :buddyId")
    void incrementWins(int buddyId);

    @Query("UPDATE " + CreatureBuddyDatabase.BUDDY_RANKING_TABLE + 
           " SET losses = losses + 1, " +
           "winPercentage = (CAST(wins AS FLOAT) / (wins + losses + 1)) * 100 " +
           "WHERE buddyId = :buddyId")
    void incrementLosses(int buddyId);

    @Query("DELETE FROM " + CreatureBuddyDatabase.BUDDY_RANKING_TABLE)
    void deleteAll();

    @Query("DELETE FROM " + CreatureBuddyDatabase.BUDDY_RANKING_TABLE + " WHERE buddyId = :buddyId")
    void deleteRanking(int buddyId);
}