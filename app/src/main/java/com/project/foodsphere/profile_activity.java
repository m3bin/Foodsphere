package com.project.foodsphere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.security.auth.PrivateCredentialPermission;

public class profile_activity extends AppCompatActivity {

    Button prof_updt,prdt_ordrs;
    private DatabaseReference ref;
    private ImageView dpic;
    profile prof;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_activity);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = user.getUid();
        final String uname = user.getDisplayName();
        final String uemail = user.getEmail();

        ref = FirebaseDatabase.getInstance().getReference();
        ref.child("profile");
        //Toast.makeText(this,ref.toString(), Toast.LENGTH_SHORT).show();
        TextView name = (TextView) findViewById(R.id.name);
        name.setText(uname);
        TextView email = (TextView) findViewById(R.id.email);
        email.setText(uemail);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        String personPhotoUrl = acct.getPhotoUrl().toString();
        dpic = (ImageView) findViewById(R.id.dp);
        Glide.with(getApplicationContext()).load(personPhotoUrl).into(dpic);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot snap=dataSnapshot.child("profile");
                for (DataSnapshot postSnapshot : snap.getChildren()) {
                     prof = postSnapshot.getValue(profile.class);
                    //Toast.makeText(buy_activity.this, products.getName(), Toast.LENGTH_SHORT).show();
                    if (prof.getUid().equals(uid)){
                        String hname = prof.getHouse_name();
                        TextView house = (TextView) findViewById(R.id.hname);
                        house.setText(hname);

                        String landmark = prof.getLandmark();
                        TextView landmark1 = (TextView) findViewById(R.id.landmark);
                        landmark1.setText(landmark);

                        String city  = prof.getCity();
                        if (city==null){
                            TextView city1 = (TextView) findViewById(R.id.city);
                            city1.setText("Please update address");
                        }
                        else
                        {
                            TextView city1 = (TextView) findViewById(R.id.city);
                            city1.setText(city);
                        }


                        String district = prof.getDistrict();
                        TextView district1 = (TextView) findViewById(R.id.district);
                        district1.setText(district);

                        String pincode = prof.getPincode();
                        TextView pincode1 = (TextView) findViewById(R.id.pincode);
                        pincode1.setText(pincode);

                        String phone = prof.getPhone();
                        if (phone==null){
                            TextView phone1 = (TextView) findViewById(R.id.phone);
                            phone1.setText("Please update phone number");
                        }
                        else
                        {
                            TextView phone1 = (TextView) findViewById(R.id.phone);
                            phone1.setText(phone);
                        }
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

      /*  ref.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    prof = postSnapshot.getValue(profile.class);
                    Toast.makeText(profile_activity.this, prof.getUid(), Toast.LENGTH_SHORT).show();
                    if (prof.getUid().equals(uid)){
                        String hname = prof.getHouse_name();
                        TextView house = (TextView) findViewById(R.id.hname);
                        house.setText(hname);

                        String landmark = prof.getLandmark();
                        TextView landmark1 = (TextView) findViewById(R.id.landmark);
                        landmark1.setText(landmark);

                        String city  = prof.getCity();
                        if (city==null){
                            TextView city1 = (TextView) findViewById(R.id.city);
                            city1.setText("Please update address");
                        }
                        else
                        {
                            TextView city1 = (TextView) findViewById(R.id.city);
                            city1.setText(city);
                        }


                        String district = prof.getDistrict();
                        TextView district1 = (TextView) findViewById(R.id.district);
                        district1.setText(district);

                        String pincode = prof.getPincode();
                        TextView pincode1 = (TextView) findViewById(R.id.pincode);
                        pincode1.setText(pincode);

                        String phone = prof.getPhone();
                        if (phone==null){
                            TextView phone1 = (TextView) findViewById(R.id.phone);
                            phone1.setText("Please update phone number");
                        }
                        else
                        {
                            TextView phone1 = (TextView) findViewById(R.id.phone);
                            phone1.setText(phone);
                        }
                    }

                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        }); */

        prof_updt = (Button) findViewById(R.id.prof_update);
        prof_updt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profile_activity.this,fill_profile.class);
                startActivity(intent);
            }
        });
        prdt_ordrs = findViewById(R.id.product_orders);
        prdt_ordrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profile_activity.this,my_products.class);
                startActivity(intent);
            }
        });

    }
}
