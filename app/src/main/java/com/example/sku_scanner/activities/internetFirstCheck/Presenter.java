package com.example.sku_scanner.activities.internetFirstCheck;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;

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
    public void btnConnectDataClicked() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$DataUsageSummaryActivity"));
        context.startActivity(intent);
    }

    @Override
    public void btnConnectWifiClicked() {
        context.startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));
    }
}
