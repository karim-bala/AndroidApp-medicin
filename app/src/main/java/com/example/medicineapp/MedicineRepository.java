package com.example.medicineapp;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Update;

import java.util.List;

public class MedicineRepository {
    private medicineDao medicineDao;

    private LiveData<List<Medicine>> getAll;

    public MedicineRepository(Application app){
        MedicineRoomDb db=MedicineRoomDb.getInstance(app);
        medicineDao medicineDao=db.medicineDao();
        getAll = medicineDao.getAll();


    }

    // operations

    // insert
    public  void insert(Medicine medicine){
        new InsertAsyncTask(medicineDao).execute(medicine);

    }

    // update
    public  void update(Medicine medicine){
        new UpdateAsyncTask(medicineDao).execute(medicine);

    }

    //getAll
    public LiveData<List<Medicine>>getAll(){
        return getAll;
    }
    // delete
    public  void delete(Medicine medicine){
        new DeleteAsyncTask(medicineDao).execute(medicine);

    }

    // delete
    public  void deleteAll(){
        new DeleteAllAsyncTask(medicineDao).execute();

    }

    private static class InsertAsyncTask extends AsyncTask<Medicine,Void,Void>{
        private medicineDao medicineDao;
        public InsertAsyncTask (medicineDao medicineDao){

            this.medicineDao=medicineDao;
        }
        @Override
        protected Void doInBackground(Medicine... medicines) {
            medicineDao.insert(medicines[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Medicine,Void,Void>{
        private medicineDao medicineDao;
        public DeleteAsyncTask (medicineDao medicineDao){

            this.medicineDao=medicineDao;
        }
        @Override
        protected Void doInBackground(Medicine... medicines) {
            medicineDao.delete(medicines[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Medicine,Void,Void>{
        private medicineDao medicineDao;
        public UpdateAsyncTask (medicineDao medicineDao){

            this.medicineDao=medicineDao;
        }
        @Override
        protected Void doInBackground(Medicine... medicines) {
            medicineDao.update(medicines[0]);
            return null;
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask<Void,Void,Void>{
        private medicineDao medicineDao;

        @Override
        protected Void doInBackground(Void... voids) {
            medicineDao.deleteAll();
            return null;
        }

        public DeleteAllAsyncTask (medicineDao medicineDao){

            this.medicineDao=medicineDao;
        }

    }
}
