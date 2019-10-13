package com.example.sku_scanner.activities.main;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sku_scanner.R;
import com.example.sku_scanner.activities.splash.SplashActivity;
import com.example.sku_scanner.helpers.App;
import com.example.sku_scanner.helpers.Cache;
import com.example.sku_scanner.helpers.DialogFactory;
import com.example.sku_scanner.helpers.GeneralTools;
import com.example.sku_scanner.helpers.PersianAppcompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends PersianAppcompatActivity implements Contract.View {
    Contract.Presenter presenter = new Presenter();
    Context context;

    boolean doubleBackToExitPressedOnce = false;
    BroadcastReceiver connectivityReceiver = null;


    @BindView(R.id.txtToolbarMain)
    TextView txtToolbarMain;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_newShop)
    Button btnNewShop;
    @BindView(R.id.btnShop)
    Button btnShop;
    @BindView(R.id.btn_newFamily)
    Button btnNewFamily;
    @BindView(R.id.btn_family)
    Button btnFamily;
    @BindView(R.id.btn_registerBarCode)
    Button btnRegisterBarCode;
    @BindView(R.id.pb_family)
    ProgressBar pbFamily;

    String idSpnShop, strShopSpn;
    String idSpnFamily, strProductSpn;
    @BindView(R.id.pb_chooseshop)
    ProgressBar pbChooseshop;
    @BindView(R.id.rl_root)
    RelativeLayout rlRoot;

    Boolean chooseShop = false, chooseFamily = false;
    @BindView(R.id.txtChooseShop)
    TextView txtChooseShop;
    @BindView(R.id.txtChooseProduct)
    TextView txtChooseProduct;
    @BindView(R.id.ll_info)
    LinearLayout llInfo;


    DialogFactory dialogFactory;
    @BindView(R.id.img_rahbar)
    ImageView imgRahbar;
    @BindView(R.id.ll1)
    LinearLayout ll1;
    @BindView(R.id.ll2)
    LinearLayout ll2;


//    EditText edtQR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        context = this;
        presenter.attachView(context, this);


        //initial Dialog factory
        dialogFactory = new DialogFactory(MainActivity.this);


        //check network broadcast reciever
        GeneralTools tools = GeneralTools.getInstance();
        connectivityReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                tools.doCheckNetwork(MainActivity.this, findViewById(R.id.rl_root));
            }

        };
//        edtQR=findViewById(R.id.edtQRcode);


//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(true);
//        toolbar.setTitle("داشبورد");

        txtToolbarMain.setText(App.loginResult.result.getEmail());
//        presenter.loadView();


        btnNewFamily.setOnClickListener(v -> {
            inflateNewCategory();
        });
        btnNewShop.setOnClickListener(v -> {
            presenter.btnNewShopPressed();
        });
        btnFamily.setOnClickListener(v -> {
            inflateChooseFamily();
        });
        btnShop.setOnClickListener(v -> {
            presenter.requestShopList();
        });


        btnRegisterBarCode.setOnClickListener(v -> {
            presenter.btnRegiserCodePressed();
        });

    }


    public void inflateChooseFamily() {
        presenter.requestCategoryList();
    }


    private void inflateNewCategory() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.inflate_alert_new_family);


        dialog.show();
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            exitApp();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "برای خروج مجددا دکمه ی بازگشت را بزنید", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
    }


    private void exitApp() {
        finish();
        startActivity(new Intent(Intent.ACTION_MAIN).
                addCategory(Intent.CATEGORY_HOME).
                setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAndRemoveTask();
        }
        Process.killProcess(Process.myPid());
        super.finish();
    }


    @Override
    public void setFamilySpinner() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.inflate_choose_family);

        Spinner spnFamily = dialog.findViewById(R.id.spinnerChooseFamily);
        Button btnRegisterChooseFamily = dialog.findViewById(R.id.btRegisterChooseFamily);
        ProgressBar pbFamily = dialog.findViewById(R.id.pbRegisterChooseFamily);


        List<String> categoryList = new ArrayList<>();
        for (int i = 0; i < App.categoryList.data.size(); i++) {
            categoryList.add(App.categoryList.data.get(i).title);
        }

        ArrayAdapter<String> spnCityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoryList);
        spnCityAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        spnFamily.setAdapter(spnCityAdapter);


        spnFamily.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int spnFamilyPosition = spnFamily.getSelectedItemPosition();
                idSpnFamily = App.categoryList.data.get(spnFamilyPosition).getId();
                App.idSpnFamily = idSpnFamily;
                strProductSpn = spnFamily.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnRegisterChooseFamily.setOnClickListener(v -> {

            App.idSpnFamily = idSpnFamily;
            chooseFamily = true;
            checkVisibilityBtnRgsBarcode();
            //todo we can save it in sharePref too
//             Cache.setString("idSpnFamily",idSpnFamily);
            dialog.dismiss();
        });


        dialog.show();
    }

    @Override
    public void setShopSpinner() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.inflate_choose_shop);
        dialog.setTitle("Title...");

        List<String> listSpnChooseShop = new ArrayList<String>();
        for (int i = 0; i < App.shopList.data.size(); i++) {
            listSpnChooseShop.add(App.shopList.data.get(i).name);
        }

        Spinner spnShop = dialog.findViewById(R.id.spinnerChooseShop);
        Button btRegisterChooseShop = dialog.findViewById(R.id.btRegisterChooseShop);
        ProgressBar pbShop = dialog.findViewById(R.id.pbRegisterChooseShop);

        ArrayAdapter<String> spnChooseShopAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listSpnChooseShop);
        spnChooseShopAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        spnShop.setAdapter(spnChooseShopAdapter);

        spnShop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int spnShopPosition = spnShop.getSelectedItemPosition();
                idSpnShop = App.shopList.data.get(spnShopPosition).getId();
                App.idSpnShop = idSpnShop;
                strShopSpn = spnShop.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btRegisterChooseShop.setOnClickListener(v -> {
            App.idSpnShop = idSpnShop;
            chooseShop = true;
            checkVisibilityBtnRgsBarcode();
            dialog.dismiss();
        });

        dialog.show();
    }

    private void checkVisibilityBtnRgsBarcode() {
        if (chooseShop && chooseFamily) {
            btnRegisterBarCode.setVisibility(View.VISIBLE);
            llInfo.setVisibility(View.VISIBLE);
            txtChooseShop.setText("انتخاب فروشگاه :" + " " + strShopSpn);
            txtChooseProduct.setText("خانواده محصول :" + " " + strProductSpn);
        } else {
            btnRegisterBarCode.setVisibility(View.GONE);
            llInfo.setVisibility(View.GONE);
        }
    }

    @Override
    public void hideBtnChooseShop() {
        btnShop.setVisibility(View.GONE);
        pbChooseshop.setVisibility(View.VISIBLE);
    }

    @Override
    public void showBtnChooseshop() {
        btnShop.setVisibility(View.VISIBLE);
        pbChooseshop.setVisibility(View.GONE);
    }


    @Override
    public void hideBtnFamily() {
        btnFamily.setVisibility(View.GONE);
        pbFamily.setVisibility(View.VISIBLE);
    }

    @Override
    public void showBtnFamily() {
        btnFamily.setVisibility(View.VISIBLE);
        pbFamily.setVisibility(View.GONE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(this, "نیاز به اجازه ی دسترسی دوربین", Toast.LENGTH_SHORT).show();
                }

        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu_main; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_reset) {
            Toast.makeText(context, "reset", Toast.LENGTH_SHORT).show();
            dialogFactory.createResetDialog(new DialogFactory.DialogFactoryInteraction() {
                @Override
                public void onAcceptButtonClicked(String... strings) {
                    Cache.setString("email", "");
                    Cache.setString("password", "");
                    startActivity(new Intent(MainActivity.this, SplashActivity.class));
                    (MainActivity.this).finish();
                }

                @Override
                public void onDeniedButtonClicked(boolean cancel_dialog) {

                }
            }, rlRoot);

//            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(connectivityReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        btnRegisterBarCode.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(connectivityReceiver);
        super.onDestroy();
    }
}
