package com.ayoyo.merchant.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ayoyo.merchant.json.UpdateTokenRequestJson;
import com.ayoyo.merchant.json.UpdateTokenResponseJson;
import com.ayoyo.merchant.utils.api.ServiceGenerator;
import com.ayoyo.merchant.utils.api.service.MerchantService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.ayoyo.merchant.R;
import com.ayoyo.merchant.constants.BaseApp;
import com.ayoyo.merchant.constants.Constants;
import com.ayoyo.merchant.constants.VersionChecker;
import com.ayoyo.merchant.fragment.HistoryFragment;
import com.ayoyo.merchant.fragment.HomeFragment;
import com.ayoyo.merchant.fragment.MenuFragment;
import com.ayoyo.merchant.fragment.MessageFragment;
import com.ayoyo.merchant.fragment.SettingsFragment;
import com.ayoyo.merchant.models.User;
import com.google.firebase.iid.FirebaseInstanceId;


import java.util.Objects;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    long mBackPressed;


    public static String apikey;

    LinearLayout mAdViewLayout;

    @SuppressLint("StaticFieldLeak")
    public static MainActivity mainActivity;
    private FragmentManager fragmentManager;
    BottomNavigationView navigation;
    int previousSelect = 0;

    private void tes(){
//        try {
//            User loginUser = BaseApp.getInstance(getApplicationContext()).getLoginUser();
//
//            UpdateTokenRequestJson requestJson = new UpdateTokenRequestJson();
//            String phoneNumber = loginUser.getNoTelepon();
//            requestJson.setNotelepon(phoneNumber);
//            requestJson.setRegId(FirebaseInstanceId.getInstance().getToken());
//
//            MerchantService service = ServiceGenerator.createService(MerchantService.class);
//            service.updatetoken(requestJson).enqueue(new Callback<UpdateTokenResponseJson>() {
//                @Override
//                public void onResponse(Call<UpdateTokenResponseJson> call, Response<UpdateTokenResponseJson> response) {
//
//                    System.out.println(response);
//                    if (response.isSuccessful()) {
//                        if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("found")) {
//                            User user = response.body().getData().get(0);
//                            saveUser(user);
//                        }
//                    }
//                }
//                @Override
//                public void onFailure(Call<UpdateTokenResponseJson> call, Throwable t) {
//
//                }
//            });
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
    }

    private void saveUser(User user) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.delete(User.class);
        realm.copyToRealm(user);
        realm.commitTransaction();
        BaseApp.getInstance(this).setLoginUser(user);
    }

    public static MainActivity getInstance() {
        return mainActivity;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Menu menu = navigation.getMenu();
            menu.findItem(R.id.home).setIcon(R.drawable.ic_store_s);
            menu.findItem(R.id.history).setIcon(R.drawable.ic_history_s);
            menu.findItem(R.id.chat).setIcon(R.drawable.ic_pesan);
            menu.findItem(R.id.menu).setIcon(R.drawable.ic_menu_s);
            menu.findItem(R.id.settings).setIcon(R.drawable.ic_settings_s);
            switch (item.getItemId()) {
                case R.id.home:
                    HomeFragment homeFragment = new HomeFragment();
                    navigationItemSelected(0);
                    item.setIcon(R.drawable.ic_store);
                    loadFrag(homeFragment, getString(R.string.menu_store), fragmentManager);
                    return true;

                case R.id.history:
                    HistoryFragment historyFragment = new HistoryFragment();
                    navigationItemSelected(1);
                    item.setIcon(R.drawable.ic_history);
                    loadFrag(historyFragment, getString(R.string.menu_history), fragmentManager);
                    return true;

                case R.id.chat:
                    MessageFragment messageFragment = new MessageFragment();
                    navigationItemSelected(2);
                    item.setIcon(R.drawable.ic_pesan_s);
                    loadFrag(messageFragment, getString(R.string.menu_chat), fragmentManager);
                    return true;

                case R.id.menu:
                    MenuFragment menuFragment = new MenuFragment();
                    navigationItemSelected(3);
                    item.setIcon(R.drawable.ic_menu);
                    loadFrag(menuFragment, getString(R.string.menu_menu), fragmentManager);
                    return true;

                case R.id.settings:
                    SettingsFragment settingsFragment = new SettingsFragment();
                    navigationItemSelected(4);
                    item.setIcon(R.drawable.ic_settings);
                    loadFrag(settingsFragment, getString(R.string.menu_settings), fragmentManager);
                    return true;

            }
            return false;
        }
    };

    ImageView ivtes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivtes = findViewById(R.id.ivtes);
        mAdViewLayout = findViewById(R.id.adView);
        fragmentManager = getSupportFragmentManager();
        navigation = findViewById(R.id.navigation);
        navigation.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setItemIconTintList(null);
        Menu menu = navigation.getMenu();
        menu.findItem(R.id.home).setIcon(R.drawable.ic_store);
        HomeFragment homeFragment = new HomeFragment();
        loadFrag(homeFragment, getString(R.string.menu_store), fragmentManager);
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        Constants.USERID = loginUser.getId();
        apikey = getString(R.string.google_maps_key);

        ivtes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tes();
            }
        });

        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Constants.versionname = Objects.requireNonNull(packageInfo).versionName;


    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Check_version();
    }

    public void Check_version(){
        VersionChecker versionChecker = new VersionChecker(this);
        versionChecker.execute();
    }

    @Override
    public void onBackPressed() {
        int count = this.getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            if (mBackPressed + 2000 > System.currentTimeMillis()) {
                super.onBackPressed();
            } else {
                clickDone();

            }
        } else {
            super.onBackPressed();
        }
    }

    public void clickDone() {
        new AlertDialog.Builder(this, R.style.DialogStyle)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle(getString(R.string.app_name))
                .setMessage(getString(R.string.exit))
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }





    public void loadFrag(Fragment f1, String name, FragmentManager fm) {
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.Container, f1, name);
        ft.commit();
    }

    public void navigationItemSelected(int position) {
        previousSelect = position;
    }




}
