package com.example.wagba;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DAO {
    @Insert
    void insertAll(Profile... profiles);

    @Query("SELECT * from profile")
    List<Profile> getAllProfiles();
}
