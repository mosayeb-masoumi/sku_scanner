package com.example.sku_scanner.activities.new_shop;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.sku_scanner.R;
import com.example.sku_scanner.activities.main.MainActivity;

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
    public void loadDataSpinnerArea() {
        String[] spinnerAreaData = {"0","1", "2","3","4","5","6","7","8","9","10","11","12"};
        view.setSpinnerArea(spinnerAreaData);
    }

    @Override
    public void viewLoaded() {

        model.requestProvinceData();
    }

    @Override
    public void resultGetProvinceList(int result) {

        if(result == 1){

            view.setSpinnerProvinve();
        } else if(result== -4){
            Toast.makeText(context, R.string.serverFaield, Toast.LENGTH_SHORT).show();
        }else if(result== -5){
            Toast.makeText(context, R.string.connectionFaield, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void requestDataSpnCity(int position) {

        model.requestDataSpnCity(position);
    }

    @Override
    public void requestGetCityResult(int result) {

        if(result == 1){
            view.stopLoading();
            view.setSpinnerCity();
        } else if(result== -4){
            Toast.makeText(context, R.string.serverFaield, Toast.LENGTH_SHORT).show();
        }else if(result== -5){
            Toast.makeText(context, R.string.connectionFaield, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void btnRegisterNewShopClicked(String shopName, String shopAddress, String shopTel,
                                          int provinceItemPosition, int cityItemPosition, int areaItemPosition) {

        view.hideBtnRegister();
        view.showPbRegister();

        model.requestToRegisterNewShop(shopName,shopAddress,shopTel,provinceItemPosition,cityItemPosition,areaItemPosition);

    }

    @Override
    public void registerNewShopResult(int result) {
        if(result == 1){
            view.hidePb();
            view.showBtn();
            context.startActivity(new Intent(context, MainActivity.class));
            Toast.makeText(context, "فروشگاه جدید با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();
        }else if(result==-4){
            Toast.makeText(context, R.string.serverFaield, Toast.LENGTH_SHORT).show();
        }else if(result==-5){
            Toast.makeText(context, R.string.connectionFaield, Toast.LENGTH_SHORT).show();
        }


    }
}
