package com.example.sku_scanner.activities.photo;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.sku_scanner.activities.qrcode.ScanActivity;
import com.example.sku_scanner.helpers.App;
import com.example.sku_scanner.helpers.Toaster;
import com.example.sku_scanner.models.login.LoginResult;
import com.example.sku_scanner.models.scan.ScanSendData;
import com.example.sku_scanner.network.Service;
import com.example.sku_scanner.network.ServiceProvider;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Model implements Contract.Model {

    private Contract.Presenter presenter;
    private Context context;

    @Override
    public void attachPresenter(Contract.Presenter presenter , Context context) {
        this.presenter = presenter;
        this.context=context;
    }

    @Override
    public void sendData(String strScanResult, String strBm1) {


        ScanSendData scanData = new ScanSendData();
        scanData.setBarcode(strScanResult);
        scanData.setImage(strBm1);
        scanData.setShop_id(App.idSpnShop);
        scanData.setCategory_id(App.idSpnFamily);

        Service service = new ServiceProvider(context).getmService();
        Call<Boolean> call = service.sendScanData(scanData);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.code()==200){
                    Toast.makeText(context, "200", Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context,ScanActivity.class));
                    Toast.makeText(context, "با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(context, "server error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
