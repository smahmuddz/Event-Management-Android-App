package edu.ewubd.cse489120221;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class CreateEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_information_layout);

        EditText eName = findViewById(R.id.etName);
        EditText ePlace = findViewById(R.id.etPlace);
        RadioButton rdOnline = findViewById(R.id.rdOnline);
        EditText eDateTime = findViewById(R.id.etDateTime);
        EditText eCapacity = findViewById(R.id.etCapacity);
        EditText eBudget = findViewById(R.id.etBudget);
        EditText eEmail = findViewById(R.id.etEmail);
        EditText ePhone = findViewById(R.id.etPhone);
        EditText eDescription = findViewById(R.id.etDescription);
        Button btnSave = findViewById(R.id.btnSave);
        Button btnCancel = findViewById(R.id.btnCancel);
        Button btnShare = findViewById(R.id.btnShare);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CreateEventActivity.this, "Share Button", Toast.LENGTH_SHORT).show();
            }
        });



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = eName.getText().toString().trim();
                String place = ePlace.getText().toString().trim();
                boolean isOnlineSelected = rdOnline.isChecked();
                String time = eDateTime.getText().toString().trim();
                String capacity = eCapacity.getText().toString().trim();
                String budget = eBudget.getText().toString().trim();
                String email = eEmail.getText().toString().trim();
                String phone = ePhone.getText().toString().trim();
                String description = eDescription.getText().toString().trim();

                if (eName.getText().toString().length() == 0){
                    eName.setError("Name is required!");
                }
                if (eEmail.getText().toString().length() == 0){
                    eEmail.setError("Email is required!");
                }


                System.out.println("Name: "+name);
                System.out.println("Place: "+place);
                System.out.println("isOnlineSelected: "+isOnlineSelected);
                System.out.println("Date & Time: "+time);
                System.out.println("Capacity: "+capacity);
                System.out.println("Budget: "+budget);
                System.out.println("Email: "+email);
                System.out.println("Phone: "+phone);
                System.out.println("Description: "+description);

                String value = name + "-::-" + place + "-::-" +isOnlineSelected+"-::-"+time+ "-::-" + capacity + "-::-" + budget + "-::-" + email + "-::-" + phone+"-::-"+description;
                if (name.length() > 0) {
                    value += "-::-" + name;
                }

                Intent current = getIntent();
                String key = current.getStringExtra("key");
                if (key != null && !key.isEmpty()) {
                    Util.getInstance().setKeyValue(CreateEventActivity.this, key, value);
                }

            }
        });
    }
}