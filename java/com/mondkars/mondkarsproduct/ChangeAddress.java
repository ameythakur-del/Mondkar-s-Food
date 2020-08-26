package com.mondkars.mondkarsproduct;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ChangeAddress extends AppCompatActivity {

    private Button submit;
    private EditText edit1, edit2, edit3, mobile, mobile1;
    private ProgressDialog progressDialog;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Registering users");
    private FirebaseUser user;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_address);

        progressDialog = new ProgressDialog(this);
        submit = (Button)findViewById(R.id.submit);
        edit1 = (EditText)findViewById(R.id.new_address1);
        edit2 = (EditText)findViewById(R.id.new_address2);
        edit3 = (EditText)findViewById(R.id.new_address3);
        mobile = findViewById(R.id.mobile);
        mobile1 = findViewById(R.id.mobile1);

        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String address = edit1.getText().toString().trim() +", "+  edit2.getText().toString().trim() +", "   + edit3.getText().toString().trim();
                final String Mobile = mobile.getText().toString().trim();
                final String Mobile1 = mobile1.getText().toString().trim();
                if (edit1.getText().toString().isEmpty() || edit2.getText().toString().isEmpty() || edit3.getText().toString().isEmpty() && TextUtils.isEmpty(Mobile)){
                    Toast.makeText(ChangeAddress.this, "Please Enter the address and mobile number", Toast.LENGTH_LONG).show();
                }
                else if(edit1.getText().toString().isEmpty() || edit2.getText().toString().isEmpty() || edit3.getText().toString().isEmpty()){
                    Toast.makeText(ChangeAddress.this, "Please Enter your address", Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(Mobile)){
                    Toast.makeText(ChangeAddress.this, "Please Enter your mobile numbers", Toast.LENGTH_LONG).show();
                }
                else {
                    progressDialog.setMessage("We are updating your profile...");
                    progressDialog.show();

                    collectionReference.document(userId).update("Address", address);
                    collectionReference.document(userId).update("Mobile", Mobile);
                    collectionReference.document(userId).update("Alternate mobile", Mobile1);
                    progressDialog.dismiss();;
                    Toast.makeText(ChangeAddress.this, "updated successfully", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }
}
