package com.example.sku_scanner.activities.login;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.sku_scanner.R;
import com.example.sku_scanner.helpers.GeneralTools;
import com.example.sku_scanner.helpers.PersianAppcompatActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends PersianAppcompatActivity implements Contract.View {
    Contract.Presenter presenter = new Presenter();
    Context context;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.btLogin)
    Button btLogin;
    @BindView(R.id.pbLogin)
    ProgressBar pbLogin;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.rlButtons)
    RelativeLayout rlButtons;
    @BindView(R.id.llLogin)
    RelativeLayout llLogin;

    String email, password;

    BroadcastReceiver connectivityReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        context = this;
        presenter.attachView(context, this);

        //check network broadcast reciever
        GeneralTools tools = GeneralTools.getInstance();
        connectivityReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                tools.doCheckNetwork(LoginActivity.this, findViewById(R.id.llLogin));
            }

        };

        //todo test
        etEmail.setText("user2@sku.com");
        etPassword.setText("123456789");


        btLogin.setOnClickListener(v -> {
//            if (presenter.gpsOn()) {
//                if (presenter.checkGpsPermission()) {
//                    email = etEmail.getText().toString().trim();
//                    password = etPassword.getText().toString().trim();
//                    presenter.btLoginClicked(email, password);
//                } else {
//                    presenter.getGpsPermission();
//                }
//            } else {
//                displayLocationSettingsRequest(context, 123);
//            }

            if (presenter.checkGpsPermission()) {
                if (presenter.gpsOn()) {
                    email = etEmail.getText().toString().trim();
                    password = etPassword.getText().toString().trim();
                    presenter.btLoginClicked(email, password);
                } else {
                    displayLocationSettingsRequest(context, 123);
                }
            } else {

                presenter.getGpsPermission();
            }

        });

    }


    @Override
    public void showEmpetyPassword() {
        etPassword.setError("لطفا کلمه ی عبور را وارد نمایید");
    }

    @Override
    public void showErrorEmpetyEmail() {
        etEmail.setError("لطفا ایمیل خود را وارد نمایید");
    }

    @Override
    public void showBtn() {
        btLogin.setVisibility(View.VISIBLE);
        pbLogin.setVisibility(View.GONE);
    }

    @Override
    public void hideBtnLogin() {
        btLogin.setVisibility(View.GONE);
        pbLogin.setVisibility(View.VISIBLE);
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


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case 3 :
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED ){

                    if (presenter.gpsOn()) {
                        email = etEmail.getText().toString().trim();
                        password = etPassword.getText().toString().trim();
                        presenter.btLoginClicked(email, password);
                    } else {
                        displayLocationSettingsRequest(context,123);
                    }
//                    email = etEmail.getText().toString().trim();
//                    password = etPassword.getText().toString().trim();
//                    presenter.btLoginClicked(email, password);
                }else {
                    Toast.makeText(this, "نیاز به اجازه ی دسترسی دوربین", Toast.LENGTH_SHORT).show();
                }

            case 123 :
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED ){

//                    email = etEmail.getText().toString().trim();
//                    password = etPassword.getText().toString().trim();
//                    presenter.btLoginClicked(email, password);
//                    presenter.btLoginClicked(email, password);

                }else {
                }

        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(connectivityReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(connectivityReceiver);
        super.onDestroy();
    }
}
