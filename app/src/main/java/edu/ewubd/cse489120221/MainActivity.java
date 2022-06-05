package edu.ewubd.cse489120221;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button btnCreate, btnExit,btnHistory;

    // Reference objects for handling event lists
    private ListView lvEvents;
    private ArrayList<Event> events;
    private CustomEventAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("@MainActivity-onCreate()");

        btnCreate = findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, EventActivity.class);
            startActivity(intent);
        });
        btnExit = findViewById(R.id.btnExit);
        btnExit.setOnClickListener(view -> finish());
        findViewById(R.id.btnHistory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MyAttendanceActivity.class);
                startActivity(intent);
            }
        });


        // initialize list-reference by ListView object defined in XML
        lvEvents = findViewById(R.id.lvEvents);
        // load events from database if there is any
        loadData();
    }

    private void loadData(){
        events = new ArrayList<>();
        KeyValueDB db = new KeyValueDB(this);
        Cursor rows = db.execute("SELECT * FROM key_value_pairs");
        if (rows.getCount() == 0) {
            return;
        }
        //events = new Event[rows.getCount()];
        while (rows.moveToNext()) {
            String key = rows.getString(0);
            String eventData = rows.getString(1);
            String[] fieldValues = eventData.split("-::-");
            String name = fieldValues[0];
            String place = fieldValues[1];
            String dateTime=fieldValues[3];
            String eventType=fieldValues[2];
            String capacity=fieldValues[4];
            String buget=fieldValues[5];
            String email=fieldValues[6];
            String phone=fieldValues[7];
            String desc=fieldValues[8];
            Event e = new Event(key, name, place, dateTime, capacity, buget, email, phone,desc, eventType);
            events.add(e);
        }
        db.close();
        adapter = new CustomEventAdapter(this, events);
        lvEvents.setAdapter(adapter);

        // handle the click on an event-list item
        lvEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                // String item = (String) parent.getItemAtPosition(position);
                System.out.println(position);
                Intent i = new Intent(MainActivity.this, EventActivity.class);
                i.putExtra("EventKey", events.get(position).key);
                startActivity(i);
            }
        });

        // handle the long-click on an event-list item
        lvEvents.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                String message = "Do you want to delete event - "+events.get(position).name +" ?";
                System.out.println(message);
              showDialog(message,"Delete Event",events.get(position).key);
                return true;
            }
        });
    }

    private void showDialog(String message, String title, String key) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Util.getInstance().deleteByKey(MainActivity.this,key);
                dialogInterface.cancel();
                loadData();
                adapter.notifyDataSetChanged();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alert=builder.create();
        alert.show();
    }

    @Override
    public void onStart(){
        super.onStart();
        System.out.println("@MainActivity-onStart()");
    }
    @Override
    public void onResume(){
        super.onResume();
        System.out.println("@MainActivity-onResume()");
    }
    @Override
    public void onPause(){
        super.onPause();
        System.out.println("@MainActivity-onPause()");
    }
    @Override
    public void onStop(){
        super.onStop();
        System.out.println("@MainActivity-onStop()");
        // clear the event data from memory as the page is completely hidden by now
        events.clear();
    }
    @Override
    public void onRestart(){
        super.onRestart();
        System.out.println("@MainActivity-onRestart()");
        // re-load events from database after coming back from the next page
        loadData();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        System.out.println("@MainActivity-onDestroy()");
    }
}