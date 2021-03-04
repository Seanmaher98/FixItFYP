package com.example.fixitfyp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fixitfyp.Model.Products;
import com.example.fixitfyp.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class UserNavigationActivity extends AppCompatActivity {

    AppBarConfiguration mAppBarConfiguration;
    RecyclerView recyclerView;
    DatabaseReference ProductsRef;
    DatabaseReference UserRef;
    String userName;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_navigation);

        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Trades");
        UserRef = FirebaseDatabase.getInstance().getReference("Users");
        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Default when creating UserNavigation Activity
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.user_profile_name);
        //Default when creating UserNavigation Activity
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "You have no messages", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //Default when creating UserNavigation Activity
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_bookings, R.id.nav_account, R.id.nav_logOut)
                .setDrawerLayout(drawer)
                .build();
        //Adapted from the Dashboard Activity this code displays the logged in users username
        FirebaseDatabase.getInstance().getReference("Users")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            Users users = snapshot.getValue(Users.class);
                            //This sets the textviews to the data pulled from firebase
                            navUsername.setText(users.getUserName());
                        }
                        else {
                            startActivity(new Intent(getApplicationContext(), Dashboard.class));
                            finish();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        //This code allows us to log out when the log out item of the nav is selected
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int id = item.getItemId();

                    if(id == R.id.nav_logOut){
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        Toast.makeText(getApplication(),"You are now logged out", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else if(id == R.id.nav_bookings){
                        startActivity(new Intent(getApplicationContext(), UserBookings.class));
                    }
                    return true;

            }
        });

    }

    @Override
        protected void onStart() {
            super.onStart();
            //This recycler view is a firebase recycler view, it is used to pull the data from my database.
            //The code uses my database reference to pull data from my "Trades" database path
            // Displaying Products on Home Activity using Firebase RecyclerView Android Studio Tutorial- https://youtu.be/745ElNRjJew
            //This is the same code as seen in Home Activity
            //START OF YOUTUBE CODE
            FirebaseRecyclerOptions<Products> options =
                    new FirebaseRecyclerOptions.Builder<Products>()
                            .setQuery(ProductsRef, Products.class)
                            .build();

            FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
                    new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i, @NonNull Products products) {
                            productViewHolder.txtProductName.setText(products.getTradeName());
                            productViewHolder.txtProductDescription.setText(products.getTradeJob());
                            //Picasso.get().load(products.getImages()).into(productViewHolder.imgProduct);

                            productViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(UserNavigationActivity.this, ProductDetailsActivity.class);
                                    intent.putExtra("tradeId", products.getTradeId());
                                    startActivity(intent);
                                }
                            });

                        }
                        @NonNull
                        @Override
                        public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            //inflates my cardview containing database data
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                            ProductViewHolder holder = new ProductViewHolder(view);
                            return holder;

                        }
                    };
            adapter.startListening();
            recyclerView.setAdapter(adapter);
        }
        //END OF YOUTUBE CODE
}