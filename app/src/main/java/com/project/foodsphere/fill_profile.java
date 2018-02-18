package com.project.foodsphere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class fill_profile extends AppCompatActivity {
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_profile);
        button = (Button) findViewById(R.id.update);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et1 = (EditText) findViewById(R.id.housename);
                String hname = et1.getText().toString();
                EditText et2 = (EditText) findViewById(R.id.landmark);
                String landmark = et2.getText().toString();
                EditText et3 = (EditText) findViewById(R.id.city);
                String city = et3.getText().toString();
                EditText et4 = (EditText) findViewById(R.id.district);
                String district = et4.getText().toString();
                EditText et5 = (EditText) findViewById(R.id.pincode);
                String pincode = et5.getText().toString();
                EditText et6 = (EditText) findViewById(R.id.phone);
                String phone = et6.getText().toString();

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();
                DatabaseReference ref = database.getReference("profile");

                Map user_info = new HashMap();
                user_info.put("house name", hname);
                user_info.put("landmark", landmark);
                user_info.put("city", city);
                user_info.put("district", district);
                user_info.put("pincode", pincode);
                user_info.put("phone", phone);
                ref.child(uid).setValue(user_info);
                Toast.makeText(fill_profile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(fill_profile.this,profile_activity.class);
                startActivity(intent);
            }
        });
    }
}
