package com.example.sku_scanner.models.city;

import com.google.gson.annotations.SerializedName;

public class City {
    @SerializedName("id")
    public Integer id;
    @SerializedName("province_id")
    public String provinceId;
    @SerializedName("city")
    public String city;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
