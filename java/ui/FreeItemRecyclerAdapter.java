package ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mondkars.mondkarsproduct.MyCart;
import com.mondkars.mondkarsproduct.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.CartItem;
import model.Item;

public class FreeItemRecyclerAdapter extends RecyclerView.Adapter<FreeItemRecyclerAdapter.ViewHolder> {
    private Context context;
    private List<Item> itemList;

    public FreeItemRecyclerAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public FreeItemRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.free_order, viewGroup, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull FreeItemRecyclerAdapter.ViewHolder viewHolder, int position) {
        Item item = itemList.get(position);
        String imageUrl;

        viewHolder.ietem.setText(item.getItem());
        viewHolder.per.setText(item.getPer());
        if (item.getAvailable() != null) {
            if (item.getAvailable().equals("False")) {
                viewHolder.delivery.setTextColor(Color.parseColor("#FF0000"));
                viewHolder.delivery.setText("Not Available");
            } else {
                viewHolder.delivery.setText(item.getDelivery());
            }
        } else {
            viewHolder.delivery.setText("Delivers in: " + item.getDelivery());
        }

        imageUrl = item.getImageUrl();

        if (imageUrl != null) {
            Picasso.get()
                    .load(imageUrl)
                    .fit()
                    .into(viewHolder.image);
        } else {
            viewHolder.image.setImageResource(R.drawable.logo);
            viewHolder.image.setAdjustViewBounds(true);
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView
                ietem,
                delivery,
                per;
        DatabaseReference databaseReference;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserId, number = "1";
        public Button buy;
        public FirebaseAuth firebaseAuth;
        DatabaseReference data = FirebaseDatabase.getInstance().getReference().child("Stop");
        public ImageButton image;

        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;

            per = itemView.findViewById(R.id.row_per);
            firebaseAuth = FirebaseAuth.getInstance();
            ietem = itemView.findViewById(R.id.item_name_list);
            delivery = itemView.findViewById(R.id.delivers_in);
            image = itemView.findViewById(R.id.item_image_list);
            image.setOnClickListener(this);
            final FirebaseUser user = firebaseAuth.getCurrentUser();
            buy = itemView.findViewById(R.id.add_to_cart);
            buy.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == buy) {
                int position = getAdapterPosition();
                final Item item = itemList.get(position);
                if (user != null) {
                    data.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue().toString().equals("True") && !item.getCategory().equals("Masalyache Padartha")){
                                Toast.makeText(context, "Sorry! We are not serving now", Toast.LENGTH_LONG).show();
                            }
                            else {
                                if (item.getAvailable() != null) {
                                    if (item.getAvailable().equals("False")) {
                                        Toast.makeText(context, item.getItem() + " is not available right now !", Toast.LENGTH_LONG).show();
                                    } else if (number != null) {
                                        final String name = item.getItem();
                                        final String taste = item.getTaste();
                                        final String price = item.getPrice();
                                        final String deliviery = item.getDelivery();
                                        final String category = item.getCategory();
                                        final String imageUrl = item.getImageUrl();
                                        final String per = item.getPer();
                                        final String Number = number;
                                        final String userId = currentUserId;
                                        CartItem cartItem = new CartItem();
                                        cartItem.setItem(name);
                                        cartItem.setTaste(taste);
                                        cartItem.setPrice(price);
                                        cartItem.setPer(per);
                                        cartItem.setImageUrl(imageUrl);
                                        cartItem.setCategory(category);
                                        cartItem.setDelivery(deliviery);
                                        cartItem.setNumber(number);
                                        cartItem.setUserId(user.getPhoneNumber().toString());
                                        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
                                        cartItem.setTime(currentDateTimeString);
                                        databaseReference = FirebaseDatabase.getInstance().getReference().child("cart").child(user.getPhoneNumber().toString() + cartItem.getItem());
                                        databaseReference.setValue(cartItem);
                                        Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show();
                                        if(item.getPrice().equals("0")){
                                            ((Activity)context).finish();
                                        }
                                    } else {
                                        Toast.makeText(context, "Please select the number of items you want to buy", Toast.LENGTH_LONG).show();
                                    }
                                }
                                else {
                                    if (number != null) {
                                        final String name = item.getItem();
                                        final String taste = item.getTaste();
                                        final String price = item.getPrice();
                                        final String deliviery = item.getDelivery();
                                        final String category = item.getCategory();
                                        final String imageUrl = item.getImageUrl();
                                        final String per = item.getPer();
                                        final String Number = number;
                                        final String userId = currentUserId;
                                        CartItem cartItem = new CartItem();
                                        cartItem.setItem(name);
                                        cartItem.setTaste(taste);
                                        cartItem.setPrice(price);
                                        cartItem.setPer(per);
                                        cartItem.setImageUrl(imageUrl);
                                        cartItem.setCategory(category);
                                        cartItem.setDelivery(deliviery);
                                        cartItem.setNumber(number);
                                        cartItem.setUserId(user.getPhoneNumber().toString());
                                        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
                                        cartItem.setTime(currentDateTimeString);
                                        databaseReference = FirebaseDatabase.getInstance().getReference().child("cart").child(user.getPhoneNumber().toString() + cartItem.getItem());
                                        databaseReference.setValue(cartItem);
                                        Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show();
                                        if(item.getPrice().equals("0")){
                                            ((Activity)context).finish();
                                        }
                                    } else {
                                        Toast.makeText(context, "Please select the number of items you want to buy", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                else {
                    Toast.makeText(context, "You need to log in first !", Toast.LENGTH_LONG).show();
                    ((Activity)context).finish();
                }
            }
        }
    }
}