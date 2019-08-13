 package com.saiproject.firebase2;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.saiproject.firebase2.Model.Computer;

 public class MainActivity extends AppCompatActivity {

     EditText edtComputerName;
     EditText edtComputerRam;
     EditText edtComputerPower;
     EditText edtComputerSpeed;

     Button btnUploadData;
     Button btnGetData;

     TextView txtComputerData;


     FirebaseDatabase db;
     DatabaseReference myRef;

     String uniqueComputerID;             //Valid 1 per user


     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);


         edtComputerName = findViewById(R.id.edtComputerName);
         edtComputerPower = findViewById(R.id.edtComputerPower);
         edtComputerRam = findViewById(R.id.edtComputerRAM);
         edtComputerSpeed = findViewById(R.id.edtComputerSpeed);


         btnUploadData = findViewById(R.id.btnUpload);
         btnGetData = findViewById(R.id.btnRetreiveData);

         txtComputerData = findViewById(R.id.txtComputerData);


         db = FirebaseDatabase.getInstance();


         changeUploadButtonText();
         /*

                          //Sample code for using myRef


         myRef = db.getReference("Computers");     //Refer to the Computers directory if not present, is created

         myRef.child("Key").setValue("Value");        //Child "Key" is created and value "Value" is set, if not present is created

         */



         myRef = db.getReference("Computers");     //Refer to the Computers directory if not present, is created


                            //Sample code for using ValueEventListener

         db.getReference("APPLICATION").setValue("Android 7.1.1 Nougat");



         db.getReference("APPLICATION").addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                 String applicationName = dataSnapshot.getValue(String.class);       //Class type is passed


                 getSupportActionBar().setTitle(applicationName);


             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {


             }
         });







         btnUploadData.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {


                 String computerName = edtComputerName.getText().toString();
                 String computerPower = edtComputerPower.getText().toString();
                 String computerSpeed = edtComputerSpeed.getText().toString();
                 String computerRAM = edtComputerRam.getText().toString();

                 int compPower = 0;
                 int compSpeed = 0;
                 int compRam = 0;

                 try {

                     compPower = Integer.parseInt(computerPower);
                     compRam = Integer.parseInt(computerRAM);
                     compSpeed = Integer.parseInt(computerSpeed);


                 } catch (Exception e) {
                     e.printStackTrace();
                 }



                 if(TextUtils.isEmpty(uniqueComputerID)){   // New user



                     createAndProduceNewComputer(computerName, compPower , compSpeed, compRam);

                     edtComputerName.setText("");
                     edtComputerPower.setText("");
                     edtComputerSpeed.setText("");
                     edtComputerRam.setText("");

                 }

                 else{          // Device already registered

                     modifyProducedComputer(computerName,computerPower,computerSpeed,computerRAM);

                 }



             }
         });



         btnGetData.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 if(uniqueComputerID == null)
                     return;

                 computerDataChangeListener();    // Gets data from Server







             }
         });

     }





     private void createAndProduceNewComputer(String computerName,
                                              int computerPower,
                                              int computerSpeed,
                                              int computerRAM) {


         if (TextUtils.isEmpty(uniqueComputerID)) {

             uniqueComputerID = myRef.push().getKey();  //push method creates a new ID

         }


         Computer computer = new Computer(computerName,computerPower,computerSpeed,computerRAM);

         myRef.child(uniqueComputerID).setValue(computer); //Create a child under key uniqueID and upload computer data

         changeUploadButtonText();

     }



     private void changeUploadButtonText(){     //Change Upload Button text as per situation


         if(TextUtils.isEmpty(uniqueComputerID))
             btnUploadData.setText("Produce new computer and send to server");

         else{
             btnUploadData.setText("Modify the produced computer data");
         }


     }




     private  void  modifyProducedComputer(String computerName,String computerPow,
                                           String computerSpeed, String computerRam
                                           ){

         if(!TextUtils.isEmpty(computerName)){
             myRef.child(uniqueComputerID).child("computerName").setValue(computerName);

         }




         if(!TextUtils.isEmpty(computerPow)){

             try{
                 String string=computerPow;
                 int computerPower = Integer.parseInt(string);
                 myRef.child(uniqueComputerID).child("computerPower").setValue(computerPower);

             }

             catch (Exception e){
                 e.printStackTrace();
             }

         }




         if(!TextUtils.isEmpty(computerSpeed)){

             try{
                 String string=computerSpeed;
                 int computerSPEED = Integer.parseInt(string);
                 myRef.child(uniqueComputerID).child("computerSpeed").setValue(computerSPEED);

             }

             catch (Exception e){
                 e.printStackTrace();
             }

         }



         if(!TextUtils.isEmpty(computerRam)){

             try{
                 String string=computerRam;
                 int computerRAM = Integer.parseInt(string);
                 myRef.child(uniqueComputerID).child("computerRam").setValue(computerRAM );

             }

             catch (Exception e){
                 e.printStackTrace();
             }

         }



     }



     private  void computerDataChangeListener(){


         myRef.child(uniqueComputerID).addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                 Computer computer = dataSnapshot.getValue(Computer.class);

                 if(computer == null)
                     return;



                 txtComputerData.setText("Computer Name: " + computer.getComputerName()+
                         "Computer Power: "+computer.getComputerPower()+
                         "Computer Speed: "+computer.getComputerSpeed()+
                         "Computer RAM: "+computer.getComputerRAM());



             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });




     }




 }
