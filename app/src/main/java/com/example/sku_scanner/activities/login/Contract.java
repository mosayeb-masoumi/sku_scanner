package com.example.sku_scanner.activities.login;

import android.content.Context;

public interface Contract {
    interface View{

        void showEmpetyPassword();

        void hideBtnLogin();

        void showErrorEmpetyEmail();

        void showBtn();
    }

    interface Presenter {

        void attachView (Context context, View view);

        void btLoginClicked(String email, String password);


        void loginResult(int result);


//        boolean gpsPermission();

        void getGpsPermission();

        boolean gpsOn();

        void saveEmailPassword(String email, String password);

        boolean checkGpsPermission();
    }

    interface Model{

        void attachPresenter (Presenter presenter , Context context);

        void requestLogin(String email, String password);

        void saveEmailPassword(String email, String password);
    }
}
