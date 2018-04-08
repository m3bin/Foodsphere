package com.project.foodsphere;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import java.util.List;
/**
 * Created by Lenovo on 2/17/2018.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Context context;
    private List<Products> product;

    public MyAdapter(Context context, List<Products> product) {
        this.product = product;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Products products = product.get(position);
        holder.textViewName.setText(products.getName());
        holder.textViewPrice.setText("₹ "+products.getPrice());

        Glide.with(context).load(products.getImage()).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str=products.getrKey();
                //Toast.makeText(context, "Key" + str, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,product_display.class);
                intent.putExtra("key",str);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return product.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewName;
        public TextView textViewPrice;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            textViewPrice = (TextView) itemView.findViewById(R.id.textViewPrice);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            itemView.setClickable(true);


            }

        }
    }