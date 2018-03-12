package com.project.foodsphere;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Lenovo on 2/20/2018.
 */
public class DisplayAdapter extends ArrayAdapter<Products> {

    private Context context;
    private ArrayList<Products> product;

    private static class ViewHolder {
        TextView name, description, measurement, price, quantity;
        ImageView image;
    }

    public DisplayAdapter(ArrayList<Products> data, Context context) {
        super(context, R.layout.activity_product_display, data);
        this.product = data;
        this.context = context;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Products product  = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
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
        // Return the completed view to render on screen
        return convertView;
    }
}




