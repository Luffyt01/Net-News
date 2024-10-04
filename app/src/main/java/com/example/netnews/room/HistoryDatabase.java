package com.example.netnews.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {History.class}, version = 1)
public abstract class HistoryDatabase extends RoomDatabase {
    public abstract HistoryDAO getHistoryDAO();

    // Singleton Pattern
    private static HistoryDatabase dbInstance;
    public static synchronized HistoryDatabase getInstance(Context context){
        if(dbInstance == null){
            dbInstance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    HistoryDatabase.class,
                    "historyDB"
            ).fallbackToDestructiveMigration().build();
        }
        return dbInstance;
    }


}
