package com.example.medicineapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class medicineViewModel extends AndroidViewModel {
    private MedicineRepository medicineRepo;
    private LiveData<List<Medicine>> medicineAll;

    public medicineViewModel(@NonNull Application application) {
        super(application);
        medicineRepo = new MedicineRepository(application);
        medicineAll=medicineRepo.getAll();
    }
    public void insert(Medicine medicine){
        medicineRepo.insert(medicine);
    }

    public void delete(Medicine medicine){
        medicineRepo.delete(medicine);
    }

    public void update(Medicine medicine){
        medicineRepo.update(medicine);
    }

    public void deleteAll(){
        medicineRepo.deleteAll();
    }

    public LiveData<List<Medicine>> getAll(){
        return medicineAll;
    }
}
