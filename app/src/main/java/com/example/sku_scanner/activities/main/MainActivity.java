package com.example.sku_scanner.activities.main;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sku_scanner.R;
import com.example.sku_scanner.activities.qrcode.ScanActivity;
import com.example.sku_scanner.activities.splash.SplashActivity;
import com.example.sku_scanner.helpers.App;
import com.example.sku_scanner.helpers.Cache;
import com.example.sku_scanner.helpers.DialogFactory;
import com.example.sku_scanner.helpers.GeneralTools;
import com.example.sku_scanner.helpers.PersianAppcompatActivity;
import com.example.sku_scanner.models.city.CityList;
import com.example.sku_scanner.models.province.ProvinceList;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends PersianAppcompatActivity implements Contract.View {
    Contract.Presenter presenter = new Presenter();
    Context context;

    boolean doubleBackToExitPressedOnce = false;
    BroadcastReceiver connectivityReceiver = null;


    private static final String SHOWCASE_ID = "sequence example";

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

    String idSpnShop, strShopSpn = "";
    String idSpnFamily, strProductSpn = "";
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

    Button btn_register_category;
    ProgressBar pb_register_category;

    Dialog newShopDialog, newCategoryDialog;
    @BindView(R.id.pb_newShop)
    ProgressBar pbNewShop;

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


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setTitle("داشبورد");
        txtToolbarMain.setText(App.loginResult.result.getEmail());
//        presenter.loadView();


        btnNewFamily.setOnClickListener(v -> {
            inflateNewCategory();
        });
        btnNewShop.setOnClickListener(v -> {
            //for inflating newShop dialog
            presenter.requestProvinceData();
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


        presenter.checkUpdate();

    }



    @Override
    public void setNewShopDialog(ProvinceList provincelist) {
        newShopDialog = new Dialog(context);
        newShopDialog.setContentView(R.layout.inflate_alert_new_shop);

        EditText edtShopName = newShopDialog.findViewById(R.id.edtShopName);
        EditText edtShopAddress = newShopDialog.findViewById(R.id.edtShopAddress);
        EditText edtShopTel = newShopDialog.findViewById(R.id.edtShopTel);
        Spinner spinnerProvince = newShopDialog.findViewById(R.id.spinnerProvince);
        Spinner spinnercity = newShopDialog.findViewById(R.id.spinnercity);
        Spinner spinnerArea = newShopDialog.findViewById(R.id.spinnerArea);
        Button btnRegister = newShopDialog.findViewById(R.id.btRegister_newShop);
        ProgressBar pbRegister = newShopDialog.findViewById(R.id.pbRegister__newShop);
        AVLoadingIndicatorView avi = newShopDialog.findViewById(R.id.avi);

        if (newShopDialog.getWindow() != null) {

            newShopDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        List<String> provinceList = new ArrayList<>();
        for (int i = 0; i < provincelist.data.size(); i++) {
            provinceList.add(provincelist.data.get(i).getProvince());
        }

        String[] spinnerAreaData = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        ArrayAdapter<String> spnAreaAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerAreaData);
        spnAreaAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        spinnerArea.setAdapter(spnAreaAdapter);

        ArrayAdapter<String> spnProvinceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, provinceList);
        spnProvinceAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        spinnerProvince.setAdapter(spnProvinceAdapter);


        spinnerProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                avi.show();

                int positionProvince = spinnerProvince.getSelectedItemPosition();
                presenter.requestDataSpnCity(spinnercity, positionProvince, provincelist, avi);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRegister.setVisibility(View.GONE);
                pbRegister.setVisibility(View.VISIBLE);
                if (!edtShopName.getText().toString().equals("") && !edtShopAddress.getText().toString().equals("") &&
                        !edtShopTel.getText().toString().equals("") && (spinnerProvince.getSelectedItem().toString() != "")
                        && (spinnercity.getSelectedItem().toString() != "") && (spinnerArea.getSelectedItem().toString() != "")) {

                    btnRegister.setVisibility(View.GONE);
                    pbRegister.setVisibility(View.VISIBLE);
                    Cache.setBoolean("chooseShop", true);
                    presenter.btNewShopClicked(edtShopName.getText().toString(), edtShopAddress.getText().toString()
                            , edtShopTel.getText().toString(), spinnerProvince.getSelectedItemPosition(),
                            spinnercity.getSelectedItemPosition(), spinnerArea.getSelectedItemPosition(), btnRegister, pbRegister, newShopDialog);
                } else {
                    Toast.makeText(context, "لطفا فیلد ها را پر نمایید", Toast.LENGTH_SHORT).show();
                }

            }
        });


        newShopDialog.show();

    }

    @Override
    public void setSpinerCity(CityList cityList, Spinner spinnercity, AVLoadingIndicatorView avi) {

        avi.hide();
        List<String> cityLists = new ArrayList<>();
        for (int i = 0; i < cityList.data.size(); i++) {
            cityLists.add(cityList.data.get(i).getCity());
        }

        ArrayAdapter<String> spnCityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cityLists);
        spnCityAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        spinnercity.setAdapter(spnCityAdapter);


    }

    @Override
    public void showBtnRegisterNewShop(Button btnRegister, ProgressBar pbRegister, boolean b, Dialog newShopDialog, String edtShopName) {
        btnRegister.setVisibility(View.VISIBLE);
        pbRegister.setVisibility(View.GONE);
        newShopDialog.dismiss();
//        strShopSpn = edtShopName;
        Cache.setString("strShop", edtShopName);
        chooseShop = true;
        if (b) {
            checkVisibilityBtnRgsBarcode();
        }


    }

    @Override
    public void newFamilyRegistered(Dialog newCategoryDialog, String title) {
        newCategoryDialog.dismiss();
        chooseFamily = true;
//        strProductSpn = title;
        Cache.setString("strCategory", title);
        checkVisibilityBtnRgsBarcode();
    }

    @Override
    public void showUpdateDialog() {

        dialogFactory.createUpdateDialog(new DialogFactory.DialogFactoryInteraction() {
            @Override
            public void onAcceptButtonClicked(String... strings) {

                if (presenter.storagePermissionGranted()) {
                    presenter.updateApp();
                } else {
                    presenter.requestStoragePermission();
                }

            }

            @Override
            public void onDeniedButtonClicked(boolean cancel_dialog) {
                exitApp();
            }
        }, rlRoot);
    }

    @Override
    public void hideBtnNewShop() {
        btnNewShop.setVisibility(View.GONE);
        pbNewShop.setVisibility(View.VISIBLE);
    }

    @Override
    public void showBtnNewShop() {
        btnNewShop.setVisibility(View.VISIBLE);
        pbNewShop.setVisibility(View.GONE);
    }


    public void inflateChooseFamily() {
        presenter.requestCategoryList();
    }


    private void inflateNewCategory() {
        newCategoryDialog = new Dialog(context);
        newCategoryDialog.setContentView(R.layout.inflate_alert_new_family);

        EditText edtTitle = newCategoryDialog.findViewById(R.id.edtTitle_inflate_new_family);
        EditText edtDesctiption = newCategoryDialog.findViewById(R.id.edtDesc_inflate_new_family);
        btn_register_category = newCategoryDialog.findViewById(R.id.btRegisterChooseFamily);
        pb_register_category = newCategoryDialog.findViewById(R.id.pbRegisterChooseFamily);


        if (newCategoryDialog.getWindow() != null) {
            newCategoryDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        btn_register_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edtTitle.getText().toString();
                String description = edtDesctiption.getText().toString();
                if (title.equals("") || description.equals("")) {
                    Toast.makeText(context, "لطفا فیلدها را پر نمایید", Toast.LENGTH_SHORT).show();
                } else {
                    presenter.registerNewCategory(title, description, newCategoryDialog);

                    Cache.setBoolean("chooseFamily", true);
                }


            }
        });

        newCategoryDialog.show();
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


        if (dialog.getWindow() != null) {

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }


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
                Cache.setString("idFamily", idSpnFamily);
                strProductSpn = spnFamily.getSelectedItem().toString();
                Cache.setString("strCategory", strProductSpn);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnRegisterChooseFamily.setOnClickListener(v -> {

            App.idSpnFamily = idSpnFamily;
            chooseFamily = true;
            Cache.setBoolean("chooseFamily", true);
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


        if (dialog.getWindow() != null) {

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
                Cache.setString("idShop", idSpnShop);
                strShopSpn = spnShop.getSelectedItem().toString();
                Cache.setString("strShop", strShopSpn);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btRegisterChooseShop.setOnClickListener(v -> {
            App.idSpnShop = idSpnShop;
            chooseShop = true;
            Cache.setBoolean("chooseShop", true);
            checkVisibilityBtnRgsBarcode();
            dialog.dismiss();
        });

        dialog.show();
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
    public void hideBtnRegisterNewCategory() {
        btn_register_category.setVisibility(View.GONE);
        pb_register_category.setVisibility(View.VISIBLE);
    }

    @Override
    public void showBtnRegisterNewCategory() {
        btn_register_category.setVisibility(View.VISIBLE);
        pb_register_category.setVisibility(View.GONE);
    }

    @Override
    public void setSpinnerArea(String[] spinnerAreaData) {
//        ArrayAdapter<String> spinnerAreaAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerAreaData);
//        spinnerAreaAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
//        spinnerArea.setAdapter(spinnerAreaAdapter);
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

                    startActivity(new Intent(MainActivity.this, ScanActivity.class));
                } else {
                    Toast.makeText(this, "نیاز به اجازه ی دسترسی دوربین", Toast.LENGTH_SHORT).show();
                }
                break;

            case 555:
                presenter.updateApp();
                break;

        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_reset) {
            dialogFactory.createResetDialog(new DialogFactory.DialogFactoryInteraction() {
                @Override
                public void onAcceptButtonClicked(String... strings) {
                    Cache.setString("email", "");
                    Cache.setString("password", "");

                    Cache.setString("idShop", "");
                    Cache.setString("idFamily", "");
                    Cache.setString("strShop", "");
                    Cache.setString("strCategory", "");

                    startActivity(new Intent(MainActivity.this, SplashActivity.class));
                    (MainActivity.this).finish();
                }

                @Override
                public void onDeniedButtonClicked(boolean cancel_dialog) {

                }
            }, rlRoot);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void checkVisibilityBtnRgsBarcode() {

        String strShop = Cache.getString("strShop");
        String strCategory = Cache.getString("strCategory");
        if (!strShop.equals("") && !strCategory.equals("")) {
//        if (Cache.getBoolean("chooseShop") && Cache.getBoolean("chooseFamily")) {
            btnRegisterBarCode.setVisibility(View.VISIBLE);
            llInfo.setVisibility(View.VISIBLE);
            txtChooseShop.setText("انتخاب فروشگاه :" + " " + Cache.getString("strShop"));
            txtChooseProduct.setText("خانواده محصول :" + " " + Cache.getString("strCategory"));
        } else {
            btnRegisterBarCode.setVisibility(View.GONE);
            llInfo.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(connectivityReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        String strShop = Cache.getString("strShop");
        String strCategory = Cache.getString("strCategory");
        if (!strShop.equals("") && !strCategory.equals("")) {
            btnRegisterBarCode.setVisibility(View.VISIBLE);
            llInfo.setVisibility(View.VISIBLE);

            txtChooseShop.setText("انتخاب فروشگاه :" + " " + Cache.getString("strShop"));
            txtChooseProduct.setText("خانواده محصول :" + " " + Cache.getString("strCategory"));


        } else {
            btnRegisterBarCode.setVisibility(View.GONE);
            llInfo.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(connectivityReceiver);
        super.onDestroy();
    }
}
