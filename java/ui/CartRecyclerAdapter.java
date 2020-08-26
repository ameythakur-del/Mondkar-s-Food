package ui;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mondkars.mondkarsproduct.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

import model.CartItem;
import model.Wish;

public class CartRecyclerAdapter extends RecyclerView.Adapter<CartRecyclerAdapter.ViewHolder> {
    private Context context;
    private List<CartItem> myCart;

    public CartRecyclerAdapter(Context context, List<CartItem> myCart) {
        this.context = context;
        this.myCart = myCart;
    }

    @NonNull
    @Override
    public CartRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.cart_row, viewGroup, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull CartRecyclerAdapter.ViewHolder viewHolder, int position) {
        final CartItem cartItem = myCart.get(position);
        final String imageUrl;

        viewHolder.ietem.setText(cartItem.getItem());
        viewHolder.taste.setText(cartItem.getTaste());
        viewHolder.price.setText("\u20B9" +cartItem.getPrice());
        viewHolder.delivery.setText(cartItem.getDelivery());
        viewHolder.per.setText(cartItem.getPer());

        viewHolder.quantity.setText(cartItem.getNumber());

        imageUrl = cartItem.getImageUrl();

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
        return myCart.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView
                ietem,
                taste,
                price,
                delivery,
                quantity,
                per;
        DatabaseReference databaseReference, reference;
        public Button buy, wishlist;
        ProgressBar progressBar = new ProgressBar(context);

        public long countPosts = 0;
        public FirebaseAuth firebaseAuth;
        public FirebaseAuth.AuthStateListener authStateListener;

        public ImageButton image;

        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;
            wishlist = itemView.findViewById(R.id.wishlist);
            databaseReference = FirebaseDatabase.getInstance().getReference();
            per = itemView.findViewById(R.id.per);
            quantity = itemView.findViewById(R.id.cart_quantity);
            firebaseAuth = FirebaseAuth.getInstance();
            ietem = itemView.findViewById(R.id.cart_name_list);
            taste = itemView.findViewById(R.id.cart_characteristic_taste);
            price = itemView.findViewById(R.id.cart_price);
            delivery = itemView.findViewById(R.id.cart_delivers_in);
            image = itemView.findViewById(R.id.cart_image_list);
            image.setOnClickListener(this);
            buy = itemView.findViewById(R.id.zala);
            buy.setOnClickListener(this);
            wishlist.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == wishlist){
                progressBar.setVisibility(View.VISIBLE);
                int position = getAdapterPosition();
                CartItem cartitem = myCart.get(position);
//
                Wish wish = new Wish();
                wish.setCategory(cartitem.getCategory());
                wish.setDelivery(cartitem.getDelivery());
                wish.setImageUrl(cartitem.getImageUrl());
                wish.setItem(cartitem.getItem());
                wish.setNumber(cartitem.getNumber());
                wish.setPer(cartitem.getPer());
                wish.setPrice(cartitem.getPrice());
                wish.setTaste(cartitem.getTaste());
                wish.setUserId(cartitem.getUserId());
                String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
                wish.setTime(currentDateTimeString);
                databaseReference = FirebaseDatabase.getInstance().getReference().child("wish").child(cartitem.getUserId() + cartitem.getItem());
                databaseReference.setValue(wish);
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(context, cartitem.getItem() + " is added to your wishlist", Toast.LENGTH_SHORT).show();
            }

            if (v == buy) {
                int position = getAdapterPosition();
                CartItem cartitem = myCart.get(position);
                reference = FirebaseDatabase.getInstance().getReference().child("cart").child(cartitem.getUserId().toString() + cartitem.getItem());
                reference.removeValue();
                ((Activity) context).recreate();
            }
        }
    }
}



