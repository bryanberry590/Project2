package com.example.project2.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.project2.MainActivity;
import com.example.project2.database.entities.Buddies;
import com.example.project2.database.entities.BuddyRanking;
import com.example.project2.database.entities.User;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class CreatureBuddyRepository {

    private final UserDAO userDAO;
    private final BuddiesDAO buddiesDAO;
    private final BuddyRankingDAO buddyRankingDAO;
    private static CreatureBuddyRepository repository;

    private CreatureBuddyRepository(Application application) {
        CreatureBuddyDatabase db = CreatureBuddyDatabase.getDatabase(application);
        this.userDAO = db.userDAO();
        this.buddiesDAO = db.buddiesDAO();
        this.buddyRankingDAO = db.buddyRankingDAO();
    }

    public static CreatureBuddyRepository getRepository(Application application) {
        if (repository != null) {
            return repository;
        }
        Future<CreatureBuddyRepository> future = CreatureBuddyDatabase.databaseWriteExecutor.submit(
                new Callable<CreatureBuddyRepository>() {
                    @Override
                    public CreatureBuddyRepository call() throws Exception {
                        return new CreatureBuddyRepository(application);
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.i(MainActivity.TAG, "There was a problem getting repository in repository");
        }
        return null;
    }

    public void insertUser(User... user) {
        CreatureBuddyDatabase.databaseWriteExecutor.execute(() ->
        {
            try {
                userDAO.insert(user); // Perform the insert on the background thread
                Log.d("CreatureBuddyRepository", "User inserted successfully: " + user[0].getUsername());
            } catch (Exception e) {
                Log.e("CreatureBuddyRepository", "Error inserting user", e);
            }
        });

    }

    public void updateUser(int userId, String newUsername, String newPassword){
        CreatureBuddyDatabase.databaseWriteExecutor.execute(() ->{
            userDAO.updateUser(userId, newUsername, newPassword);
        });
    }

    public void deleteUser(int userId){
        CreatureBuddyDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.deleteUser(userId);
        });
    }

    public LiveData<User> getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }

    public LiveData<User> getUserByUserId(int userId) {
        return userDAO.getUserByUserId(userId);
    }

    // Start of all the buddies methods
    public void insertBuddies(Buddies... buddies){
        CreatureBuddyDatabase.databaseWriteExecutor.execute(() -> {
            try {
                buddiesDAO.insert(buddies);
                Log.d("CreatureBuddyRepository", "Buddies inserted successfully: " + buddies[0].getName());
        } catch (Exception e) {
                Log.e("CreatureBuddyRepository", "Error inserting buddies", e);
            }
        });
    }

    public void updateBuddies(int buddiesId, String newName, int newHealth, int newAttack, int newDefense, int newExp, String newImage){
        CreatureBuddyDatabase.databaseWriteExecutor.execute(() -> {
            buddiesDAO.updateBuddies(buddiesId, newName, newHealth, newAttack, newDefense, newExp ,newImage);
        });
    }

    public void deleteBuddies(int buddiesId){
        CreatureBuddyDatabase.databaseWriteExecutor.execute(() -> {
            buddiesDAO.deleteBuddies(buddiesId);
        });
    }

    public LiveData<Buddies> getBuddiesById(int buddiesId){
        return buddiesDAO.getBuddiesById(buddiesId);
    }

    public LiveData<List<Buddies>> getAllBuddies() {
        return buddiesDAO.getAllBuddies();
    }

    // Buddy Ranking methods
    public void insertBuddyRanking(BuddyRanking... rankings) {
        CreatureBuddyDatabase.databaseWriteExecutor.execute(() -> {
            try {
                buddyRankingDAO.insert(rankings);
                Log.d("CreatureBuddyRepository", "Buddy ranking inserted successfully");
            } catch (Exception e) {
                Log.e("CreatureBuddyRepository", "Error inserting buddy ranking", e);
            }
        });
    }

    public LiveData<List<BuddyRanking>> getAllRankings() {
        return buddyRankingDAO.getAllRankings();
    }

    public LiveData<BuddyRanking> getRankingByBuddyId(int buddyId) {
        return buddyRankingDAO.getRankingByBuddyId(buddyId);
    }

    public void incrementWins(int buddyId) {
        CreatureBuddyDatabase.databaseWriteExecutor.execute(() -> {
            buddyRankingDAO.incrementWins(buddyId);
        });
    }

    public void incrementLosses(int buddyId) {
        CreatureBuddyDatabase.databaseWriteExecutor.execute(() -> {
            buddyRankingDAO.incrementLosses(buddyId);
        });
    }

    public void deleteBuddyRanking(int buddyId) {
        CreatureBuddyDatabase.databaseWriteExecutor.execute(() -> {
            buddyRankingDAO.deleteRanking(buddyId);
        });
    }
}
