package com.example.sku_scanner.helpers.api_error;

import com.google.gson.annotations.SerializedName;

public class APIError {

    @SerializedName("message")
    public String message;
    @SerializedName("errors")
    public Errors errors;
}
