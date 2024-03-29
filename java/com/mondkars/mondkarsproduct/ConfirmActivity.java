package com.mondkars.mondkarsproduct;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.mondkars.mondkarsproduct.Utils.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Timer;

public class ConfirmActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Registering users");
    TextView name, address, mobile1;
    Button button, confirm;
    String currentUserId;
    RadioButton checkBox1, checkBox2;
    final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
    Timer timer;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Availed");
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("charge");
    int payment=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        currentUserId = user.getPhoneNumber();

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        checkBox1 = findViewById(R.id.home_check);
        checkBox1.setChecked(true);
        checkBox2 = findViewById(R.id.takeaway_check);
        confirm = findViewById(R.id.change);
        name = findViewById(R.id.name_view);
        address = findViewById(R.id.address_view);
        mobile1 = findViewById(R.id.mobile1);
        button = findViewById(R.id.confirm);

        String currentUserPhone = user.getPhoneNumber();

        collectionReference.document(currentUserPhone).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                {

                    if (value.exists()) {
                        {
                            Users users = Users.getInstance();
                            String temp = value.getString("Address");
                            address.setText(temp + ".");
                            name.setText(value.getString("Name"));
                            mobile1.setText(value.getId());
                        }
                    }else
                        Toast.makeText(ConfirmActivity.this, "Couldn't find your address !", Toast.LENGTH_LONG).show();
                }
            }
        });
     confirm.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             if(getIntent().hasExtra("coupon")) {
                 reference.child(getIntent().getStringExtra("coupon")).child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).setValue(getIntent().getStringExtra("coupon"));
             }

             if (!checkBox2.isChecked() && !checkBox1.isChecked()){
                 Toast.makeText(ConfirmActivity.this, "Kindly select any of the above options", Toast.LENGTH_LONG).show();
             }
             else {
                 payment = getIntent().getIntExtra("payment", 0);
                 if(payment == 0){
                     setResult(RESULT_OK);
                     finish();
                     return;
                 }
                 databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot snapshot) {
                         if(payment < Integer.parseInt(snapshot.child("Min").getValue().toString()) && checkBox2.isChecked()){
                             payment -= Integer.parseInt(snapshot.child("delivery").getValue().toString());
                         }
                         Intent intent = new Intent(ConfirmActivity.this, PaymentActivity.class);
                         intent.putExtra("payment", payment);
                         startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);
                     }

                     @Override
                     public void onCancelled(@NonNull DatabaseError error) {

                     }
                 });
             }

         }
     });
    button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(ConfirmActivity.this, ChangeAddress.class));
        }
    });
    }
    public void radio1Clicked(View view)
    {
        checkBox2.setChecked(false);
    }
    public void radio2Clicked(View view)
    {
        checkBox1.setChecked(false);
    }
    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }
    public boolean onOptionsItemSelected(MenuItem item){
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent intent = new Intent();
        if(checkBox1.isChecked()){
            intent.putExtra("Delivery", "HomeDelivery");
        }
        else{
            intent.putExtra("Delivery", "Take Away");
        }
        setResult(resultCode, intent);
        finish();
    }
}
