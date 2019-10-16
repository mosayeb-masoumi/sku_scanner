package com.example.sku_scanner.activities.main;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.ActivityCompat;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sku_scanner.helpers.App;
import com.example.sku_scanner.helpers.Cache;
import com.example.sku_scanner.helpers.GpsTracker;
import com.example.sku_scanner.models.category.CategoryList;
import com.example.sku_scanner.models.city.CityList;
import com.example.sku_scanner.models.city.CitySendData;
import com.example.sku_scanner.models.province.ProvinceList;
import com.example.sku_scanner.models.register_category.RegisterCategory;
import com.example.sku_scanner.models.register_category.RegisterCategorySendData;
import com.example.sku_scanner.models.register_shop.RegisterShop;
import com.example.sku_scanner.models.register_shop.RegisterShopSendData;
import com.example.sku_scanner.models.shop.ShopList;
import com.example.sku_scanner.network.Service;
import com.example.sku_scanner.network.ServiceProvider;
import com.wang.avi.AVLoadingIndicatorView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Model implements Contract.Model {

    private Contract.Presenter presenter;
    private Context context;
    private GpsTracker gpsTracker;
    String strLat,strLng;


    @Override
    public void attachPresenter(Contract.Presenter presenter , Context context) {
        this.presenter = presenter;
        this.context=context;
    }


    @Override
    public void requestShopList() {


        Service service = new ServiceProvider(context).getmService();
//        Call<ShopList> call = service.getShoplist("Bearer "+App.loginResult.result.getEmail());
        Call<ShopList> call = service.getShoplist();

//        Call<ShopList> call = apiService.getShoplist();
        call.enqueue(new Callback<ShopList>() {
            @Override
            public void onResponse(Call<ShopList> call, Response<ShopList> response) {
                if(response.code()==200){

                    App.shopList = response.body();
                    presenter.sopListResult(1);

                }else{
                    Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
                    presenter.sopListResult(-4);
                }
            }

            @Override
            public void onFailure(Call<ShopList> call, Throwable t) {

                Toast.makeText(context, ""+t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                presenter.sopListResult(-5);
            }
        });
    }


    @Override
    public void requestCategoryList() {

//        APIService apiService=APIClient.getClient().create(APIService.class);
//        Call<CategoryList> call = apiService.getCategoryList();
        Service service = new ServiceProvider(context).getmService();
        Call<CategoryList> call = service.getCategoryList();


        call.enqueue(new Callback<CategoryList>() {
            @Override
            public void onResponse(Call<CategoryList> call, Response<CategoryList> response) {
                if(response.code()==200){
                    App.categoryList=response.body();
                    presenter.getCategoryListResult(1);
                }else{
                    Toast.makeText(context, "server error", Toast.LENGTH_SHORT).show();
                    presenter.getCategoryListResult(-4);
                }
            }

            @Override
            public void onFailure(Call<CategoryList> call, Throwable t) {

                Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                presenter.getCategoryListResult(-5);
            }
        });

    }


    @Override
    public void requestPermissionCamera() {
        ActivityCompat.requestPermissions((Activity) context,new String[]{Manifest.permission.CAMERA},1);
    }

    @Override
    public void registerNewCategory(String title, String description, Dialog newCategoryDialog) {
        RegisterCategorySendData data=new RegisterCategorySendData();
        data.setTitle(title);
        data.setDescription(description);

        Service service = new ServiceProvider(context).getmService();
        Call<RegisterCategory> call = service.getRegisterCategory(data);

        call.enqueue(new Callback<RegisterCategory>() {
            @Override
            public void onResponse(Call<RegisterCategory> call, Response<RegisterCategory> response) {
                if(response.code()==200){

                    String strIdFamily = response.body().getId();
                    App.idSpnFamily = response.body().getId();
                    Cache.setString("idFamily",strIdFamily);
                    presenter.newFamilyResult(1,newCategoryDialog,title);
                    Cache.setString("strCategoty",title);
                }else{
                    presenter.newFamilyResult(-4,newCategoryDialog,title);
                }
            }

            @Override
            public void onFailure(Call<RegisterCategory> call, Throwable t) {
                presenter.newFamilyResult(-5,newCategoryDialog,title);


            }
        });
    }

    @Override
    public void requestProvinceData() {
        Service service = new ServiceProvider(context).getmService();
        Call<ProvinceList> call = service.getProvinceList();

        call.enqueue(new Callback<ProvinceList>() {
            @Override
            public void onResponse(Call<ProvinceList> call, Response<ProvinceList> response) {
                if(response.code()==200){

                    presenter.setNewShopDialogProvince(response.body());
                    presenter.resultGetProvinceList(1);
                }else{
                    presenter.resultGetProvinceList(-4);
                }
            }

            @Override
            public void onFailure(Call<ProvinceList> call, Throwable t) {
                Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                presenter.resultGetProvinceList(-5);
            }
        });
    }

    @Override
    public void requestNewShopLists() {

    }

    @Override
    public void requestDataSpnCity(Spinner spinnercity, int positionProvince, ProvinceList provincelist, AVLoadingIndicatorView avi) {
        CitySendData citySendData = new CitySendData();
        citySendData.setId(provincelist.data.get(positionProvince).id);

        Service service = new ServiceProvider(context).getmService();
        Call<CityList> call = service.getCityList(citySendData);

        call.enqueue(new Callback<CityList>() {
            @Override
            public void onResponse(Call<CityList> call, Response<CityList> response) {
                if(response.code()==200){
                    presenter.setSpinnerCity(response.body() , spinnercity,avi);
                    App.cityList = response.body();

                }else{
                    Toast.makeText(context, "server error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CityList> call, Throwable t) {
                Toast.makeText(context, "error connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void btNewShopClicked(String edtShopName, String edtShopAddress, String edtShopTel,
                                 int spinnerProvincePosition, int spinnercityPosition, int spinnerAreaPosition,
                                 Button btnRegister, ProgressBar pbRegister, Dialog newShopDialog) {

        getLocation();
        RegisterShopSendData registerShopSendData = new RegisterShopSendData();

        registerShopSendData.name = edtShopName;
        registerShopSendData.address = edtShopAddress;
        registerShopSendData.tel = edtShopTel;

        registerShopSendData.lat = strLat;
        registerShopSendData.lng = strLng;
//        registerShopSendData.city_id = String.valueOf(App.cityList.data.get(cityItemPosition).id);
        registerShopSendData.city_id = String.valueOf(App.cityList.data.get(spinnercityPosition).getId());
        registerShopSendData.region = String.valueOf(spinnerAreaPosition);


//        APIService apiService = APIClient.getClient().create(APIService.class);
//        Call<RegisterShop> call = apiService.getRegisterShop(registerShopSendData);

        Service service = new ServiceProvider(context).getmService();
        Call<RegisterShop> call = service.getRegisterShop(registerShopSendData);


        call.enqueue(new Callback<RegisterShop>() {
            @Override
            public void onResponse(Call<RegisterShop> call, Response<RegisterShop> response) {
                if(response.code()==200){

                    App.idSpnShop = response.body().getId();
                    String strIdShop = response.body().getId();
                    Cache.setString("idShop",strIdShop);
                    presenter.showBtnRegisterNewShop(btnRegister,pbRegister,newShopDialog,edtShopName);
                    Cache.setString("strShop",edtShopName);

                }else{
                    presenter.registerNewShopResult(-4,btnRegister,pbRegister,newShopDialog,edtShopName);
                }
            }

            @Override
            public void onFailure(Call<RegisterShop> call, Throwable t) {
//                Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                presenter.registerNewShopResult(-5,btnRegister,pbRegister,newShopDialog,edtShopName);
            }
        });
    }

    public void getLocation(){
        gpsTracker = new GpsTracker(context);
        if(gpsTracker.canGetLocation()){
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            strLat = (String.valueOf(latitude));
            strLng = (String.valueOf(longitude));
        }else{
            gpsTracker.showSettingsAlert();
        }
    }
}
