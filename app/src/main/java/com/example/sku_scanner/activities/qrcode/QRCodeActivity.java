package com.example.sku_scanner.activities.qrcode;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.sku_scanner.R;
import com.example.sku_scanner.helpers.GeneralTools;
import com.example.sku_scanner.helpers.PersianAppcompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QRCodeActivity extends PersianAppcompatActivity implements Contract.View {
    Contract.Presenter presenter = new Presenter();
    Context context;

    public static String ResultScan = "";

    @BindView(R.id.edtQR)
    EditText edtQR;
    @BindView(R.id.toolbar_Scanner)
    Toolbar toolbarScanner;
    @BindView(R.id.btnScanner)
    Button btnScanner;
    @BindView(R.id.btRegisterScannerResult)
    Button btRegisterScannerResult;
    @BindView(R.id.pbRegister)
    ProgressBar pbRegister ;
    BroadcastReceiver connectivityReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        ButterKnife.bind(this);
        context = this;
        presenter.attachView(context, this);



        //check network broadcast reciever
        GeneralTools tools = GeneralTools.getInstance();
        connectivityReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                tools.doCheckNetwork(QRCodeActivity.this, findViewById(R.id.rl_root));
            }

        };

        setSupportActionBar(toolbarScanner);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        setTitle("اسکنر");


//        btnScanner.setOnClickListener(v -> startActivity(new Intent(QRCodeActivity.this, QRcodeScaner.class)));
        btnScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED ){

                    requestCameraPermision();

                }else{
//                    turnOnGPS();
                    startActivity(new Intent(QRCodeActivity.this,ScanActivity.class));
                }
            }
        });

        btRegisterScannerResult.setOnClickListener(v -> {
            if(edtQR.getText().toString().equals("")){
                edtQR.setError("لطفا بارکد را وارد نمایید");
            }else
                presenter.btnRegisterPressed(edtQR.getText().toString());
        });

    }

    private void requestCameraPermision() {
        ActivityCompat.requestPermissions(this
                ,new String[]{Manifest.permission.CAMERA},1234);
    }


    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(connectivityReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void hideBtn() {
        btRegisterScannerResult.setVisibility(View.GONE);
        pbRegister.setVisibility(View.VISIBLE);
    }

    @Override
    public void showBtn() {
        btRegisterScannerResult.setVisibility(View.VISIBLE);
        pbRegister.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(connectivityReceiver);
        super.onDestroy();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1234:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED ){

                    startActivity(new Intent(QRCodeActivity.this,ScanActivity.class));
                }

            default:
                super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        }
    }
}
