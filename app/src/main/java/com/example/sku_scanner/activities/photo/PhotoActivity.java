package com.example.sku_scanner.activities.photo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sku_scanner.R;
import com.example.sku_scanner.helpers.Converter;
import com.example.sku_scanner.helpers.GeneralTools;
import com.example.sku_scanner.helpers.Toaster;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.enums.EPickType;
import com.vansuita.pickimage.listeners.IPickResult;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.sku_scanner.activities.qrcode.QRCodeActivity.ResultScan;

public class PhotoActivity extends AppCompatActivity implements Contract.View,IPickResult {

    Contract.Presenter presenter = new Presenter();
    Context context;

    public static String ResultScan = "";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.img1_delete)
    ImageView img1Delete;
    @BindView(R.id.rl_camera1)
    RelativeLayout rlCamera1;
    @BindView(R.id.img1)
    ImageView img1;
    @BindView(R.id.btnSendPhoto)
    Button btnSendPhoto;
    @BindView(R.id.pbSendPhoto)
    ProgressBar pbSendPhoto;
    @BindView(R.id.rlButtons)
    RelativeLayout rlButtons;
    @BindView(R.id.rl_root)
    RelativeLayout rlRoot;
    @BindView(R.id.txtBarcode)
    TextView txtBarcode;

    Bitmap bm1;
    String strBm1;
    BroadcastReceiver connectivityReceiver = null;
    String strScanResult="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);

        context = this;
        presenter.attachView(context, this);

        //check network broadcast reciever
        GeneralTools tools = GeneralTools.getInstance();
        connectivityReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                tools.doCheckNetwork(PhotoActivity.this, findViewById(R.id.rl_root));
            }

        };


        rlCamera1.setOnClickListener(v -> {

            choose_pic();
        });


        img1Delete.setOnClickListener(v -> {
            strBm1 = "";
            img1.setImageDrawable(null);
        });


        btnSendPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.sendData(strScanResult,strBm1);
            }
        });

    }

    private void choose_pic() {
        PickSetup setup = new PickSetup()
                .setTitle("settitle")
                .setProgressText("progress text")
                .setPickTypes(EPickType.CAMERA)
                .setSystemDialog(true);
        PickImageDialog.build(setup).show(this);
    }

    @Override
    public void onPickResult(PickResult r) {
        if (r.getError() == null) {

            img1.setImageBitmap(r.getBitmap());
            bm1 = r.getBitmap();
            strBm1 = Converter.bitmapToString(bm1);

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(connectivityReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        if (ResultScan != null && ResultScan.length() > 0) {
            txtBarcode.setText(ResultScan);
            strScanResult = ResultScan;
            ResultScan = "";

        }else{
//            edtQR.setText(R.string.pleaseTypeBarcode);
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(connectivityReceiver);
        super.onDestroy();
    }

    @Override
    public void hideBtn() {
        btnSendPhoto.setVisibility(View.GONE);
        pbSendPhoto.setVisibility(View.VISIBLE);
    }
}
