package com.example.project2.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.project2.MainActivity;
import com.example.project2.database.entities.User;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class CreatureBuddyRepository {

    private final UserDAO userDAO;
    private static CreatureBuddyRepository repository;

    private CreatureBuddyRepository(Application application) {
        CreatureBuddyDatabase db = CreatureBuddyDatabase.getDatabase(application);
        this.userDAO = db.userDAO();
    }

    public static CreatureBuddyRepository getRepository(Application application){
        if(repository != null){
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

    public LiveData<User> getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }

    public LiveData<User> getUserByUserId(int userId) {
        return userDAO.getUserByUserId(userId);
    }
}
