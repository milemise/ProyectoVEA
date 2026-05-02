package com.ulp.vigiaespacialarg.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.ulp.vigiaespacialarg.model.Satellite;
import java.util.List;

@Dao
public interface SatelliteDao {
    @Insert
    void insert(Satellite satellite);

    @Query("SELECT * FROM satellites ORDER BY id DESC")
    List<Satellite> getAllSatellites();
}