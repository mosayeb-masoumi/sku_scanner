package com.example.sku_scanner.activities.photo;

import android.content.Context;

public interface Contract {

    interface View{

        void hideBtn();
    }

    interface Presenter {

        void attachView (Context context,View view);

        void sendData(String strScanResult, String strBm1);
    }

    interface Model{

        void attachPresenter (Presenter presenter , Context context);

        void sendData(String strScanResult, String strBm1);
    }
}
