package com.example.sku_scanner.activities.login;

import android.content.Context;
import android.widget.Toast;

import com.example.sku_scanner.helpers.App;
import com.example.sku_scanner.helpers.Cache;
import com.example.sku_scanner.helpers.GpsTracker;
import com.example.sku_scanner.helpers.Toaster;
import com.example.sku_scanner.models.login.LoginResult;
import com.example.sku_scanner.models.login.LoginSendData;
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
    public void requestLogin(String email, String password) {

        getLocation();

        LoginSendData senddata = new LoginSendData();
        senddata.email=email;
        senddata.password=password;
        senddata.lat = strLat;
        senddata.lng = strLng;




//        APIService apiService = APIClient.getClient().create(APIService.class);
//        Call<LoginResult> call = apiService.getLogin(senddata);

        Service service = new ServiceProvider(context).getmService();
        Call<LoginResult> call = service.getLogin(senddata);

        call.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {

                if (response.code()==200){

                    presenter.loginResult(1);
                    App.loginResult = response.body();
                    presenter.saveEmailPassword(response.body().result.email ,response.body().result.password);

                }else {

                    if (response.code() == 403){
                        Toaster.shorter("رمز صحیح نمی باشد");
                    }else{
                        Toaster.shorter("خطا در ارتباط با سرور");
                    }
                }
            }


            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
                Toast.makeText(context, ""+t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                String error1 = "java.lang.IllegalStateException: Expected BEGIN_OBJECT but was STRING at line 1 column 1 path $";
                if(t.getMessage().toString().equals(error1)){
                    Toaster.shorter("The selected email is invalid.");
                }

                presenter.loginResult(-5);
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



    @Override
    public void saveEmailPassword(String email, String password) {
        Cache.setString("email",email);
        Cache.setString("password",password);
    }
}
