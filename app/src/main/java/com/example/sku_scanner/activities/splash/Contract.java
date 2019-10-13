package com.example.sku_scanner.activities.splash;

import android.content.Context;

public interface Contract {
    interface View{

    }

    interface Presenter {

        void attachView (Context context,View view);

        void activityLoaded();

        void requestLogin();


        boolean checkGpsON();



    }

    interface Model{

        void attachPresenter (Presenter presenter , Context context);


        void requestLogin();

        boolean checkGpsON();
    }
}
