package com.project.foodsphere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class profile_activity extends AppCompatActivity {

    Button prof_updt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_activity);
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
