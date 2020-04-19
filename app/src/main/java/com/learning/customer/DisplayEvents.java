package com.learning.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DisplayEvents extends AppCompatActivity {

    ListView listView;
    DatabaseReference refEvent;
    DatabaseReference refClient;
    List<Events> eventsList;
    List<String> eventIdList;
    Button buttonLogout;
    AlertDialog.Builder builder;
    Button buttonToRegisteredEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refEvent = FirebaseDatabase.getInstance().getReference("Events");
        refClient = FirebaseDatabase.getInstance().getReference("client");

        eventsList = new ArrayList<>();
        eventIdList = new ArrayList<>();
        listView = findViewById(R.id.listView);
        buttonToRegisteredEvents = findViewById(R.id.buttonToRegisteredEvents);
        buttonLogout = findViewById(R.id.buttonLogout);
        builder = new AlertDialog.Builder(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        refClient.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                eventIdList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Clients client = ds.getValue(Clients.class);
                    if (client.getUserUID().equals(Login.User_UID)) {
                        eventIdList.add(client.getEventIDj());
                    }
                }
                refEvent.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        eventsList.clear();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Events event = ds.getValue(Events.class);

                            if ((!eventIdList.contains(event.getId())) && event.getStatus().equals("Approved")) {
                                eventsList.add(event);
                            }
                        }

                        EventsAdapter adapter = new EventsAdapter(DisplayEvents.this, eventsList);
                        listView.setAdapter(adapter);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(DisplayEvents.this, EventDetails.class);
                                intent.putExtra("intentFrom", "DisplayEvents");
                                intent.putExtra("eventObj", eventsList.get(position));
                                startActivity(intent);
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        buttonToRegisteredEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayEvents.this, RegisteredEventsActivity.class);
                startActivity(intent);
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Setting message manually and performing action on button click
                builder.setMessage("Do you want to logout ?")
                        .setCancelable(false)
                        .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                                FirebaseAuth.getInstance().signOut();
                                Intent intToMain = new Intent(DisplayEvents.this, Login.class);
                                startActivity(intToMain);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Logout Alert!");
                alert.show();
            }
        });
    }
}


