package com.mondkars.mondkarsproduct;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mondkars.mondkarsproduct.R;
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

import model.Wish;
import ui.WishRecyclerAdapter;

public class wishlist extends AppCompatActivity {

    private List<Wish> wishList;
    public RecyclerView recyclerView;
    public WishRecyclerAdapter wishRecyclerAdapter;
    private ProgressBar progressBar;
    private DatabaseReference databaseReference;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_wishlist);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        recyclerView = findViewById(R.id.wish_view);
        progressBar = findViewById(R.id.wish_progress);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("wish");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            String currentUserId = user.getPhoneNumber();
            {
                {
                    {
                        Query query = databaseReference.orderByChild("userId").equalTo(currentUserId);
                        query.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                wishList = new ArrayList<Wish>();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    {
                                        Wish ameya = dataSnapshot1.getValue(Wish.class);
                                        {
                                            wishList.add(ameya);
                                        }
                                    }
                                }
                                wishRecyclerAdapter = new WishRecyclerAdapter(wishlist.this, wishList);
                                progressBar.setVisibility(View.INVISIBLE);
                                recyclerView.setAdapter(wishRecyclerAdapter);
                                wishRecyclerAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                }
            }
        }
        else{
            startActivity(new Intent(this, Activity2.class));
            finish();
            Toast.makeText(this, "You need to login first to see your favourites", Toast.LENGTH_LONG).show();
        }
    }
}
