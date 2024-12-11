package com.example.project2;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.project2.database.BuddiesDAO;
import com.example.project2.database.BuddyRankingDAO;
import com.example.project2.database.CreatureBuddyDatabase;
import com.example.project2.database.entities.Buddies;
import com.example.project2.database.entities.BuddyRanking;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class BuddyRankingDatabaseTest {
    private BuddyRankingDAO buddyRankingDAO;
    private BuddiesDAO buddiesDAO;
    private CreatureBuddyDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, CreatureBuddyDatabase.class)
                .allowMainThreadQueries()
                .build();
        buddyRankingDAO = db.buddyRankingDAO();
        buddiesDAO = db.buddiesDAO();
        
        // Insert a test buddy that we can reference
        Buddies testBuddy = new Buddies("TestBuddy", 100, 50, 30, 0, "@drawable/test");
        testBuddy.setId(1); // Set ID to 1 to match our test cases
        buddiesDAO.insert(testBuddy);
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void insertBuddyRanking() {
        // Create a test buddy ranking
        BuddyRanking ranking = new BuddyRanking(1); // Using ID 1 which matches our test buddy
        ranking.setWins(5);
        ranking.setLosses(2);
        ranking.updateWinPercentage();

        // Insert the ranking
        buddyRankingDAO.insert(ranking);

        // Get all rankings and verify using direct query
        List<BuddyRanking> rankings = buddyRankingDAO.getAllRankingsSync();
        assertTrue(!rankings.isEmpty());
        BuddyRanking insertedRanking = rankings.get(0);
        assertEquals(1, insertedRanking.getBuddyId());
        assertEquals(5, insertedRanking.getWins());
        assertEquals(2, insertedRanking.getLosses());
    }

    @Test
    public void updateBuddyRanking() {
        // Create and insert initial ranking
        BuddyRanking ranking = new BuddyRanking(1); // Using ID 1 which matches our test buddy
        buddyRankingDAO.insert(ranking);

        // Update by incrementing wins
        buddyRankingDAO.incrementWins(1);

        // Verify the update using direct query
        BuddyRanking updatedRanking = buddyRankingDAO.getRankingByBuddyIdSync(1);
        assertEquals(1, updatedRanking.getWins());
    }

    @Test
    public void deleteBuddyRanking() {
        // Create and insert a ranking
        BuddyRanking ranking = new BuddyRanking(1); // Using ID 1 which matches our test buddy
        buddyRankingDAO.insert(ranking);

        // Delete the ranking
        buddyRankingDAO.deleteRanking(1);

        // Verify deletion using direct query
        List<BuddyRanking> rankings = buddyRankingDAO.getAllRankingsSync();
        assertTrue(rankings.isEmpty());
    }
}