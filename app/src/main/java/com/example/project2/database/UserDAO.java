package com.example.project2.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.project2.database.entities.User;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User... user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM " + CreatureBuddyDatabase.USER_TABLE + " ORDER BY username")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM " + CreatureBuddyDatabase.USER_TABLE + " ORDER BY username")
    List<User> getAllUsersSync();

    @Query("DELETE FROM " + CreatureBuddyDatabase.USER_TABLE)
    void deleteAll();

    @Query("SELECT * FROM " + CreatureBuddyDatabase.USER_TABLE + " WHERE username == :username")
    LiveData<User> getUserByUsername(String username);

    @Query("SELECT * FROM " + CreatureBuddyDatabase.USER_TABLE + " WHERE username == :username")
    User getUserByUsernameSync(String username);

    @Query("SELECT * FROM " + CreatureBuddyDatabase.USER_TABLE + " WHERE id == :userId")
    LiveData<User> getUserByUserId(int userId);

    @Query("SELECT * FROM " + CreatureBuddyDatabase.USER_TABLE + " WHERE id == :userId")
    User getUserByUserIdSync(int userId);

    @Query("UPDATE " + CreatureBuddyDatabase.USER_TABLE + " SET username = :newUsername, password = :newPassword WHERE id = :userId")
    void updateUser(int userId, String newUsername, String newPassword);

    @Query("DELETE FROM " + CreatureBuddyDatabase.USER_TABLE + " WHERE id == :userId")
    void deleteUser(int userId);

}