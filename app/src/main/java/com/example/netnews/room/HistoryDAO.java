package com.example.netnews.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HistoryDAO {

    @Insert
    void insert(History history);

    @Delete
    void delete(History history);

    @Query("SELECT * FROM history_table")
    LiveData<List<History>> getHistory();
}
