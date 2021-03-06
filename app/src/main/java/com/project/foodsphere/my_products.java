package com.project.foodsphere;

import android.app.ProgressDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class my_products extends AppCompatActivity {
    private RecyclerView recyclerView;

    //adapter object
    private RecyclerView.Adapter adapter;
    //database reference
    private DatabaseReference mDatabase;
    //progress dialog
    private ProgressDialog progressDialog;

    private List<seller_order_holder> product_holder;
    seller_order_holder products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_products);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = user.getUid();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressDialog = new ProgressDialog(this);
        product_holder = new ArrayList<>();

        //displaying progress dialog while fetching images
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("sellerorder");
        //adding an event listener to fetch values
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //dismissing the progress dialog
                progressDialog.dismiss();
                DataSnapshot snap = snapshot.child("sellerorder/" + uid);

                for (DataSnapshot postSnapshot : snap.getChildren()) {
                    products = postSnapshot.getValue(seller_order_holder.class);
                    product_holder.add(products);
                }
                if (product_holder.isEmpty()){
                    Snackbar.make(findViewById(R.id.myproducts),"No products to display",Snackbar.LENGTH_LONG).show();
                }
                else {
                    //creating adapter
                    adapter = new my_product_Adapter(getApplicationContext(),product_holder);
                    //adding adapter to recyclerview
                    recyclerView.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
    }
}
