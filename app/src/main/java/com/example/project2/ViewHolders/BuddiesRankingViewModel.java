package com.example.project2.ViewHolders;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.project2.database.CreatureBuddyRepository;
import com.example.project2.database.entities.Buddies;
import com.example.project2.database.entities.BuddyRanking;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuddiesRankingViewModel extends AndroidViewModel {
    private final CreatureBuddyRepository repository;
    private final LiveData<List<BuddyRanking>> allBuddyRankings;
    private final LiveData<List<Buddies>> allBuddies;
    private final MediatorLiveData<Map<Integer, Buddies>> buddiesMap;

    public BuddiesRankingViewModel(Application application) {
        super(application);
        repository = CreatureBuddyRepository.getRepository(application);
        allBuddyRankings = repository.getAllRankings();
        allBuddies = repository.getAllBuddies();
        buddiesMap = new MediatorLiveData<>();
        
        // Update buddiesMap whenever allBuddies changes
        buddiesMap.addSource(allBuddies, buddiesList -> {
            Map<Integer, Buddies> map = new HashMap<>();
            if (buddiesList != null) {
                for (Buddies buddy : buddiesList) {
                    map.put(buddy.getId(), buddy);
                }
            }
            buddiesMap.setValue(map);
        });
    }

    public LiveData<List<BuddyRanking>> getAllBuddyRankings() {
        return allBuddyRankings;
    }

    public LiveData<Map<Integer, Buddies>> getBuddiesMap() {
        return buddiesMap;
    }

    public void incrementWins(int buddyId) {
        repository.incrementWins(buddyId);
    }

    public void incrementLosses(int buddyId) {
        repository.incrementLosses(buddyId);
    }
}
