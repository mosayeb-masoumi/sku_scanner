package com.example.sku_scanner.activities.login;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.example.sku_scanner.activities.main.MainActivity;
import com.example.sku_scanner.R;

public class Presenter implements Contract.Presenter {
    private Context context;
    private Contract.View view;
    private Contract.Model model = new Model();

    @Override
    public void attachView(Context context, Contract.View view) {
        this.view = view;
        this.context = context;
        model.attachPresenter(this,context);
    }

    @Override
    public void btLoginClicked(String email, String password) {

        if(email.equals(""))
            view.showErrorEmpetyEmail();
        else if(password.equals(""))
            view.showEmpetyPassword();
        else{
            view.hideBtnLogin();
            model.requestLogin(email , password);
        }
    }


    @Override
    public void loginResult(int result) {

        view.showBtn();
        if(result == 1 ){
            context.startActivity(new Intent(context, MainActivity.class));
        }else if(result== -4){
            Toast.makeText(context, R.string.serverFaield, Toast.LENGTH_SHORT).show();
        }else if(result == -5){
            Toast.makeText(context, R.string.connectionFaield, Toast.LENGTH_SHORT).show();
        }


    }



    @Override
    public void getGpsPermission() {
        ActivityCompat.requestPermissions((Activity) context
                ,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},3);
    }

    @Override
    public boolean gpsOn() {
        final LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return false;
        }
        return true;
    }


    @Override
    public void saveEmailPassword(String email, String password) {

        model.saveEmailPassword(email,password);
    }

    @Override
    public boolean checkGpsPermission() {
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            return false;
        }
        return true;
    }

}
