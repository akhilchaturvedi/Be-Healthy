package com.example.behealthy;

import android.content.Context;

import androidx.room.PrimaryKey;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.security.PrivateKey;

@androidx.room.Database(entities = {Patient.class},version =6)
public abstract class PatientDatabase extends RoomDatabase {
    public abstract PatientDao patientDao();
    private static PatientDatabase patientDatabase;
    static PatientDatabase databaseObject(final Context context)
    {
        patientDatabase=Room.databaseBuilder(context.getApplicationContext(),PatientDatabase.class,"our_databse").fallbackToDestructiveMigration().build();
        return patientDatabase;
    }

}
