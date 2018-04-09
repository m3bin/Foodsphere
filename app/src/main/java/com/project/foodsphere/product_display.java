package com.project.foodsphere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class product_display extends AppCompatActivity {

    private ArrayList<Products> products;
    DatabaseReference mDatabase;
    TextView name, description, measurement, price, quantity;
    ImageView image;
    DisplayAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_display);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = user.getUid();
        listView = (ListView) findViewById(R.id.list);

        final String key = getIntent().getStringExtra("key");
        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        measurement = findViewById(R.id.measurement);
        price = findViewById(R.id.price);
        quantity = findViewById(R.id.quantity);
        image = findViewById(R.id.prod_image);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("products");
        mDatabase.child(key);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                products = new ArrayList<>();
                DataSnapshot post = snapshot.child(key);
                Products product = post.getValue(Products.class);
                products.add(product);
                adapter = new DisplayAdapter(products, getBaseContext());
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

}


