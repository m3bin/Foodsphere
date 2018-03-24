package com.project.foodsphere;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
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

public class DisplayAdapter extends ArrayAdapter<Products> {

    private Context context;
    private ArrayList<Products> product;
    DatabaseReference mDatabase;

    private static class ViewHolder {
        TextView name, description, measurement, price, quantity;
        ImageView image;
        EditText buy_quantity;
        Button cart;
    }

    public DisplayAdapter(ArrayList<Products> product, Context context) {
        super(context, R.layout.activity_product_display, product);
        this.product = product;
        this.context = context;
        //Toast.makeText(context, "hmmmmm "+product, Toast.LENGTH_SHORT).show();

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Products products = product.get(position);
        // Check if an existing view is being reused, otherwise inflate the view

        final ViewHolder viewHolder; // view lookup cache stored in tag
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

        viewHolder.name.setText(products.getName());
        viewHolder.description.setText(products.getDescription());
        viewHolder.price.setText(products.getPrice());
        viewHolder.quantity.setText(products.getQuantity());
        viewHolder.measurement.setText(products.getMeasurement());
        Glide.with(context).load(products.getImage()).into(viewHolder.image);
        viewHolder.cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String buy_qty = viewHolder.buy_quantity.getText().toString();
                final String prd_qty = products.getQuantity();
                double cmp1 = Double.parseDouble(prd_qty);
                double qnty = Double.parseDouble(buy_qty);
                double prdt_price = Double.parseDouble(products.getPrice());
                String tot_price = Double.toString(qnty*prdt_price);
                if (cmp1 >= qnty) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = user.getUid();
                    Toast.makeText(context, "Item Added to Cart", Toast.LENGTH_SHORT).show();
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("cart/"+uid);
                    String id=mDatabase.push().getKey();
                    final Map cart = new HashMap();
                    //cart.put("key",products.getrKey());
                    cart.put("ckey",id);
                    cart.put("key",products.getrKey());
                    cart.put("name",products.getName());
                    cart.put("image",products.getImage());
                    cart.put("price",tot_price);
                    cart.put("buy_quantity",buy_qty);
                    cart.put("avail_quantity",prd_qty);
                    cart.put("uploader",products.getUploader());
                    cart.put("measurement",products.getMeasurement());
                    mDatabase.child(id).setValue(cart);
                    //mDatabase.child(uid).setValue(cart);
                    context.startActivity(new Intent(context,cart_activity.class));

                }
                else {
                    Snackbar.make(v, "Requested quantity is greater than available quantity",
                            Snackbar.LENGTH_SHORT)
                            .setActionTextColor(Color.BLUE)
                            .show();
                    //Toast.makeText(context, "Requested quantity is greater than available quantity", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }
}




