package com.mondkars.mondkarsproduct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    Timer timer;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Version");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View overlay = findViewById(R.id.mylayout);

        overlay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                |  View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                |  View.SYSTEM_UI_FLAG_FULLSCREEN);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue().toString().equals("2")) {
                            Intent intent = new Intent(MainActivity.this, Activity2.class);
                            startActivity(intent);
                            startActivity(new Intent(MainActivity.this, Main2Activity.class));
                            finish();
                        }
                        else {
                            startActivity(new Intent(MainActivity.this, Update.class));
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }, 3000);
    }
}
