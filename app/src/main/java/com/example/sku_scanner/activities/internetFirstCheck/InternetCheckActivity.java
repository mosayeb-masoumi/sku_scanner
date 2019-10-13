package com.example.sku_scanner.activities.internetFirstCheck;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.sku_scanner.R;
import com.example.sku_scanner.activities.splash.SplashActivity;
import com.example.sku_scanner.helpers.PersianAppcompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InternetCheckActivity extends PersianAppcompatActivity implements Contract.View {
    Contract.Presenter presenter = new Presenter();
    Context context;

    CheckNetwork checkNetwork;
    @BindView(R.id.ivIcon)
    ImageView ivIcon;
    @BindView(R.id.progress)
    RelativeLayout progress;
    @BindView(R.id.btn_connectData)
    Button btnConnectData;
    @BindView(R.id.btn_connectWifi)
    Button btnConnectWifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet);
        ButterKnife.bind(this);

        context = this;
        presenter.attachView(context, this);


        btnConnectData.setOnClickListener(v -> {

            presenter.btnConnectDataClicked();
        });

        btnConnectWifi.setOnClickListener(v -> {
            presenter.btnConnectWifiClicked();
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        checkNetwork = new CheckNetwork();
        registerReceiver(checkNetwork, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    @Override
    protected void onStop() {
        unregisterReceiver(checkNetwork);
        super.onStop();
    }

    @OnClick({R.id.btn_connectData, R.id.btn_connectWifi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_connectData:
                break;
            case R.id.btn_connectWifi:
                break;
        }
    }

    private class CheckNetwork extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();

            if (isConnected) {
//                Toast.makeText(context, "اینترنت وصل است", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(InternetCheckActivity.this, SplashActivity.class));
                finish();

            } else {
//                Toast.makeText(context, "لطفا اتصال دستگاه  به اینترنت را چک کنید", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
