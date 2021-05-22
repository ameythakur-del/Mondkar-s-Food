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

import com.mondkars.mondkarsproduct.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
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

public class FirstFragment extends Fragment {
    public List<Item> itemList;
    public RecyclerView recyclerView, recyclerView2, recyclerView3, recyclerView4, recyclerView5, recyclerView6, recyclerView7, recyclerView8,recyclerView9,recyclerView10, recyclerView11, recyclerView12, recyclerView13, recyclerView14,  recyclerView15,  recyclerView16,  recyclerView17,  recyclerView18,  recyclerView19,  recyclerView20,  recyclerView21,  recyclerView22, recyclerView23, recyclerView24;
    public ItemRecyclerAdapter itemRecyclerAdapter;
    public SliderView horizontal;
    public List<Image> snacksList;
    private ProgressBar progressBar;
    DatabaseReference reference, reference2, reference3;
    TextView text1, text2, text3, text4, text5, text6, text7,text8, text9, text10, text11, text12, text13, text14, text15, text16, text17, text18, text19, text20, text21, text22, text23, text24;
    View view1, view2, view3, view4, view5, view6, view7, view8, view9, view10, view11, view12, view13, view14, view15, view16, view17, view18, view19, view20, view21, view22, view23, view24;
    DatabaseReference data = FirebaseDatabase.getInstance().getReference().child("Stop");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.first_fragment, container, false);
            itemList = new ArrayList<>();
            snacksList = new ArrayList<>();

            progressBar = view.findViewById(R.id.progressBar3);
            progressBar.setVisibility(View.VISIBLE);

            horizontal = view.findViewById(R.id.horizontal_view);
            recyclerView = view.findViewById(R.id.view1);
            recyclerView2 = view.findViewById(R.id.view2);
            recyclerView3 = view.findViewById(R.id.view3);
            recyclerView4 = view.findViewById(R.id.view4);
            recyclerView5 = view.findViewById(R.id.view5);
            recyclerView6 = view.findViewById(R.id.view6);
            recyclerView7 = view.findViewById(R.id.view7);
            recyclerView8 = view.findViewById(R.id.view8);
            recyclerView9 = view.findViewById(R.id.view9);
            recyclerView10 = view.findViewById(R.id.view10);
            recyclerView11 = view.findViewById(R.id.view11);
            recyclerView12 = view.findViewById(R.id.view12);
            recyclerView13 = view.findViewById(R.id.view13);
        recyclerView14 = view.findViewById(R.id.view14);
        recyclerView15 = view.findViewById(R.id.view15);
        recyclerView16 = view.findViewById(R.id.view16);
        recyclerView17 = view.findViewById(R.id.view17);
        recyclerView18 = view.findViewById(R.id.view18);
        recyclerView19 = view.findViewById(R.id.view19);
        recyclerView20 = view.findViewById(R.id.view20);
        recyclerView21 = view.findViewById(R.id.view21);
        recyclerView22 = view.findViewById(R.id.view22);
        recyclerView23 = view.findViewById(R.id.view23);
        recyclerView24 = view.findViewById(R.id.view24);

            reference2 = FirebaseDatabase.getInstance().getReference().child("Snacks");
            reference = FirebaseDatabase.getInstance().getReference().child("items");
            Query query1 = reference.orderByChild("subcategory").equalTo("Breakfast");
            Query query2 = reference.orderByChild("subcategory").equalTo("Chapati Bhaji");
            Query query3 = reference.orderByChild("subcategory").equalTo("Starter");
            Query query4 = reference.orderByChild("subcategory").equalTo("Veg");
            Query query5 = reference.orderByChild("subcategory").equalTo("Non Veg");
            Query query6 = reference.orderByChild("subcategory").equalTo("Momos");
            Query query7 = reference.orderByChild("subcategory").equalTo("Fries");
            Query query8 = reference.orderByChild("subcategory").equalTo("Frankie");
            Query query9 = reference.orderByChild("subcategory").equalTo("Rolls");
            Query query10 = reference.orderByChild("subcategory").equalTo("Sandwich");
            Query query11 = reference.orderByChild("subcategory").equalTo("Traditional");
            Query query12 = reference.orderByChild("subcategory").equalTo("Other");
            Query query13 = reference.orderByChild("subcategory").equalTo("Rice");
        Query query14 = reference.orderByChild("subcategory").equalTo("Fish");
        Query query15 = reference.orderByChild("subcategory").equalTo("Noodles");
        Query query16 = reference.orderByChild("subcategory").equalTo("Pizza");
        Query query17 = reference.orderByChild("subcategory").equalTo("Burger");
        Query query18 = reference.orderByChild("subcategory").equalTo("Snacks");
        Query query19 = reference.orderByChild("subcategory").equalTo("Sizzlers");
        Query query20 = reference.orderByChild("subcategory").equalTo("Pasta");
        Query query21 = reference.orderByChild("subcategory").equalTo("Shakes");
        Query query22 = reference.orderByChild("subcategory").equalTo("Mojito");
        Query query23 = reference.orderByChild("subcategory").equalTo("One");
        Query query24 = reference.orderByChild("subcategory").equalTo("Smoothie");

            recyclerView.setHasFixedSize(true);
            recyclerView2.setHasFixedSize(true);
            recyclerView3.setHasFixedSize(true);
            recyclerView4.setHasFixedSize(true);
            recyclerView5.setHasFixedSize(true);
            recyclerView6.setHasFixedSize(true);
            recyclerView7.setHasFixedSize(true);
            recyclerView8.setHasFixedSize(true);
            recyclerView9.setHasFixedSize(true);
            recyclerView10.setHasFixedSize(true);
            recyclerView11.setHasFixedSize(true);
            recyclerView12.setHasFixedSize(true);
            recyclerView13.setHasFixedSize(true);
        recyclerView14.setHasFixedSize(true);
        recyclerView15.setHasFixedSize(true);
        recyclerView16.setHasFixedSize(true);
        recyclerView17.setHasFixedSize(true);
        recyclerView18.setHasFixedSize(true);
        recyclerView19.setHasFixedSize(true);
        recyclerView20.setHasFixedSize(true);
        recyclerView21.setHasFixedSize(true);
        recyclerView22.setHasFixedSize(true);
        recyclerView23.setHasFixedSize(true);
        recyclerView24.setHasFixedSize(true);

            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView3.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView4.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView5.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView6.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView7.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView8.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView9.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView10.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView11.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView12.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView13.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView14.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView15.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView16.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView17.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView18.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView19.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView20.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView21.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView22.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView23.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView24.setLayoutManager(new LinearLayoutManager(getActivity()));

            text1 = view.findViewById(R.id.text1);
            text2 = view.findViewById(R.id.text2);
        text3 = view.findViewById(R.id.text3);
        text4 = view.findViewById(R.id.text4);
        text5 = view.findViewById(R.id.text5);
        text6 = view.findViewById(R.id.text6);
        text7 = view.findViewById(R.id.text7);
        text8 = view.findViewById(R.id.text8);
        text9 = view.findViewById(R.id.text9);
        text10 = view.findViewById(R.id.text10);
        text11 = view.findViewById(R.id.text11);
        text12 = view.findViewById(R.id.text12);
        text13 = view.findViewById(R.id.text13);
        text14 = view.findViewById(R.id.text14);
        text15 = view.findViewById(R.id.text15);
        text16 = view.findViewById(R.id.text16);
        text17 = view.findViewById(R.id.text17);
        text18 = view.findViewById(R.id.text18);
        text19 = view.findViewById(R.id.text19);
        text20 = view.findViewById(R.id.text20);
        text21 = view.findViewById(R.id.text21);
        text22 = view.findViewById(R.id.text22);
        text23 = view.findViewById(R.id.text23);
        text24 = view.findViewById(R.id.text24);

        view1 = view.findViewById(R.id.v1);
        view2 = view.findViewById(R.id.v2);
        view3 = view.findViewById(R.id.v3);
        view4 = view.findViewById(R.id.v4);
        view5 = view.findViewById(R.id.v5);
        view6 = view.findViewById(R.id.v6);
        view7 = view.findViewById(R.id.v7);
        view8 = view.findViewById(R.id.v8);
        view9 = view.findViewById(R.id.v9);
        view10 = view.findViewById(R.id.v10);
        view11 = view.findViewById(R.id.v11);
        view12 = view.findViewById(R.id.v12);
        view13 = view.findViewById(R.id.v13);
        view14 = view.findViewById(R.id.v14);
        view15 = view.findViewById(R.id.v15);
        view16 = view.findViewById(R.id.v16);
        view17 = view.findViewById(R.id.v17);
        view18 = view.findViewById(R.id.v18);
        view19 = view.findViewById(R.id.v19);
        view20 = view.findViewById(R.id.v20);
        view21 = view.findViewById(R.id.v21);
        view22 = view.findViewById(R.id.v22);
        view23 = view.findViewById(R.id.v23);
        view24 = view.findViewById(R.id.v24);

        reference3 = FirebaseDatabase.getInstance().getReference().child("Off Tag");
            final LinearLayoutManager layoutManager
                    = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

            data.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue().toString().equals("True")){
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
                    }

                    else{
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
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
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
                                        if(dataSnapshot1.child("category").exists())  {
                                            if (ameya.getCategory().equals("Snacks") && ameya.getVisibility()) {
                                                itemList.add(ameya);
                                            }
                                        }
                                    }
                                }
                                if (itemList.toString() != "[]"){
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

                                        if(dataSnapshot1.child("category").exists())  {
                                            if (ameya.getCategory().equals("Snacks") && ameya.getVisibility()) {
                                                itemList.add(ameya);
                                            }
                                        }
                                    }
                                }
                                if (itemList.toString() != "[]"){
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

                                        if(dataSnapshot1.child("category").exists())  {
                                            if (ameya.getCategory().equals("Snacks") && ameya.getVisibility()) {
                                                itemList.add(ameya);
                                            }
                                        }
                                    }
                                }
                                if (itemList.toString() != "[]"){
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
                                        if(dataSnapshot1.child("category").exists())  {
                                            if (ameya.getCategory().equals("Snacks") && ameya.getVisibility()) {
                                                itemList.add(ameya);
                                            }
                                        }
                                    }
                                }
                                if (itemList.toString() != "[]"){
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
                                        if(dataSnapshot1.child("category").exists())  {
                                            if (ameya.getCategory().equals("Snacks") && ameya.getVisibility()) {
                                                itemList.add(ameya);
                                            }
                                        }
                                    }
                                }
                                if (itemList.toString() != "[]"){
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
                                        if(dataSnapshot1.child("category").exists()) {
                                            if (ameya.getCategory().equals("Snacks") && ameya.getVisibility()) {
                                                itemList.add(ameya);
                                            }
                                        }
                                    }
                                }
                                if (itemList.toString() != "[]"){
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
                    {
                        query7.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                itemList = new ArrayList<Item>();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    {
                                        Item ameya = dataSnapshot1.getValue(Item.class);
                                        if(dataSnapshot1.child("category").exists())  {
                                            if (ameya.getCategory().equals("Snacks") && ameya.getVisibility()) {
                                                itemList.add(ameya);
                                            }
                                        }
                                    }
                                }
                                if (itemList.toString() != "[]"){
                                    text7.setVisibility(View.VISIBLE);
                                    view7.setVisibility(View.VISIBLE);
                                    recyclerView7.setVisibility(View.VISIBLE);
                                }
                                itemRecyclerAdapter = new ItemRecyclerAdapter(getActivity(), itemList);
                                progressBar.setVisibility(View.INVISIBLE);
                                recyclerView7.setAdapter(itemRecyclerAdapter);
                                itemRecyclerAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                    {
                        query8.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                itemList = new ArrayList<Item>();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    {
                                        Item ameya = dataSnapshot1.getValue(Item.class);
                                        if(dataSnapshot1.child("category").exists())  {
                                            if (ameya.getCategory().equals("Snacks") && ameya.getVisibility()) {
                                                itemList.add(ameya);
                                            }
                                        }
                                    }
                                }
                                if (itemList.toString() != "[]"){
                                    text8.setVisibility(View.VISIBLE);
                                    view8.setVisibility(View.VISIBLE);
                                    recyclerView8.setVisibility(View.VISIBLE);
                                }
                                itemRecyclerAdapter = new ItemRecyclerAdapter(getActivity(), itemList);
                                progressBar.setVisibility(View.INVISIBLE);
                                recyclerView8.setAdapter(itemRecyclerAdapter);
                                itemRecyclerAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                    {
                        query9.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                itemList = new ArrayList<Item>();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    {
                                        Item ameya = dataSnapshot1.getValue(Item.class);
                                        if(dataSnapshot1.child("category").exists() && ameya.getVisibility())  {
                                            if (ameya.getCategory().equals("Snacks")) {
                                                itemList.add(ameya);
                                            }
                                        }
                                    }
                                }
                                if (itemList.toString() != "[]"){
                                    text9.setVisibility(View.VISIBLE);
                                    view9.setVisibility(View.VISIBLE);
                                    recyclerView9.setVisibility(View.VISIBLE);
                                }
                                itemRecyclerAdapter = new ItemRecyclerAdapter(getActivity(), itemList);
                                progressBar.setVisibility(View.INVISIBLE);
                                recyclerView9.setAdapter(itemRecyclerAdapter);
                                itemRecyclerAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                    {
                        query10.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                itemList = new ArrayList<Item>();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    {
                                        Item ameya = dataSnapshot1.getValue(Item.class);
                                        if(dataSnapshot1.child("category").exists())  {
                                            if (ameya.getCategory().equals("Snacks") && ameya.getVisibility()) {
                                                itemList.add(ameya);
                                            }
                                        }
                                    }
                                }
                                if (itemList.toString() != "[]"){
                                    text10.setVisibility(View.VISIBLE);
                                    view10.setVisibility(View.VISIBLE);
                                    recyclerView10.setVisibility(View.VISIBLE);
                                }
                                itemRecyclerAdapter = new ItemRecyclerAdapter(getActivity(), itemList);
                                progressBar.setVisibility(View.INVISIBLE);
                                recyclerView10.setAdapter(itemRecyclerAdapter);
                                itemRecyclerAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                    {
                        query11.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                itemList = new ArrayList<Item>();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    {
                                        Item ameya = dataSnapshot1.getValue(Item.class);
                                        if(dataSnapshot1.child("category").exists())  {
                                            if (ameya.getCategory().equals("Snacks") && ameya.getVisibility()) {
                                                itemList.add(ameya);
                                            }
                                        }
                                    }
                                }
                                if (itemList.toString() != "[]"){
                                    text11.setVisibility(View.VISIBLE);
                                    view11.setVisibility(View.VISIBLE);
                                    recyclerView11.setVisibility(View.VISIBLE);
                                }
                                itemRecyclerAdapter = new ItemRecyclerAdapter(getActivity(), itemList);
                                progressBar.setVisibility(View.INVISIBLE);
                                recyclerView11.setAdapter(itemRecyclerAdapter);
                                itemRecyclerAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                    {
                        query12.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                itemList = new ArrayList<Item>();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    {
                                        Item ameya = dataSnapshot1.getValue(Item.class);
                                        if(dataSnapshot1.child("category").exists()) {
                                            if (ameya.getCategory().equals("Snacks") && ameya.getVisibility()) {
                                                itemList.add(ameya);
                                            }
                                        }
                                    }
                                }
                                if (itemList.toString() != "[]"){
                                    text12.setVisibility(View.VISIBLE);
                                    view12.setVisibility(View.VISIBLE);
                                    recyclerView12.setVisibility(View.VISIBLE);
                                }
                                itemRecyclerAdapter = new ItemRecyclerAdapter(getActivity(), itemList);
                                progressBar.setVisibility(View.INVISIBLE);
                                recyclerView12.setAdapter(itemRecyclerAdapter);
                                itemRecyclerAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                    {
                        query13.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                itemList = new ArrayList<Item>();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    {
                                        Item ameya = dataSnapshot1.getValue(Item.class);
                                        if(dataSnapshot1.child("category").exists()) {
                                            if (ameya.getCategory().equals("Snacks") && ameya.getVisibility()) {
                                                itemList.add(ameya);
                                            }
                                        }
                                    }
                                }
                                if (itemList.toString() != "[]"){
                                    text13.setVisibility(View.VISIBLE);
                                    view13.setVisibility(View.VISIBLE);
                                    recyclerView13.setVisibility(View.VISIBLE);
                                }
                                itemRecyclerAdapter = new ItemRecyclerAdapter(getActivity(), itemList);
                                progressBar.setVisibility(View.INVISIBLE);
                                recyclerView13.setAdapter(itemRecyclerAdapter);
                                itemRecyclerAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                    {
                        query14.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                itemList = new ArrayList<Item>();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    {
                                        Item ameya = dataSnapshot1.getValue(Item.class);

                                        if(dataSnapshot1.child("category").exists())  {
                                            if (ameya.getCategory().equals("Snacks") && ameya.getVisibility()) {
                                                itemList.add(ameya);
                                            }
                                        }
                                    }
                                }
                                if (itemList.toString() != "[]"){
                                    text14.setVisibility(View.VISIBLE);
                                    view14.setVisibility(View.VISIBLE);
                                    recyclerView14.setVisibility(View.VISIBLE);
                                }
                                itemRecyclerAdapter = new ItemRecyclerAdapter(getActivity(), itemList);
                                progressBar.setVisibility(View.INVISIBLE);
                                recyclerView14.setAdapter(itemRecyclerAdapter);
                                itemRecyclerAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                    {
                        query15.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                itemList = new ArrayList<Item>();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    {
                                        Item ameya = dataSnapshot1.getValue(Item.class);

                                        if(dataSnapshot1.child("category").exists())  {
                                            if (ameya.getCategory().equals("Snacks") && ameya.getVisibility()) {
                                                itemList.add(ameya);
                                            }
                                        }
                                    }
                                }
                                if (itemList.toString() != "[]"){
                                    text15.setVisibility(View.VISIBLE);
                                    view15.setVisibility(View.VISIBLE);
                                    recyclerView15.setVisibility(View.VISIBLE);
                                }
                                itemRecyclerAdapter = new ItemRecyclerAdapter(getActivity(), itemList);
                                progressBar.setVisibility(View.INVISIBLE);
                                recyclerView15.setAdapter(itemRecyclerAdapter);
                                itemRecyclerAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                    {
                        query16.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                itemList = new ArrayList<Item>();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    {
                                        Item ameya = dataSnapshot1.getValue(Item.class);

                                        if(dataSnapshot1.child("category").exists())  {
                                            if (ameya.getCategory().equals("Snacks") && ameya.getVisibility()) {
                                                itemList.add(ameya);
                                            }
                                        }
                                    }
                                }
                                if (itemList.toString() != "[]"){
                                    text16.setVisibility(View.VISIBLE);
                                    view16.setVisibility(View.VISIBLE);
                                    recyclerView16.setVisibility(View.VISIBLE);
                                }
                                itemRecyclerAdapter = new ItemRecyclerAdapter(getActivity(), itemList);
                                progressBar.setVisibility(View.INVISIBLE);
                                recyclerView16.setAdapter(itemRecyclerAdapter);
                                itemRecyclerAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                    {
                        query18.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                itemList = new ArrayList<Item>();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    {
                                        Item ameya = dataSnapshot1.getValue(Item.class);

                                        if(dataSnapshot1.child("category").exists())  {
                                            if (ameya.getCategory().equals("Snacks") && ameya.getVisibility()) {
                                                itemList.add(ameya);
                                            }
                                        }
                                    }
                                }
                                if (itemList.toString() != "[]"){
                                    text18.setVisibility(View.VISIBLE);
                                    view18.setVisibility(View.VISIBLE);
                                    recyclerView18.setVisibility(View.VISIBLE);
                                }
                                itemRecyclerAdapter = new ItemRecyclerAdapter(getActivity(), itemList);
                                progressBar.setVisibility(View.INVISIBLE);
                                recyclerView18.setAdapter(itemRecyclerAdapter);
                                itemRecyclerAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                    {
                        query19.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                itemList = new ArrayList<Item>();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    {
                                        Item ameya = dataSnapshot1.getValue(Item.class);

                                        if(dataSnapshot1.child("category").exists())  {
                                            if (ameya.getCategory().equals("Snacks") && ameya.getVisibility()) {
                                                itemList.add(ameya);
                                            }
                                        }
                                    }
                                }
                                if (itemList.toString() != "[]"){
                                    text19.setVisibility(View.VISIBLE);
                                    view19.setVisibility(View.VISIBLE);
                                    recyclerView19.setVisibility(View.VISIBLE);
                                }
                                itemRecyclerAdapter = new ItemRecyclerAdapter(getActivity(), itemList);
                                progressBar.setVisibility(View.INVISIBLE);
                                recyclerView19.setAdapter(itemRecyclerAdapter);
                                itemRecyclerAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                    {
                        query20.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                itemList = new ArrayList<Item>();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    {
                                        Item ameya = dataSnapshot1.getValue(Item.class);

                                        if(dataSnapshot1.child("category").exists())  {
                                            if (ameya.getCategory().equals("Snacks") && ameya.getVisibility()) {
                                                itemList.add(ameya);
                                            }
                                        }
                                    }
                                }
                                if (itemList.toString() != "[]"){
                                    text20.setVisibility(View.VISIBLE);
                                    view20.setVisibility(View.VISIBLE);
                                    recyclerView20.setVisibility(View.VISIBLE);
                                }
                                itemRecyclerAdapter = new ItemRecyclerAdapter(getActivity(), itemList);
                                progressBar.setVisibility(View.INVISIBLE);
                                recyclerView20.setAdapter(itemRecyclerAdapter);
                                itemRecyclerAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                    {
                        query21.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                itemList = new ArrayList<Item>();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    {
                                        Item ameya = dataSnapshot1.getValue(Item.class);

                                        if(dataSnapshot1.child("category").exists())  {
                                            if (ameya.getCategory().equals("Snacks") && ameya.getVisibility()) {
                                                itemList.add(ameya);
                                            }
                                        }
                                    }
                                }
                                if (itemList.toString() != "[]"){
                                    text21.setVisibility(View.VISIBLE);
                                    view21.setVisibility(View.VISIBLE);
                                    recyclerView21.setVisibility(View.VISIBLE);
                                }
                                itemRecyclerAdapter = new ItemRecyclerAdapter(getActivity(), itemList);
                                progressBar.setVisibility(View.INVISIBLE);
                                recyclerView21.setAdapter(itemRecyclerAdapter);
                                itemRecyclerAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                    {
                        query22.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                itemList = new ArrayList<Item>();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    {
                                        Item ameya = dataSnapshot1.getValue(Item.class);

                                        if(dataSnapshot1.child("category").exists())  {
                                            if (ameya.getCategory().equals("Snacks") && ameya.getVisibility()) {
                                                itemList.add(ameya);
                                            }
                                        }
                                    }
                                }
                                if (itemList.toString() != "[]"){
                                    text22.setVisibility(View.VISIBLE);
                                    view22.setVisibility(View.VISIBLE);
                                    recyclerView22.setVisibility(View.VISIBLE);
                                }
                                itemRecyclerAdapter = new ItemRecyclerAdapter(getActivity(), itemList);
                                progressBar.setVisibility(View.INVISIBLE);
                                recyclerView22.setAdapter(itemRecyclerAdapter);
                                itemRecyclerAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                    {
                        query23.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                itemList = new ArrayList<Item>();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    {
                                        Item ameya = dataSnapshot1.getValue(Item.class);

                                        if(dataSnapshot1.child("category").exists())  {
                                            if (ameya.getCategory().equals("Snacks") && ameya.getVisibility()) {
                                                itemList.add(ameya);
                                            }
                                        }
                                    }
                                }
                                if (itemList.toString() != "[]"){
                                    text23.setVisibility(View.VISIBLE);
                                    view23.setVisibility(View.VISIBLE);
                                    recyclerView23.setVisibility(View.VISIBLE);
                                }
                                itemRecyclerAdapter = new ItemRecyclerAdapter(getActivity(), itemList);
                                progressBar.setVisibility(View.INVISIBLE);
                                recyclerView23.setAdapter(itemRecyclerAdapter);
                                itemRecyclerAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                    {
                        query24.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                itemList = new ArrayList<Item>();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    {
                                        Item ameya = dataSnapshot1.getValue(Item.class);

                                        if(dataSnapshot1.child("category").exists())  {
                                            if (ameya.getCategory().equals("Snacks") && ameya.getVisibility()) {
                                                itemList.add(ameya);
                                            }
                                        }
                                    }
                                }
                                if (itemList.toString() != "[]"){
                                    text24.setVisibility(View.VISIBLE);
                                    view24.setVisibility(View.VISIBLE);
                                    recyclerView24.setVisibility(View.VISIBLE);
                                }
                                itemRecyclerAdapter = new ItemRecyclerAdapter(getActivity(), itemList);
                                progressBar.setVisibility(View.INVISIBLE);
                                recyclerView24.setAdapter(itemRecyclerAdapter);
                                itemRecyclerAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                    {
                        query17.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                itemList = new ArrayList<Item>();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    {
                                        Item ameya = dataSnapshot1.getValue(Item.class);

                                        if(dataSnapshot1.child("category").exists())  {
                                            if (ameya.getCategory().equals("Snacks") && ameya.getVisibility()) {
                                                itemList.add(ameya);
                                            }
                                        }
                                    }
                                }
                                if (itemList.toString() != "[]"){
                                    text17.setVisibility(View.VISIBLE);
                                    view17.setVisibility(View.VISIBLE);
                                    recyclerView17.setVisibility(View.VISIBLE);
                                }
                                itemRecyclerAdapter = new ItemRecyclerAdapter(getActivity(), itemList);
                                progressBar.setVisibility(View.INVISIBLE);
                                recyclerView17.setAdapter(itemRecyclerAdapter);
                                itemRecyclerAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                }
            }
            return view;
        }
}