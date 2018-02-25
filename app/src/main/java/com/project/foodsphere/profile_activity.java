package com.project.foodsphere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class profile_activity extends AppCompatActivity {

    Button prof_updt;
    private DatabaseReference ref;
    profile prof;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_activity);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        final String uname = user.getDisplayName();
        final String uemail = user.getEmail();

        ref = FirebaseDatabase.getInstance().getReference();
        ref.child("profile/"+uid);
        //Toast.makeText(this,ref.toString(), Toast.LENGTH_SHORT).show();
        TextView name = (TextView) findViewById(R.id.name);
        name.setText(uname);
        TextView email = (TextView) findViewById(R.id.email);
        email.setText(uemail);
        ref.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    prof = postSnapshot.getValue(profile.class);

                    String hname = prof.getHouse_name();
                    TextView house = (TextView) findViewById(R.id.hname);
                    house.setText(hname);

                    String landmark = prof.getLandmark();
                    TextView landmark1 = (TextView) findViewById(R.id.landmark);
                    landmark1.setText(landmark);

                    String city  = prof.getCity();
                    TextView city1 = (TextView) findViewById(R.id.city);
                    city1.setText(city);

                    String district = prof.getDistrict();
                    TextView district1 = (TextView) findViewById(R.id.district);
                    district1.setText(district);

                    String pincode = prof.getPincode();
                    TextView pincode1 = (TextView) findViewById(R.id.pincode);
                    pincode1.setText(pincode);

                    String phone = prof.getPhone();
                    TextView phone1 = (TextView) findViewById(R.id.phone);
                    phone1.setText(phone);
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
        });

        prof_updt = (Button) findViewById(R.id.prof_update);
        prof_updt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profile_activity.this,fill_profile.class);
                startActivity(intent);
            }
        });


    }
}
