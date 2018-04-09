package com.project.foodsphere;

import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.util.ULocale;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView,recyclerView2;
    private RecyclerView.Adapter adapter;
    private List<Category> category;
    private List<Products> product;
    private ImageView imgProfilePic;
    private ProgressDialog progressDialog;
    TextView order,recom;
    DatabaseReference ref,ref1,ref2,ref3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
     /*   Button t1 = findViewById(R.id.buy_temp);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(HomeActivity.this,buy_activity.class);
                startActivity(intent1);
            }
        });
        Button t2 = findViewById(R.id.sell_temp);
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(HomeActivity.this,sell_activity.class);
                startActivity(intent1);
            }
        });
        Button t3 = findViewById(R.id.prof_temp);
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(HomeActivity.this,profile_activity.class);
                startActivity(intent1);
            }
        }); */

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cart = new Intent(HomeActivity.this,cart_activity.class);
                startActivity(cart);
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show(); */
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

        if (acct != null) {
            View header=navigationView.getHeaderView(0);
            String personName = acct.getDisplayName();
            TextView username = (TextView) header.findViewById(R.id.uname);
            username.setText(personName);
            String personEmail = acct.getEmail();
            TextView mailText = (TextView) header.findViewById(R.id.uemail);
            mailText.setText(personEmail);

            String personPhotoUrl = acct.getPhotoUrl().toString();
            imgProfilePic = (ImageView) header.findViewById(R.id.imageView);
            Glide.with(getApplicationContext()).load(personPhotoUrl).into(imgProfilePic);
        }
        order=findViewById(R.id.order);
        recom=findViewById(R.id.recom);
        category=new ArrayList<>();
        product=new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2 = (RecyclerView) findViewById(R.id.recyclerView3);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = user.getUid();
        ref = FirebaseDatabase.getInstance().getReference();
        ref.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot1){

                if (snapshot1.hasChild("preference/"+uid)){
                    ref2 = FirebaseDatabase.getInstance().getReference().child("preference/"+uid);
                    ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot shot: dataSnapshot.getChildren()) {

                                Category cat=shot.getValue(Category.class);
                                category.add(cat);
                            }
                            recom.setText("Recommendations");
                            adapter = new RowAdapter(getApplicationContext(), category);
                            //adding adapter to recyclerview
                            recyclerView.setAdapter(adapter);
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            progressDialog.dismiss();
                        }
                    });

                } else {
                    ref1 = FirebaseDatabase.getInstance().getReference().child("default");
                    ref1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            //Toast.makeText(HomeActivity.this, "else" +dataSnapshot.getKey(), Toast.LENGTH_SHORT).show();
                            for (DataSnapshot shot: dataSnapshot.getChildren()) {
                                Category cat=shot.getValue(Category.class);
                                //Toast.makeText(HomeActivity.this, "else2" +shot.getKey(), Toast.LENGTH_SHORT).show();
                                category.add(cat);
                            }
                            recom.setText("Recommendations");
                            adapter = new RowAdapter(getApplicationContext(), category);
                            //adding adapter to recyclerview
                            recyclerView.setAdapter(adapter);
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            progressDialog.dismiss();
                        }
                    });
                    //ref4.child(category).setValue("1");
                    //Toast.makeText(cart_activity.this, "else" , Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ref3 = FirebaseDatabase.getInstance().getReference().child("order/"+uid);
        ref3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot shot: dataSnapshot.getChildren()) {
                    Products cat=shot.getValue(Products.class);
                    product.add(cat);
                }
                if(!product.isEmpty())
                {
                    order.setText("Your Orders");
                }

                adapter = new OrderAdapter(getApplicationContext(), product);
                //adding adapter to recyclerview
                recyclerView2.setAdapter(adapter);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }

        });
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    } */

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        displaySelectedScreen(item.getItemId());
        return true;
    }

    public void displaySelectedScreen(int itemId){

       // Fragment fragment = null;

        switch(itemId) {
            case R.id.nav_camera:
                //fragment = new Menu1();
                break;
            case R.id.nav_gallery:
                Intent intent1 = new Intent(HomeActivity.this,buy_activity.class);
                startActivity(intent1);
                break;
            case R.id.nav_slideshow:
                Intent intent = new Intent(HomeActivity.this,sell_activity.class);
                startActivity(intent);
               //fragment = new sell_fragment();
                break;
            case R.id.nav_profile:
                Intent prof_intent = new Intent(HomeActivity.this,profile_activity.class);
                startActivity(prof_intent);
                break;
        }

       /* if(fragment!=null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.sell_frame, fragment);
            ft.commit();
        } */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }


}
