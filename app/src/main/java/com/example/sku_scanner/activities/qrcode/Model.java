package com.example.sku_scanner.activities.qrcode;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.sku_scanner.helpers.App;
import com.example.sku_scanner.models.barcode_check.BarcodeCheck;
import com.example.sku_scanner.models.barcode_check.BarcodeCheckSendData;
import com.example.sku_scanner.network.Service;
import com.example.sku_scanner.network.ServiceProvider;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Model implements Contract.Model {

    private Contract.Presenter presenter;
    private Context context;

    @Override
    public void attachPresenter(Contract.Presenter presenter , Context context) {
        this.presenter = presenter;
        this.context=context;
    }

    @Override
    public void requestSendBarcode(String barcodResult) {

        BarcodeCheckSendData barcodeCheckSendData= new BarcodeCheckSendData();
        barcodeCheckSendData.setCode(barcodResult);




//        APIService  apiService = APIClient.getClient().create(APIService.class);
//        Call<BarcodeCheck> call=apiService.getBarcodeCheck(barcodeCheckSendData);

        Service service = new ServiceProvider(context).getmService();
        Call<BarcodeCheck> call = service.getBarcodeCheck(barcodeCheckSendData);


        call.enqueue(new Callback<BarcodeCheck>() {
            @Override
            public void onResponse(Call<BarcodeCheck> call, Response<BarcodeCheck> response) {
                if(response.code()==200){

                    BarcodeCheck result = response.body();
//                    if(result.data == true){
//                        context.startActivity(new Intent(context, RegisterProduct_ProductMainList.class));
//                        App.barcodeResult = barcodResult;
//                    }else{
//                        context.startActivity(new Intent(context, BarcodeListActivity.class)); //ok
//                        App.barcodeResult = barcodResult;
//                    }

                    presenter.barcodeProductsList(1);

                }else{
                    Toast.makeText(context, "serverError", Toast.LENGTH_SHORT).show();
                    presenter.barcodeProductsList(-4);
                }
            }

            @Override
            public void onFailure(Call<BarcodeCheck> call, Throwable t) {
                presenter.barcodeProductsList(-5);
            }
        });





    }
}
