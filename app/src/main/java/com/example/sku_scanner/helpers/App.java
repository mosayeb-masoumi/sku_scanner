package com.example.sku_scanner.helpers;

import android.app.Application;
import android.content.Context;

import com.example.sku_scanner.models.category.CategoryList;
import com.example.sku_scanner.models.city.CityList;
import com.example.sku_scanner.models.login.LoginResult;
import com.example.sku_scanner.models.province.ProvinceList;
import com.example.sku_scanner.models.shop.ShopList;

public class App extends Application {

    // public static String ServerURL = "https://sku.rahbarbazar.com/sku/api/v1/";
    public static String ServerURL = "https://test.rahbarbazar.com/sku/api/v1/";

    public static Context context;

    public static LoginResult loginResult = new LoginResult();
    public static ShopList shopList = new ShopList();
    public static ProvinceList provinceList = new ProvinceList();
    public static CityList cityList = new CityList();
    public static CategoryList categoryList = new CategoryList();
    //    public static BarcodeProductsList barcodeProductsList = new BarcodeProductsList();


//    public static TotalSpnLists totalSpnLists = new TotalSpnLists();
//    public static SubBrandList subBrandList = new SubBrandList();
//    public static SubCategoryList2 subCategoryList2 = new SubCategoryList2();
//    public static ProductRegisterDetailDataList productRegisterDetailDataList = new ProductRegisterDetailDataList();
//    public static ProductDetailInfoParent productDetailInfoParent= new ProductDetailInfoParent();


    public static String idSpnFamily = "";
    public static String idSpnShop = "";
    public static String barcodeResult = "";
    public static String productId = "";
    public static int spnPosition ;
    public static int spnPosition2;


    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}
