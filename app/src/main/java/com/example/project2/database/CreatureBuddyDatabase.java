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
import com.example.project2.database.entities.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// increment every time you change the database
@Database(entities = {User.class , Buddies.class}, version = 5, exportSchema = false)
public abstract class CreatureBuddyDatabase extends RoomDatabase {

    public static final String USER_TABLE = "usertable";
    public static final String BuddiesTable = "buddiesTable";
    private static final String DATABASE_NAME = "creatureBuddyDatabase";

    private static volatile CreatureBuddyDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static CreatureBuddyDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CreatureBuddyDatabase.class) {
                if (INSTANCE == null){
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

    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback(){
        @Override
        public void onCreate (@NonNull SupportSQLiteDatabase db){
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
                Buddies testBuddy1 = new Buddies("testBuddy1", 10, 100, 10, "@drawable/mewtwo");
                testBuddy1.setStarter(true);
                dao2.insert(testBuddy1);
                Buddies testBuddy2 = new Buddies("testBuddy2", 1, 400, 20, "@drawable/bulbasaur");
                dao2.insert(testBuddy2);
            });
        }
    };

    public abstract UserDAO userDAO();
    public abstract BuddiesDAO buddiesDAO();
}
