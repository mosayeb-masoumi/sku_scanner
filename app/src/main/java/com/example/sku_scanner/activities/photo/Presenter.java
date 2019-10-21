package com.example.sku_scanner.activities.photo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.sku_scanner.R;
import com.example.sku_scanner.activities.main.MainActivity;
import com.example.sku_scanner.activities.qrcode.ScanActivity;

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
    public void sendData(String strScanResult, String strBm1) {
        view.hideBtn();
        model.sendData(strScanResult,strBm1);
    }

    @Override
    public void sendDataResult(int result) {
        view.showBtn();
        if(result ==1){
            context.startActivity(new Intent(context, ScanActivity.class));
            Toast.makeText(context, "با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();
        }else if(result ==-4){
            Toast.makeText(context, R.string.serverFaield, Toast.LENGTH_SHORT).show();
        }else if(result==-5){
            Toast.makeText(context, R.string.connectionFaield, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void imgBackPressed() {
        context.startActivity(new Intent(context, MainActivity.class));
        ((Activity)context).finish();
    }
}
