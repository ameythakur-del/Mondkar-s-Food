package com.mondkars.mondkarsproduct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import model.Coupon;
import ui.CouponRecyclerAdapter;

public class CouponActivity extends AppCompatActivity {

    RecyclerView recyclerView, recyclerView2, recyclerView3;
    CouponRecyclerAdapter couponRecyclerAdapter;
    DatabaseReference reference, reference2;
    public List<Coupon> couponList;
    SearchView searchView;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
    ProgressBar progressBar;
    TextView textView;
    View v1, v2, v3, v4;
    CardView cardi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);

        cardi = findViewById(R.id.cardi);
        v1 = findViewById(R.id.vi1);
        v2 = findViewById(R.id.vi2);
        v3 = findViewById(R.id.vi3);
        v4 = findViewById(R.id.vi4);
        progressBar = findViewById(R.id.progress_bar);
        couponList = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference().child("Special users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference2 = FirebaseDatabase.getInstance().getReference().child("Coupons");
        searchView = findViewById(R.id.coupon_se);

        textView = findViewById(R.id.no_coupons);
        recyclerView3 = findViewById(R.id.cons_all);
        recyclerView2 = findViewById(R.id.coupen_all);
        recyclerView = findViewById(R.id.coupen_view);
        recyclerView.setHasFixedSize(true);
        recyclerView3.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        recyclerView3.setLayoutManager(new LinearLayoutManager(this));
        progressBar.setVisibility(View.VISIBLE);
        {
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    couponList = new ArrayList<Coupon>();
                    {
                        final Coupon coupon = dataSnapshot.getValue(Coupon.class);
                        if (dataSnapshot.child("code").exists()) {
                            couponList.add(coupon);
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    final Coupon coupon = dataSnapshot1.getValue(Coupon.class);
                    couponList.add(coupon);
                }
                {
                    if (couponList.toString() == "[]") {
                        progressBar.setVisibility(View.INVISIBLE);
                        textView.setVisibility(View.VISIBLE);
                        searchView.setVisibility(View.INVISIBLE);
                        v1.setVisibility(View.INVISIBLE);
                        v2.setVisibility(View.INVISIBLE);
                        v3.setVisibility(View.INVISIBLE);
                        v4.setVisibility(View.INVISIBLE);
                        cardi.setVisibility(View.INVISIBLE);
                    } else {
                        couponRecyclerAdapter = new CouponRecyclerAdapter(CouponActivity.this, couponList);
                        progressBar.setVisibility(View.INVISIBLE);
                        recyclerView2.setAdapter(couponRecyclerAdapter);
                        couponRecyclerAdapter.notifyDataSetChanged();
                    }
                }

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        couponRecyclerAdapter.getFilter().filter(newText);
                        recyclerView2.setAdapter(couponRecyclerAdapter);
                        couponRecyclerAdapter.notifyDataSetChanged();
                        return true;
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MyCart.class);
        startActivityForResult(myIntent, 0);
        finish();
        return true;
    }
    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(CouponActivity.this, MyCart.class));
    }
}
