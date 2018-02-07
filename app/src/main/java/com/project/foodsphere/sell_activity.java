package com.project.foodsphere;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class sell_activity extends AppCompatActivity {

    Button uploadbtn, choosebtn;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_activity);


        uploadbtn = (Button) findViewById(R.id.prod_btn);
        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText pnameEditText = (EditText) findViewById(R.id.prod_name);
                String pname = pnameEditText.getText().toString();

                EditText pdescEditText = (EditText) findViewById(R.id.prod_desc);
                String pdesc = pdescEditText.getText().toString();

                EditText pcategoryEditText = (EditText) findViewById(R.id.prod_category);
                String pcategory = pcategoryEditText.getText().toString();

                EditText ppriceEditText = (EditText) findViewById(R.id.prod_price);
                String pprice = ppriceEditText.getText().toString();

                EditText pmeasurementEditText = (EditText) findViewById(R.id.prod_measurement);
                String pmeasurement = pmeasurementEditText.getText().toString();

                EditText pquantityEditText = (EditText) findViewById(R.id.prod_quantity);
                String pquantity = pquantityEditText.getText().toString();

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference(" products");

                Map products = new HashMap();
                products.put("name", pname);
                products.put("description", pdesc);
                products.put("category", pcategory);
                products.put("price", pprice);
                products.put("measurement", pmeasurement);
                products.put("quantity", pquantity);

                String pid = ref.push().getKey();
                ref.child(pid).setValue(products);
            }
        });
    }
}