package com.mondkars.mondkarsproduct.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.Query;
import com.mondkars.mondkarsproduct.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import model.Image;
import model.Item;
import ui.ItemRecyclerAdapter;
import ui.SliderAdapterExample;

public class SecondFragment extends Fragment {
    public List<Item> itemList;
    public RecyclerView recyclerView, recyclerView2, recyclerView3, recyclerView4, recyclerView5, recyclerView6;
    public ItemRecyclerAdapter itemRecyclerAdapter;
    TextView text1, text2, text3, text4, text5, text6;
    View view1, view2, view3, view4, view5, view6;
    private ProgressBar progressBar;
    DatabaseReference reference, reference2, reference3;

    public SliderView horizontal;
    public List<Image> snacksList;
    DatabaseReference data = FirebaseDatabase.getInstance().getReference().child("Stop");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.second_fragment, container, false);

        itemList = new ArrayList<>();

        progressBar = view.findViewById(R.id.progressBar3);
        progressBar.setVisibility(View.VISIBLE);

        recyclerView = view.findViewById(R.id.view1);
        recyclerView2 = view.findViewById(R.id.view2);
        recyclerView3 = view.findViewById(R.id.view3);
        recyclerView4 = view.findViewById(R.id.view4);
        recyclerView5 = view.findViewById(R.id.view5);
        recyclerView6 = view.findViewById(R.id.view6);

        reference2 = FirebaseDatabase.getInstance().getReference().child("masale");
        reference = FirebaseDatabase.getInstance().getReference().child("items");

        Query query1 = reference.orderByChild("subcategory").equalTo("Fried Fishes");
        Query query2 = reference.orderByChild("subcategory").equalTo("Fish Curries");
        Query query3 = reference.orderByChild("subcategory").equalTo("Specials");
        Query query4 = reference.orderByChild("subcategory").equalTo("Rice");
        Query query5 = reference.orderByChild("subcategory").equalTo("Thali");
        Query query6 = reference.orderByChild("subcategory").equalTo("Combo");

        recyclerView.setHasFixedSize(true);
        recyclerView2.setHasFixedSize(true);
        recyclerView3.setHasFixedSize(true);
        recyclerView4.setHasFixedSize(true);
        recyclerView5.setHasFixedSize(true);
        recyclerView6.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView3.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView4.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView5.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView6.setLayoutManager(new LinearLayoutManager(getActivity()));

        text1 = view.findViewById(R.id.text1);
        text2 = view.findViewById(R.id.text2);
        text3 = view.findViewById(R.id.text3);
        text4 = view.findViewById(R.id.text4);
        text5 = view.findViewById(R.id.text5);
        text6 = view.findViewById(R.id.text6);

        view1 = view.findViewById(R.id.v1);
        view2 = view.findViewById(R.id.v2);
        view3 = view.findViewById(R.id.v3);
        view4 = view.findViewById(R.id.v4);
        view5 = view.findViewById(R.id.v5);
        view6 = view.findViewById(R.id.v6);

        horizontal = view.findViewById(R.id.horizontal_view2);

        reference3 = FirebaseDatabase.getInstance().getReference().child("Off Tag");
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue().toString().equals("True")) {
                    {
                        {
                            {
                                reference3.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        snacksList = new ArrayList<Image>();
                                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                            {
                                                final Image image = dataSnapshot1.getValue(Image.class);
                                                {
                                                    snacksList.add(image);
                                                }
                                            }
                                        }
                                        SliderAdapterExample sliderAdapterExample = new SliderAdapterExample(getActivity(), snacksList);
                                        horizontal.setSliderAdapter(sliderAdapterExample);
                                        sliderAdapterExample.notifyDataSetChanged();
                                        horizontal.startAutoCycle();
                                        horizontal.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
                                        horizontal.setIndicatorAnimation(IndicatorAnimationType.SLIDE);
                                        horizontal.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });
                            }
                        }
                    }
                } else {
                    {
                        {
                            {
                                reference2.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        snacksList = new ArrayList<Image>();
                                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                            {
                                                final Image image = dataSnapshot1.getValue(Image.class);
                                                {
                                                    snacksList.add(image);
                                                }
                                            }
                                        }
                                        SliderAdapterExample sliderAdapterExample = new SliderAdapterExample(getActivity(), snacksList);
                                        horizontal.setSliderAdapter(sliderAdapterExample);
                                        sliderAdapterExample.notifyDataSetChanged();
                                        horizontal.startAutoCycle();
                                        horizontal.setIndicatorAnimation(IndicatorAnimationType.WORM);
                                        horizontal.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Toast.makeText(getActivity(), "Something wrong happened", Toast.LENGTH_LONG).show();
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
        {
            {
                {
                    {
                        query1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                itemList = new ArrayList<Item>();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    {
                                        Item ameya = dataSnapshot1.getValue(Item.class);
                                        if (dataSnapshot1.child("category").exists()) {
                                            if (ameya.getCategory().equals("Masale") && ameya.getVisibility()) {
                                                itemList.add(ameya);
                                            }
                                        }
                                    }
                                }
                                if (itemList.toString() != "[]") {
                                    text1.setVisibility(View.VISIBLE);
                                    view1.setVisibility(View.VISIBLE);
                                    recyclerView.setVisibility(View.VISIBLE);
                                }
                                itemRecyclerAdapter = new ItemRecyclerAdapter(getActivity(), itemList);
                                progressBar.setVisibility(View.INVISIBLE);
                                recyclerView.setAdapter(itemRecyclerAdapter);
                                itemRecyclerAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                    {
                        query2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                itemList = new ArrayList<Item>();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    {
                                        Item ameya = dataSnapshot1.getValue(Item.class);

                                        if (dataSnapshot1.child("category").exists()) {
                                            if (ameya.getCategory().equals("Masale") && ameya.getVisibility()) {
                                                itemList.add(ameya);
                                            }
                                        }
                                    }
                                }
                                if (itemList.toString() != "[]") {
                                    text2.setVisibility(View.VISIBLE);
                                    view2.setVisibility(View.VISIBLE);
                                    recyclerView2.setVisibility(View.VISIBLE);
                                }
                                itemRecyclerAdapter = new ItemRecyclerAdapter(getActivity(), itemList);
                                progressBar.setVisibility(View.INVISIBLE);
                                recyclerView2.setAdapter(itemRecyclerAdapter);
                                itemRecyclerAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                    {
                        query3.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                itemList = new ArrayList<Item>();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    {
                                        Item ameya = dataSnapshot1.getValue(Item.class);

                                        if (dataSnapshot1.child("category").exists()) {
                                            if (ameya.getCategory().equals("Masale") && ameya.getVisibility()) {
                                                itemList.add(ameya);
                                            }
                                        }
                                    }
                                }
                                if (itemList.toString() != "[]") {
                                    text3.setVisibility(View.VISIBLE);
                                    view3.setVisibility(View.VISIBLE);
                                    recyclerView3.setVisibility(View.VISIBLE);
                                }
                                itemRecyclerAdapter = new ItemRecyclerAdapter(getActivity(), itemList);
                                progressBar.setVisibility(View.INVISIBLE);
                                recyclerView3.setAdapter(itemRecyclerAdapter);
                                itemRecyclerAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                    {
                        query4.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                itemList = new ArrayList<Item>();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    {
                                        Item ameya = dataSnapshot1.getValue(Item.class);
                                        if (dataSnapshot1.child("category").exists()) {
                                            if (ameya.getCategory().equals("Masale") && ameya.getVisibility()) {
                                                itemList.add(ameya);
                                            }
                                        }
                                    }
                                }
                                if (itemList.toString() != "[]") {
                                    text4.setVisibility(View.VISIBLE);
                                    view4.setVisibility(View.VISIBLE);
                                    recyclerView4.setVisibility(View.VISIBLE);
                                }
                                itemRecyclerAdapter = new ItemRecyclerAdapter(getActivity(), itemList);
                                progressBar.setVisibility(View.INVISIBLE);
                                recyclerView4.setAdapter(itemRecyclerAdapter);
                                itemRecyclerAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                    {
                        query5.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                itemList = new ArrayList<Item>();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    {
                                        Item ameya = dataSnapshot1.getValue(Item.class);
                                        if (dataSnapshot1.child("category").exists()) {
                                            if (ameya.getCategory().equals("Masale") && ameya.getVisibility()) {
                                                itemList.add(ameya);
                                            }
                                        }
                                    }
                                }
                                if (itemList.toString() != "[]") {
                                    text5.setVisibility(View.VISIBLE);
                                    view5.setVisibility(View.VISIBLE);
                                    recyclerView5.setVisibility(View.VISIBLE);
                                }
                                itemRecyclerAdapter = new ItemRecyclerAdapter(getActivity(), itemList);
                                progressBar.setVisibility(View.INVISIBLE);
                                recyclerView5.setAdapter(itemRecyclerAdapter);
                                itemRecyclerAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                    {
                        query6.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                itemList = new ArrayList<Item>();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    {
                                        Item ameya = dataSnapshot1.getValue(Item.class);
                                        if (dataSnapshot1.child("category").exists()) {
                                            if (ameya.getCategory().equals("Masale") && ameya.getVisibility()) {
                                                itemList.add(ameya);
                                            }
                                        }
                                    }
                                }
                                if (itemList.toString() != "[]") {
                                    text6.setVisibility(View.VISIBLE);
                                    view6.setVisibility(View.VISIBLE);
                                    recyclerView6.setVisibility(View.VISIBLE);
                                }
                                itemRecyclerAdapter = new ItemRecyclerAdapter(getActivity(), itemList);
                                progressBar.setVisibility(View.INVISIBLE);
                                recyclerView6.setAdapter(itemRecyclerAdapter);
                                itemRecyclerAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                }
            }
        }
        return view;
    }
}




