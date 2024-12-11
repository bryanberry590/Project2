package com.example.project2;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.project2.database.BuddiesDAO;
import com.example.project2.database.CreatureBuddyDatabase;
import com.example.project2.database.entities.Buddies;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class BuddiesDatabaseTest {
    private BuddiesDAO buddiesDAO;
    private CreatureBuddyDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, CreatureBuddyDatabase.class)
                .allowMainThreadQueries()
                .build();
        buddiesDAO = db.buddiesDAO();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void insertBuddy() {
        // Create a test buddy
        Buddies buddy = new Buddies("TestBuddy", 100, 50, 30, 0, "@drawable/test");

        // Insert the buddy
        buddiesDAO.insert(buddy);

        // Get buddy by name and verify
        Buddies insertedBuddy = buddiesDAO.getBuddiesByNameSync("TestBuddy");
        assertEquals("TestBuddy", insertedBuddy.getName());
        assertEquals(100, insertedBuddy.getHealth());
        assertEquals(50, insertedBuddy.getAttack());
        assertEquals(30, insertedBuddy.getDefense());
    }

    @Test
    public void updateBuddy() {
        // Insert initial buddy
        Buddies buddy = new Buddies("TestBuddy", 100, 50, 30, 0, "@drawable/test");
        buddiesDAO.insert(buddy);

        // Get the buddy to get its ID
        Buddies insertedBuddy = buddiesDAO.getBuddiesByNameSync("TestBuddy");
        int buddyId = insertedBuddy.getId();

        // Update the buddy
        buddiesDAO.updateBuddies(buddyId, "UpdatedBuddy", 200, 75, 45, 10, "@drawable/test");

        // Verify the update
        Buddies updatedBuddy = buddiesDAO.getBuddiesByIdSync(buddyId);
        assertEquals("UpdatedBuddy", updatedBuddy.getName());
        assertEquals(200, updatedBuddy.getHealth());
        assertEquals(75, updatedBuddy.getAttack());
        assertEquals(45, updatedBuddy.getDefense());
        assertEquals(10, updatedBuddy.getExp());
    }

    @Test
    public void deleteBuddy() {
        // Insert a buddy
        Buddies buddy = new Buddies("TestBuddy", 100, 50, 30, 0, "@drawable/test");
        buddiesDAO.insert(buddy);

        // Get the buddy to get its ID
        Buddies insertedBuddy = buddiesDAO.getBuddiesByNameSync("TestBuddy");
        int buddyId = insertedBuddy.getId();

        // Delete the buddy
        buddiesDAO.deleteBuddies(buddyId);

        // Verify deletion
        List<Buddies> buddies = buddiesDAO.getAllBuddiesSync();
        assertTrue(buddies.isEmpty());
    }
}