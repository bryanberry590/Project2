package com.example.project2.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.project2.database.entities.Buddies;

import java.util.List;

@Dao
public interface BuddiesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Buddies... buddies);

    @Delete
    void delete(Buddies buddies);

    @Query("SELECT * FROM " + CreatureBuddyDatabase.BuddiesTable + " ORDER BY name")
    LiveData<List<Buddies>> getAllBuddies();

    @Query("SELECT * FROM " + CreatureBuddyDatabase.BuddiesTable + " ORDER BY name")
    List<Buddies> getAllBuddiesSync();

    @Query("DELETE FROM " + CreatureBuddyDatabase.BuddiesTable)
    void deleteAll();

    @Query("SELECT * FROM " + CreatureBuddyDatabase.BuddiesTable + " WHERE name == :name")
    LiveData<Buddies> getBuddiesByName(String name);

    @Query("SELECT * FROM " + CreatureBuddyDatabase.BuddiesTable + " WHERE name == :name")
    Buddies getBuddiesByNameSync(String name);

    @Query("SELECT * FROM " + CreatureBuddyDatabase.BuddiesTable + " WHERE id == :buddiesId")
    LiveData<Buddies> getBuddiesById(int buddiesId);

    @Query("SELECT * FROM " + CreatureBuddyDatabase.BuddiesTable + " WHERE id == :buddiesId")
    Buddies getBuddiesByIdSync(int buddiesId);

    @Query("UPDATE " + CreatureBuddyDatabase.BuddiesTable + " SET name = :newName, health = :newHealth, attack = :newAttack, defense = :newDefense, exp = :newExp, imageSource = :newImage WHERE id = :buddiesId")
    void updateBuddies(int buddiesId, String newName, int newHealth, int newAttack, int newDefense, int newExp, String newImage);

    @Query("DELETE FROM " + CreatureBuddyDatabase.BuddiesTable + " WHERE id == :buddiesId")
    void deleteBuddies(int buddiesId);

}
