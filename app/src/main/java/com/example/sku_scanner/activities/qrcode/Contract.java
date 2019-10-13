package com.example.sku_scanner.activities.qrcode;

import android.content.Context;

public interface Contract {
    interface View{

        void hideBtn();

        void showBtn();
    }

    interface Presenter {

        void attachView (Context context, View view);

        void btnRegisterPressed(String barcodResult);

        void barcodeProductsList(int result);
    }

    interface Model{

        void attachPresenter (Presenter presenter , Context context);

        void requestSendBarcode(String barcodResult);
    }
}
