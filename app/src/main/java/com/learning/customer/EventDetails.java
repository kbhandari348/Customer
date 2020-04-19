package com.learning.customer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.learning.customer.EventsAdapter.Event_ID;
import static com.learning.customer.R.drawable.button_rectangle_disabled;

public class EventDetails extends AppCompatActivity {

    Events event;
    String intentFrom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        Intent intent =getIntent();

        event = (Events) intent.getExtras().getSerializable("eventObj");
        intentFrom = intent.getExtras().getString("intentFrom");

        TextView nameD = findViewById(R.id.textViewNameD);
        TextView organiserD = findViewById(R.id.textViewOrganiserD);
        final TextView venueD = findViewById(R.id.textViewVenueD);
        TextView startDateD = findViewById(R.id.textViewStartDateD);
        TextView startTimeD = findViewById(R.id.textViewStartTimeD);
        TextView endDateD = findViewById(R.id.textViewEndDateD);
        TextView phoneD = findViewById(R.id.textViewPhoneD);
        TextView priceD = findViewById(R.id.textViewTicketPriceD);
        Button buttonNavigate = findViewById(R.id.buttonNavigate);
        Button buttonRegister = findViewById(R.id.buttonRegister);
        Button buttonBackToDisplay = findViewById(R.id.buttonBackToDisplay);

        nameD.setText(event.getName());
        organiserD.setText(event.getOrganiser());
        venueD.setText(event.getVenue());
        startDateD.setText(event.getStartDate());
        startTimeD.setText(event.getStartTime());
        endDateD.setText(event.getEndDate());
        phoneD.setText(event.getContactInfo());
        priceD.setText(event.getTicketPrice());

        buttonNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s = venueD.getText().toString();
                String d = s.replaceAll(" ","+");
                String m = d.replaceAll(",","+");
                String uri = String.format("google.navigation:q=%s",m);
                Uri gmmIntentUri = Uri.parse(uri);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }

            }
        });

        if (intentFrom.equals("RegisteredEventsActivity")) {
            buttonRegister.setText("Registered");
            buttonRegister.setBackgroundResource(button_rectangle_disabled);
        }

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (intentFrom.equals("RegisteredEventsActivity")) {
                    Toast.makeText(EventDetails.this, "Event Already Registered!", Toast.LENGTH_SHORT).show();
                } else  if (intentFrom.equals("DisplayEvents")) {
                    Intent intent = new Intent(EventDetails.this, EventRegistration.class);
                    intent.putExtra(Event_ID, event.getId());
                    intent.putExtra("eventObj", event);
                    startActivity(intent);
                }
            }
        });

        buttonBackToDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                if(intentFrom.equals("DisplayEvents")){
                    Intent intent = new Intent(EventDetails.this, DisplayEvents.class);
                    startActivity(intent);
                } else if(intentFrom.equals("RegisteredEventsActivity")) {
                    Intent intent = new Intent(EventDetails.this, RegisteredEventsActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
