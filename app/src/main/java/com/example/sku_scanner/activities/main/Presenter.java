package com.example.sku_scanner.activities.main;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sku_scanner.R;
import com.example.sku_scanner.activities.new_shop.NewShopActivity;
import com.example.sku_scanner.activities.qrcode.ScanActivity;
import com.example.sku_scanner.models.city.CityList;
import com.example.sku_scanner.models.province.ProvinceList;
import com.wang.avi.AVLoadingIndicatorView;

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
    public void registerNewCategory(String title, String description, Dialog newCategoryDialog) {
        view.hideBtnRegisterNewCategory();
        model.registerNewCategory(title, description,newCategoryDialog);
    }

    @Override
    public void newFamilyResult(int result, Dialog newCategoryDialog, String title) {
        view.showBtnRegisterNewCategory();

        if (result == 1) {
            Toast.makeText(context, "خانواده جدید با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();
            view.newFamilyRegistered(newCategoryDialog,title);
        } else if (result == -4) {
            Toast.makeText(context, R.string.serverFaield, Toast.LENGTH_SHORT).show();
        } else if (result == -5) {
//            Toast.makeText(context, R.string.connectionFaield, Toast.LENGTH_SHORT).show();
            Toast.makeText(context, "خانواده تکراری وارد کرده اید", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void requestProvinceData() {
        model.requestProvinceData();
    }

    @Override
    public void loadDataSpinnerArea() {
        String[] spinnerAreaData = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        view.setSpinnerArea(spinnerAreaData);
    }

    @Override
    public void setNewShopDialogProvince(ProvinceList provincelist) {
        view.setNewShopDialog(provincelist);
    }

    @Override
    public void resultGetProvinceList(int result) {

        if (result == -4) {
            Toast.makeText(context, R.string.serverFaield, Toast.LENGTH_SHORT).show();
        } else if (result == -5) {
            Toast.makeText(context, R.string.connectionFaield, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void requestDataSpnCity(Spinner spinnercity, int positionProvince, ProvinceList provincelist, AVLoadingIndicatorView avi) {
        model.requestDataSpnCity(spinnercity, positionProvince, provincelist, avi);
    }

    @Override
    public void setSpinnerCity(CityList cityList, Spinner spinnercity, AVLoadingIndicatorView avi) {
        view.setSpinerCity(cityList, spinnercity, avi);
    }

    @Override
    public void btNewShopClicked(String edtShopName, String edtShopAddress, String edtShopTel,
                                 int spinnerProvincePosition, int spinnercityPosition, int spinnerAreaPosition,
                                 Button btnRegister, ProgressBar pbRegister, Dialog newShopDialog) {

        model.btNewShopClicked(edtShopName, edtShopAddress, edtShopTel, spinnerProvincePosition, spinnercityPosition, spinnerAreaPosition,
                btnRegister, pbRegister,newShopDialog);
    }

    @Override
    public void registerNewShopResult(int result, Button btnRegister, ProgressBar pbRegister, Dialog newShopDialog, String edtShopName) {

        view.showBtnRegisterNewShop(btnRegister,pbRegister,false,newShopDialog,edtShopName);
       if (result == -4) {
            Toast.makeText(context, R.string.serverFaield, Toast.LENGTH_SHORT).show();
        } else if (result == -5) {
           Toast.makeText(context, "نام فروشگاه تکراری می باشد", Toast.LENGTH_SHORT).show();
//            Toast.makeText(context, R.string.connectionFaield, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showBtnRegisterNewShop(Button btnRegister, ProgressBar pbRegister, Dialog newShopDialog, String edtShopName) {
        Toast.makeText(context, "فروشگاه جدید با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();
        view.showBtnRegisterNewShop(btnRegister,pbRegister,true,newShopDialog,edtShopName);
    }

//    @Override
//    public void requestNewShopLists() {
//        model.requestProvinceData();
//    }

    @Override
    public void sopListResult(int result) {
        view.showBtnChooseshop();
        view.setShopSpinner();
        if (result == 1) {

        } else if (result == -4) {
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
