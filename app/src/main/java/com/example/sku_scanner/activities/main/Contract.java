package com.example.sku_scanner.activities.main;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.sku_scanner.models.city.CityList;
import com.example.sku_scanner.models.province.ProvinceList;
import com.wang.avi.AVLoadingIndicatorView;

public interface Contract {
    interface View{


        void setFamilySpinner();

        void hideBtnFamily();

        void showBtnFamily();

        void setShopSpinner();

        void hideBtnChooseShop();

        void showBtnChooseshop();

        void hideBtnRegisterNewCategory();

        void showBtnRegisterNewCategory();

        void setSpinnerArea(String[] spinnerAreaData);

        void setNewShopDialog(ProvinceList provincelist);

        void setSpinerCity(CityList cityList, Spinner spinnercity, AVLoadingIndicatorView avi);

        void showBtnRegisterNewShop(Button btnRegister, ProgressBar pbRegister, boolean b, Dialog newShopDialog, String edtShopName);

        void newFamilyRegistered(Dialog newCategoryDialog, String title);
    }

    interface Presenter {

        void attachView (Context context, View view);

        void loadView();

        void sopListResult(int result);

        void btnNewShopPressed();

        void requestCategoryList();

        void getCategoryListResult(int result);

        void btnRegiserCodePressed();

        void requestShopList();

        void registerNewCategory(String title, String description, Dialog newCategoryDialog);

        void newFamilyResult(int result, Dialog newCategoryDialog, String title);

        void requestProvinceData();

        void loadDataSpinnerArea();

        void setNewShopDialogProvince(ProvinceList provincelist);

        void resultGetProvinceList(int result);

        void requestDataSpnCity(Spinner spinnercity, int positionProvince, ProvinceList provincelist, AVLoadingIndicatorView avi);

        void setSpinnerCity(CityList cityList, Spinner spinnercity, AVLoadingIndicatorView avi);

        void btNewShopClicked(String edtShopName, String edtShopAddress, String edtShopTel,
                              int spinnerProvincePosition, int spinnercityPosition, int spinnerAreaPosition, Button btnRegister, ProgressBar pbRegister, Dialog newShopDialog);

        void registerNewShopResult(int result, Button btnRegister, ProgressBar pbRegister, Dialog newShopDialog, String edtShopName);

        void showBtnRegisterNewShop(Button btnRegister, ProgressBar pbRegister, Dialog newShopDialog, String edtShopName);
    }

    interface Model{

        void attachPresenter (Presenter presenter ,Context context);

        void requestShopList();

        void requestCategoryList();

        void requestPermissionCamera();

        void registerNewCategory(String title, String description, Dialog newCategoryDialog);

        void requestProvinceData();

        void requestNewShopLists();

        void requestDataSpnCity(Spinner spinnercity, int positionProvince, ProvinceList provincelist, AVLoadingIndicatorView avi);

        void btNewShopClicked(String edtShopName, String edtShopAddress, String edtShopTel, int spinnerProvincePosition, int spinnercityPosition, int spinnerAreaPosition, Button btnRegister, ProgressBar pbRegister, Dialog newShopDialog);
    }
}
