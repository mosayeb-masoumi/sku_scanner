package com.example.sku_scanner.models.register_category;

import com.google.gson.annotations.SerializedName;

public class RegisterCategory {
    @SerializedName("id")
    public String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
