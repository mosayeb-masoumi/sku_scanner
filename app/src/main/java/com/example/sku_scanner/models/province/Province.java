package com.example.sku_scanner.models.province;

import com.google.gson.annotations.SerializedName;

public class Province {
    @SerializedName("id")
    public String id;
    @SerializedName("province")
    public String province;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
