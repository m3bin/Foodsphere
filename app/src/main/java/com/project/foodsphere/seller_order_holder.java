package com.project.foodsphere;
//import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by mebin on 25-03-2018.
 */
//@IgnoreExtraProperties
public class seller_order_holder {
    public String buy_quantity;
    public String image;
    public String key;
    public String name;
    public String orderid;
    public String price;
    public String measurement;
    public String buyer;
    public String buyer_name;
    public String buyer_email;
    public String buyer_housename;
    public String buyer_landmark;
    public String buyer_city;
    public String buyer_district;
    public String buyer_pincode;
    public String buyer_phone;
    //default constructor
    public seller_order_holder(){

    }

    public String getBuy_quantity(){return buy_quantity;}
    public String getImage(){return image;}
    public String getKey(){return key;}
    public String getName(){return name;}
    public String getOrderid(){return orderid;}
    public String getPrice(){return price;}
    public String getMeasurement(){return measurement;}

    public String getBuyer_id(){return buyer;}
    public String getBuyer_name(){return buyer_name;}
    public String getBuyer_email(){return buyer_email;}
    public String getHouse_name() {
        return buyer_housename;
    }
    public String getLandmark() {
        return buyer_landmark;
    }
    public String getCity() {
        return buyer_city;
    }
    public String getDistrict() {
        return buyer_district;
    }
    public String getPincode() { return buyer_pincode; }
    public String getPhone() {
        return buyer_phone;
    }
}
