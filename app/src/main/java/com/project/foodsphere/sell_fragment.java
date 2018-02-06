package com.project.foodsphere;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mebin on 03-02-2018.
 */

public class sell_fragment extends Fragment {

    Button uploadbtn;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.sell_fragment_layout,container,false);
        uploadbtn = (Button) v.findViewById(R.id.prod_btn);
        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText pnameEditText = (EditText) getActivity().findViewById(R.id.prod_name);
                String pname = pnameEditText.getText().toString();

                EditText pdescEditText = (EditText) getActivity().findViewById(R.id.prod_desc);
                String pdesc = pdescEditText.getText().toString();

                EditText pcategoryEditText = (EditText) getActivity().findViewById(R.id.prod_category);
                String pcategory = pcategoryEditText.getText().toString();

                EditText ppriceEditText = (EditText) getActivity().findViewById(R.id.prod_price);
                String pprice = ppriceEditText.getText().toString();

                EditText pmeasurementEditText = (EditText) getActivity().findViewById(R.id.prod_measurement);
                String pmeasurement = pmeasurementEditText.getText().toString();

                EditText pquantityEditText = (EditText) getActivity().findViewById(R.id.prod_quantity);
                String pquantity = pquantityEditText.getText().toString();

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference("products");

                Map products = new HashMap();
                products.put("name",pname);
                products.put("description",pdesc);
                products.put("category",pcategory);
                products.put("price",pprice);
                products.put("measurement",pmeasurement);
                products.put("quantity",pquantity);

                String pid = ref.push().getKey();
                ref.child(pid).setValue(products);
            }
        });

        return v;
    }


}
