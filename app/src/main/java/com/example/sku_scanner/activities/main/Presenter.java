package com.example.sku_scanner.activities.main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.example.sku_scanner.R;
import com.example.sku_scanner.activities.new_shop.NewShopActivity;
import com.example.sku_scanner.activities.qrcode.QRCodeActivity;
import com.example.sku_scanner.activities.qrcode.ScanActivity;

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
    public void loadView() {
//        model.requestShopList();
    }

    @Override
    public void requestShopList() {
        model.requestShopList();
        view.hideBtnChooseShop();
    }

    @Override
    public void sopListResult(int result) {
        if(result == 1){

            view.showBtnChooseshop();
            view.setShopSpinner();

        }else if (result == -4) {
            Toast.makeText(context, R.string.serverFaield, Toast.LENGTH_SHORT).show();
        } else if (result == -5) {
            Toast.makeText(context, R.string.connectionFaield, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void btnNewShopPressed() {
        context.startActivity(new Intent(context, NewShopActivity.class));
    }


    @Override
    public void requestCategoryList() {
        view.hideBtnFamily();
        model.requestCategoryList();
    }

    @Override
    public void getCategoryListResult(int result) {
        view.hideBtnFamily();
        if (result == 1) {
            view.setFamilySpinner();
            view.showBtnFamily();
        } else if (result == -4) {
            Toast.makeText(context, R.string.serverFaield, Toast.LENGTH_SHORT).show();
        } else if (result == -5) {
            Toast.makeText(context, R.string.connectionFaield, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void btnRegiserCodePressed() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            model.requestPermissionCamera();
        } else {
            context.startActivity(new Intent(context, ScanActivity.class));
        }
    }


}
