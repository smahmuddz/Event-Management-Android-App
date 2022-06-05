package edu.ewubd.cse489120221;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EventActivity extends AppCompatActivity {
    private EditText etName,etPlace,etDateTime,etCapacity,etBudget,etEmail,etPhone,etDescription;
    private RadioButton rdIndoor,rdOutdoor,rdOnline;

    // declare existingKey variable
    private String existingKey = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("@EventInformationActivity-onCreate()");
        setContentView(R.layout.event_information_layout);

       etName = findViewById(R.id.etName);
       etPlace = findViewById(R.id.etPlace);
       etDateTime = findViewById(R.id.etDateTime);
       etCapacity = findViewById(R.id.etCapacity);
       etBudget = findViewById(R.id.etBudget);
       etEmail = findViewById(R.id.etEmail);
       etPhone = findViewById(R.id.etPhone);
       etDescription = findViewById(R.id.etDescription);

       rdIndoor = findViewById(R.id.rdIndoor);
       rdOutdoor=findViewById(R.id.rdOutdoor);
       rdOnline=findViewById(R.id.rdOnline);


        findViewById(R.id.btnCancel).setOnClickListener(view -> finish());
        findViewById(R.id.btnSave).setOnClickListener(view -> save());

        // check in intent if there is any key set in putExtra
        Intent i = getIntent();
        existingKey = i.getStringExtra("EventKey");
        if(existingKey != null && !existingKey.isEmpty()) {
            initializeFormWithExistingData(existingKey);
        }
    }

    private void initializeFormWithExistingData(String eventKey){

        String value = Util.getInstance().getValueByKey(this, eventKey);
        System.out.println("Key: " + eventKey + "; Value: "+value);

        if(value != null) {
            String[] fieldValues = value.split("-::-");
            String name = fieldValues[0];
            String place = fieldValues[1];
            String dateTime=fieldValues[3];
            String eventType=fieldValues[2];
            String capacity=fieldValues[4];
            String buget=fieldValues[5];
            String email=fieldValues[6];
            String phone=fieldValues[7];
            String desc=fieldValues[8];

            etName.setText(name);
            etDateTime.setText(dateTime);
            etPlace.setText(place);
            etCapacity.setText(capacity);
            etBudget.setText(buget);
            etEmail.setText(email);
            etPhone.setText(phone);
            etDescription.setText(desc);


            if(eventType.equals("INDOOR")){
                rdIndoor.setChecked(true);
            }
           else if(eventType.equals("OUTDOOR")){
                rdOutdoor.setChecked(true);
            }
           else if(eventType.equals("ONLINE")){
                rdOnline.setChecked(true);
            }
        }
    }
    @Override
    public void onStart(){
        super.onStart();
        System.out.println("@EventInformationActivity-onStart()");
    }
    @Override
    public void onResume(){
        super.onResume();
        System.out.println("@EventInformationActivity-onResume()");
    }
    @Override
    public void onPause(){
        super.onPause();
        System.out.println("@EventInformationActivity-onPause()");
    }
    @Override
    public void onRestart(){
        super.onRestart();
        System.out.println("@EventInformationActivity-onRestart()");
    }
    @Override
    public void onStop(){
        super.onStop();
        System.out.println("@EventInformationActivity-onStop()");
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        System.out.println("@EventInformationActivity-onDestroy()");
    }

    private void save(){
        String name = etName.getText().toString().trim();
        String place = etPlace.getText().toString().trim();
        boolean isOnlineSelected = rdOnline.isChecked();
        String time = etDateTime.getText().toString().trim();
        String capacity = etCapacity.getText().toString().trim();
        String budget = etBudget.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        System.out.println("Event Name: "+name);
        boolean isIndoorChecked = rdIndoor.isChecked();
        boolean isOutdoorChecked = rdOutdoor.isChecked();
        boolean isOnlineChecked = rdOnline.isChecked();


        System.out.println("Is indoor checked: "+isIndoorChecked);
        // read all data types here
        if(name.length() < 4 || time.isEmpty()){
            System.out.println("Invalid Data");
            return;
        }
        String eventType = isIndoorChecked ? "INDOOR" : "";



        String key = name + "-::-" + place + "-::-" +isOnlineSelected+"-::-"+time+ "-::-" + capacity + "-::-" + budget + "-::-" + email + "-::-" + phone+"-::-"+description;
        if(existingKey != null){
            key = existingKey;
        }

        String value = name + "-::-" + place + "-::-" +isOnlineSelected+"-::-"+time+ "-::-" + capacity + "-::-" + budget + "-::-" + email + "-::-" + phone+"-::-"+description;
        Util.getInstance().setKeyValue(this, key, value);
        System.out.println();

        // show success message to the user
        Toast.makeText(this, "Event information has been saved successfully", Toast.LENGTH_LONG).show();
        // if data is saved successfully, destroy the current page
        finish();
    }
}