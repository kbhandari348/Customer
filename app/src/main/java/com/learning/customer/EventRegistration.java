package com.learning.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class EventRegistration extends AppCompatActivity implements PaymentResultListener {

    Button registerEvent;
    EditText nameE;
    EditText collegeNameE;
    EditText collegeStudentIdE;
    EditText emailE;
    EditText phoneE;
    DatabaseReference ref;
    String tag;
    String idj;
    Button buttonBackToEventDetails;
    Events event;
    String nameEj;
    String collegeNameEj;
    String collegeStudentIdEj;
    String emailEj;
    String phoneEj;
    String userUID;
    Boolean flagPaymentSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventregistration);

        Intent intent = getIntent();
        idj= intent.getStringExtra(EventsAdapter.Event_ID);
        event = (Events) intent.getExtras().getSerializable("eventObj");
        ref=FirebaseDatabase.getInstance().getReference("client");

        tag = "Payment Error";

        registerEvent = findViewById(R.id.buttonRegisterEvent);
        nameE = findViewById(R.id.eventregName);
        collegeNameE = findViewById(R.id.eventregCollegeName);
        collegeStudentIdE = findViewById(R.id.eventregStudentID);
        emailE = findViewById(R.id.eventregEmail);
        phoneE = findViewById(R.id.eventregPhoneNumber);
        buttonBackToEventDetails = findViewById(R.id.buttonBackToEventDetails);

        registerEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveClient();
            }
        });

        buttonBackToEventDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(EventRegistration.this,EventDetails.class);
                intent.putExtra("eventObj",event);
                startActivity(intent);
            }
        });
    }

    private void saveClient() {
        nameEj = nameE.getText().toString();
        collegeNameEj = collegeNameE.getText().toString();
        collegeStudentIdEj = collegeStudentIdE.getText().toString();
        emailEj = emailE.getText().toString();
        phoneEj = phoneE.getText().toString();
        userUID = Login.User_UID;

        flagPaymentSuccess = false;

        if(nameEj.isEmpty()) {
            nameE.setError("Name is required");
            nameE.requestFocus();
            return;
        }

        if(collegeNameEj.isEmpty()) {
            collegeNameE.setError("College Name is required");
            collegeNameE.requestFocus();
            return;
        }

        if(collegeStudentIdEj.isEmpty()) {
            collegeStudentIdE.setError("Student ID is required");
            collegeStudentIdE.requestFocus();
            return;
        }

        if(emailEj.isEmpty()) {
            emailE.setError("Email is required");
            emailE.requestFocus();
            return;
        } else if(!emailEj.matches("[a-zA-Z0-9._-]+@[a-z]+(\\.+[a-z]+)+")){
            emailE.setError("Please! enter a valid email address");
            emailE.requestFocus();
            return;
        }

        if(phoneEj.isEmpty()) {
            phoneE.setError("Phone Number is required");
            phoneE.requestFocus();
            return;
        } else if(!phoneEj.matches("[6-9]{1}[0-9]{9}")){
            phoneE.setError("Phone Number is invalid");
            phoneE.requestFocus();
            return;
        }

        startPayment();
    }

    public void startPayment() {

        /**
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();
        // checkout.setKeyID("rzp_test_Ls5G93gnQXeVCo");
        /**
         * Set your logo here
         */
        //checkout.setImage(R.drawable.logo);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            /**
             * Merchant Name
             * eg: ACME Corp || HasGeek etc.
             */
            options.put("name", "Eventopedia");

            /**
             * Description can be anything
             * eg: Reference No. #123123 - This order number is passed by you for your internal reference. This is not the `razorpay_order_id`.
             *     Invoice Payment
             *     etc.
             */
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            //options.put("order_id", "order_9A33XWu170gUtm");
            options.put("currency", "INR");

            /**
             * Amount is always passed in currency subunits
             * Eg: "500" = INR 5.00
             */
            options.put("amount", event.getTicketPrice().replace(".",""));

            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e(tag, "Error in starting Razorpay Checkout", e);
        }
    }
    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this,"Payment Successful",Toast.LENGTH_LONG).show();
        flagPaymentSuccess = true;
        String id = ref.push().getKey();
        Clients client = new Clients(id, nameEj, collegeNameEj, collegeStudentIdEj, emailEj, phoneEj, idj, userUID);
        ref.child(id).setValue(client);
        Toast toast = Toast.makeText(EventRegistration.this,"You are successfully registered",Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this,"Error:Unable to make Payment",Toast.LENGTH_LONG).show();
    }
}
