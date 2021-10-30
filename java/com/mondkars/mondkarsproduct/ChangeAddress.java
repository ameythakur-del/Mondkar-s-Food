package com.mondkars.mondkarsproduct;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ChangeAddress extends AppCompatActivity implements View.OnClickListener {

    private EditText name, address1, address2;
    private Button register;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    String currentUserPhone;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    DatabaseReference databaseReference;
    private CollectionReference collectionReference = db.collection("Registering users");
    DatabaseReference pincodes = FirebaseDatabase.getInstance().getReference().child("Pincodes");
    Spinner staticSpinner;
    DatabaseReference cities = FirebaseDatabase.getInstance().getReference().child("Cities");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_address);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        staticSpinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.Specialization_array,
                        android.R.layout.simple_spinner_item);
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        staticSpinner.setAdapter(staticAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        address1 = (EditText) findViewById(R.id.house_change);
        address2 = (EditText) findViewById(R.id.road_change);
        register = (Button) findViewById(R.id.save_change);
        name = (EditText) findViewById(R.id.name_change);
        register.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        currentUserPhone = currentUser.getPhoneNumber();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collectionReference.document(currentUserPhone).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                name.setText(value.get("Name").toString());
            }
        });
    }

    private void verifySignInCode() {
        {
            final String Name = name.getText().toString().trim();
            final String Pincode = staticSpinner.getSelectedItem().toString();
            final String Address = address1.getText().toString().trim() + ", " + address2.getText().toString().trim() + ", " + staticSpinner.getSelectedItem().toString();
            {
                progressDialog.setMessage("We are updating your profile...");
                progressDialog.show();

                collectionReference.document(currentUserPhone).update("Address", Address);
                collectionReference.document(currentUserPhone).update("Name", Name);
                collectionReference.document(currentUserPhone).update("City", Pincode);
                progressDialog.dismiss();
                Toast.makeText(ChangeAddress.this, "updated successfully", Toast.LENGTH_LONG).show();
                startActivity(new Intent(ChangeAddress.this, MyCart.class));
                finish();
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view == register) {
            progressDialog.setMessage("Saving your details");
            progressDialog.show();
            final String Name = name.getText().toString().trim();
            final String City = staticSpinner.getSelectedItem().toString();
            final String Address = address1.getText().toString().trim() + ", " + address2.getText().toString().trim() + ", " + staticSpinner.getSelectedItem();
            if (TextUtils.isEmpty(Name) && TextUtils.isEmpty(Address)) {
                progressDialog.dismiss();
                Toast.makeText(ChangeAddress.this, "Please fill all the details", Toast.LENGTH_LONG).show();
            } else if (TextUtils.isEmpty(Name)) {
                progressDialog.dismiss();
                Toast.makeText(ChangeAddress.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                return;
            }
            else if (staticSpinner.getSelectedItem().toString().equals("Select City")) {
                progressDialog.dismiss();
                Toast.makeText(ChangeAddress.this, "Please select your city", Toast.LENGTH_SHORT).show();
                return;
            }
            else if (address1.getText().toString().isEmpty() || address2.getText().toString().isEmpty()) {
                progressDialog.dismiss();
                Toast.makeText(ChangeAddress.this, "Please complete the address", Toast.LENGTH_LONG).show();
            } else {
                sendVerificationCode();
            }
        }
    }

    private void sendVerificationCode() {
        cities.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (staticSpinner.getSelectedItem().toString().equals(dataSnapshot.getValue().toString())) {
                        verifySignInCode();
                        return;
                    }
                }
                progressDialog.cancel();
                Toast.makeText(ChangeAddress.this, "Sorry, We are not serving in your locality as of now. We will notify you once we start our service in your area.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

            ;
        });
    }
}
