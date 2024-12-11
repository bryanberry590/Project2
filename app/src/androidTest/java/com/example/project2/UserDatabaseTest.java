package com.example.project2;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.project2.database.CreatureBuddyDatabase;
import com.example.project2.database.UserDAO;
import com.example.project2.database.entities.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class UserDatabaseTest {
    private UserDAO userDAO;
    private CreatureBuddyDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, CreatureBuddyDatabase.class)
                .allowMainThreadQueries()
                .build();
        userDAO = db.userDAO();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void insertUser() {
        // Create a test user
        User user = new User("testUser", "password123");
        user.setAdmin(false);

        // Insert the user
        userDAO.insert(user);

        // Get user by username and verify
        User insertedUser = userDAO.getUserByUsernameSync("testUser");
        assertEquals("testUser", insertedUser.getUsername());
        assertEquals("password123", insertedUser.getPassword());
        assertFalse(insertedUser.isAdmin());
    }

    @Test
    public void updateUser() {
        // Insert initial user
        User user = new User("testUser", "password123");
        userDAO.insert(user);

        // Get the user to get its ID
        User insertedUser = userDAO.getUserByUsernameSync("testUser");
        int userId = insertedUser.getId();

        // Update the user
        userDAO.updateUser(userId, "updatedUser", "newPassword123");

        // Verify the update
        User updatedUser = userDAO.getUserByUserIdSync(userId);
        assertEquals("updatedUser", updatedUser.getUsername());
        assertEquals("newPassword123", updatedUser.getPassword());
    }

    @Test
    public void deleteUser() {
        // Insert a user
        User user = new User("testUser", "password123");
        userDAO.insert(user);

        // Get the user to get its ID
        User insertedUser = userDAO.getUserByUsernameSync("testUser");
        int userId = insertedUser.getId();

        // Delete the user
        userDAO.deleteUser(userId);

        // Verify deletion
        List<User> users = userDAO.getAllUsersSync();
        assertTrue(users.isEmpty());
    }

    @Test
    public void insertAdminUser() {
        // Create an admin user
        User adminUser = new User("adminUser", "adminPass");
        adminUser.setAdmin(true);

        // Insert the admin user
        userDAO.insert(adminUser);

        // Get user by username and verify
        User insertedUser = userDAO.getUserByUsernameSync("adminUser");
        assertEquals("adminUser", insertedUser.getUsername());
        assertEquals("adminPass", insertedUser.getPassword());
        assertTrue(insertedUser.isAdmin());
    }
}