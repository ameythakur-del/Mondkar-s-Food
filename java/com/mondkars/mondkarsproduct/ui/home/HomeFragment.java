package com.mondkars.mondkarsproduct.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.mondkars.mondkarsproduct.MyCart;
import com.mondkars.mondkarsproduct.R;
import com.mondkars.mondkarsproduct.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import model.Item;
import ui.ItemRecyclerAdapter;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SearchView searchView;
    public List<Item> itemList;
    public RecyclerView recyclerView;
    public ItemRecyclerAdapter itemRecyclerAdapter;
    private ProgressBar progressBar;
    private TextView notext, nowarning;
    private ImageView noimage;
    DatabaseReference reference, databaseReference;
    DatabaseReference data = FirebaseDatabase.getInstance().getReference().child("Stop");
    View v1, v2, v3, v4;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth;
    private CollectionReference collectionReference = db.collection("Registering users");
    String city;
    float a, b, c;
    CardView cardView;
    TextView number, price;
    MaterialCardView linearLayout;
    DatabaseReference cartReference = FirebaseDatabase.getInstance().getReference().child("cart");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        progressBar = view.findViewById(R.id.progressBar);

        cardView = (CardView) view.findViewById(R.id.cart_card);
        price = view.findViewById(R.id.total_price);
        linearLayout = view.findViewById(R.id.view_cart);
        number = view.findViewById(R.id.number_items);

        searchView = (SearchView) view.findViewById(R.id.search_bar25);
        notext = view.findViewById(R.id.notext);
        nowarning = view.findViewById(R.id.nowarning);
        noimage = view.findViewById(R.id.noimage);
        tabLayout = (TabLayout) view.findViewById(R.id.tablayout_id);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager_id);
        v1 = view.findViewById(R.id.vi1);
        v2 = view.findViewById(R.id.vi2);
        v3 = view.findViewById(R.id.vi3);
        v4 = view.findViewById(R.id.vi4);
        v1.setVisibility(View.VISIBLE);
        v2.setVisibility(View.VISIBLE);
        v3.setVisibility(View.VISIBLE);
        v4.setVisibility(View.VISIBLE);
        {
               {
                    v1.setVisibility(View.VISIBLE);
                    v2.setVisibility(View.VISIBLE);
                    v3.setVisibility(View.VISIBLE);
                    v4.setVisibility(View.VISIBLE);
                    tabLayout.setVisibility(View.VISIBLE);
                    viewPager.setVisibility(View.VISIBLE);
                    noimage.setVisibility(View.INVISIBLE);
                    notext.setVisibility(View.INVISIBLE);
                    nowarning.setVisibility(View.INVISIBLE);
                    searchView.setVisibility(View.VISIBLE);
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Tab names");
                    progressBar.setVisibility(View.VISIBLE);
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String meal = dataSnapshot.child("Meal").getValue().toString();
                            String spices = dataSnapshot.child("Spices").getValue().toString();
                            String special = dataSnapshot.child("Special").getValue().toString();

                            if(FirebaseAuth.getInstance().getCurrentUser() != null){
                                collectionReference.document(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                        if(value.getString("City") != null) {
                                            if (value.getString("City").equals("Sawantwadi")) {
                                                ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
                                                adapter.AddFragment(new FirstFragment(), meal);
                                                adapter.AddFragment(new SecondFragment(), spices);
                                                adapter.AddFragment(new ThirdFragment(), special);

                                                Log.d("HomeFragment", "onEvent: 1");

                                                viewPager.setAdapter(adapter);
                                                progressBar.setVisibility(View.INVISIBLE);
                                                tabLayout.setupWithViewPager(viewPager);
                                            }
                                            else{
                                                ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
                                                adapter.AddFragment(new ThirdFragment(), special);
                                                Log.d("HomeFragment", "onEvent: 4");

                                                viewPager.setAdapter(adapter);
                                                progressBar.setVisibility(View.INVISIBLE);
                                                tabLayout.setupWithViewPager(viewPager);
                                            }
                                        }
                                        else{
                                            ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
                                            adapter.AddFragment(new FirstFragment(), meal);
                                            adapter.AddFragment(new SecondFragment(), spices);
                                            adapter.AddFragment(new ThirdFragment(), special);
                                            Log.d("HomeFragment", "onEvent: 2");

                                            viewPager.setAdapter(adapter);
                                            progressBar.setVisibility(View.INVISIBLE);
                                            tabLayout.setupWithViewPager(viewPager);
                                        }
                                    }
                                });
                            }
                            else {
                                ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
                                adapter.AddFragment(new FirstFragment(), meal);
                                adapter.AddFragment(new SecondFragment(), spices);
                                adapter.AddFragment(new ThirdFragment(), special);
                                Log.d("HomeFragment", "onDataChange: 3");

                                viewPager.setAdapter(adapter);
                                progressBar.setVisibility(View.INVISIBLE);
                                tabLayout.setupWithViewPager(viewPager);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    itemList = new ArrayList<>();

                    recyclerView = view.findViewById(R.id.view_2);

                    reference = FirebaseDatabase.getInstance().getReference().child("items");

                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                    {
                        {
                            {
                                reference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        itemList = new ArrayList<Item>();
                                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                            {
                                                Item ameya = dataSnapshot1.getValue(Item.class);

                                                itemList.add(ameya);
                                            }
                                        }
                                        itemRecyclerAdapter = new ItemRecyclerAdapter(getActivity(), itemList);

                                        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                            @Override
                                            public boolean onQueryTextSubmit(String query) {
                                                return false;
                                            }
                                            @Override
                                            public boolean onQueryTextChange(String newText) {
                                                itemRecyclerAdapter.getFilter().filter(newText);
                                                recyclerView.setAdapter(itemRecyclerAdapter);
                                                itemRecyclerAdapter.notifyDataSetChanged();
                                                return true;
                                            }
                                        });
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        }
                    }
                }
            }

        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            Query query = cartReference.orderByChild("userPhone");
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.d(TAG, "onDataChange: 90");
                    int count = 0;
                    c=0;
                    for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                        if(dataSnapshot1.exists()){
                            if(dataSnapshot1.child("userId").getValue().toString().equals(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().toString())){
                                cardView.setVisibility(View.VISIBLE);
                                count++;
                                a = Float.parseFloat(dataSnapshot1.child("number").getValue().toString());
                                b = Float.parseFloat(dataSnapshot1.child("price").getValue().toString());
                                c = c + a*b;
                            }
                        }
                    }
                    if(String.valueOf(count).equals("0")){
                        cardView.setVisibility(View.INVISIBLE);
                    }
                    else if(String.valueOf(count).equals("1")){
                        String a = String.valueOf(count);
                        number.setText(a + " item");
                    }
                    else {
                        String a = String.valueOf(count);
                        number.setText(a + " items");
                    }
                    String k = String.valueOf(Math.round(c));
                    price.setText("\u20B9"+k);
                    Log.d(TAG, "onDataChange: 100");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), MyCart.class));
                }
            });
        }
        return view;
    }
    }