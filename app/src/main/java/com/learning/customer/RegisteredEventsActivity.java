package com.learning.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RegisteredEventsActivity extends AppCompatActivity {

    ListView listView;
    List<Events> eventsList;
    List<String> eventIdList;
    DatabaseReference refEvent;
    DatabaseReference refClient;
    Button buttonBackToDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_events);

        Intent intent1 = getIntent();

        refEvent = FirebaseDatabase.getInstance().getReference("Events");
        refClient = FirebaseDatabase.getInstance().getReference("client");
        eventsList = new ArrayList<>();
        eventIdList = new ArrayList<>();
        listView = findViewById(R.id.listView);
        buttonBackToDisplay = findViewById(R.id.buttonBackToDisplay);
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
                            if (eventIdList.contains(event.getId())) {
                                eventsList.add(event);
                            }
                        }

                        EventsAdapter adapter = new EventsAdapter(RegisteredEventsActivity.this, eventsList);
                        listView.setAdapter(adapter);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(RegisteredEventsActivity.this, EventDetails.class);
                                intent.putExtra("intentFrom", "RegisteredEventsActivity");
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

        buttonBackToDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(RegisteredEventsActivity.this, DisplayEvents.class);
                startActivity(intent);
            }
        });
    }
}
