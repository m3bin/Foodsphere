package com.project.foodsphere;
import com.google.firebase.database.IgnoreExtraProperties;
/**
 * Created by Lenovo on 2/17/2018.
 */
@IgnoreExtraProperties
public class Products {
    public String name;
    public String image;
    public String price;
    public String key;
    public String description;
    public String measurement;
    public String quantity;
    public String location;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Products() {
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
    public String getQuantity(){return quantity;}
    public String getLocation(){return location;}

}
