package com.example.timer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EventDao {
    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void insertAll(Event... events);

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void insert(Event event);

    @Query("SELECT * FROM event")
    LiveData<List<Event>> loadAllUsers();

    @Query("SELECT * FROM event")
    List<Event> getEvents();

    @Delete
    void deleteEvent(Event... events);
}
