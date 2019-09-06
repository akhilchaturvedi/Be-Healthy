package com.example.behealthy;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class OurViewModel extends AndroidViewModel{
    PatientDatabase patientDatabase;
    PatientDao patientDao;
    LiveData<Patient> patient;
    LiveData<List<Patient>> allMembers;
    public OurViewModel(@NonNull Application application) {
        super(application);
        patientDatabase=PatientDatabase.databaseObject(application);
        patientDao=patientDatabase.patientDao();
    }
    public void update(final Patient patient)
    {

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    patientDao.update(patient);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
    public void insert(Patient patient){
        new InsertAsyncTask(patientDao).execute(patient);

    }
    class InsertAsyncTask extends AsyncTask<Patient,Void,Void>{
        PatientDao patientDao;

        public InsertAsyncTask(PatientDao patientDao) {
            this.patientDao = patientDao;
        }

        @Override
        protected Void doInBackground(Patient... patients) {
            try {
                patientDao.insert(patients[0]);
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
    public LiveData<Patient> getOurPatient(String name)
    {
        patient=patientDao.getOurPatient(name);
        return patient;
    }
    public LiveData<List<Patient>> getFamilyMembers(String familyname)
    {
        allMembers=patientDao.getFamilyMembers(familyname);
        return allMembers;
    }
}
