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

public class ItemRecyclerAdapter extends RecyclerView.Adapter<ItemRecyclerAdapter.ViewHolder> implements Filterable {
    private Context context;
    private List<Item> itemList;
    private List<Item> itemListFull;

    public ItemRecyclerAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
        itemListFull = new ArrayList<>(itemList);
    }

    @NonNull
    @Override
    public ItemRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_row, viewGroup, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRecyclerAdapter.ViewHolder viewHolder, int position) {
        Item item = itemList.get(position);
        String imageUrl;

        viewHolder.ietem.setText(item.getItem());
        viewHolder.taste.setText(item.getTaste());
        viewHolder.price.setText("\u20B9" + item.getPrice());
        viewHolder.per.setText(item.getPer());
        if (item.getAvailable() != null) {
            if (item.getAvailable().equals("False")) {
                viewHolder.delivery.setTextColor(Color.parseColor("#FF0000"));
                viewHolder.delivery.setText("Not Available");
            } else {
                viewHolder.delivery.setText(item.getDelivery());
            }
        }
        else {
            viewHolder.delivery.setText(item.getDelivery());
        }

        if (item.getOriginal() != null && item.getOriginal() != item.getPrice()){
            if(!item.getOriginal().isEmpty()) {
                viewHolder.line.setText("|");
                viewHolder.original.setText("\u20B9" + item.getOriginal());
                viewHolder.original.setPaintFlags(viewHolder.original.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                Float a = Float.parseFloat(item.getOriginal());
                Float b = Float.parseFloat(item.getPrice());
                Float c = (a-b)/a*100;
                int d = Math.round(c);
                viewHolder.percent.setText(String.valueOf(d) + "% off");
            }
        }

        imageUrl = item.getImageUrl();

        if (imageUrl != null) {
            Picasso.get()
                    .load(imageUrl)
                    .fit()
                    .into(viewHolder.image);
        }
        else {
            viewHolder.image.setImageResource(R.drawable.logo);
            viewHolder.image.setAdjustViewBounds(true);
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public Filter getFilter() {
        return itemFilter;
    }

    private Filter itemFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Item> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(itemListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Item item : itemListFull) {
                    if (item.getItem() != null) {
                        if (item.getItem().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            itemList.clear();
            itemList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView
                ietem,
                taste,
                price,
                delivery,
                per,
                original,
                percent,
                line;
        DatabaseReference databaseReference;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserId, number;
        public Button button, buy;
        public FirebaseAuth firebaseAuth;
        public ElegantNumberButton quantity;
        DatabaseReference data = FirebaseDatabase.getInstance().getReference().child("Stop");
        public ImageButton image;

        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;

            line = itemView.findViewById(R.id.line);
            per = itemView.findViewById(R.id.row_per);
            quantity = (ElegantNumberButton) itemView.findViewById(R.id.quantity);
            quantity.setOnClickListener(new ElegantNumberButton.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Item item = itemList.get(position);
                    number = quantity.getNumber();
                }
            });
            firebaseAuth = FirebaseAuth.getInstance();
            ietem = itemView.findViewById(R.id.item_name_list);
            taste = itemView.findViewById(R.id.characteristic_taste);
            price = itemView.findViewById(R.id.price);
            delivery = itemView.findViewById(R.id.delivers_in);
            button = itemView.findViewById(R.id.add_to_cart);
            image = itemView.findViewById(R.id.item_image_list);
            image.setOnClickListener(this);
            button.setOnClickListener(this);
            final FirebaseUser user = firebaseAuth.getCurrentUser();
            buy = itemView.findViewById(R.id.buy_now);
            buy.setOnClickListener(this);
            original = itemView.findViewById(R.id.original_price);
            original.setOnClickListener(this);
            percent = itemView.findViewById(R.id.discount_percent);
        }

        @Override
        public void onClick(View v) {
            if (v == button) {
                int position = getAdapterPosition();
                final Item item = itemList.get(position);
                if (user != null) {
                    data.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue().toString().equals("True")){
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
                                        cartItem.setUserId(user.getUid().toString());
                                        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
                                        cartItem.setTime(currentDateTimeString);
                                        databaseReference = FirebaseDatabase.getInstance().getReference().child("cart").child(user.getUid().toString() + cartItem.getItem());
                                        databaseReference.setValue(cartItem);
                                        Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show();
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
                                        cartItem.setUserId(user.getUid().toString());
                                        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
                                        cartItem.setTime(currentDateTimeString);
                                        databaseReference = FirebaseDatabase.getInstance().getReference().child("cart").child(user.getUid().toString() + cartItem.getItem());
                                        databaseReference.setValue(cartItem);
                                        Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show();
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
                } else {
                    Toast.makeText(context, "You need to log in first !", Toast.LENGTH_LONG).show();
                    ((Activity)context).finish();
                }
            }
            if (v == buy) {
                int position = getAdapterPosition();
                final Item item = itemList.get(position);
                    if (user != null) {
                        data.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.getValue().toString().equals("True")){
                                    Toast.makeText(context, "Sorry, We are not serving now", Toast.LENGTH_LONG).show();
                                }
                                else {
                                    if (item.getAvailable() != null) {
                                        if (item.getAvailable().equals("False")) {
                                            Toast.makeText(context, item.getItem() + " is not available right now !", Toast.LENGTH_LONG).show();
                                        }
                                        else if (number != null) {
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
                                            cartItem.setUserId(user.getUid().toString());
                                            String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
                                            cartItem.setTime(currentDateTimeString);
                                            databaseReference = FirebaseDatabase.getInstance().getReference().child("cart").child(user.getUid().toString() + cartItem.getItem());
                                            databaseReference.setValue(cartItem);
                                            context.startActivity(new Intent(context, MyCart.class));
                                        }
                                        else {
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
                                            cartItem.setUserId(user.getUid().toString());
                                            String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
                                            cartItem.setTime(currentDateTimeString);
                                            databaseReference = FirebaseDatabase.getInstance().getReference().child("cart").child(user.getUid().toString() + cartItem.getItem());
                                            databaseReference.setValue(cartItem);
                                            context.startActivity(new Intent(context, MyCart.class));
                                        }
                                        else {
                                            Toast.makeText(context, "Please select the number of items you want to buy", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    } else {
                        Toast.makeText(context, "You need to log in first !", Toast.LENGTH_LONG).show();
                        ((Activity)context).finish();
                    }
                }
            }
        }
    }