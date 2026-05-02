package com.ulp.vigiaespacialarg.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.ulp.vigiaespacialarg.model.Satellite;

@Database(entities = {Satellite.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract SatelliteDao satelliteDao();

    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "vigia_db")
                    .fallbackToDestructiveMigration() // Limpia datos si hay conflicto de versiones
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}