package com.mondkars.mondkarsproduct;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.mondkars.mondkarsproduct.Utils.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Activity3 extends AppCompatActivity implements View.OnClickListener {

    private EditText username, name, mobile, address1, address2, address3, alternate;
    private EditText password, password2;
    private Button register;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    DatabaseReference databaseReference;
    private CollectionReference collectionReference = db.collection("Registering users");
    String codeSent;
    int c=0;
    String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        mobile = (EditText)findViewById(R.id.mobile);
        alternate = (EditText)findViewById(R.id.alternate_mobile);
        address1 = (EditText) findViewById(R.id.address1);
        address2 = (EditText) findViewById(R.id.address2);
        address3 = (EditText) findViewById(R.id.address3);
        register = (Button)findViewById(R.id.button3);
        username = (EditText) findViewById(R.id.username);
        name = (EditText) findViewById(R.id.name);
        password = (EditText)findViewById(R.id.password);
        password2 = (EditText)findViewById(R.id.password2);
        register.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

    }

    private void verifySignInCode() {
        {
            final String Username = username.getText().toString().trim();
            final String Password = password.getText().toString().trim();
            String Password2 = password2.getText().toString().trim();
            final String Name = name.getText().toString().trim();
            final String Mobile = mobile.getText().toString().trim();
            final String Alternate = alternate.getText().toString().trim();
            final String Address = address1.getText().toString().trim() + ", " + address2.getText().toString().trim() + ", " + address3.getText().toString().trim();
          {
              AlertDialog.Builder builder = new AlertDialog.Builder(Activity3.this, R.style.CustomDialogTheme);
              builder.setTitle("Enter the OTP");
// Set up the input
                final EditText input = new EditText(Activity3.this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
              input.setInputType(InputType.TYPE_CLASS_NUMBER);
              input.setTextColor(Color.BLACK);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog.setMessage("We are creating your account...");
                        progressDialog.show();
                        String m_Text = "";
                        m_Text = input.getText().toString();
                        if (m_Text.equals(code)){
                            {
                                collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                if (document.getString("Mobile").equals(Mobile)) {
                                                    c++;
                                                }
                                            }
                                            if (c > 0) {
                                                progressDialog.dismiss();
                                                Toast.makeText(Activity3.this, "You have already created the account with the same number", Toast.LENGTH_LONG).show();
                                                c = 0;
                                            } else {

                                                {
                                                    firebaseAuth.createUserWithEmailAndPassword(Username, Password)
                                                            .addOnCompleteListener(Activity3.this, new OnCompleteListener<AuthResult>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                                    if (task.isSuccessful()) {
                                                                        // Sign in success, update UI with the signed-in user's information
                                                                        currentUser = firebaseAuth.getCurrentUser();
                                                                        assert currentUser != null;
                                                                        final String currentUserId = currentUser.getUid();
                                                                        Map<String, String> userObj = new HashMap<>();
                                                                        userObj.put("Name", Name);
                                                                        userObj.put("Username", Username);
                                                                        userObj.put("UserId", currentUserId);
                                                                        userObj.put("Mobile", Mobile);
                                                                        userObj.put("Alternate mobile", Alternate);
                                                                        userObj.put("Address", Address);

                                                                        collectionReference.document(currentUserId).set(userObj);

                                                                        Users users = Users.getInstance();
                                                                        users.setName(Name);
                                                                        users.setUserId(currentUserId);
                                                                        users.setMobile(Mobile);
                                                                        users.setAlternate(Alternate);
                                                                        users.setAddress(Address);
                                                                        Toast.makeText(Activity3.this, "Your account is created successfully.", Toast.LENGTH_LONG).show();
                                                                        progressDialog.dismiss();
                                                                        finish();
                                                                        Intent intent = new Intent(Activity3.this, Main2Activity.class);
                                                                        startActivity(intent);
                                                                        // ...
                                                                    } else {
                                                                        {
                                                                            Toast.makeText(Activity3.this, "Something wrong happened", Toast.LENGTH_LONG).show();
                                                                            progressDialog.dismiss();
                                                                        }
                                                                    }
                                                                }
                                                            });
                                                }

                                            }
                                        }

                                        // Log.d(TAG, document.getId() + " => " + document.getData());


                                    }
                                });


                            }
                        }
                        else {
                            progressDialog.dismiss();
                            Toast.makeText(Activity3.this, "Please enter the valid OTP", Toast.LENGTH_LONG).show();
                        }

                    }

                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        }
    }

    @Override
    public void onClick(View view) {
        if(view == register) {
            final String Username = username.getText().toString().trim();
            final String Password = password.getText().toString().trim();
            String Password2 = password2.getText().toString().trim();
            final String Name = name.getText().toString().trim();
            final String Mobile = mobile.getText().toString().trim();
            final String Alternate = alternate.getText().toString().trim();
            final String Address = address1.getText().toString().trim() + ", " + address2.getText().toString().trim() + ", " + address3.getText().toString().trim();
            if (TextUtils.isEmpty(Username) && TextUtils.isEmpty(Password) && TextUtils.isEmpty(Name) && TextUtils.isEmpty(Mobile)) {
                Toast.makeText(Activity3.this, "Please fill all the details", Toast.LENGTH_LONG).show();
            } else if (TextUtils.isEmpty(Name)) {
                Toast.makeText(Activity3.this, "Please fill your name", Toast.LENGTH_SHORT).show();
                return;
            } else if (TextUtils.isEmpty(Username)) {
                Toast.makeText(Activity3.this, "Please create your username", Toast.LENGTH_SHORT).show();
                return;
            } else if (TextUtils.isEmpty(Mobile)) {
                Toast.makeText(Activity3.this, "Please fill your mobile number", Toast.LENGTH_SHORT).show();
                return;
            } else if (address1.getText().toString().isEmpty() || address2.getText().toString().isEmpty() || address3.getText().toString().isEmpty()) {
                Toast.makeText(Activity3.this, "Please enter the address", Toast.LENGTH_LONG).show();
            } else if (TextUtils.isEmpty(Password)) {
                Toast.makeText(Activity3.this, "Please create password", Toast.LENGTH_SHORT).show();
                return;
            } else if (!Password.equals(Password2)) {
                Toast.makeText(Activity3.this, "Both passwords are not matching", Toast.LENGTH_LONG).show();
            } else {
                progressDialog.setMessage("Generating OTP");
                progressDialog.show();
                sendVerificationCode();
            }
        }
    }

    private void sendVerificationCode() {
        String phone = mobile.getText().toString();

        if (phone.isEmpty()){
            progressDialog.dismiss();
            mobile.setError("Phone number is required");
            mobile.requestFocus();
            return;
        }

        if (phone.length() < 10){
            progressDialog.dismiss();
            mobile.setError("Please enter valid phone");
            mobile.requestFocus();
            return;
        }
        else {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    "+91" + phone,        // Phone number to verify
                    60,                 // Timeout duration
                    TimeUnit.SECONDS,   // Unit of timeout
                    this,               // Activity (for callback binding)
                    mCallbacks);
        }
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            progressDialog.dismiss();
            code = phoneAuthCredential.getSmsCode();
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
           progressDialog.dismiss();
           Toast.makeText(Activity3.this, "You have crossed the limit of number of mobile verifications. Try after some time", Toast.LENGTH_LONG).show();
        }


        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codeSent = s;
           progressDialog.dismiss();
            verifySignInCode();
        }
    };
}
