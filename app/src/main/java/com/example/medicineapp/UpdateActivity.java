package com.example.medicineapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UpdateActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

//    EditText title_input2, author_input2, pages_input;
//    Button update_button, delete_button,updateBtnFab_datePicker;

    EditText therap_input2, com_input2, code_input2, labo_input2,denom_input2 , form_input2,duree_input2,lot_input2 ,descrp_input2 ,
            prix_input2 , qnt_input2;
    CheckBox checkBox2;
    Button update_button,delete_button,btnFab_datePicker2,btnPermp_datePicker2 , codeBareBtn2 , ocrBtn2;
    TextView created_at_input,updateDate_fab;

    DatePickerDialog datePickerDialog;
    DatePickerDialog datePickerDialogPermp;

    String id,resultOcr;
    String title;
    String author;
    String pages;
    String datePickerValue;
    String created_at;
    MyDatabaseHelper myDB;
    ImageView empty_imageview;
    TextView no_data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        therap_input2 = findViewById(R.id.title_input2);
        com_input2 = findViewById(R.id.author_input2);
        code_input2 = findViewById(R.id.pages_input2);
        labo_input2=findViewById(R.id.labo_input2);
        denom_input2=findViewById(R.id.denom_input2);
        form_input2=findViewById(R.id.form_input2);
        duree_input2=findViewById(R.id.duree_input2);
        lot_input2=findViewById(R.id.lot_input2);
        descrp_input2=findViewById(R.id.descrp_input2);
        prix_input2=findViewById(R.id.prix_input2);
        qnt_input2=findViewById(R.id.qnt_input2);
        checkBox2=findViewById(R.id.checkBox2);
        btnFab_datePicker2=findViewById(R.id.btnFab_datePicker2);
        btnPermp_datePicker2=findViewById(R.id.btnPermp_datePicker2);

        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);
        created_at_input=findViewById(R.id.create_at);

        codeBareBtn2=findViewById(R.id.codeBareBtn2);

        ocrBtn2=findViewById(R.id.ocrBtn2);



        // codebare btn

        codeBareBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator= new IntentIntegrator(UpdateActivity.this);
//                Log.d("cursor", String.valueOf(intentIntegrator));
                // set promp text
                intentIntegrator.setPrompt("pour le flash, utilisez les touches d'augmentation et de diminution du volume");

                intentIntegrator.setBarcodeImageEnabled(true);
//                // set beep
                intentIntegrator.setBeepEnabled(true);

//                // looked orientation
                intentIntegrator.setOrientationLocked(false);
//
//                //set capture activity
//
//                intentIntegrator.setCaptureActivity(CaptureAct.class);
//
                intentIntegrator.setDesiredBarcodeFormats(intentIntegrator.ONE_D_CODE_TYPES);

                // initiate scan
                intentIntegrator.initiateScan();
//                scan();
            }
        });

        // codebare btn end




        //First we call this
        getAndSetIntentData();

        initDatePicker();
        initDatePickerPermp();

        //Set actionbar title after getAndSetIntentData method
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title);
        }

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //And only then we call this
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
//                title = title_input.getText().toString().trim();
//                author = author_input.getText().toString().trim();
//
//                pages = pages_input.getText().toString().trim();
//                datePickerValue=updateBtnFab_datePicker.getText().toString().trim();
                String r="";
                if (checkBox2.isChecked()){
                    r="oui";
                }else {
                    r="non";
                }


                boolean input1 = therap_input2.getText().toString().trim().isEmpty();
                boolean input2 = com_input2.getText().toString().trim().isEmpty();
                boolean input3 = labo_input2.getText().toString().trim().isEmpty();
                boolean input4 = denom_input2.getText().toString().trim().isEmpty();
                boolean input5 = form_input2.getText().toString().trim().isEmpty();
                boolean input6 = duree_input2.getText().toString().trim().isEmpty();
                boolean input7 = lot_input2.getText().toString().trim().isEmpty();
                boolean input8 = btnFab_datePicker2.getText().toString().trim().equals("Calendrier");
                boolean input9 = btnPermp_datePicker2.getText().toString().trim().equals("Calendrier");
                boolean input10 = descrp_input2.getText().toString().trim().isEmpty();
                boolean input11 = prix_input2.getText().toString().trim().isEmpty();
                boolean input12 = qnt_input2.getText().toString().trim().isEmpty();
                boolean input13 = code_input2.getText().toString().trim().isEmpty();


                if ( input1  ||
                        input2  ||
                        input3  ||
                        input4  ||
                        input5  ||
                        input6  ||
                        input7  ||
                        input8  ||
                        input9  ||
                        input10 ||
                        input11 ||
                        input12 ||
                        input13
                ){
                    myDB.ToastDesplay();





                    if(input1)
                    {
                        therap_input2.setError("le champ est vide");
                    }

                    if(input2)
                    {
                        com_input2.setError("le champ est vide");
                    }

                    if(input3)
                    {
                        labo_input2.setError("le champ est vide");
                    }
                    if(input4)
                    {
                        denom_input2.setError("le champ est vide");
                    }

                    if(input5)
                    {
                        form_input2.setError("le champ est vide");
                    }

                    if(input6)
                    {
                        duree_input2.setError("le champ est vide");
                    }

                    if(input7)
                    {
                        lot_input2.setError("le champ est vide");
                    }

                    if(input8)
                    {
                        btnFab_datePicker2.setError("le champ est vide");
                    }

                    if(input9)
                    {
                        btnPermp_datePicker2.setError("le champ est vide");
                    }

                    if(input10)
                    {
                        descrp_input2.setError("le champ est vide");
                    }

                    if(input11)
                    {
                        prix_input2.setError("le champ est vide");
                    }

                    if(input12)
                    {
                        qnt_input2.setError("le champ est vide");
                    }

                    if(input13)
                    {
                        code_input2.setError("le champ est vide");
                    }




                } else {

                    myDB.updateData(id,
                            /*fff*/
                            therap_input2.getText().toString().trim(),
                            com_input2.getText().toString().trim(),
                            labo_input2.getText().toString().trim(),
                            denom_input2.getText().toString().trim(),
                            form_input2.getText().toString().trim(),
                            duree_input2.getText().toString().trim(),
                            r,

                            lot_input2.getText().toString().trim(),
                            btnFab_datePicker2.getText().toString().trim(),
                            btnPermp_datePicker2.getText().toString().trim(),
                            descrp_input2.getText().toString().trim(),
                            prix_input2.getText().toString().trim(),
                            qnt_input2.getText().toString().trim(),
                            code_input2.getText().toString().trim());



                }




            }
        });
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });

    }

    void getAndSetIntentData(){
//        && getIntent().hasExtra("title") &&
//                getIntent().hasExtra("author") && getIntent().hasExtra("pages")
        if(getIntent().hasExtra("id") ){
            //Getting Data from Intent

//            id = getIntent().getStringExtra("id");
////            title = getIntent().getStringExtra("title");
////            author = getIntent().getStringExtra("author");
////            pages = getIntent().getStringExtra("pages");
            id = getIntent().getStringExtra("id");
//            Log.d("cursor", String.valueOf(id));


            MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
//            myDB.deleteOneRow(id);
//            Cursor cursor=myDB.readDatabase(id);
//            cursor.moveToFirst();
////            Log.d("cursor", String.valueOf(cursor));
            MedicinModel medicinModel= myDB.getMedicin(id);




//            if(cursor.getCount() == 0){
//                cursor.moveToFirst();
//                empty_imageview.setVisibility(View.VISIBLE);
//                no_data.setVisibility(View.VISIBLE);
//            }else{


//                title =cursor.getString(1);
//                author = cursor.getString(2);
//                pages = cursor.getString(3);
//
//                empty_imageview.setVisibility(View.GONE);
//                no_data.setVisibility(View.GONE);
//            }
//
//            title = getIntent().getStringExtra("title");
//            author = getIntent().getStringExtra("author");
//            pages = getIntent().getStringExtra("pages");


            //Setting Intent Data
//            created_at=new SimpleDateFormat("dd-MM-yyyy-hh:mm:ss", Locale.getDefault()).format(new Date());
            created_at_input.setText(medicinModel.getCreated_at());
            therap_input2.setText(medicinModel.getTitle());
            com_input2.setText(medicinModel.getCommercial());
            labo_input2.setText(medicinModel.getLabo());
            denom_input2.setText(medicinModel.getDenom());
            form_input2.setText(medicinModel.getForm());
            duree_input2.setText(medicinModel.getDuree());
            if (medicinModel.getRembousable().equals("oui")){
                checkBox2.setChecked(true);
            }else {
                checkBox2.setChecked(false);
            }
            lot_input2.setText(medicinModel.getLot());
            btnFab_datePicker2.setText(medicinModel.getDate_fab());
            btnPermp_datePicker2.setText(medicinModel.getDate_peremp());
            descrp_input2.setText(medicinModel.getDescrip());
            prix_input2.setText(medicinModel.getPrix());
            qnt_input2.setText(medicinModel.getQnt());
            code_input2.setText(medicinModel.getBare_code());
//            updateBtnFab_datePicker2.setText(medicinModel.getDate_fab());
            Log.d("new", medicinModel.getDate_fab());
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Supprimer " + title + " ?");
        builder.setMessage("Êtes-vous sûr de vouloir supprimer " + title + " ?");
        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteOneRow(id);
                finish();
            }
        });
        builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }


    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                btnFab_datePicker2.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = android.app.AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private void initDatePickerPermp()
    {
        DatePickerDialog.OnDateSetListener dateSetListener1 = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                btnPermp_datePicker2.setText(date);

            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = android.app.AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialogPermp = new DatePickerDialog(this, style, dateSetListener1, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }
    private String makeDateString(int day, int month, int year)
    {
        return day + "-" + getMonthFormat(month) + "-" + year;
    }
    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "AVR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUT";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }
    public void openDatePickerPermp(View view)
    {
        datePickerDialogPermp.show();
    }




    //  functions for  codebare and ocr

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 205){


            Bundle bundle = data.getExtras();
            //from bundle, extract the image
            Bitmap bitmap = (Bitmap) bundle.get("data");
            //set image in imageview
//            imageVi.setImageBitmap(bitmap);
            //process the image
            //1. create a FirebaseVisionImage object from a Bitmap object
            FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmap);
            //2. Get an instance of FirebaseVision
            FirebaseVision firebaseVision = FirebaseVision.getInstance();
            //3. Create an instance of FirebaseVisionTextRecognizer
            FirebaseVisionTextRecognizer firebaseVisionTextRecognizer = firebaseVision.getOnDeviceTextRecognizer();
            //4. Create a task to process the image
            Task<FirebaseVisionText> task = firebaseVisionTextRecognizer.processImage(firebaseVisionImage);
            //5. if task is success
            task.addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                @Override
                public void onSuccess(FirebaseVisionText firebaseVisionText) {
                    String s = firebaseVisionText.getText();

                    resultOcr=s;
                }
            });
            //6. if task is failure
            task.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        }
        else{
            // initialize init result
            IntentResult intentResult= IntentIntegrator.parseActivityResult(
                    requestCode, resultCode,data
            );

            // check
            if (intentResult  != null && intentResult.getContents() != null){
                // alert dialog
//                AlertDialog.Builder  builder =new AlertDialog.Builder(AddActivity.this);
//                builder.setTitle("result");
                code_input2.setText(intentResult.getContents());
////                builder.setMessage("Vous trouvez le code dans la case code bare");
//                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//                builder.show();
            }else{
//            Toast.makeText(getApplicationContext(),"ops no result",Toast.LENGTH_SHORT).show();
                android.app.AlertDialog.Builder  builder =new android.app.AlertDialog.Builder(UpdateActivity.this);
                builder.setTitle("result");
                builder.setMessage("Aucun resultat");
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();

            }

        }


    }


    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }




    public boolean onMenuItemClick(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item1:
                doProcess();
                return  true;


            case R.id.item2:
                android.app.AlertDialog.Builder  builder1 =new android.app.AlertDialog.Builder(UpdateActivity.this);
//                builder.setTitle("result");
                TextView d1= new TextView(UpdateActivity.this);
                d1.setText(resultOcr);
                d1.setTextIsSelectable(true);


                builder1.setView(d1);

                builder1.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder1.show();

                return true;

//            case R.id.item3:
//                Toast.makeText(this, "Item 3 clicked", Toast.LENGTH_SHORT).show();
//                return true;

            default:
                return false;
        }
    }


    // ocr
    public void doProcess() {
        //open the camera => create an Intent object
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,205);
    }

    // end function for codebare and ocr
}




