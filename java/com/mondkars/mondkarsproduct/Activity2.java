package com.mondkars.mondkarsproduct;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mondkars.mondkarsproduct.Utils.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;



public class Activity2 extends AppCompatActivity  {
    private Button button3;
    private EditText editText;
    private EditText editText2;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private Button signup;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Registering users");
    private TextView forget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        firebaseAuth = FirebaseAuth.getInstance();

        forget = (TextView) findViewById(R.id.forget_password);
        editText = (EditText) findViewById(R.id.username);
        editText2 = (EditText) findViewById(R.id.password);
        button3 = (Button) findViewById(R.id.button3);
        progressDialog = new ProgressDialog(this);
        signup = (Button) findViewById(R.id.loginButton);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v == forget){
                    startActivity(new Intent(Activity2.this, ForgetPassword.class));
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == signup) {
                    startActivity(new Intent(Activity2.this, Activity3.class));
                }
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firebaseAuth.getCurrentUser() == null) {
                    loginUser(editText.getText().toString().trim(), editText2.getText().toString().trim());
                }
            }

            private void loginUser(final String username, String password) {

                if (TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
                    progressDialog.dismiss();
                    Toast.makeText(Activity2.this, "Please enter the username", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password) && !TextUtils.isEmpty(username) ) {
                    progressDialog.dismiss();
                    Toast.makeText(Activity2.this, "Please enter the password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password) && TextUtils.isEmpty(username) ) {
                    progressDialog.dismiss();
                    Toast.makeText(Activity2.this, "Please enter the username and the password", Toast.LENGTH_SHORT).show();
                    return;
                }



                else {
                    progressDialog.setMessage("You are logging in...");
                    progressDialog.show();
                    firebaseAuth.signInWithEmailAndPassword(username, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (firebaseAuth.getCurrentUser() != null) {
                                        final FirebaseUser user = firebaseAuth.getCurrentUser();
                                        assert user != null;
                                        final String currentUserId = user.getUid();

                                        collectionReference
                                                .whereEqualTo("UserId", currentUserId)
                                                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                                        if (e != null) {

                                                        }
                                                        assert queryDocumentSnapshots != null;
                                                        if (!queryDocumentSnapshots.isEmpty()) {
                                                            progressDialog.setMessage("You are logging in...");
                                                            progressDialog.show();
                                                            for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                                                                Users users = Users.getInstance();
                                                                users.setName(snapshot.getString("Name"));
                                                                users.setUserId(snapshot.getString("UserId"));
                                                                users.setMobile(snapshot.getString("Mobile"));
                                                                users.setAlternate(snapshot.getString("Alternate mobile"));
                                                                users.setAddress(snapshot.getString("Address"));
                                                                startActivity(new Intent(Activity2.this, Main2Activity.class));
                                                                progressDialog.dismiss();
                                                                Toast.makeText(Activity2.this, "Successfully logged in", Toast.LENGTH_LONG).show();
                                                                editText.getText().clear();
                                                                editText2.getText().clear();
                                                            }
                                                        }
                                                    }
                                                });
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(Activity2.this, "Something wrong happened", Toast.LENGTH_LONG).show();
                                }
                            });
                }
            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), Main2Activity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }
    @Override
    public void onBackPressed() {
       startActivity(new Intent(Activity2.this, Main2Activity.class));
    }
}

