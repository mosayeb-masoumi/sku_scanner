package com.example.sku_scanner.activities.qrcode;

import android.content.Context;
import android.widget.Toast;

import com.example.sku_scanner.R;

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
    public void btnRegisterPressed(String barcodResult) {

        view.hideBtn();
        model.requestSendBarcode(barcodResult);


    }

    @Override
    public void barcodeProductsList(int result) {
        view.showBtn();
        if (result==1){
            view.showBtn();
        }else if(result==-4){
            Toast.makeText(context, R.string.serverFaield, Toast.LENGTH_SHORT).show();
        }else if(result == -5){
            Toast.makeText(context, R.string.connectionFaield, Toast.LENGTH_SHORT).show();
        }
    }
}
