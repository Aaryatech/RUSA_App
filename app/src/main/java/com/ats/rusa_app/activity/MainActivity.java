package com.ats.rusa_app.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.ats.rusa_app.R;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.fcm.SharedPrefManager;
import com.ats.rusa_app.fragment.ContactUsFragment;
import com.ats.rusa_app.fragment.ContentFragment;
import com.ats.rusa_app.fragment.HomeFragment;
import com.ats.rusa_app.fragment.NewContentFragment;
import com.ats.rusa_app.fragment.YoutubeThmbFragment;
import com.ats.rusa_app.model.AppToken;
import com.ats.rusa_app.model.CategoryList;
import com.ats.rusa_app.model.MenuGroup;
import com.ats.rusa_app.model.MenuModel;
import com.ats.rusa_app.model.TestImonialList;
import com.ats.rusa_app.util.CommonDialog;
import com.ats.rusa_app.util.Constant;
import com.ats.rusa_app.util.CustomSharedPreference;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //  Menu drawerMenu;

    boolean doubleBackToExitPressedOnce = false;
    public Locale locale;
    public Configuration config;
    String language;
    ArrayList<CategoryList> menuCatList = new ArrayList<>();

    private ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;

    ArrayList<MenuGroup> headerList = new ArrayList<>();
    HashMap<MenuGroup, ArrayList<MenuGroup>> childList = new HashMap<>();

    int languageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String langId = CustomSharedPreference.getString(MainActivity.this, CustomSharedPreference.LANGUAGE_SELECTED);
        try {
            languageId = Integer.parseInt(langId);
        } catch (Exception e) {
            languageId = 1;
        }

        String token = SharedPrefManager.getmInstance(MainActivity.this).getDeviceToken();
        Log.e("Token : ", "---------" + token);

        String str = CustomSharedPreference.getString(MainActivity.this, CustomSharedPreference.PREFERENCE_TOKEN);
        Gson gson2 = new Gson();
        AppToken appToken = gson2.fromJson(str, AppToken.class);
        Log.e("APP TOKEN", "-------------- " + appToken);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        if (appToken != null) {
            if (appToken.getApptokenId() > 0) {
                appToken.setToken(token);
                saveToken(appToken);
            }
        } else {
            AppToken appToken1 = new AppToken(0, "android", getMacAddress(), token, 0, sdf.format(Calendar.getInstance().getTimeInMillis()));
            saveToken(appToken1);
        }


        Log.e("MAC", "---------------- " + getMacAddress());


        // Toast.makeText(this, "" + languageId, Toast.LENGTH_SHORT).show();

        expandableListView = findViewById(R.id.expandableListView);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // drawerMenu = navigationView.getMenu();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, new HomeFragment(), "Exit");
        ft.commit();


        getMenuData(languageId);


    }

    @Override
    public void onBackPressed() {

        Fragment exit = getSupportFragmentManager().findFragmentByTag("Exit");
        Fragment homeFragment = getSupportFragmentManager().findFragmentByTag("HomeFragment");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (exit instanceof HomeFragment && exit.isVisible()) {

            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            doubleBackToExitPressedOnce = true;
            Toast.makeText(MainActivity.this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);


        } else if (homeFragment instanceof ContentFragment && homeFragment.isVisible() ||
                homeFragment instanceof NewContentFragment && homeFragment.isVisible() ||
                homeFragment instanceof ContactUsFragment && homeFragment.isVisible()) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new HomeFragment(), "Exit");
            ft.commit();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_english) {

            language = CustomSharedPreference.LANGUAGE_ENG;
            CustomSharedPreference.putString(MainActivity.this, CustomSharedPreference.LANGUAGE_ENG, CustomSharedPreference.LANGUAGE_ENG_ID);
            Constant.yourLanguage(MainActivity.this, language);

            CustomSharedPreference.putString(MainActivity.this, CustomSharedPreference.LANGUAGE_SELECTED, CustomSharedPreference.LANGUAGE_ENG_ID);

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            finish();
            startActivity(intent);

            return true;

        } else if (id == R.id.action_marathi) {
            language = CustomSharedPreference.LANGUAGE_MAR;
            CustomSharedPreference.putString(MainActivity.this, CustomSharedPreference.LANGUAGE_MAR, CustomSharedPreference.LANGUAGE_MAR_ID);
            Constant.yourLanguage(MainActivity.this, language);

            CustomSharedPreference.putString(MainActivity.this, CustomSharedPreference.LANGUAGE_SELECTED, CustomSharedPreference.LANGUAGE_MAR_ID);

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            finish();
            startActivity(intent);
            return true;
        } else if (id == R.id.action_contactUs) {
//            Intent intent=new Intent(getApplicationContext(), ContactUsActivity.class);
//            startActivity(intent);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new ContactUsFragment(), "HomeFragment");
            ft.commit();

            return true;
        }else if (id == R.id.action_notification) {
            Intent intent=new Intent(getApplicationContext(), NotificationActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);


    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        for (int i = 0; i < menuCatList.size(); i++) {
            if (item.toString().equalsIgnoreCase(menuCatList.get(i).getCatName())) {
                Log.e("MENU  : ", "------------- " + menuCatList.get(i).getPageId());
                Toast.makeText(this, "" + menuCatList.get(i).getCatName(), Toast.LENGTH_SHORT).show();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void getMenuData(int lnagId) {

        if (Constants.isOnline(this)) {
            final CommonDialog commonDialog = new CommonDialog(MainActivity.this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<MenuModel> listCall = Constants.myInterface.getMenuData(lnagId);
            listCall.enqueue(new Callback<MenuModel>() {
                @Override
                public void onResponse(Call<MenuModel> call, Response<MenuModel> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("MENU DATA : ", " - " + response.body());

                            MenuModel model = response.body();

                            menuCatList.clear();

                            for (int i = 0; i < model.getCategoryList().size(); i++) {
                                menuCatList.add(model.getCategoryList().get(i));
                            }

                            if (model.getSectionlist().size() > 0) {
                                for (int i = 0; i < model.getSectionlist().size(); i++) {
                                    // drawerMenu.add(model.getSectionlist().get(i).getSectionName());

                                    if (model.getCategoryList().size() > 0) {
                                        //   Menu topChannelMenu = drawerMenu.addSubMenu("" + model.getSectionlist().get(i).getSectionName());
                                        MenuGroup menuGroup;
                                        if (model.getSectionlist().get(i).getCatCount() == 0) {
                                            menuGroup = new MenuGroup(model.getSectionlist().get(i).getSectionName(), false, false, "" + model.getSectionlist().get(i).getSectionSlugname());
                                            headerList.add(menuGroup);
                                        } else {
                                            menuGroup = new MenuGroup(model.getSectionlist().get(i).getSectionName(), true, true, "");
                                            headerList.add(menuGroup);
                                        }
                                       /* if (!menuGroup.hasChildren) {
                                            childList.put(menuGroup, null);
                                        }*/

                                        ArrayList<MenuGroup> childModelsList = new ArrayList<>();


                                        for (int j = 0; j < model.getCategoryList().size(); j++) {
                                            if (model.getSectionlist().get(i).getSectionId() == model.getCategoryList().get(j).getSectionId()) {
                                                // topChannelMenu.add("" + model.getCategoryList().get(j).getCatName());

                                                MenuGroup childModel = new MenuGroup(model.getCategoryList().get(j).getCatName(), false, false, model.getCategoryList().get(j).getSlugName());
                                                childModelsList.add(childModel);

                                                if (model.getSubCatList().size() > 0) {
                                                    // SubMenu subChannelMenu;
                                                    for (int k = 0; k < model.getSubCatList().size(); k++) {
                                                        if (model.getCategoryList().get(j).getCatId() == model.getSubCatList().get(k).getParentId()) {
                                                            // subChannelMenu = topChannelMenu.addSubMenu("" + model.getCategoryList().get(j).getCatName());
                                                            MenuGroup childModel1 = new MenuGroup(model.getSubCatList().get(k).getSubCatName(), false, false, model.getSubCatList().get(k).getSubSlugName());
                                                            childModelsList.add(childModel1);
                                                        }

                                                    }
                                                }
                                            }
                                        }

                                        if (menuGroup.hasChildren) {
                                            Log.e("" + menuGroup.getMenuName(), "--------------");
                                            childList.put(menuGroup, childModelsList);
                                        }
                                    }

                                }
                            }

                            MenuGroup menuGroup1 = new MenuGroup(""+getResources().getString(R.string.str_login), false, false, "login");
                            headerList.add(menuGroup1);

                            MenuGroup menuGroup2 = new MenuGroup(""+getResources().getString(R.string.str_settings), true, true, "login");
                            headerList.add(menuGroup2);

                            ArrayList<MenuGroup> childModelsList = new ArrayList<>();

                            MenuGroup childModel1 = new MenuGroup(""+getResources().getString(R.string.strMenuLanguage), false, false, "lang");
                            childModelsList.add(childModel1);

                            childList.put(menuGroup2, childModelsList);


                            populateExpandableList();

                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<MenuModel> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(MainActivity.this, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }

    private void populateExpandableList() {

        expandableListAdapter = new com.ats.rusa_app.adapter.ExpandableListAdapter(this, headerList, childList);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (headerList.get(groupPosition).isGroup) {
                    if (!headerList.get(groupPosition).hasChildren) {
                    }
                }

                if (!headerList.get(groupPosition).isGroup) {
                    Log.e("Header ------------- ", " ---" + headerList.get(groupPosition).getUrl());

                    String url = headerList.get(groupPosition).getUrl();

                    if (url.equalsIgnoreCase("contact-us8")) {
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, new ContactUsFragment(), "HomeFragment");
                        ft.commit();

                    }
                    if (url.equalsIgnoreCase("login")) {
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));

                    } else {

                        Fragment adf = new NewContentFragment();
                        Bundle args = new Bundle();
                        args.putString("slugName", url);
                        adf.setArguments(args);
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "HomeFragment").commit();
                    }

                    Toast.makeText(MainActivity.this, "" + url, Toast.LENGTH_SHORT).show();

                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);

                }

                return false;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                if (childList.get(headerList.get(groupPosition)) != null) {
                    MenuGroup model = childList.get(headerList.get(groupPosition)).get(childPosition);
                    if (model.getUrl().equalsIgnoreCase("lang")) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle(getResources().getString(R.string.strLanguageChoose))
                                .setItems(R.array.lauguage, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int pos) {
                                        // The 'which' argument contains the index position
                                        // of the selected item
                                        if (pos == 0) {
                                            Constant.yourLanguage(MainActivity.this, CustomSharedPreference.LANGUAGE_ENG);
                                            CustomSharedPreference.putString(MainActivity.this, CustomSharedPreference.LANGUAGE_ENG, CustomSharedPreference.LANGUAGE_ENG_ID);

                                            //setLocale("ta");
                                        } else if (pos == 1) {
                                            Constant.yourLanguage(MainActivity.this, CustomSharedPreference.LANGUAGE_MAR);
                                            CustomSharedPreference.putString(MainActivity.this, CustomSharedPreference.LANGUAGE_MAR, CustomSharedPreference.LANGUAGE_MAR_ID);
                                            //setLocale("hi");
                                        }
                                        Intent refresh = new Intent(MainActivity.this, MainActivity.class);
                                        startActivity(refresh);
                                        finish();
                                    }
                                });
                        builder.create();
                        builder.show();

                    } else {
//                        WebView webView = findViewById(R.id.webView);
//                        webView.loadUrl(model.url);
//                        onBackPressed();

                        Fragment adf = new NewContentFragment();
                        Bundle args = new Bundle();
                        args.putString("slugName", model.getUrl());
                        adf.setArguments(args);
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "HomeFragment").commit();

                        Toast.makeText(MainActivity.this, "" + model.getUrl(), Toast.LENGTH_SHORT).show();

                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);
                    }
                }

                return false;
            }
        });
    }

    public String getMacAddress() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        return wInfo.getMacAddress();
    }

    private void saveToken(AppToken appToken) {

        if (Constants.isOnline(this)) {
            final CommonDialog commonDialog = new CommonDialog(MainActivity.this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<AppToken> listCall = Constants.myInterface.saveAppToken(appToken);
            listCall.enqueue(new Callback<AppToken>() {
                @Override
                public void onResponse(Call<AppToken> call, Response<AppToken> response) {
                    try {
                        if (response.body() != null) {

                            Gson gson1 = new Gson();
                            String str = gson1.toJson(response.body());

                            CustomSharedPreference.putString(MainActivity.this, CustomSharedPreference.PREFERENCE_TOKEN, str);

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<AppToken> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(MainActivity.this, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }



}
