package com.example.timer.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.AbstractSavedStateViewModelFactory;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.timer.EventDao;

public class DataViewModelFactory extends AbstractSavedStateViewModelFactory {

    private final EventDao dao;

    public DataViewModelFactory(EventDao dao) {
        this.dao = dao;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    protected <T extends ViewModel> T create(
            @NonNull String key, @NonNull Class<T> modelClass, @NonNull SavedStateHandle handle
    ) {
        return (T) new DataViewModel(dao, handle);
    }
}
