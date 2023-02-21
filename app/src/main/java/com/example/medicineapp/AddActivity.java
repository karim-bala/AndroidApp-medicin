package com.example.medicineapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    public Context context;




    EditText therap_input, com_input, code_input, labo_input,denom_input , form_input,duree_input,lot_input ,descrp_input ,
    prix_input , qnt_input;



    CheckBox checkBox;
    Button add_button,btnFab_datePicker,btnPermp_datePicker , codeBareBtn , ocrBtn;
    TextView date_fab;
    DatePickerDialog datePickerDialog;
    DatePickerDialog datePickerDialogPermp;
    String resultOcr="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
//        Log.d("cursor", );

        therap_input = findViewById(R.id.title_input);
        com_input = findViewById(R.id.author_input);
        code_input = findViewById(R.id.pages_input);

        btnFab_datePicker=findViewById(R.id.btnFab_datePicker);
        btnPermp_datePicker=findViewById(R.id.btnPermp_datePicker);
        date_fab=findViewById(R.id.date_fab);

        labo_input=findViewById(R.id.labo_input);
        denom_input=findViewById(R.id.denom_input);
        form_input=findViewById(R.id.form_input);
        duree_input=findViewById(R.id.duree_input);
        lot_input=findViewById(R.id.lot_input);
        descrp_input=findViewById(R.id.descrp_input);
        prix_input=findViewById(R.id.prix_input);
        qnt_input=findViewById(R.id.qnt_input);
        checkBox=findViewById(R.id.checkBox);

        codeBareBtn=findViewById(R.id.codeBareBtn);

        ocrBtn=findViewById(R.id.ocrBtn);

//        // Write a message to the database
//        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("message");
////        DatabaseReference myRef = database.getReference("message");
//        database.setValue("abdelhak , meha");







        // codebare btn

        codeBareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator= new IntentIntegrator(AddActivity.this);
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



        initDatePickerFab();
        initDatePickerPermp();





        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);
                String r="";

                if(checkBox.isChecked()){
                 r="oui";
                }else{
                    r="non";
                }




                boolean input1 = therap_input.getText().toString().trim().isEmpty();
                boolean input2 = com_input.getText().toString().trim().isEmpty();
                boolean input3 = labo_input.getText().toString().trim().isEmpty();
                boolean input4 = denom_input.getText().toString().trim().isEmpty();
                boolean input5 = form_input.getText().toString().trim().isEmpty();
                boolean input6 = duree_input.getText().toString().trim().isEmpty();
                boolean input7 = lot_input.getText().toString().trim().isEmpty();
                boolean input8 = btnFab_datePicker.getText().toString().trim().equals("Calendrier");
                boolean input9 = btnPermp_datePicker.getText().toString().trim().equals("Calendrier");
                boolean input10 = descrp_input.getText().toString().trim().isEmpty();
                boolean input11 = prix_input.getText().toString().trim().isEmpty();
                boolean input12 = qnt_input.getText().toString().trim().isEmpty();
                boolean input13 = code_input.getText().toString().trim().isEmpty();



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
                        therap_input.setError("le champ est vide");
                    }

                        if(input2)
                        {
                            com_input.setError("le champ est vide");
                        }

                        if(input3)
                        {
                            labo_input.setError("le champ est vide");
                        }
                        if(input4)
                        {
                            denom_input.setError("le champ est vide");
                        }

                        if(input5)
                        {
                            form_input.setError("le champ est vide");
                        }

                        if(input6)
                        {
                            duree_input.setError("le champ est vide");
                        }

                        if(input7)
                        {
                            lot_input.setError("le champ est vide");
                        }

                        if(input8)
                        {
                            btnFab_datePicker.setError("le champ est vide");
                        }

                        if(input9)
                        {
                            btnPermp_datePicker.setError("le champ est vide");
                        }

                        if(input10)
                        {
                            descrp_input.setError("le champ est vide");
                        }

                        if(input11)
                        {
                            prix_input.setError("le champ est vide");
                        }

                        if(input12)
                        {
                            qnt_input.setError("le champ est vide");
                        }

                        if(input13)
                        {
                            code_input.setError("le champ est vide");
                        }




                     } else {

                    myDB.addMedicin(
                            therap_input.getText().toString().trim(),
                            com_input.getText().toString().trim(),
                            labo_input.getText().toString().trim(),
                            denom_input.getText().toString().trim(),
                            form_input.getText().toString().trim(),
                            Integer.valueOf(duree_input.getText().toString().trim()),
                            r,
                            lot_input.getText().toString().trim(),
                            btnFab_datePicker.getText().toString().trim(),
                            btnPermp_datePicker.getText().toString().trim(),
                            descrp_input.getText().toString().trim(),
                            Integer.valueOf(prix_input.getText().toString().trim()),
                            Integer.valueOf(qnt_input.getText().toString().trim()),
                            code_input.getText().toString().trim()

                    );


                    therap_input.setText("");
                    com_input.setText("");
                    labo_input.setText("");
                    denom_input.setText("");
                    form_input.setText("");
                    duree_input.setText(null);
                    checkBox.setChecked(false);
                    lot_input.setText("");
                    btnFab_datePicker.setText("Calendrier");
                    btnPermp_datePicker.setText("Calendrier");
                    descrp_input.setText("");
                    prix_input.setText(null);
                    qnt_input.setText(null);
                    code_input.setText(null);


                }


            }
        });
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


    private void initDatePickerFab()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                btnFab_datePicker.setText(date);

            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

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
                btnPermp_datePicker.setText(date);

            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

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
                code_input.setText(intentResult.getContents());
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
                AlertDialog.Builder  builder =new AlertDialog.Builder(AddActivity.this);
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
                AlertDialog.Builder  builder1 =new AlertDialog.Builder(AddActivity.this);
//                builder.setTitle("result");
                TextView d1= new TextView(AddActivity.this);
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
//
//    public void scan(){
//        IntentIntegrator integrator = new IntentIntegrator(AddActivity.this);
//        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
//        integrator.setPrompt("Scan QR Code");
////        integrator.setCameraId(0);  // Use a specific camera of the device
//        integrator.setBeepEnabled(false);
//        integrator.setBarcodeImageEnabled(true);
//        integrator.setCaptureActivity(Capture.class);
//        integrator.initiateScan();
//    }


    // ocr
    public void doProcess() {
        //open the camera => create an Intent object
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,205);
    }
}
