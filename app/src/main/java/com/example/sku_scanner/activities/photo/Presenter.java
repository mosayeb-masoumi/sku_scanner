package com.example.sku_scanner.activities.photo;

import android.content.Context;

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
}
