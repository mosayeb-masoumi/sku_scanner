package com.example.sku_scanner.models.login;

import com.google.gson.annotations.SerializedName;

public class LoginResult {
    @SerializedName("result")
    public Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

}
