package com.mondkars.mondkarsproduct;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.mondkars.mondkarsproduct.Utils.Users;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class UserDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText name, address1, address2, email;
    private Button register;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    DatabaseReference databaseReference;
    private CollectionReference collectionReference = db.collection("Registering users");
    DatabaseReference cities = FirebaseDatabase.getInstance().getReference().child("Cities");
    Spinner staticSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        staticSpinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.Specialization_array,
                        android.R.layout.simple_spinner_item);
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        staticSpinner.setAdapter(staticAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        address1 = (EditText) findViewById(R.id.house);
        address2 = (EditText) findViewById(R.id.road);
        register = (Button) findViewById(R.id.save);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        register.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void verifySignInCode() {
        {
            final String Name = name.getText().toString().trim();
            final String Email = email.getText().toString().trim();
            String City = staticSpinner.getSelectedItem().toString();
            final String Address = address1.getText().toString().trim() + ", " + address2.getText().toString().trim() + ", " + staticSpinner.getSelectedItem().toString();
            {
                {
                    {
                        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    {
                                        // Sign in success, update UI with the signed-in user's information
                                        currentUser = firebaseAuth.getCurrentUser();
                                        final String currentUserNumber = currentUser.getPhoneNumber();
                                        Map<String, String> userObj = new HashMap<>();
                                        userObj.put("Name", Name);
                                        userObj.put("City", City);
                                        userObj.put("Address", Address);
                                        userObj.put("Email", Email);

                                        collectionReference.document(currentUserNumber).set(userObj);

                                        Users users = Users.getInstance();
                                        users.setEmail(Email);
                                        users.setName(Name);
                                        users.setUserNumber(currentUserNumber);
                                        users.setCity(City);
                                        users.setAddress(Address);
                                        Toast.makeText(com.mondkars.mondkarsproduct.UserDetailsActivity.this, "Your details are saved successfully.", Toast.LENGTH_LONG).show();
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(com.mondkars.mondkarsproduct.UserDetailsActivity.this, Main2Activity.class);
                                        startActivity(intent);
                                        finish();
                                        // ...
                                    }
                                }
                            }
                        });
                    }


                }
            }

        }
    }

    @Override
    public void onClick(View view) {
        if (view == register) {
            progressDialog.setMessage("Saving your details");
            progressDialog.show();
            final String Name = name.getText().toString().trim();
            final String City = staticSpinner.getSelectedItem().toString().trim();
            final String Address = address1.getText().toString().trim() + ", " + address2.getText().toString().trim() + ", " + staticSpinner.getSelectedItem().toString();
            if (TextUtils.isEmpty(Name) && TextUtils.isEmpty(Address)) {
                Toast.makeText(com.mondkars.mondkarsproduct.UserDetailsActivity.this, "Please fill all the details", Toast.LENGTH_LONG).show();
            } else if (TextUtils.isEmpty(Name)) {
                Toast.makeText(com.mondkars.mondkarsproduct.UserDetailsActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                return;
            }else if (staticSpinner.getSelectedItem().toString().equals("Select City")) {
                progressDialog.dismiss();
                Toast.makeText(UserDetailsActivity.this, "Please select your city", Toast.LENGTH_SHORT).show();
                return;
            }
            else if (address1.getText().toString().isEmpty() || address2.getText().toString().isEmpty()) {
                Toast.makeText(com.mondkars.mondkarsproduct.UserDetailsActivity.this, "Please complete the address", Toast.LENGTH_LONG).show();
            } else if (email.getText().toString().isEmpty()) {
                Toast.makeText(com.mondkars.mondkarsproduct.UserDetailsActivity.this, "Please enter your Email", Toast.LENGTH_SHORT).show();
            } else {
                sendVerificationCode();
            }
        }
    }

    private void sendVerificationCode() {
        cities.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    if(staticSpinner.getSelectedItem().toString().equals(dataSnapshot.getValue().toString())){
                        verifySignInCode();
                        return;
                    }
                }
                progressDialog.cancel();
                Toast.makeText(com.mondkars.mondkarsproduct.UserDetailsActivity.this, "Sorry, We are not serving in your locality as of now. We will notify you once we start our service in your area.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            };
        });
    }
    public boolean onOptionsItemSelected(MenuItem item){
        firebaseAuth.getCurrentUser().delete();
        firebaseAuth.signOut();
        finish();
        return true;
    }
    @Override
    public void onBackPressed() {
        firebaseAuth.getCurrentUser().delete();
        firebaseAuth.signOut();
        finish();
    }
}
