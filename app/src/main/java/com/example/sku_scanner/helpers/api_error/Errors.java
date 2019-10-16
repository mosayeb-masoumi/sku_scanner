package com.example.sku_scanner.helpers.api_error;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Errors {
    @SerializedName("title")
    public List<String> title = null;
    @SerializedName("description")
    public List<String> description = null;
}
