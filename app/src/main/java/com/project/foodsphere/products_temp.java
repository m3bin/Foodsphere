package com.project.foodsphere;
import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by mebin on 24-03-2018.
 */

@IgnoreExtraProperties
public class products_temp {
    public String name;
    public String ckey;
    public String image;
    public String price;
    public String key;
    public String description;
    public String measurement;
    public String avail_quantity;
    public String buy_quantity;
    public String location;
    public String uploader;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public products_temp() {
    }

    public String getName() {
        return name;
    }
    public String getImage() {
        return image;
    }
    public String getPrice()
    {
        return price;
    }
    public String getrKey(){return key;}
    public String getDescription(){return description;}
    public String getMeasurement(){return measurement;}
    public String getAvail_quantity(){return avail_quantity;}
    public String getBuy_quantity(){return  buy_quantity;}
    public String getLocation(){return location;}
    public String getCkey(){return ckey;}
    public String getUploader(){return uploader;}
}
