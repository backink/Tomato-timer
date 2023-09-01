package com.example.timer.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.AbstractSavedStateViewModelFactory;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.ViewModelInitializer;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import static androidx.lifecycle.SavedStateHandleSupport.createSavedStateHandle;
import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import android.app.Application;

import com.example.timer.Event;
import com.example.timer.EventDao;
import com.example.timer.EventDatabase;

import java.util.List;

public class DataViewModel extends ViewModel {

    private EventDao eventDao;
    private LiveData<List<Event>> allEvents;

    private List<Event> events;

    public DataViewModel(EventDao dao, SavedStateHandle savedStateHandle) {
        eventDao = dao;
        allEvents = eventDao.loadAllUsers();
    }

    public LiveData<List<Event>> getAllEvents() {
        return allEvents;
    }

    public List<Event> getEvents() {

        return eventDao.getEvents();
    }

    public Event getFirst() {
        return allEvents.getValue().get(2);
    }

    public void setDao(EventDao dao) {
        eventDao = dao;
    }


}

