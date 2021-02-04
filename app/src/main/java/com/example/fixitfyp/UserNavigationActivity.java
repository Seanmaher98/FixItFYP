package com.example.fixitfyp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
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
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserNavigationActivity extends AppCompatActivity {

    AppBarConfiguration mAppBarConfiguration;
    RecyclerView recyclerView;
    DatabaseReference ProductsRef;
    DatabaseReference UserRef;
    TextView txtLoggedInUsername;
    FirebaseAuth firebaseAuth;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_navigation);

        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Trades");
        UserRef = FirebaseDatabase.getInstance().getReference("Users");
        txtLoggedInUsername = findViewById(R.id.user_profile_name);

        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "You have no messages", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_navigation,menu);
        return true;
    }

    @Override
        protected void onStart() {
            super.onStart();
            //This recycler view is a firebase recycler view, it is used to pull the data from my database.
            //The code uses my database reference to pull data from my "Trades" database path - YouTube Tutorial - https://youtu.be/745ElNRjJew
            //This is the same code as seen in Home Activity

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
    //Not working at the moment
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.nav_logOut){
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            Toast.makeText(getApplicationContext(),"You are now logged out", Toast.LENGTH_SHORT).show();
            finish();
        }
        return true;
    }
}