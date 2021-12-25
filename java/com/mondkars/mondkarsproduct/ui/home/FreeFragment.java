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
import androidx.appcompat.app.AppCompatActivity;
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
import ui.FreeItemRecyclerAdapter;
import ui.ItemRecyclerAdapter;
import ui.SliderAdapterExample;

public class FreeFragment extends AppCompatActivity {
    public List<Item> itemList;
    public FreeItemRecyclerAdapter freeitemRecyclerAdapter;
    private ProgressBar progressBar;
    DatabaseReference reference, reference2, reference3;
    public SliderView horizontal;
    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_fragment);

        itemList = new ArrayList<>();

        progressBar = findViewById(R.id.progressBar3);
        progressBar.setVisibility(View.VISIBLE);

        recyclerView = findViewById(R.id.view);

        reference2 = FirebaseDatabase.getInstance().getReference().child("Free Images");
        reference = FirebaseDatabase.getInstance().getReference().child("Free items");

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(FreeFragment.this));
        horizontal = findViewById(R.id.horizontal_view3);

        horizontal.setVisibility(View.GONE);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(FreeFragment.this, LinearLayoutManager.HORIZONTAL, false);

        reference3 = FirebaseDatabase.getInstance().getReference().child("Off Tag");
        LinearLayoutManager ilayoutManager
                = new LinearLayoutManager(FreeFragment.this, LinearLayoutManager.HORIZONTAL, false);

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
                            freeitemRecyclerAdapter = new FreeItemRecyclerAdapter(FreeFragment.this, itemList);
                            progressBar.setVisibility(View.INVISIBLE);
                            recyclerView.setAdapter(freeitemRecyclerAdapter);
                            freeitemRecyclerAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }

                return ;
            }
        }
    }
}






