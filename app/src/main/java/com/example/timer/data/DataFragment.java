package com.example.timer.data;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.timer.Event;
import com.example.timer.EventAdapter;
import com.example.timer.EventDatabase;
import com.example.timer.MainActivity;
import com.example.timer.R;
import com.example.timer.databinding.FragmentDataBinding;

import java.util.List;

public class DataFragment extends Fragment {
    private FragmentDataBinding binding;

    private DataViewModel dataViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDataBinding.inflate(inflater, container, false);

        EventDatabase db = EventDatabase.getDatabase(getContext());
        DataViewModelFactory factory = new DataViewModelFactory(db.eventDao());
        dataViewModel = new ViewModelProvider(this, factory).get(DataViewModel.class);

        EventAdapter eventAdapter = new EventAdapter();
        binding.eventList.setAdapter(eventAdapter);

        dataViewModel.getAllEvents().observe(getViewLifecycleOwner(), new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> events) {
                //eventAdapter.setData(events);
            }
        });

        for (Event e : dataViewModel.getEvents()) {
            Log.i("Debug", String.valueOf(e.id));
        }


        return binding.getRoot();
    }


}
