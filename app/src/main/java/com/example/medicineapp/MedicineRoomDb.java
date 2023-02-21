package com.example.medicineapp;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Medicine.class},version = 1)

public abstract  class MedicineRoomDb extends RoomDatabase {

    private  static MedicineRoomDb instance;
    public  abstract medicineDao medicineDao();

    //Singloton
    public static synchronized MedicineRoomDb getInstance(Context context){
        if (instance == null){
            instance= Room.databaseBuilder(context.getApplicationContext(),
                    MedicineRoomDb.class,"Medicine")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();

        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDataAsyncTask(instance).execute();
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };

    private static class PopulateDataAsyncTask extends AsyncTask<Void,Void,Void>{
        private medicineDao medicineDao;
        PopulateDataAsyncTask(MedicineRoomDb db){
         medicineDao =db.medicineDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            medicineDao.insert(new Medicine("bbb","bbb","bbb","bbb","bbb","bbb","bbb","bbb","bbb","bbb","bbb","bbb","bbb","bbb","bbb"));
            return null;
        }
    }

}

