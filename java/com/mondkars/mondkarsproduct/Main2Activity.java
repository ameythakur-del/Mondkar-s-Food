package com.mondkars.mondkarsproduct;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Main2Activity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth;
    private CollectionReference collectionReference = db.collection("Registering users");
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_about_us, R.id.contact_us)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        updateNavHeader();

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Mondkars Food</font>"));
    }

    private void updateNavHeader() {

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            user = FirebaseAuth.getInstance().getCurrentUser();
            {
                currentUserId = user.getUid();
               {
                    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                    View headerView = navigationView.getHeaderView(0);
                    final TextView navUsername = headerView.findViewById(R.id.nav_user_name);

                    collectionReference
                            .whereEqualTo("UserId", currentUserId)
                            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                    if (e != null) {

                                    }
                                    assert queryDocumentSnapshots != null;
                                    if (!queryDocumentSnapshots.isEmpty()) {
                                        for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                                            navUsername.setText(snapshot.getString("Name"));
                                        }
                                    }
                                }
                            });
               }
            }
        }
        else{
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            View headerView = navigationView.getHeaderView(0);
            TextView navUsername = headerView.findViewById(R.id.nav_user_name);
            navUsername.setText("Guest user");
        }
    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_add:
            {
                if (user != null) {
                    startActivity(new Intent(Main2Activity.this,
                            MyCart.class));
                } else {
                    finish();
                    Toast.makeText(Main2Activity.this, "You need to log in first !", Toast.LENGTH_LONG).show();
                }
            }
            break;
            case R.id.wish_list:
            {
                if (user != null) {
                    startActivity(new Intent(Main2Activity.this,
                            wishlist.class));
                } else {
                    finish();
                    Toast.makeText(Main2Activity.this, "You need to log in first !", Toast.LENGTH_LONG).show();
                }
            }
            break;

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onResume() {

        super.onResume();

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.getMenu().findItem(R.id.about_us).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(Main2Activity.this, AboutUsFragment.class));
                drawer.closeDrawers();
                return false;
            }
        });

        navigationView.getMenu().findItem(R.id.contact).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(Main2Activity.this, ContactUs.class));
                drawer.closeDrawers();
                return false;
            }
        });

        navigationView.getMenu().findItem(R.id.my_orders).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(FirebaseAuth.getInstance().getCurrentUser() != null) {
                    startActivity(new Intent(Main2Activity.this, GalleryFragment.class));
                    drawer.closeDrawers();
                }
                else{
                    finish();
                    Toast.makeText(Main2Activity.this, "You need to login first to view your orders.", Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });

        if (firebaseAuth.getCurrentUser() == null) {
            navigationView.getMenu().findItem(R.id.about_us).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    startActivity(new Intent(Main2Activity.this, AboutUsFragment.class));
                    return false;
                }
            });
            navigationView.getMenu().findItem(R.id.logout).setVisible(false);
            navigationView.getMenu().findItem(R.id.login).setVisible(true).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    finish();
                    return true;
                }
            });
        } else {
            navigationView.getMenu().findItem(R.id.logout).setVisible(true).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    firebaseAuth.signOut();;
                    finish();
                    return true;
                }
            });
            navigationView.getMenu().findItem(R.id.login).setVisible(false);
        }
    }
}
