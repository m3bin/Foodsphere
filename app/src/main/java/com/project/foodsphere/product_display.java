package com.project.foodsphere;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ChildEventListener;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class product_display extends AppCompatActivity {

    private ArrayList<Products> products;
    DatabaseReference mDatabase;
    TextView name, description, measurement, price, quantity;
    ImageView image;
    DisplayAdapter adapter;
    ListView listView;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_display);
        listView=(ListView)findViewById(R.id.list);
        final String key = getIntent().getStringExtra("key");
        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        measurement = findViewById(R.id.measurement);
        price = findViewById(R.id.price);
        quantity = findViewById(R.id.quantity);
        image = findViewById(R.id.prod_image);

       /* LayoutInflater inflater = LayoutInflater.from(product_display.this); // or (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_product_display, null);
        b1 = findViewById(R.id.addtocart);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(product_display.this, "click", Toast.LENGTH_SHORT).show();
            }
        }); */
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("products").child(key);
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String s) {
                products=new ArrayList<>();
                        //Toast.makeText(product_display.this, "Key"+snapshot.child(key).getKey(), Toast.LENGTH_SHORT).show();
                        DataSnapshot post=snapshot.child(key);

                            Products product = post.getValue(Products.class);
                            products.add(product);

                adapter= new DisplayAdapter(products,getApplicationContext());
                listView.setAdapter(adapter);
            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot snapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    @Override
    public void onBackPressed(){
        this.finish();
    }

    public void addtocart(View view) {
        Toast.makeText(product_display.this, "click", Toast.LENGTH_SHORT).show();
    }
}



