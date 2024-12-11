package com.example.project2.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.project2.MainActivity;
import com.example.project2.R;
import com.example.project2.database.entities.Buddies;
import com.example.project2.database.entities.BuddyRanking;
import com.example.project2.database.entities.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// increment every time you change the database
@Database(entities = {User.class, Buddies.class, BuddyRanking.class}, version = 6, exportSchema = false)
public abstract class CreatureBuddyDatabase extends RoomDatabase {

    public static final String USER_TABLE = "usertable";
    public static final String BuddiesTable = "buddiesTable";
    public static final String BUDDY_RANKING_TABLE = "buddyRankingTable";
    private static final String DATABASE_NAME = "creatureBuddyDatabase";

    private static volatile CreatureBuddyDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static CreatureBuddyDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CreatureBuddyDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            CreatureBuddyDatabase.class,
                            DATABASE_NAME
                    )
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.i(MainActivity.TAG, "DATABASE created!");
            databaseWriteExecutor.execute(() -> {
                UserDAO dao = INSTANCE.userDAO();
                dao.deleteAll();
                User admin = new User("admin1", "admin1");
                admin.setAdmin(true);
                dao.insert(admin);
                User testUser1 = new User("testUser1", "testUser1");
                dao.insert(testUser1);

                BuddiesDAO dao2 = INSTANCE.buddiesDAO();
                dao2.deleteAll();
                Buddies breloom = new Buddies("breloom", 150, 100, 10, 0, "@drawable/breloom");
                breloom.setStarter(true);

                Buddies bulbasaur = new Buddies("bulbasaur", 160, 80, 20, 0, "@drawable/bulbasaur");
                Buddies charizard = new Buddies("charizard", 200, 300, 25, 0, "@drawable/charizard");
                Buddies gardevoir = new Buddies("gardevoir", 130, 150, 15, 0, "@drawable/gardevoir");
                Buddies hawlucha = new Buddies("hawlucha", 170, 200, 20, 0, "@drawable/hawlucha");
                Buddies mewtwo = new Buddies("mewtwo", 160, 180, 23, 0, "@drawable/mewtwo");
                Buddies pikachu = new Buddies("pikachu", 175, 200, 25, 0, "@drawable/pikachu");
                Buddies squirtle = new Buddies("squirtle", 185, 120, 15, 0, "@drawable/squirtle");
                Buddies umbreon = new Buddies("umbreon", 190, 160, 18, 0, "@drawable/umbreon");
                dao2.insert(breloom);
                dao2.insert(bulbasaur);
                dao2.insert(charizard);
                dao2.insert(gardevoir);
                dao2.insert(hawlucha);
                dao2.insert(mewtwo);
                dao2.insert(pikachu);
                dao2.insert(squirtle);
                dao2.insert(umbreon);

                // Initialize rankings for all buddies
                BuddyRankingDAO rankingDAO = INSTANCE.buddyRankingDAO();
                rankingDAO.deleteAll();
                rankingDAO.insert(new BuddyRanking(1)); // breloom
                rankingDAO.insert(new BuddyRanking(2)); // bulbasaur
                rankingDAO.insert(new BuddyRanking(3)); // charizard
                rankingDAO.insert(new BuddyRanking(4)); // gardevoir
                rankingDAO.insert(new BuddyRanking(5)); // hawlucha
                rankingDAO.insert(new BuddyRanking(6)); // mewtwo
                rankingDAO.insert(new BuddyRanking(7)); // pikachu
                rankingDAO.insert(new BuddyRanking(8)); // squirtle
                rankingDAO.insert(new BuddyRanking(9)); // umbreon
            });
        }
    };

    public abstract UserDAO userDAO();
    public abstract BuddiesDAO buddiesDAO();
    public abstract BuddyRankingDAO buddyRankingDAO();
}
