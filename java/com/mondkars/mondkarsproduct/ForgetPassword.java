package com.mondkars.mondkarsproduct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {
    private EditText forgetEmail;
    private Button forgetButton;
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        forgetEmail = (EditText) findViewById(R.id.forget_username);
        forgetButton = (Button) findViewById(R.id.forget_button);

        forgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == forgetButton){
                    final String Username = forgetEmail.getText().toString().trim();
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    if (!TextUtils.isEmpty(Username)){
                        auth.sendPasswordResetEmail(Username)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(ForgetPassword.this, "Email sent to your registered email address", Toast.LENGTH_LONG).show();
                                            finish();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ForgetPassword.this, "No such user", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    else
                        Toast.makeText(ForgetPassword.this, "Error", Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}
