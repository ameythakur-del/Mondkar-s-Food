package com.mondkars.mondkarsproduct;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

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
    TextView name, address, mobile1, mobile2;
    Button button, confirm;
    String currentUserId;
    RadioButton checkBox1, checkBox2;
    final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
    Timer timer;
    final String CHANNEL_ID = "personal notifications";
    final int NOTIFICATION_ID = 001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        currentUserId = user.getUid();

        checkBox1 = findViewById(R.id.home_check);
        checkBox1.setChecked(true);
        checkBox2 = findViewById(R.id.takeaway_check);
        confirm = findViewById(R.id.change);
        name = findViewById(R.id.name_view);
        address = findViewById(R.id.address_view);
        mobile1 = findViewById(R.id.mobile1);
        mobile2 = findViewById(R.id.mobile2);
        button = findViewById(R.id.confirm);

        collectionReference
                .whereEqualTo("UserId", currentUserId)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if(e != null){

                        }
                        assert  queryDocumentSnapshots != null;
                        if(!queryDocumentSnapshots.isEmpty()){
                            for(QueryDocumentSnapshot snapshot : queryDocumentSnapshots){
                                Users users = Users.getInstance();
                                String temp = snapshot.getString("Address");
                                address.setText(temp + ".");
                                name.setText(snapshot.getString("Name"));
                                mobile1.setText(snapshot.getString("Mobile"));
                                mobile2.setText(snapshot.getString("Alternate mobile"));
                            }
                        }
                        else
                            Toast.makeText(ConfirmActivity.this, "Couldn't find your address !", Toast.LENGTH_LONG).show();
                    }
                });
     confirm.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             createNotificationChannel();
             NotificationCompat.Builder builder = new NotificationCompat.Builder(ConfirmActivity.this, CHANNEL_ID);
             builder.setSmallIcon(R.drawable.logo);
             builder.setContentTitle("Thank you for ordering with us !");
             builder.setContentText("Be ready with your plates, We are coming soon !");
             builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

             Intent fintent = new Intent(ConfirmActivity.this, ContactUs.class);
             fintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
             PendingIntent pendingIntent = PendingIntent.getActivity(ConfirmActivity.this, 0, fintent, PendingIntent.FLAG_UPDATE_CURRENT);
             builder.setContentIntent(pendingIntent);

             NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(ConfirmActivity.this);
             notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
             if (checkBox2.isChecked()){
                 Intent intent = new Intent();
                 intent.putExtra("Take", "True");
                 setResult(RESULT_OK, intent);
                 finish();
             }
             if (!checkBox2.isChecked() && !checkBox1.isChecked()){
                 Toast.makeText(ConfirmActivity.this, "Kindly select any of the above options", Toast.LENGTH_LONG).show();
             }
             if (checkBox1.isChecked()){
                 Intent intent = new Intent();
                 setResult(RESULT_FIRST_USER, intent);
                 finish();
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
        finish();
        startActivity(new Intent(ConfirmActivity.this, MyCart.class));
    }
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MyCart.class);
        startActivityForResult(myIntent, 0);
        finish();
        return true;
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            CharSequence name = "Personal Notifications";
            String description = "Include all personal notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, importance);

            notificationChannel.setDescription(description);

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}
