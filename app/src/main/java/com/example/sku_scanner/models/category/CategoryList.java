package com.example.sku_scanner.models.category;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryList {
    @SerializedName("data")
    public List<Category> data = null;
}
