package com.example.sku_scanner.models.shop;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShopList {
    @SerializedName("data")
    public List<Shop> data = null;

    public List<Shop> getData() {
        return data;
    }

    public void setData(List<Shop> data) {
        this.data = data;
    }
}
