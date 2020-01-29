package com.ats.rusa_app.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.rusa_app.R;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.fcm.SharedPrefManager;
import com.ats.rusa_app.fragment.ChangePasswordFragment;
import com.ats.rusa_app.fragment.ContactUsFragment;
import com.ats.rusa_app.fragment.ContentFragment;
import com.ats.rusa_app.fragment.EditProfileFragment;
import com.ats.rusa_app.fragment.EventFragment;
import com.ats.rusa_app.fragment.GalleryDisplayFragment;
import com.ats.rusa_app.fragment.GalleryEventDetailsFragment;
import com.ats.rusa_app.fragment.HomeFragment;
import com.ats.rusa_app.fragment.NewContentFragment;
import com.ats.rusa_app.fragment.PhotoGalleryFragment;
import com.ats.rusa_app.fragment.UpcomingEventDetailFragment;
import com.ats.rusa_app.fragment.UpcomingEventFragment;
import com.ats.rusa_app.fragment.UploadDocumentFragment;
import com.ats.rusa_app.fragment.VideoFragment;
import com.ats.rusa_app.model.AppToken;
import com.ats.rusa_app.model.CategoryList;
import com.ats.rusa_app.model.Login;
import com.ats.rusa_app.model.MenuGroup;
import com.ats.rusa_app.model.MenuModel;
import com.ats.rusa_app.model.UpdateToken;
import com.ats.rusa_app.sqlite.DatabaseHandler;
import com.ats.rusa_app.util.CommonDialog;
import com.ats.rusa_app.util.ConnectivityDialog;
import com.ats.rusa_app.util.Constant;
import com.ats.rusa_app.util.CustomSharedPreference;
import com.ats.rusa_app.util.PermissionsUtil;
import com.google.gson.Gson;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ats.rusa_app.constants.Constants.authHeader;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    //  Menu drawerMenu;

    boolean doubleBackToExitPressedOnce = false;
    public Locale locale;
    public Configuration config;
    String language;
    TextView tv_loginUserName;
    Login loginUser;
    public CommonDialog commonDialog;
    ArrayList<CategoryList> menuCatList = new ArrayList<>();
    Intent intent;
    private ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;

    ArrayList<MenuGroup> headerList = new ArrayList<>();
    HashMap<MenuGroup, ArrayList<MenuGroup>> childList = new HashMap<>();

    int languageId;
    DatabaseHandler dbHelper;

    private LinearLayout linearLayout_home, linearLayout_aboutUs, linearLayout_gallery, linearLayout_news, linearLayout_contact;

    int cacheSize = 10 * 1024 * 1024; // 10 MB

    File folder = new File(Environment.getExternalStorageDirectory() + File.separator, "RUSA_DOCS");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(""+getResources().getString(R.string.app_name));

        expandableListView = findViewById(R.id.expandableListView);

        dbHelper = new DatabaseHandler(MainActivity.this);

      /*  if( (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0){
            Log.e("FLAG_DEBUGGABLE","****************** TRUE");
        }else{
            Log.e("FLAG_DEBUGGABLE","****************** FALSE");
        }

        if(BuildConfig.DEBUG) {
            Log.e("FLAG_DEBUGGABLE","*********BuildConfig********* TRUE");
        }else{
            Log.e("FLAG_DEBUGGABLE","*********BuildConfig********* FALSE");
        }*/


        if (!Constants.isOnline(this)) {


            String newsStr1 = CustomSharedPreference.getString(MainActivity.this, CustomSharedPreference.KEY_HOME_NEWS);
            String compStr1 = CustomSharedPreference.getString(MainActivity.this, CustomSharedPreference.KEY_HOME_COMPANY);
            String homeStr1 = CustomSharedPreference.getString(MainActivity.this, CustomSharedPreference.KEY_HOME_DATA);

            if (homeStr1 == null && newsStr1 == null && compStr1 == null) {
                startActivity(new Intent(MainActivity.this, ConnectionCheckActivity.class));
                finish();
            } else {


                try {
                    displayOfflineData();
                } catch (Exception e) {
                    //Log.e("Offline--", "------------------------- ERROR - " + e.getMessage());
                   // e.printStackTrace();
                }

            }

            //startActivity(new Intent(MainActivity.this, ConnectionCheckActivity.class));
            //finish();
        }



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //setTitle("" + getResources().getString(R.string.app_name));

        if (PermissionsUtil.checkAndRequestPermissions(this)) {
        }

        createFolder();

        String langId = CustomSharedPreference.getString(MainActivity.this, CustomSharedPreference.LANGUAGE_SELECTED);
        try {
            languageId = Integer.parseInt(langId);
        } catch (Exception e) {
            languageId = 1;
        }

        linearLayout_home = findViewById(R.id.linearLayout_home);
        linearLayout_aboutUs = findViewById(R.id.linearLayout_aboutUs);
        linearLayout_gallery = findViewById(R.id.linearLayout_gallery);
        linearLayout_news = findViewById(R.id.linearLayout_news);
        linearLayout_contact = findViewById(R.id.linearLayout_contact);

        try {
            linearLayout_home.setOnClickListener(this);
            linearLayout_aboutUs.setOnClickListener(this);
            linearLayout_gallery.setOnClickListener(this);
            linearLayout_news.setOnClickListener(this);
            linearLayout_contact.setOnClickListener(this);

        } catch (Exception e) {
           // e.printStackTrace();
        }

//        String userStr = CustomSharedPreference.getString(getApplicationContext(), CustomSharedPreference.KEY_USER);
//        Gson gson = new Gson();
//        loginUser = gson.fromJson(userStr, Login.class);

        // String userStr = CustomSharedPreference.getString(getApplicationContext(), CustomSharedPreference.KEY_USER);

        try {
           // loginUser=null;
            loginUser = dbHelper.getLoginData();
           // Log.e("HOME_ACTIVITY : ", "--------USER-------" + loginUser);


        }catch (Exception e)
        {
            //e.printStackTrace();
        }


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


        try {
            String token = SharedPrefManager.getmInstance(MainActivity.this).getDeviceToken();
            //Log.e("Token : ", "---------" + token);

            String str = CustomSharedPreference.getString(MainActivity.this, CustomSharedPreference.PREFERENCE_TOKEN);
            Gson gson2 = new Gson();
            AppToken appToken = gson2.fromJson(str, AppToken.class);
            //Log.e("APP TOKEN", "-------------- " + appToken);

            if (appToken != null) {
                if (appToken.getApptokenId() > 0) {
                    if (loginUser != null) {
                        appToken.setToken(token);
                        appToken.setRegisterId(loginUser.getRegId());
                        saveToken(appToken);
                    } else {
                        appToken.setToken(token);
                        saveToken(appToken);
                    }

                }
            } else {
                if (loginUser != null) {
                    AppToken appToken1 = new AppToken(0, "android", getMacAddress(), token, loginUser.getRegId(), sdf.format(Calendar.getInstance().getTimeInMillis()));
                    saveToken(appToken1);
                } else {
                    AppToken appToken1 = new AppToken(0, "android", getMacAddress(), token, 0, sdf.format(Calendar.getInstance().getTimeInMillis()));
                    saveToken(appToken1);
                }
            }

        } catch (Exception e) {
            //Log.e("Exception : ", "-----------" + e.getMessage());
        }


        //Log.e("MAC", "---------------- " + getMacAddress());

        // Toast.makeText(this, "" + languageId, Toast.LENGTH_SHORT).show();


        try {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            View header = navigationView.getHeaderView(0);
            tv_loginUserName = header.findViewById(R.id.loginUserName);
        } catch (Exception e) {
           // e.printStackTrace();
        }

        try {
            intent = getIntent();
            String strFeedback = intent.getStringExtra("Feedback");
            //Log.e("Feedback", "-----------" + strFeedback);
            if (strFeedback.equals("Feedback")) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new EventFragment(), "HomeFragment");
                ft.commit();
            } else if (strFeedback.equalsIgnoreCase("fcm")) {
                startActivity(new Intent(MainActivity.this, NotificationActivity.class));
//                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.content_frame, new HomeFragment(), "Exit");
//                ft.commit();
            } else {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new HomeFragment(), "Exit");
                ft.commit();
            }

        } catch (Exception e) {
            //Log.e("MAIN ACT ", "------------ EXCEPTION----------------- " + e.getMessage());
           // e.printStackTrace();

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new HomeFragment(), "Exit");
            ft.commit();
        }

       /* try {
            intent = getIntent();
            final String skipLogin = intent.getStringExtra("code");
            //Log.e("Skip", "-----------" + skipLogin);
            if (skipLogin == (null)) {
                if (loginUser == null) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            } else if (loginUser == null) {
                if (!skipLogin.equals("SkipLogin")) {
                    //Log.e("Skip", "");
                }
            }
        } catch (Exception e) {
            //Log.e("MAIN ACT ", "------------ EXCEPTION----------------- " + e.getMessage());
           // e.printStackTrace();
        }*/

        try {
            if (loginUser != null) {
                tv_loginUserName.setText(loginUser.getName());
            } else {
                tv_loginUserName.setText("User is not logged in");
                // startActivity(new Intent(MainActivity.this, LoginActivity.class));
                // finish();
            }

        } catch (Exception e) {
            //Log.e("Exception User : ", "-----------" + e.getMessage());
        }

        // drawerMenu = navigationView.getMenu();


        getMenuData(languageId);

    }


    public void createFolder() {
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

    @Override
    public void onBackPressed() {

        Fragment exit = getSupportFragmentManager().findFragmentByTag("Exit");
        Fragment homeFragment = getSupportFragmentManager().findFragmentByTag("HomeFragment");
        Fragment upcomingEventFragment = getSupportFragmentManager().findFragmentByTag("UpcomingEventFragment");
        Fragment eventFragment = getSupportFragmentManager().findFragmentByTag("EventFragment");
        Fragment photoGalleryFragment = getSupportFragmentManager().findFragmentByTag("PhotoGalleryFragment");
        Fragment galleryEventDetailFragment = getSupportFragmentManager().findFragmentByTag("GalleryEventDetailFragment");


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
                homeFragment instanceof ContactUsFragment && homeFragment.isVisible() ||
                homeFragment instanceof UpcomingEventFragment && homeFragment.isVisible() ||
                homeFragment instanceof UpcomingEventDetailFragment && homeFragment.isVisible() ||
                homeFragment instanceof EventFragment && homeFragment.isVisible() ||
                homeFragment instanceof EditProfileFragment && homeFragment.isVisible() ||
                homeFragment instanceof ChangePasswordFragment && homeFragment.isVisible() ||
                homeFragment instanceof VideoFragment && homeFragment.isVisible() ||
                homeFragment instanceof PhotoGalleryFragment && homeFragment.isVisible() ||
                homeFragment instanceof UploadDocumentFragment && homeFragment.isVisible()) {


            if (!Constants.isOnline(MainActivity.this)) {

               /* Intent intent = new Intent(MainActivity.this, ConnectionCheckActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();*/

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new HomeFragment(), "Exit");
                ft.commit();

            } else {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new HomeFragment(), "Exit");
                ft.commit();
            }

        } else if (eventFragment instanceof UpcomingEventDetailFragment && eventFragment.isVisible()) {

            if (!Constants.isOnline(MainActivity.this)) {

               /* Intent intent = new Intent(MainActivity.this, ConnectionCheckActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();*/
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new HomeFragment(), "Exit");
                ft.commit();

            } else {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new EventFragment(), "HomeFragment");
                ft.commit();
            }

        } else if (photoGalleryFragment instanceof GalleryEventDetailsFragment && photoGalleryFragment.isVisible()) {

            if (!Constants.isOnline(MainActivity.this)) {

                /*Intent intent = new Intent(MainActivity.this, ConnectionCheckActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();*/
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new HomeFragment(), "Exit");
                ft.commit();

            } else {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new PhotoGalleryFragment(), "HomeFragment");
                ft.commit();
            }

        } else if (galleryEventDetailFragment instanceof GalleryDisplayFragment && galleryEventDetailFragment.isVisible()) {

            if (!Constants.isOnline(MainActivity.this)) {

                /*Intent intent = new Intent(MainActivity.this, ConnectionCheckActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();*/
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new HomeFragment(), "Exit");
                ft.commit();

            } else {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new GalleryEventDetailsFragment(), "PhotoGalleryFragment");
                ft.commit();
            }

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
        } else if (id == R.id.action_notification) {
            Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
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
                //Log.e("MENU  : ", "------------- " + menuCatList.get(i).getPageId());
                Toast.makeText(this, "" + menuCatList.get(i).getCatName(), Toast.LENGTH_SHORT).show();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void getMenuData(int lnagId) {


        //Log.e("MAIN_ACT"," *************************************** LANG - "+lnagId);

        if (Constants.isOnline(this)) {
            commonDialog = new CommonDialog(MainActivity.this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<MenuModel> listCall = Constants.myInterface.getMenuData(lnagId, 1, authHeader);
            listCall.enqueue(new Callback<MenuModel>() {
                @Override
                public void onResponse(Call<MenuModel> call, Response<MenuModel> response) {
                    try {
                        if (response.body() != null) {

                            //Log.e("MENU DATA : ", " - " + response.body());

                            MenuModel model = response.body();

                            Gson mGson = new Gson();
                            String mJson = mGson.toJson(model);
                            CustomSharedPreference.putString(MainActivity.this, CustomSharedPreference.KEY_MENU, mJson);

                            displayMenu(model);
                           /* menuCatList.clear();

                            for (int i = 0; i < model.getCategoryList().size(); i++) {
                                menuCatList.add(model.getCategoryList().get(i));
                            }

                            if (model.getSectionlist().size() > 0) {
                                for (int i = 0; i < model.getSectionlist().size(); i++) {
                                    // drawerMenu.add(model.getSectionlist().get(i).getSectionName());

                                    if (model.getCategoryList().size() > 0) {
                                        //   Menu topChannelMenu = drawerMenu.addSubMenu("" + model.getSectionlist().get(i).getSectionName());
                                        MenuGroup menuGroup = null;
                                        if (model.getSectionlist().get(i).getCatCount() == 0) {

                                            if (model.getSectionlist().get(i).getExternalUrl() != null) {
                                                Log.e("MAIN ACT", " *********************************    NOT  NULL");

                                                if (model.getSectionlist().get(i).getExternalUrl().equalsIgnoreCase("imgGallary")) {
                                                    menuGroup = new MenuGroup(model.getSectionlist().get(i).getSectionName(), false, false, false, "" + model.getSectionlist().get(i).getExternalUrl());
                                                } else {
                                                    menuGroup = new MenuGroup(model.getSectionlist().get(i).getSectionName(), false, false, false, "" + model.getSectionlist().get(i).getSectionSlugname());
                                                }
                                            } else {
                                                menuGroup = new MenuGroup(model.getSectionlist().get(i).getSectionName(), false, false, false, "" + model.getSectionlist().get(i).getSectionSlugname());
                                            }

                                            if (model.getSectionlist().get(i).getSectionName().equalsIgnoreCase("Rusa Gallery")) {
                                                menuGroup = new MenuGroup(model.getSectionlist().get(i).getSectionName(), false, false, false, "imgGallary");
                                            }

                                            headerList.add(menuGroup);

                                        }


//                                        menuGroup = new MenuGroup(model.getSectionlist().get(i).getSectionName(), false, false, false, "" + model.getSectionlist().get(i).getSectionSlugname());
//                                        headerList.add(menuGroup);
                                        else {
                                            menuGroup = new MenuGroup(model.getSectionlist().get(i).getSectionName(), true, true, false, "");
                                            headerList.add(menuGroup);
                                        }
                                       *//* if (!menuGroup.hasChildren) {
                                            childList.put(menuGroup, null);
                                        }*//*

                                        ArrayList<MenuGroup> childModelsList = new ArrayList<>();


                                        for (int j = 0; j < model.getCategoryList().size(); j++) {
                                            if (model.getSectionlist().get(i).getSectionId() == model.getCategoryList().get(j).getSectionId()) {
                                                // topChannelMenu.add("" + model.getCategoryList().get(j).getCatName());

                                                if (model.getCategoryList().get(j).getExternalUrl() != null) {
                                                    Log.e("MAIN ACT", " *********************************    NOT  NULL");

                                                    if (model.getCategoryList().get(j).getExternalUrl().equalsIgnoreCase("imgGallary")) {
                                                        MenuGroup childModel = new MenuGroup(model.getCategoryList().get(j).getCatName(), false, false, false, model.getCategoryList().get(j).getExternalUrl());
                                                        childModelsList.add(childModel);
                                                    } else {
                                                        MenuGroup childModel = new MenuGroup(model.getCategoryList().get(j).getCatName(), false, false, false, model.getCategoryList().get(j).getSlugName());
                                                        childModelsList.add(childModel);
                                                    }

                                                    if (model.getCategoryList().get(j).getCatName().equalsIgnoreCase("Rusa Gallery")) {
                                                        MenuGroup childModel = new MenuGroup(model.getCategoryList().get(j).getCatName(), false, false, false, "imgGallary");
                                                        childModelsList.add(childModel);
                                                    }

                                                } else {
                                                    MenuGroup childModel = new MenuGroup(model.getCategoryList().get(j).getCatName(), false, false, false, model.getCategoryList().get(j).getSlugName());
                                                    childModelsList.add(childModel);
                                                }

                                                //MenuGroup childModel = new MenuGroup(model.getCategoryList().get(j).getCatName(), false, false, false, model.getCategoryList().get(j).getSlugName());
                                                // childModelsList.add(childModel);

                                                if (model.getSubCatList().size() > 0) {
                                                    // SubMenu subChannelMenu;
                                                    for (int k = 0; k < model.getSubCatList().size(); k++) {
                                                        if (model.getCategoryList().get(j).getCatId() == model.getSubCatList().get(k).getParentId()) {
                                                            // subChannelMenu = topChannelMenu.addSubMenu("" + model.getCategoryList().get(j).getCatName());


                                                            if (model.getSubCatList().get(k).getExternalUrl() != null) {
                                                                Log.e("MAIN ACT", " *********************************    NOT  NULL");

                                                                if (model.getSubCatList().get(k).getExternalUrl().equalsIgnoreCase("imgGallary")) {
                                                                    MenuGroup childModel1 = new MenuGroup(model.getSubCatList().get(k).getSubCatName(), false, false, false, model.getSubCatList().get(k).getExternalUrl());
                                                                    childModelsList.add(childModel1);
                                                                } else {
                                                                    MenuGroup childModel1 = new MenuGroup(model.getSubCatList().get(k).getSubCatName(), false, false, false, model.getSubCatList().get(k).getSubSlugName());
                                                                    childModelsList.add(childModel1);
                                                                }
                                                            } else {
                                                                MenuGroup childModel1 = new MenuGroup(model.getSubCatList().get(k).getSubCatName(), false, false, false, model.getSubCatList().get(k).getSubSlugName());
                                                                childModelsList.add(childModel1);
                                                            }

//                                                            MenuGroup childModel1 = new MenuGroup(model.getSubCatList().get(k).getSubCatName(), false, false, false, model.getSubCatList().get(k).getSubSlugName());
//                                                            childModelsList.add(childModel1);

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

                            MenuGroup menuGroup = new MenuGroup("Event and Workshop", false, false, false, "Event");
                            headerList.add(menuGroup);

                            if (loginUser != null) {

                                MenuGroup menuGroup0 = new MenuGroup("Edit Profile", false, false, false, "Profile");
                                headerList.add(menuGroup0);

                                MenuGroup menuGroup1 = new MenuGroup("Upload Document", false, false, false, "uploadDoc");
                                headerList.add(menuGroup1);

//                                if(loginUser.getExInt1()!=1) {
//                                    MenuGroup menuGroup4 = new MenuGroup("Change Password", false, false, "changPass");
//                                    headerList.add(menuGroup4);
//                                }
                                MenuGroup menuGroup3 = new MenuGroup("Logout", false, false, false, "logout");
                                headerList.add(menuGroup3);

                            }

                            if (loginUser == null) {
                                MenuGroup menuGroup1 = new MenuGroup("" + getResources().getString(R.string.str_login), false, false, false, "login");
                                headerList.add(menuGroup1);
                            }

                            MenuGroup menuGroup2 = new MenuGroup("" + getResources().getString(R.string.str_settings), true, true, false, "Settings");
                            headerList.add(menuGroup2);



                            ArrayList<MenuGroup> childModelsList = new ArrayList<>();

                            MenuGroup childModel1 = new MenuGroup("" + getResources().getString(R.string.strMenuLanguage), false, false, false, "lang");
                            childModelsList.add(childModel1);

                            childList.put(menuGroup2, childModelsList);


                            populateExpandableList();*/

                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            //Log.e("Data Null : ", "-----------");
                        }
                    } catch (
                            Exception e) {
                        commonDialog.dismiss();
                        //Log.e("Exception : ", "-----------" + e.getMessage());
                      //  e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<MenuModel> call, Throwable t) {
                    commonDialog.dismiss();
                    //Log.e("onFailure : ", "-----------" + t.getMessage());
                   // t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(MainActivity.this, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }


    public void displayMenu(MenuModel model) {


        menuCatList.clear();

        for (int i = 0; i < model.getCategoryList().size(); i++) {
            menuCatList.add(model.getCategoryList().get(i));
        }

        if (model.getSectionlist().size() > 0) {
            for (int i = 0; i < model.getSectionlist().size(); i++) {
                // drawerMenu.add(model.getSectionlist().get(i).getSectionName());

                if (model.getCategoryList().size() > 0) {
                    //   Menu topChannelMenu = drawerMenu.addSubMenu("" + model.getSectionlist().get(i).getSectionName());
                    MenuGroup menuGroup = null;
                    if (model.getSectionlist().get(i).getCatCount() == 0) {

                        if (model.getSectionlist().get(i).getExternalUrl() != null) {
                            //Log.e("MAIN ACT", " *********************************    NOT  NULL");

                            if (model.getSectionlist().get(i).getExternalUrl().equalsIgnoreCase("imgGallary")) {
                                menuGroup = new MenuGroup(model.getSectionlist().get(i).getSectionName(), false, false, false, "" + model.getSectionlist().get(i).getExternalUrl());
                            } else {
                                menuGroup = new MenuGroup(model.getSectionlist().get(i).getSectionName(), false, false, false, "" + model.getSectionlist().get(i).getSectionSlugname());
                            }
                        } else {
                            menuGroup = new MenuGroup(model.getSectionlist().get(i).getSectionName(), false, false, false, "" + model.getSectionlist().get(i).getSectionSlugname());
                        }

                        if (model.getSectionlist().get(i).getSectionName().equalsIgnoreCase("Rusa Gallery")) {
                            menuGroup = new MenuGroup(model.getSectionlist().get(i).getSectionName(), false, false, false, "imgGallary");
                        }

                        headerList.add(menuGroup);

                    }


//                                        menuGroup = new MenuGroup(model.getSectionlist().get(i).getSectionName(), false, false, false, "" + model.getSectionlist().get(i).getSectionSlugname());
//                                        headerList.add(menuGroup);
                    else {
                        menuGroup = new MenuGroup(model.getSectionlist().get(i).getSectionName(), true, true, false, "");
                        headerList.add(menuGroup);
                    }
                                       /* if (!menuGroup.hasChildren) {
                                            childList.put(menuGroup, null);
                                        }*/

                    ArrayList<MenuGroup> childModelsList = new ArrayList<>();


                    for (int j = 0; j < model.getCategoryList().size(); j++) {
                        if (model.getSectionlist().get(i).getSectionId() == model.getCategoryList().get(j).getSectionId()) {
                            // topChannelMenu.add("" + model.getCategoryList().get(j).getCatName());

                            if (model.getCategoryList().get(j).getExternalUrl() != null) {
                                //Log.e("MAIN ACT", " *********************************    NOT  NULL");

                                if (model.getCategoryList().get(j).getExternalUrl().equalsIgnoreCase("imgGallary")) {
                                    MenuGroup childModel = new MenuGroup(model.getCategoryList().get(j).getCatName(), false, false, false, model.getCategoryList().get(j).getExternalUrl());
                                    childModelsList.add(childModel);
                                } else {
                                    MenuGroup childModel = new MenuGroup(model.getCategoryList().get(j).getCatName(), false, false, false, model.getCategoryList().get(j).getSlugName());
                                    childModelsList.add(childModel);
                                }

                                if (model.getCategoryList().get(j).getCatName().equalsIgnoreCase("Rusa Gallery")) {
                                    MenuGroup childModel = new MenuGroup(model.getCategoryList().get(j).getCatName(), false, false, false, "imgGallary");
                                    childModelsList.add(childModel);
                                }

                            } else {
                                MenuGroup childModel = new MenuGroup(model.getCategoryList().get(j).getCatName(), false, false, false, model.getCategoryList().get(j).getSlugName());
                                childModelsList.add(childModel);
                            }

                            //MenuGroup childModel = new MenuGroup(model.getCategoryList().get(j).getCatName(), false, false, false, model.getCategoryList().get(j).getSlugName());
                            // childModelsList.add(childModel);

                            if (model.getSubCatList().size() > 0) {
                                // SubMenu subChannelMenu;
                                for (int k = 0; k < model.getSubCatList().size(); k++) {
                                    if (model.getCategoryList().get(j).getCatId() == model.getSubCatList().get(k).getParentId()) {
                                        // subChannelMenu = topChannelMenu.addSubMenu("" + model.getCategoryList().get(j).getCatName());


                                        if (model.getSubCatList().get(k).getExternalUrl() != null) {
                                            //Log.e("MAIN ACT", " *********************************    NOT  NULL");

                                            if (model.getSubCatList().get(k).getExternalUrl().equalsIgnoreCase("imgGallary")) {
                                                MenuGroup childModel1 = new MenuGroup(model.getSubCatList().get(k).getSubCatName(), false, false, false, model.getSubCatList().get(k).getExternalUrl());
                                                childModelsList.add(childModel1);
                                            } else {
                                                MenuGroup childModel1 = new MenuGroup(model.getSubCatList().get(k).getSubCatName(), false, false, false, model.getSubCatList().get(k).getSubSlugName());
                                                childModelsList.add(childModel1);
                                            }
                                        } else {
                                            MenuGroup childModel1 = new MenuGroup(model.getSubCatList().get(k).getSubCatName(), false, false, false, model.getSubCatList().get(k).getSubSlugName());
                                            childModelsList.add(childModel1);
                                        }

//                                                            MenuGroup childModel1 = new MenuGroup(model.getSubCatList().get(k).getSubCatName(), false, false, false, model.getSubCatList().get(k).getSubSlugName());
//                                                            childModelsList.add(childModel1);

                                    }

                                }
                            }
                        }
                    }

                    if (menuGroup.hasChildren) {
                        //Log.e("" + menuGroup.getMenuName(), "--------------");
                        childList.put(menuGroup, childModelsList);
                    }
                }

            }
        }

        MenuGroup menuGroup = new MenuGroup(getResources().getString(R.string.str_event_and_workshop), false, false, false, "Event");
        headerList.add(menuGroup);

        if (loginUser != null) {

            MenuGroup menuGroup0 = new MenuGroup(getResources().getString(R.string.str_edit_profile), false, false, false, "Profile");
            headerList.add(menuGroup0);

            MenuGroup menuGroupchPass = new MenuGroup(getResources().getString(R.string.str_change_passs), false, false, false, "Profile");
            headerList.add(menuGroupchPass);

            MenuGroup menuGroup1 = new MenuGroup(getResources().getString(R.string.str_upload_document), false, false, false, "uploadDoc");
            headerList.add(menuGroup1);

//                                if(loginUser.getExInt1()!=1) {
//                                    MenuGroup menuGroup4 = new MenuGroup("Change Password", false, false, "changPass");
//                                    headerList.add(menuGroup4);
//                                }
            MenuGroup menuGroup3 = new MenuGroup(getResources().getString(R.string.str_logout), false, false, false, "logout");
            headerList.add(menuGroup3);

        }

        if (loginUser == null) {
            MenuGroup menuGroup1 = new MenuGroup("" + getResources().getString(R.string.str_login), false, false, false, "login");
            headerList.add(menuGroup1);
        }

        MenuGroup menuGroup2 = new MenuGroup("" + getResources().getString(R.string.str_settings), true, true, false, "Settings");
        headerList.add(menuGroup2);

//                            if(loginUser!=null) {
//
//                                MenuGroup menuGroup3 = new MenuGroup("Logout", false, false, "logout");
//                                headerList.add(menuGroup3);
//                            }

        ArrayList<MenuGroup> childModelsList = new ArrayList<>();

        MenuGroup childModel1 = new MenuGroup("" + getResources().getString(R.string.strMenuLanguage), false, false, false, "lang");
        childModelsList.add(childModel1);

        childList.put(menuGroup2, childModelsList);


        populateExpandableList();

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
                    //Log.e("Header ------------- ", " ---" + headerList.get(groupPosition).getUrl());

                    String url = headerList.get(groupPosition).getUrl();

                    if (headerList.get(groupPosition).menuName.equalsIgnoreCase("Contact Us")) {

                        if (!Constants.isOnline(MainActivity.this)) {

                            new ConnectivityDialog(MainActivity.this).show();

                        } else {
                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.content_frame, new ContactUsFragment(), "HomeFragment");
                            ft.commit();
                        }

                    } else if (url.equalsIgnoreCase("ContactUs")) {

                        if (!Constants.isOnline(MainActivity.this)) {

                            new ConnectivityDialog(MainActivity.this).show();

                        } else {

                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.content_frame, new ContactUsFragment(), "HomeFragment");
                            ft.commit();
                        }

                    } else if (url.equalsIgnoreCase("imgGallary")) {

                        if (!Constants.isOnline(MainActivity.this)) {

                            new ConnectivityDialog(MainActivity.this).show();

                        } else {

                           // Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.content_frame, new PhotoGalleryFragment(), "HomeFragment");
                            ft.commit();
                        }

                    } else if (headerList.get(groupPosition).getMenuName().equalsIgnoreCase("RUSA GALLERY")) {
                        //Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_SHORT).show();

                        if (!Constants.isOnline(MainActivity.this)) {

                            new ConnectivityDialog(MainActivity.this).show();

                        } else {


                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.content_frame, new PhotoGalleryFragment(), "HomeFragment");
                            ft.commit();

                        }

                    } else if (url.equalsIgnoreCase("login")) {

                        if (!Constants.isOnline(MainActivity.this)) {

                            new ConnectivityDialog(MainActivity.this).show();

                        } else {

                            startActivity(new Intent(MainActivity.this, LoginActivity.class));

                        }

                    } else if (url.equalsIgnoreCase("logout")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogTheme);
                        builder.setTitle("Logout");
                        builder.setMessage("Are you sure you want to logout?");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                updateToken(loginUser.getRegId());
//                                CustomSharedPreference.deletePreference(MainActivity.this);
//                                Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                startActivity(intent);
//                                finish();

                            }
                        });
                        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();


                    } else if (url.equalsIgnoreCase("Event")) {


                        if (!Constants.isOnline(MainActivity.this)) {

                            new ConnectivityDialog(MainActivity.this).show();

                        } else {

                            Fragment adf = new EventFragment();
                            Bundle args = new Bundle();
                            args.putString("slugName", url);
                            adf.setArguments(args);
                            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "HomeFragment").commit();
                        }

                    } else if (url.equalsIgnoreCase("Profile")) {

                        if (!Constants.isOnline(MainActivity.this)) {

                            new ConnectivityDialog(MainActivity.this).show();

                        } else {

//                            Fragment adf = new EditProfileFragment();
//                            Bundle args = new Bundle();
//                            args.putString("slugName", url);
//                            adf.setArguments(args);
//                            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "HomeFragment").commit();

                            Intent intent = new Intent(MainActivity.this, ChangePasswordActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);

                        }
                    } else if (url.equalsIgnoreCase("uploadDoc")) {

                        if (!Constants.isOnline(MainActivity.this)) {

                            new ConnectivityDialog(MainActivity.this).show();

                        } else {

                            Fragment adf = new UploadDocumentFragment();
                            Bundle args = new Bundle();
                            args.putString("slugName", url);
                            adf.setArguments(args);
                            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "HomeFragment").commit();

                        }
                    }
//                    else if(url.equalsIgnoreCase("changPass")) {
//                        Fragment adf = new ChangePasswordFragment();
//                        Bundle args = new Bundle();
//                        args.putString("slugName", url);
//                        adf.setArguments(args);
//                        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "HomeFragment").commit();
//                    }
                    else {

                        if (!Constants.isOnline(MainActivity.this)) {

                            new ConnectivityDialog(MainActivity.this).show();

                        } else {

                            Fragment adf = new NewContentFragment();
                            Bundle args = new Bundle();
                            args.putString("slugName", url);
                            adf.setArguments(args);
                            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "HomeFragment").commit();
                        }

                    }

                    // Toast.makeText(MainActivity.this, "" + url, Toast.LENGTH_SHORT).show();

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
                                            CustomSharedPreference.putString(MainActivity.this, CustomSharedPreference.LANGUAGE_SELECTED, CustomSharedPreference.LANGUAGE_ENG_ID);


                                            //setLocale("ta");
                                        } else if (pos == 1) {
                                            Constant.yourLanguage(MainActivity.this, CustomSharedPreference.LANGUAGE_MAR);
                                            CustomSharedPreference.putString(MainActivity.this, CustomSharedPreference.LANGUAGE_MAR, CustomSharedPreference.LANGUAGE_MAR_ID);
                                            CustomSharedPreference.putString(MainActivity.this, CustomSharedPreference.LANGUAGE_SELECTED, CustomSharedPreference.LANGUAGE_MAR_ID);

                                            //setLocale("hi");
                                        }
                                        Intent refresh = new Intent(MainActivity.this, MainActivity.class);
                                        startActivity(refresh);
                                        finish();
                                    }
                                });
                        builder.create();
                        builder.show();

                    } else if (model.getUrl().equalsIgnoreCase("videos19")) {

                        if (!Constants.isOnline(MainActivity.this)) {

                            new ConnectivityDialog(MainActivity.this).show();

                        } else {

                            Fragment adf = new VideoFragment();
                            Bundle args = new Bundle();
                            args.putString("slugName", model.getUrl());
                            adf.setArguments(args);
                            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "HomeFragment").commit();
                        }

                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);

                    } else if (model.getUrl().equalsIgnoreCase("imgGallary")) {

                        if (!Constants.isOnline(MainActivity.this)) {

                            new ConnectivityDialog(MainActivity.this).show();

                        } else {

                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.content_frame, new PhotoGalleryFragment(), "HomeFragment");
                            ft.commit();
                        }

                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);

                    } else if (model.getMenuName().equalsIgnoreCase("RUSA GALLERY")) {

                        if (!Constants.isOnline(MainActivity.this)) {

                            new ConnectivityDialog(MainActivity.this).show();

                        } else {

                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.content_frame, new PhotoGalleryFragment(), "HomeFragment");
                            ft.commit();
                        }

                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);

                    } else {
//                        WebView webView = findViewById(R.id.webView);
//                        webView.loadUrl(model.url);
//                        onBackPressed();

                        //  Toast.makeText(MainActivity.this, "" + model.getUrl(), Toast.LENGTH_SHORT).show();

                        if (!Constants.isOnline(MainActivity.this)) {

                            new ConnectivityDialog(MainActivity.this).show();

                        } else {

                            Fragment adf = new NewContentFragment();
                            Bundle args = new Bundle();
                            args.putString("slugName", model.getUrl());
                            adf.setArguments(args);
                            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "HomeFragment").commit();
                        }

                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);
                    }
                }

                return false;
            }
        });
    }

    private void updateToken(Integer regId) {
      //  Log.e("PARAMETER","       REG ID   "+regId);
        if (Constants.isOnline(MainActivity.this)) {

            final CommonDialog commonDialog = new CommonDialog(MainActivity.this, "Loading", "Please Wait...");
            commonDialog.show();

            String token = "NULL";

            Call<UpdateToken> listCall = Constants.myInterface.updateToken(regId,token,authHeader);
            listCall.enqueue(new Callback<UpdateToken>() {
                @Override
                public void onResponse(Call<UpdateToken> call, Response<UpdateToken> response) {
                   // Log.e("RESPONCE","----------------------UPDATE TOKEN-------------------"+response.body());
                    try {
                        if (response.body() != null) {
                            if(!response.body().getError()) {

                                dbHelper.deleteData("user_data");
                                loginUser=null;
                                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();

                            }else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogTheme);
                                builder.setTitle("Alert");
                                builder.setMessage("" + response.body().getMsg());
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog dialog = builder.create();
                                dialog.show();

                            }

                            commonDialog.dismiss();
                        }else {
                            commonDialog.dismiss();
                            //Log.e("Data Null : ", "-----------");
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        //Log.e("Exception : ", "-----------" + e.getMessage());
                        //  e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<UpdateToken> call, Throwable t) {
                    commonDialog.dismiss();
                    //Log.e("onFailure : ", "-----------" + t.getMessage());
                    // t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(MainActivity.this, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
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

            Call<AppToken> listCall = Constants.myInterface.saveAppToken(appToken, authHeader);
            listCall.enqueue(new Callback<AppToken>() {
                @Override
                public void onResponse(Call<AppToken> call, Response<AppToken> response) {
                    try {
                        if (response.body() != null) {

                            Gson gson1 = new Gson();
                            String str = gson1.toJson(response.body());

                            CustomSharedPreference.putString(MainActivity.this, CustomSharedPreference.PREFERENCE_TOKEN, str);
                            commonDialog.dismiss();
                        } else {
                            commonDialog.dismiss();
                            //Log.e("Data Null : ", "-----------");
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        //Log.e("Exception : ", "-----------" + e.getMessage());
                      //  e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<AppToken> call, Throwable t) {
                    commonDialog.dismiss();
                    //Log.e("onFailure : ", "-----------" + t.getMessage());
                   // t.printStackTrace();
                }
            });
        } else {
            //  Toast.makeText(MainActivity.this, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.linearLayout_home) {

            if (!Constants.isOnline(this)) {

                new ConnectivityDialog(MainActivity.this).show();

            } else {

                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }

//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.content_frame, new HomeFragment(), "Exit");
//            ft.commit();
        } else if (v.getId() == R.id.linearLayout_aboutUs) {

            if (!Constants.isOnline(this)) {

                new ConnectivityDialog(MainActivity.this).show();

            } else {
                Fragment adf = new NewContentFragment();
                Bundle args = new Bundle();
                args.putString("slugName", "about-rusa9");
                adf.setArguments(args);
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "HomeFragment").commit();
            }

        } else if (v.getId() == R.id.linearLayout_gallery) {

            if (!Constants.isOnline(this)) {

                new ConnectivityDialog(MainActivity.this).show();

            } else {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new PhotoGalleryFragment(), "HomeFragment");
                ft.commit();

            }

        } else if (v.getId() == R.id.linearLayout_news) {
//            Fragment adf = new NewContentFragment();
//            Bundle args = new Bundle();
//            args.putString("slugName", model.getUrl());
//            adf.setArguments(args);
//            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "HomeFragment").commit();

        } else if (v.getId() == R.id.linearLayout_contact) {

            if (!Constants.isOnline(this)) {

                new ConnectivityDialog(MainActivity.this).show();

            } else {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new ContactUsFragment(), "HomeFragment");
                ft.commit();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (commonDialog != null) {
            commonDialog.dismiss();
        }
    }


   /* public class ConnectivityDialog extends Dialog {

        public ConnectivityDialog(@NonNull Context context) {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_connectivity);
            setCancelable(false);

            Window window = getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER ;
            wlp.x = 10;
            wlp.y = 10;
            wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(wlp);

            TextView tvOk = findViewById(R.id.tvOk);

            tvOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

        }
    }*/


    public void displayOfflineData() {

        String menuStr = CustomSharedPreference.getString(MainActivity.this, CustomSharedPreference.KEY_MENU);
        Gson gson = new Gson();
        MenuModel model = gson.fromJson(menuStr, MenuModel.class);

        //Log.e("Offline--", "------------------------- MENU - " + model);
        displayMenu(model);


    }

}
