package com.example.medicineapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import com.example.medicineapp.MedicinModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.widget.Toast;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "BookLibrary.db";
    private static final int DATABASE_VERSION = 1;

    FirebaseDatabase rooteNode;
    DatabaseReference reference;

    private static final String TABLE_NAME = "my_library";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "_title";
    private static final String COLUMN_AUTHOR = "_author";
    private static final String COLUMN_PAGES = "_pages";
    private static final String COLUMN_CREATED = "created_at";
    private static final String COLUMN_FAB = "date_fab";
    private static final String COLUMN_LABO  = "labo";
    private static final String COLUMN_DENOM   = "denom";
    private static final String COLUMN_FORM    = "form";
    private static final String COLUMN_DUREE    = "duree";
    private static final String COLUMN_REMBOURSABLE    = "rembousable";
    private static final String COLUMN_LOT    = "lot";
    private static final String COLUMN_PERMP    = "date_permp";
    private static final String COLUMN_DESCRIP    = "descrip";
    private static final String COLUMN_PRIX    = "prix";
    private static final String COLUMN_QNT    = "qnt";

    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TITLE + " TEXT, " +
                        COLUMN_AUTHOR + " TEXT, " +
                        COLUMN_PAGES + " TEXT, " +
                        COLUMN_FAB + " TEXT , " +
                        COLUMN_LABO + " TEXT , " +
                        COLUMN_DENOM + " TEXT , " +
                        COLUMN_FORM + " TEXT , " +
                        COLUMN_DUREE + " INTEGER , " +
                        COLUMN_REMBOURSABLE + " TEXT , " +
                        COLUMN_LOT + " TEXT , " +
                        COLUMN_PERMP + " TEXT , " +
                        COLUMN_DESCRIP + " TEXT , " +
                        COLUMN_PRIX + " DOUBLE , " +
                        COLUMN_QNT + " INTEGER , " +
                        COLUMN_CREATED + " TEXT);";
        db.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addMedicin(String therap,String com , String labo , String denom , String form , int duree , String remb ,
                    String lot , String date_fab , String date_peremp , String desc , double prix , int qnt , String bar_code){

        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
//        onCreate(db);
        ContentValues cv = new ContentValues();
        String created_at = new SimpleDateFormat("dd-MM-yyyy-hh:mm:ss", Locale.getDefault()).format(new Date());

        cv.put(COLUMN_TITLE, therap);
        cv.put(COLUMN_AUTHOR, com);
        cv.put(COLUMN_PAGES, bar_code);
        cv.put(COLUMN_LABO, labo);
        cv.put(COLUMN_DENOM, denom);
        cv.put(COLUMN_FORM, form);
        cv.put(COLUMN_DUREE, duree);
        cv.put(COLUMN_REMBOURSABLE, remb);
        cv.put(COLUMN_LOT, lot);
        cv.put(COLUMN_PERMP, date_peremp);
        cv.put(COLUMN_DESCRIP, desc);
        cv.put(COLUMN_PRIX, prix);
        cv.put(COLUMN_QNT, qnt);
        cv.put(COLUMN_FAB, date_fab);

        cv.put(COLUMN_CREATED, created_at);




        long result = db.insert(TABLE_NAME,null, cv);
        Log.d("cursor", String.valueOf(result));
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
//            MainActivity main= new MainActivity();
//            boolean b=main.isNetworkAvailable();
            Toast.makeText(context, "Medicament ajouter!", Toast.LENGTH_SHORT).show();
            rooteNode= FirebaseDatabase.getInstance("https://medicineapp-9a374-default-rtdb.europe-west1.firebasedatabase.app/");
            reference= rooteNode.getReference("medicins");
            MedicinModel medicinModel=new MedicinModel(String.valueOf(result),com,bar_code,therap,created_at,date_fab,String.valueOf(prix),desc,String.valueOf(qnt),date_peremp,lot,remb,String.valueOf(duree),form,labo,denom);
            reference.child(String.valueOf(result)).setValue(medicinModel);
        }
    }



    void ToastDesplay(){
        Toast.makeText(context, "Veuillez remplir vos champs", Toast.LENGTH_SHORT).show();
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
//        onCreate(db);


        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor readOneData(){
//        String query = "SELECT * FROM " + TABLE_NAME + " WHERE "+ COLUMN_ID +"= ?"+4 ;
        SQLiteDatabase db;
        db = this.getReadableDatabase();

//        String selection= COLUMN_ID+"WHERE ="+;
//        String[] selection_args={COLUMN_ID};
//        Cursor cursor = null;
//

        Cursor cursor = db.query(TABLE_NAME,new String[]{COLUMN_ID,COLUMN_AUTHOR,COLUMN_PAGES,COLUMN_TITLE},COLUMN_ID+" = ?",new String[]{String.valueOf(13)},null,null,null);
//          Cursor cursor=db.rawQuery(query,null);
        if(cursor != null){
            cursor.moveToFirst();
        }
//        db.close();
        return cursor;
    }

    public MedicinModel getMedicin(String row) {
        String query1 = "SELECT * FROM "+TABLE_NAME+ " WHERE _id =?";
        SQLiteDatabase db = this.getReadableDatabase();



       Cursor cursor= db.query(TABLE_NAME,new String[]{COLUMN_ID,COLUMN_AUTHOR,COLUMN_PAGES,COLUMN_TITLE,COLUMN_CREATED,COLUMN_FAB,COLUMN_PRIX,COLUMN_DESCRIP,COLUMN_QNT,COLUMN_PERMP,COLUMN_LOT,COLUMN_REMBOURSABLE,COLUMN_DUREE,COLUMN_FORM,COLUMN_LABO,COLUMN_DENOM},COLUMN_ID+" = ?",new String[]{String.valueOf(row)},null,null,null);
        cursor.moveToFirst();
        return new MedicinModel(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5)
                ,cursor.getString(6),cursor.getString(7),cursor.getString(8),cursor.getString(9),cursor.getString(10),cursor.getString(11),cursor.getString(12)
                ,cursor.getString(13),cursor.getString(14),cursor.getString(15));
    }


    void updateData(String id,String therap,String com , String labo , String denom , String form , String duree , String remb ,
                    String lot , String date_fab , String date_peremp , String desc , String prix , String qnt , String bar_code){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, therap);
        cv.put(COLUMN_AUTHOR, com);
        cv.put(COLUMN_PAGES, bar_code);
        cv.put(COLUMN_LABO, labo);
        cv.put(COLUMN_DENOM, denom);
        cv.put(COLUMN_FORM, form);
        cv.put(COLUMN_DUREE, duree);
        cv.put(COLUMN_REMBOURSABLE, remb);
        cv.put(COLUMN_LOT, lot);
        cv.put(COLUMN_PERMP, date_peremp);
        cv.put(COLUMN_DESCRIP, desc);
        cv.put(COLUMN_PRIX, prix);
        cv.put(COLUMN_QNT, qnt);
        cv.put(COLUMN_FAB, date_fab);




        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Medicament modifier!", Toast.LENGTH_SHORT).show();
        }

    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Medicament supprimer.", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

}
