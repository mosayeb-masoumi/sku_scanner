package com.example.sku_scanner.activities.splash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.LocationManager;

import com.example.sku_scanner.activities.login.LoginActivity;
import com.example.sku_scanner.helpers.Cache;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.util.Timer;
import java.util.TimerTask;

public class Presenter implements Contract.Presenter {
    private Context context;
    private Contract.View view;
    private Contract.Model model = new Model();


    @Override
    public void attachView(Context context, Contract.View view) {
        this.view = view;
        this.context = context;
        model.attachPresenter(this, context);
    }


    @Override
    public void activityLoaded() {

        String email = Cache.getString("email");
        String password = Cache.getString("password");

        if(!email.equals("") && !password.equals("")){

//              new Timer().schedule(new TimerTask() {
//                  @Override
//                  public void run() {
//                      if(model.checkGpsON()){
//                          model.requestLogin();
//                      }else{
//                          displayLocationSettingsRequest(context,125);
//                      }
//                  }
//              }, 2700);

            if(model.checkGpsON()){
                model.requestLogin();
            }else{
                displayLocationSettingsRequest(context,125);
            }





        }else{
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    gotoLogin();
                }
            }, 2700);
        }
    }



    @Override
    public void requestLogin() {
        model.requestLogin();
    }



    @Override
    public boolean checkGpsON() {
        final LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return false;
        }
        return true;
    }




    private void gotoLogin() {
        context.startActivity(new Intent(context, LoginActivity.class));
        ((Activity) context).finish();
    }



    // turn on gps as google
    private void displayLocationSettingsRequest(Context context, int requestCode) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(result1 -> {
            final Status status = result1.getStatus();
            if (status.getStatusCode() == LocationSettingsStatusCodes.RESOLUTION_REQUIRED)
                try {
                    status.startResolutionForResult((Activity) context, requestCode);

                } catch (IntentSender.SendIntentException ignored) {
                }
        });
    }

}
