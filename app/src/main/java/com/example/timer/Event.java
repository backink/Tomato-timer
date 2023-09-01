package com.example.timer;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Event {

    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "time_duration")
    public long timeDuration;
    @ColumnInfo(name = "is_done")
    public boolean isDone;
    @ColumnInfo(name = "category")
    public String category;
    @ColumnInfo(name = "remark")
    public String remark;
    @ColumnInfo(name = "date")
    public String date; // format
}
