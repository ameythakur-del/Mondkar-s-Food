package com.mondkars.mondkarsproduct;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mondkars.mondkarsproduct.Utils.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.CartItem;
import model.OrderForAdmin;
import ui.CartRecyclerAdapter;

public class MyCart extends AppCompatActivity {

    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;
    TextView offer;
    ImageView cartIcon;
    int d=0;
    public List<CartItem> myCart;
    public RecyclerView recyclerView;
    public CartRecyclerAdapter cartRecyclerAdapter;
    DatabaseReference reference, orderReference, minReference;
    public Integer cost=0;
    private TextView notext, nowarning;
    private ImageView noimage;
    public TextView total, address, name, dprice, dcharge, dtotal, discount;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Registering users");
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String currentUserId = user.getUid();
    private ProgressBar progressBar;
    public Button change, buy, coupon;
    String charge, min;
    int ch=0;
    int Min = 0;
    public String a = "0", k;
    TextView empty;
    DatabaseReference dileveryReference, adminOrder;
    CardView card1, card2, card3;
    DatabaseReference limit = FirebaseDatabase.getInstance().getReference().child("Availed").child(currentUserId);
    DatabaseReference limitnot = FirebaseDatabase.getInstance().getReference().child("limit");
    DatabaseReference data = FirebaseDatabase.getInstance().getReference().child("Stop");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
        myCart = new ArrayList<CartItem>();
        notext = findViewById(R.id.notext);
        nowarning = findViewById(R.id.nowarning);
        noimage = findViewById(R.id.noimage);
        progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.VISIBLE);
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
        offer = findViewById(R.id.offer);
        cartIcon = findViewById(R.id.cart_icon);
        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);
        empty = findViewById(R.id.empty);
        discount = findViewById(R.id.disconted_price);
        coupon = findViewById(R.id.coupon);
        buy = findViewById(R.id.buy_all);
        change = findViewById(R.id.change);
        address = findViewById(R.id.address_view);
        name = findViewById(R.id.name_view);
        total = findViewById(R.id.total_cost);
        dprice = findViewById(R.id.num_price);
        dcharge = findViewById(R.id.num_charges);
        dtotal = findViewById(R.id.num_total);

        recyclerView = (RecyclerView) findViewById(R.id.cart_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyCart.this));

        orderReference = FirebaseDatabase.getInstance().getReference().child("Order");
        reference = FirebaseDatabase.getInstance().getReference().child("cart");
        dileveryReference = FirebaseDatabase.getInstance().getReference().child("charge").child("delivery");
        minReference = FirebaseDatabase.getInstance().getReference().child("charge").child("Min");
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue().toString().equals("True")) {
                    noimage.setVisibility(View.VISIBLE);
                    notext.setVisibility(View.VISIBLE);
                    nowarning.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    card1.setVisibility(View.INVISIBLE);
                    card2.setVisibility(View.INVISIBLE);
                    card3.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);
                }
                else {
                    recyclerView.setVisibility(View.VISIBLE);
                    noimage.setVisibility(View.INVISIBLE);
                    notext.setVisibility(View.INVISIBLE);
                    nowarning.setVisibility(View.INVISIBLE);

                    collectionReference
                            .whereEqualTo("UserId", currentUserId)
                            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                    if (e != null) {

                                    }
                                    assert queryDocumentSnapshots != null;
                                    if (!queryDocumentSnapshots.isEmpty()) {
                                        for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                                            Users users = Users.getInstance();
                                            String temp = snapshot.getString("Address");
                                            address.setText(temp);
                                            name.setText(snapshot.getString("Name"));
                                        }
                                    } else
                                        Toast.makeText(MyCart.this, "Couldn't find your address !", Toast.LENGTH_LONG).show();
                                }
                            });

                    change.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (v == change) {
                                startActivity(new Intent(MyCart.this, ChangeAddress.class));
                            }
                        }
                    });

                    {
                        {
                            {
                                total.setText("\u20B9" + "0");
                                dcharge.setText("\u20B9" + "0");
                                Query query = reference.orderByChild("userId").equalTo(currentUserId);
                                query.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            myCart = new ArrayList<CartItem>();
                                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                            {
                                                final CartItem ameya = dataSnapshot1.getValue(CartItem.class);
                                                int temp = Integer.parseInt(ameya.getPrice());
                                                int temp2 = Integer.parseInt(ameya.getNumber());
                                                cost = cost + temp * temp2;
                                                myCart.add(ameya);
                                            }
                                        }
                                        if (cost == 0) {
                                            progressBar.setVisibility(View.INVISIBLE);
                                            empty.setVisibility(View.VISIBLE);
                                            cartIcon.setVisibility(View.VISIBLE);
                                            offer.setVisibility(View.VISIBLE);
                                        } else {
                                            int b = Integer.parseInt(a);
                                            int c = (cost * b / 100);
                                            d = Math.round(c);
                                            limitnot.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    if (dataSnapshot.exists()) {
                                                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                            if (dataSnapshot1.exists()) {
                                                                if (dataSnapshot1.getValue().toString().equals(currentUserId + k)) {
                                                                    d = 0;
                                                                }
                                                            }
                                                        }
                                                    }
                                                    discount.setText("\u20B9" + String.valueOf(d));
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });
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

                                                    if (cost >= Min) {
                                                        total.setText("\u20B9" + (cost - d));
                                                        dprice.setText("\u20B9" + cost);
                                                        dcharge.setText("Free");
                                                        dcharge.setTextColor(Color.parseColor("#008000"));
                                                        dtotal.setText("\u20B9" + (cost - d));
                                                    } else {
                                                        total.setText("\u20B9" + (cost + ch - d));
                                                        dprice.setText("\u20B9" + cost);
                                                        dcharge.setText("\u20B9" + charge);
                                                        dtotal.setText("\u20B9" + (cost + ch - d));
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });
                                            card3.setVisibility(View.VISIBLE);
                                            cartRecyclerAdapter = new CartRecyclerAdapter(MyCart.this, myCart);
                                            progressBar.setVisibility(View.INVISIBLE);
                                            recyclerView.setAdapter(cartRecyclerAdapter);
                                            cartRecyclerAdapter.notifyDataSetChanged();
                                            card1.setVisibility(View.VISIBLE);
                                            card2.setVisibility(View.VISIBLE);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        }
                    }
                    buy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (v == buy) {
                                Intent intent = new Intent(MyCart.this, ConfirmActivity.class);
                                startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);
                            }
                        }
                    });
                    coupon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                            startActivity(new Intent(MyCart.this, CouponActivity.class));
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that it is the SecondActivity with an OK result
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                for (CartItem cartItem : myCart){
                    String currentDateTimeString1 = java.text.DateFormat.getDateTimeInstance().format(new Date());
                    orderReference = FirebaseDatabase.getInstance().getReference().child("Order").child(cartItem.getUserId() + cartItem.getItem() + currentDateTimeString1);
                    orderReference.setValue(cartItem);

                    OrderForAdmin orderForAdmin = new OrderForAdmin();
                    orderForAdmin.setItem(cartItem.getItem());
                    orderForAdmin.setNumber(cartItem.getNumber());
                    orderForAdmin.setPrice(cartItem.getPrice());
                    orderForAdmin.setUserId(cartItem.getUserId());
                    final String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
                    adminOrder = FirebaseDatabase.getInstance().getReference().child("Order for admin").child(cartItem.getUserId()).child(cartItem.getItem() + cartItem.getPer() + cartItem.getNumber() + currentDateTimeString);
                    adminOrder.setValue(orderForAdmin);

                    final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(cartItem.getUserId());
                    final CollectionReference collectionReference = db.collection("Registering users");

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
                                            Map<String, String> userObj = new HashMap<>();
                                            userObj.put("Mobile", snapshot.getString("Mobile"));
                                            userObj.put("Name", snapshot.getString("Name"));
                                            userObj.put("UserId", snapshot.getString("UserId"));
                                            userObj.put("Alternate", snapshot.getString("Alternate mobile"));
                                            userObj.put("Address", snapshot.getString("Address"));
                                            userObj.put("Take Away", "True");
                                            ref.setValue(userObj);
                                        }
                                    }
                                }
                            });
                }
                Query query = reference.orderByChild("userId").equalTo(currentUserId);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            myCart = new ArrayList<CartItem>();
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            {
                                dataSnapshot1.getRef().removeValue();
                            }
                        }
                    }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                    });

                Intent fintent = new Intent(getBaseContext(), GalleryFragment.class);
                fintent.putExtra("PREVIOUS_ACTIVITY", this.getClass().getSimpleName());
                startActivity(fintent);
                finish();
            }


            if (resultCode == RESULT_FIRST_USER) {

                for (CartItem cartItem : myCart){
                    String currentDateTimeString1 = java.text.DateFormat.getDateTimeInstance().format(new Date());
                    orderReference = FirebaseDatabase.getInstance().getReference().child("Order").child(cartItem.getUserId() + cartItem.getItem() + cartItem.getNumber() + currentDateTimeString1);
                    orderReference.setValue(cartItem);

                    OrderForAdmin orderForAdmin = new OrderForAdmin();
                    orderForAdmin.setItem(cartItem.getItem());
                    orderForAdmin.setNumber(cartItem.getNumber());
                    orderForAdmin.setPrice(cartItem.getPrice());
                    orderForAdmin.setUserId(cartItem.getUserId());
                    final String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
                    adminOrder = FirebaseDatabase.getInstance().getReference().child("Order for admin").child(cartItem.getUserId()).child(cartItem.getItem() + cartItem.getPer() + cartItem.getNumber() + currentDateTimeString);
                    adminOrder.setValue(orderForAdmin);

                    final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(cartItem.getUserId());
                    final CollectionReference collectionReference = db.collection("Registering users");

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
                                            Map<String, String> userObj = new HashMap<>();
                                            userObj.put("Mobile", snapshot.getString("Mobile"));
                                            userObj.put("Name", snapshot.getString("Name"));
                                            userObj.put("UserId", snapshot.getString("UserId"));
                                            userObj.put("Alternate", snapshot.getString("Alternate mobile"));
                                            userObj.put("Address", snapshot.getString("Address"));
                                            ref.setValue(userObj);
                                        }
                                    }
                                }
                            });
                }

                Query query = reference.orderByChild("userId").equalTo(currentUserId);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            myCart = new ArrayList<CartItem>();
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            {
                                dataSnapshot1.getRef().removeValue();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Intent fintent = new Intent(getBaseContext(), GalleryFragment.class);
                fintent.putExtra("PREVIOUS_ACTIVITY", this.getClass().getSimpleName());
                startActivity(fintent);
                finish();
            }
        }
    }
}