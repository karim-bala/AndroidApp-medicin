package com.example.medicineapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;
import androidx.room.Query;

import java.util.List;

@Dao

public interface medicineDao {

    @Insert
    void insert(Medicine medicine);

    @Update
    void update(Medicine medicine);

    @Delete
    void delete(Medicine medicine);

    @Query(" DELETE From Medicine")
    void deleteAll();

    @Query(" SELECT * From Medicine")
    LiveData<List<Medicine>> getAll();



}
