package com.example.sku_scanner.models.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ErrorsEmail {
    @SerializedName("email")
    @Expose
    public List<String> email = null;
    @SerializedName("password")
    @Expose
    public List<String> password = null;
}
