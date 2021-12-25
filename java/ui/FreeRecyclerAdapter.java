package ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mondkars.mondkarsproduct.R;
import com.mondkars.mondkarsproduct.ViewPagerAdapter;
import com.mondkars.mondkarsproduct.ui.home.FreeFragment;

import java.util.Date;
import java.util.List;

import model.CartItem;
import model.Item;

public class FreeRecyclerAdapter extends RecyclerView.Adapter<FreeRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<Item> freeItemList;

    public FreeRecyclerAdapter(Context context, List<Item> freeItemList) {
        this.context = context;
        this.freeItemList = freeItemList;
    }

    @NonNull
    @Override
    public FreeRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.free_item, parent, false);
        return new FreeRecyclerAdapter.ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull FreeRecyclerAdapter.ViewHolder viewHolder, int position) {

        Item item = freeItemList.get(position);

        viewHolder.desc.setText(item.getPer());
    }

    @Override
    public int getItemCount() {
        return freeItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView desc;
        String currentUserId, number = "0";
        Button order;
        DatabaseReference data = FirebaseDatabase.getInstance().getReference().child("Stop");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);

            desc = itemView.findViewById(R.id.desc);
            order = itemView.findViewById(R.id.order);

            order.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            context.startActivity(new Intent(context, FreeFragment.class));
        }
    }
}
