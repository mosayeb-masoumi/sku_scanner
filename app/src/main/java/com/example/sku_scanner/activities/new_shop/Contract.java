package com.example.sku_scanner.activities.new_shop;

import android.content.Context;

public interface Contract {
    interface View{

        void setSpinnerArea(String[] spinnerAreaData);

        void setSpinnerProvinve();

        void setSpinnerCity();

        void stopLoading();

        void startLoading();

        void hideBtnRegister();

        void showPbRegister();

        void hidePb();

        void showBtn();
    }

    interface Presenter {

        void attachView (Context context, View view);

        void loadDataSpinnerArea();

        void viewLoaded();

        void resultGetProvinceList(int result);

        void requestDataSpnCity(int position);

        void requestGetCityResult(int result);

        void btnRegisterNewShopClicked(String shopName, String shopAddress, String shopTel, int provinceItemPosition, int cityItemPosition, int areaItemPosition);

        void registerNewShopResult(int result);
    }

    interface Model{

        void attachPresenter (Presenter presenter , Context context);

        void requestProvinceData();

        void requestDataSpnCity(int position);

        void requestToRegisterNewShop(String shopName, String shopAddress, String shopTel, int provinceItemPosition, int cityItemPosition, int areaItemPosition);
    }
}
