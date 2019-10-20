package com.example.sku_scanner.helpers.api_error;

import com.example.sku_scanner.models.login.ErrorsEmail;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class APIError {

    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("errors")
    @Expose
    public ErrorsEmail errors;
}
