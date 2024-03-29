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

public class ThirdFragment extends Fragment {
    public List<Item> itemList;
    public ItemRecyclerAdapter itemRecyclerAdapter;
    private ProgressBar progressBar;
    DatabaseReference reference, reference2, reference3;
    public SliderView horizontal;
    public List<Image> snacksList;
    DatabaseReference data = FirebaseDatabase.getInstance().getReference().child("Stop");
    public RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.third_fragment, container, false);

        itemList = new ArrayList<>();

        progressBar = view.findViewById(R.id.progressBar3);
        progressBar.setVisibility(View.VISIBLE);

        recyclerView = view.findViewById(R.id.view);

        reference2 = FirebaseDatabase.getInstance().getReference().child("padartha");
        reference = FirebaseDatabase.getInstance().getReference().child("items");

        Query query1 = reference.orderByChild("category").equalTo("Masalyache Padartha");

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        horizontal = view.findViewById(R.id.horizontal_view3);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        reference3 = FirebaseDatabase.getInstance().getReference().child("Off Tag");
        LinearLayoutManager ilayoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

      {
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
                                        horizontal.setIndicatorAnimation(IndicatorAnimationType.SLIDE);
                                        horizontal.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Toast.makeText(getActivity(), "Something wen't wrong", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    }
                }

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
                                        if (ameya.getCategory().equals("Masalyache Padartha") && ameya.getVisibility()) {
                                            itemList.add(ameya);
                                        }
                                    }
                                }
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

                return view;
            }
        }
    }
}






