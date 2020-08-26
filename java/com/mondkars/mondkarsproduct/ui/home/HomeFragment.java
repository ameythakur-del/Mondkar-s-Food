package com.mondkars.mondkarsproduct.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        progressBar = view.findViewById(R.id.progressBar);
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
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue().toString().equals("True")) {
                    v1.setVisibility(View.INVISIBLE);
                    v2.setVisibility(View.INVISIBLE);
                    v3.setVisibility(View.INVISIBLE);
                    v4.setVisibility(View.INVISIBLE);
                    searchView.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    tabLayout.setVisibility(View.INVISIBLE);
                    viewPager.setVisibility(View.INVISIBLE);
                    noimage.setVisibility(View.VISIBLE);
                    notext.setVisibility(View.VISIBLE);
                    nowarning.setVisibility(View.VISIBLE);
                } else {
                    tabLayout.setVisibility(View.VISIBLE);
                    viewPager.setVisibility(View.VISIBLE);
                    noimage.setVisibility(View.INVISIBLE);
                    notext.setVisibility(View.INVISIBLE);
                    nowarning.setVisibility(View.INVISIBLE);
                    searchView.setVisibility(View.VISIBLE);
                    final ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Tab names");
                    progressBar.setVisibility(View.VISIBLE);
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String meal = dataSnapshot.child("Meal").getValue().toString();
                            String spices = dataSnapshot.child("Spices").getValue().toString();
                            String special = dataSnapshot.child("Special").getValue().toString();

                            adapter.AddFragment(new FirstFragment(), meal);
                            adapter.AddFragment(new SecondFragment(), spices);
                            adapter.AddFragment(new ThirdFragment(), special);

                            viewPager.setAdapter(adapter);
                            progressBar.setVisibility(View.INVISIBLE);
                            tabLayout.setupWithViewPager(viewPager);
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
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }
    }