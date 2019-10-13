package com.example.sku_scanner.models.shop;

import com.google.gson.annotations.SerializedName;

public class Shop {

    @SerializedName("id")
    public String id;
    @SerializedName("name")
    public String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
