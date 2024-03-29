package com.example.sku_scanner.network;

import com.example.sku_scanner.models.barcode_check.BarcodeCheck;
import com.example.sku_scanner.models.barcode_check.BarcodeCheckSendData;
import com.example.sku_scanner.models.category.CategoryList;
import com.example.sku_scanner.models.city.CityList;
import com.example.sku_scanner.models.city.CitySendData;
import com.example.sku_scanner.models.login.LoginResult;
import com.example.sku_scanner.models.login.LoginSendData;
import com.example.sku_scanner.models.province.ProvinceList;
import com.example.sku_scanner.models.register_category.RegisterCategory;
import com.example.sku_scanner.models.register_category.RegisterCategorySendData;
import com.example.sku_scanner.models.register_shop.RegisterShop;
import com.example.sku_scanner.models.register_shop.RegisterShopSendData;
import com.example.sku_scanner.models.scan.ScanSendData;
import com.example.sku_scanner.models.shop.ShopList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Service {

//    @Headers("Accept: application/json")
    @POST("Login")
    Call<LoginResult> getLogin(@Body LoginSendData sendData);

//    @Headers({"Authorization: Bearer user1@sku.com"})
    @GET("Shop/Province")
    Call<ProvinceList> getProvinceList();


    @POST("Shop/City")
    Call<CityList> getCityList(@Body CitySendData citySendData);


    @POST("Shop/Create")
    Call<RegisterShop> getRegisterShop(@Body RegisterShopSendData registerShopSendData);


    @GET("Category")
    Call<CategoryList> getCategoryList();

    @GET("Shop")
    Call<ShopList> getShoplist();
//    Call<ShopList> getShoplist(@Header("Authorization") String BearerToken);


    @POST("Barcode/Check")                                                              ////////////////////////////////////////
    Call<BarcodeCheck> getBarcodeCheck(@Body BarcodeCheckSendData barcodeCheckSendData);


    @POST("Scan/Create")                                                              ////////////////////////////////////////
    Call<Boolean> sendScanData(@Body ScanSendData scanSendData);


    @POST("Category/Create")
    Call<RegisterCategory> getRegisterCategory(@Body RegisterCategorySendData registerCategorySendData);




}
