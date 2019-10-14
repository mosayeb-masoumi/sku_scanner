package com.example.sku_scanner.activities.new_shop;

import android.content.Context;
import android.widget.Toast;

import com.example.sku_scanner.helpers.App;
import com.example.sku_scanner.helpers.GpsTracker;
import com.example.sku_scanner.models.category.CategoryList;
import com.example.sku_scanner.models.city.CityList;
import com.example.sku_scanner.models.city.CitySendData;
import com.example.sku_scanner.models.province.ProvinceList;
import com.example.sku_scanner.models.register_shop.RegisterShop;
import com.example.sku_scanner.models.register_shop.RegisterShopSendData;
import com.example.sku_scanner.network.Service;
import com.example.sku_scanner.network.ServiceProvider;

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
    public void requestProvinceData() {
//        APIService apiService = APIClient.getClient().create(APIService.class);
//        Call<ProvinceList> call = apiService.getProvinceList();

        Service service = new ServiceProvider(context).getmService();
        Call<ProvinceList> call = service.getProvinceList();

        call.enqueue(new Callback<ProvinceList>() {
            @Override
            public void onResponse(Call<ProvinceList> call, Response<ProvinceList> response) {
                if(response.code()==200){

                    App.provinceList = response.body();
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
    public void requestDataSpnCity(int position) {

        CitySendData citySendData = new CitySendData();
        citySendData.setId(App.provinceList.data.get(position).id);

        Service service = new ServiceProvider(context).getmService();
        Call<CityList> call = service.getCityList(citySendData);

        call.enqueue(new Callback<CityList>() {
            @Override
            public void onResponse(Call<CityList> call, Response<CityList> response) {
                if(response.code()==200){
                    App.cityList = response.body();
                    presenter.requestGetCityResult(1);
                }else{
                    Toast.makeText(context, "server error", Toast.LENGTH_SHORT).show();
                    presenter.requestGetCityResult(-4);
                }
            }

            @Override
            public void onFailure(Call<CityList> call, Throwable t) {
                Toast.makeText(context, "error connection", Toast.LENGTH_SHORT).show();
                presenter.requestGetCityResult(-5);
            }
        });

    }

    @Override
    public void requestToRegisterNewShop(String shopName, String shopAddress, String shopTel,
                                         int provinceItemPosition, int cityItemPosition, int areaItemPosition) {


        getLocation();
        RegisterShopSendData registerShopSendData = new RegisterShopSendData();

        registerShopSendData.name = shopName;
        registerShopSendData.address = shopAddress;
        registerShopSendData.tel = shopTel;

        registerShopSendData.lat = strLat;
        registerShopSendData.lng = strLng;
//        registerShopSendData.city_id = String.valueOf(App.cityList.data.get(cityItemPosition).id);
        registerShopSendData.city_id = String.valueOf(App.cityList.data.get(cityItemPosition).getId());
        registerShopSendData.region = String.valueOf(areaItemPosition);


//        APIService apiService = APIClient.getClient().create(APIService.class);
//        Call<RegisterShop> call = apiService.getRegisterShop(registerShopSendData);

        Service service = new ServiceProvider(context).getmService();
        Call<RegisterShop> call = service.getRegisterShop(registerShopSendData);


        call.enqueue(new Callback<RegisterShop>() {
            @Override
            public void onResponse(Call<RegisterShop> call, Response<RegisterShop> response) {
                if(response.code()==200){

                    App.idSpnShop = response.body().getId();
                    presenter.registerNewShopResult(1);
                }else{
                    presenter.registerNewShopResult(-4);
                }
            }

            @Override
            public void onFailure(Call<RegisterShop> call, Throwable t) {
                Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                presenter.registerNewShopResult(-5);
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
