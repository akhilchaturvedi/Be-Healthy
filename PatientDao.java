package com.example.behealthy;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PatientDao {
    @Insert
    void insert(Patient patient);
    @Query("SELECT * FROM patienttable WHERE name = :name")
    LiveData<Patient> getOurPatient(String name);
    @Query("SELECT * FROM patienttable WHERE familyname = :familyname")
    LiveData<List<Patient>> getFamilyMembers(String familyname);
    @Update
    void update(Patient patient);

}
