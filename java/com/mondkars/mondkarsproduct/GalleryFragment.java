package com.mondkars.mondkarsproduct;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import model.MyOrder;
import ui.OrderRecyclerAdapter;

public class GalleryFragment extends AppCompatActivity {

    private static final int TRACK_ACTIVITY_REQUEST_CODE = 1;
    final String CHANNEL_ID = "personal notifications";
    final int NOTIFICATION_ID = 001;
    int d = 0;
    public List<MyOrder> myOrders;
    public RecyclerView recyclerView;
    public OrderRecyclerAdapter orderRecyclerAdapter;
    DatabaseReference reference, data, databaseReference;
    public Integer cost = 0;
    public TextView dprice, dcharge, dtotal, status;
    DatabaseReference dileveryReference, minReference;
    CardView cardView1, cardView2;
    TextView textView1, textView2, textView3, earning;
    ImageView imageView;
    ProgressBar progressBar;
    Button button;
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String currentUserId = user.getUid();
    String currentUserPhone = user.getPhoneNumber();
    DatabaseReference limit = FirebaseDatabase.getInstance().getReference().child("Availed").child(currentUserId);
    DatabaseReference limitnot = FirebaseDatabase.getInstance().getReference().child("limit");
    DatabaseReference location = FirebaseDatabase.getInstance().getReference().child("Location");
    DatabaseReference faraalOrders = FirebaseDatabase.getInstance().getReference().child("Faraal Orders");
    int off = 0;

    String charge, min;
    int ch = 0;
    int Min = 0;
    int g=0;

    TextView coupon, discount;
    public String a = "0", k;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_gallery);
        myOrders = new ArrayList<MyOrder>();

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        earning = findViewById(R.id.earning_discount_price);
        button = findViewById(R.id.track);
        progressBar = findViewById(R.id.progressbar);
        textView1 = findViewById(R.id.empty);
        textView2 = findViewById(R.id.offer);
        textView3 = findViewById(R.id.location);
        imageView = findViewById(R.id.cart_icon);
        cardView1 = findViewById(R.id.cardview1);
        cardView2 = findViewById(R.id.cardview2);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            new SweetAlertDialog(com.mondkars.mondkarsproduct.GalleryFragment.this)
                    .setTitleText("Your order is confirmed. We are arriving soon !")
                    .show();
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.mondkars.mondkarsproduct.GalleryFragment.this, TrackActivity.class);
                startActivityForResult(intent, TRACK_ACTIVITY_REQUEST_CODE);
            }
        });

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            limit.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        a = dataSnapshot.child("percent").getValue().toString();
                        k = dataSnapshot.child("code").getValue().toString();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            {
                startActivity(new Intent(this, Activity2.class));
                finish();
                Toast.makeText(this, "You need to login first to view your orders !", Toast.LENGTH_LONG).show();
            }
        }

        status = findViewById(R.id.status);

        dprice = findViewById(R.id.order_cost);

        dcharge = findViewById(R.id.onum_charges);
        dtotal = findViewById(R.id.onum_total);

        coupon = findViewById(R.id.disconted_price);
        discount = findViewById(R.id.order_discount_price);
        recyclerView = (RecyclerView) findViewById(R.id.order_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dileveryReference = FirebaseDatabase.getInstance().getReference().child("charge").child("delivery");
        minReference = FirebaseDatabase.getInstance().getReference().child("charge").child("Min");
        if (user != null) {
            reference = FirebaseDatabase.getInstance().getReference().child("Order");
            databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserPhone);
            {
                {
                    {
                        dcharge.setText("\u20B9" + "0");
                        Query query = reference.orderByChild("userId").equalTo(user.getPhoneNumber());
                        query.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                myOrders = new ArrayList<MyOrder>();
                                cost = 0;
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    {
                                        final MyOrder ameya = dataSnapshot1.getValue(MyOrder.class);
                                        int temp = Integer.parseInt(ameya.getPrice());
                                        int temp2 = Integer.parseInt(ameya.getNumber());
                                        d = d + Integer.parseInt(ameya.getDiscount());
                                        cost = cost + temp * temp2;
                                        myOrders.add(ameya);
                                    }
                                }
                                if (myOrders.toString() == "[]"){
                                    button.setVisibility(View.INVISIBLE);
                                    cardView1.removeAllViews();
                                }
                                faraalOrders.child(user.getPhoneNumber()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.exists()){
                                            for(DataSnapshot dataSnapshot4: snapshot.getChildren()) {
                                                if(dataSnapshot4.getChildrenCount() > 1) {
                                                    MyOrder ameya = dataSnapshot4.getValue(MyOrder.class);
                                                    int temp = Integer.parseInt(ameya.getPrice());
                                                    int temp2 = Integer.parseInt(ameya.getNumber());
                                                    d = d + Integer.parseInt(ameya.getDiscount());
                                                    cost = cost + temp * temp2;
                                                    myOrders.add(ameya);
                                                }
                                            }
                                        }
                                        if (myOrders.toString() == "[]") {
                                            button.setVisibility(View.INVISIBLE);
                                            progressBar.setVisibility(View.INVISIBLE);
                                            cardView1.setVisibility(View.INVISIBLE);
                                            recyclerView.setVisibility(View.INVISIBLE);
                                            cardView2.setVisibility(View.INVISIBLE);
                                            imageView.setVisibility(View.VISIBLE);
                                            textView1.setVisibility(View.VISIBLE);
                                            textView2.setVisibility(View.VISIBLE);
                                        }
                                        else {
                                            minReference.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    min = dataSnapshot.getValue().toString();
                                                    Min = Integer.valueOf(min);
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });

                                            dileveryReference.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    charge = dataSnapshot.getValue().toString();
                                                    ch = Integer.valueOf(charge);

                                                    DatabaseReference giftReference = FirebaseDatabase.getInstance().getReference().child("Marketers").child(currentUserPhone);

                                                    giftReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                            if (snapshot.exists()) {
//                                stotal.setText("\u20B9" + snapshot.child("money").getValue().toString());
//                                fintotal.setText("\u20B9" + (cost + ch - d - Integer.parseInt(snapshot.child("money").getValue().toString())));
                                                                g = Integer.parseInt(snapshot.child("money").getValue().toString());

                                                            } else {
                                                                g = 0;
                                                            }
                                                            discount.setText("\u20B9" + (d));
                                                            if (cost >= Min) {
                                                                earning.setText("\u20B9" + g);
                                                                dprice.setText("\u20B9" + (cost));
                                                                dcharge.setText("Free");
                                                                dcharge.setTextColor(Color.parseColor("#008000"));
                                                                if(g > cost - d){
                                                                    g = cost - d;
                                                                }
                                                                dtotal.setText("\u20B9" + (cost - d - g));
                                                                databaseReference.addValueEventListener(new ValueEventListener() {
                                                                    @Override
                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                        if (dataSnapshot.child("Take Away").exists()) {
                                                                            dcharge.setText("Take Away");
                                                                        }
                                                                    }

                                                                    @Override
                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                    }
                                                                });
                                                            } else {
                                                                earning.setText("\u20B9" + g);
                                                                dprice.setText("\u20B9" + (cost));
                                                                dcharge.setText("\u20B9" + charge);
                                                                if(g > cost - d + ch){
                                                                    g = cost - d + ch;
                                                                }
                                                                dtotal.setText("\u20B9" + (cost + ch - d - g));
                                                                databaseReference.addValueEventListener(new ValueEventListener() {
                                                                    @Override
                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                        if (dataSnapshot.child("Take Away").exists()) {
                                                                            dcharge.setText("Take Away");
                                                                            if(g > cost - d) {
                                                                                g = cost - d;
                                                                            }
                                                                            dcharge.setTextColor(Color.parseColor("#008000"));
                                                                            dtotal.setText("\u20B9" + (cost - d - g));
                                                                        }
                                                                    }

                                                                    @Override
                                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                                    }
                                                                });

                                                            }

                                                            progressBar.setVisibility(View.INVISIBLE);
                                                            orderRecyclerAdapter = new OrderRecyclerAdapter(com.mondkars.mondkarsproduct.GalleryFragment.this, myOrders);
                                                            recyclerView.setAdapter(orderRecyclerAdapter);
                                                            orderRecyclerAdapter.notifyDataSetChanged();
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError error) {
                                                            Toast.makeText(com.mondkars.mondkarsproduct.GalleryFragment.this, "Network error", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }
        }
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            data = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserPhone);
            data.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    status.setText("Waiting for our team to aprove your order !");
                    if (dataSnapshot.child("status").exists()) {
                        status.setText(dataSnapshot.child("status").getValue().toString());
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.child("Take Away").exists()) {
                                    textView3.setVisibility(View.VISIBLE);
                                    textView3.setText("Click here to view pick-up location");
                                    textView3.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            location.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(dataSnapshot.getValue().toString()));
                                                    startActivity(intent);
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        if (dataSnapshot.child("status").getValue().toString().equals("Sorry ! We cant place your order since ")) {
                            button.setVisibility(View.INVISIBLE);
                        }
                        if (!dataSnapshot.child("status").getValue().toString().equals("Sorry ! We cant place your order since ")) {
                            button.setVisibility(View.VISIBLE);
                        }

                        if (dataSnapshot.child("reason").exists() && dataSnapshot.child("status").getValue().toString().equals("Sorry ! We cant place your order since ")) {
                            status.setText(dataSnapshot.child("status").getValue().toString() + "" + dataSnapshot.child("reason").getValue().toString());
                        }

                        if (dataSnapshot.child("status").getValue().toString().equals("We are Out for delivery. Coming soon !") || dataSnapshot.child("status").getValue().toString().equals("Your order is ready to pickup")){
                            createNotificationChannel();
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(com.mondkars.mondkarsproduct.GalleryFragment.this, CHANNEL_ID);
                            builder.setSmallIcon(R.drawable.logo);
                            builder.setContentTitle("Thank you for ordering with us !");
                            builder.setContentText("Share your experience with our taste and service here");
                            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

                            Intent intent = new Intent(com.mondkars.mondkarsproduct.GalleryFragment.this, ContactUs.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            PendingIntent pendingIntent = PendingIntent.getActivity(com.mondkars.mondkarsproduct.GalleryFragment.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                            builder.setContentIntent(pendingIntent);

                            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(com.mondkars.mondkarsproduct.GalleryFragment.this);
                            notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
                        }

                        if (dataSnapshot.child("status").getValue().toString().equals("Sorry ! We cant place your order since ")){
                            createNotificationChannel();
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(com.mondkars.mondkarsproduct.GalleryFragment.this, CHANNEL_ID);
                            builder.setSmallIcon(R.drawable.logo);
                            builder.setContentTitle("Sorry, We can't place your order");
                            builder.setContentText("We are currently not serving in your area. Inconvenience regretted!");
                            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

                            Intent intent = new Intent(com.mondkars.mondkarsproduct.GalleryFragment.this, ContactUs.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            PendingIntent pendingIntent = PendingIntent.getActivity(com.mondkars.mondkarsproduct.GalleryFragment.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                            builder.setContentIntent(pendingIntent);

                            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(com.mondkars.mondkarsproduct.GalleryFragment.this);
                            notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
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

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (myOrders.isEmpty()) {
            dprice.setText("\u20B9" + 0);
            dcharge.setText("\u20B9" + 0);
            dtotal.setText("\u20B9" + 0);
        }
    }
}
