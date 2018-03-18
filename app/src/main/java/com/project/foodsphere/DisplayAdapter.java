package com.project.foodsphere;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lenovo on 2/20/2018.
 */
public class DisplayAdapter extends ArrayAdapter<Products> {

    private Context context;
    private ArrayList<Products> product;
    DatabaseReference mDatabase;


    private static class ViewHolder {
        TextView name, description, measurement, price, quantity;
        EditText buy_quantity;
        ImageView image;
        Button cart;
    }

    public DisplayAdapter(ArrayList<Products> data, Context context) {
        super(context, R.layout.activity_product_display, data);
        this.product = data;
        this.context = context;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Products product  = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        final ViewHolder viewHolder; // view lookup cache stored in tag
        final View result;
        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.activity_product_display, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.description = (TextView) convertView.findViewById(R.id.description);
            viewHolder.price = (TextView) convertView.findViewById(R.id.price);
            viewHolder.quantity = (TextView) convertView.findViewById(R.id.quantity);
            viewHolder.measurement = (TextView) convertView.findViewById(R.id.measurement);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.prod_image);
            viewHolder.buy_quantity = (EditText) convertView.findViewById(R.id.buy_quantity);
            viewHolder.cart = (Button) convertView.findViewById(R.id.cart);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(product.getName());
        viewHolder.description.setText(product.getDescription());
        viewHolder.price.setText(product.getPrice());
        viewHolder.quantity.setText(product.getQuantity());
        viewHolder.measurement.setText(product.getMeasurement());
        Glide.with(context).load(product.getImage()).into(viewHolder.image);

        viewHolder.cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String buy_qty = viewHolder.buy_quantity.getText().toString();
                final String prd_qty = product.getQuantity();
                if (buy_qty.compareTo(prd_qty) <= 0) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = user.getUid();
                    Toast.makeText(context, "Item Added to Cart", Toast.LENGTH_SHORT).show();
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("cart/"+uid);
                    String id=mDatabase.push().getKey();

                    final Map cart = new HashMap();

                    cart.put("name",product.getName());
                    cart.put("image",product.getImage());
                    cart.put("price",product.getPrice());
                    cart.put("key",product.getrKey());
                    cart.put("quantity",buy_qty);
                    mDatabase.child(id).setValue(cart);
                }
                else {
                    Toast.makeText(context, "Requested quantity is greater than available quantity", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // Return the completed view to render on screen
        return convertView;
    }
}




