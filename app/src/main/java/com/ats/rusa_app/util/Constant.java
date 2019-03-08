package com.ats.rusa_app.util;

import android.content.Context;
import android.content.res.Configuration;

import java.util.Locale;

/**
 * Created by V2STech on 26-07-2017.
 */

public class Constant
{
    public final static String APP_BASE_URL = "http://testapi712.mahaonlinegov.in/";
    public static int land_id = 1;

    public static String baseUrl = "http://49.248.163.138"; // test with port number 80

    static String strDomainName = baseUrl + "/";

    public static String GET_TALUKA_LIST = APP_BASE_URL + "api/GetTalukaList";
    public static String GET_DISTRICT = APP_BASE_URL + "api/GetDistrict";


    public static String SUB_CATEGORY_ONE_ONE = strDomainName + "mvd-service/mastercache/subcategories1/1";
    public static String SUB_CATEGORY_ONE_TWO = strDomainName + "mvd-service/mastercache/subcategories1/2";
    public static String SUB_CATEGORY_ONE_THREE = strDomainName + "mvd-service/mastercache/subcategories1/3";
    public static String SUB_CATEGORY_TWO = strDomainName + "mvd-service/mastercache/subcategories2/";
    public static String SUB_CATEGORY_THREE = baseUrl + "/mvd-service/mastercache/subcategories3/";//10";
    public static String RTO = strDomainName + "mvd-service/mastercache/rto";
    public static String BCP_URL = strDomainName + "mvd-service/mastercache/bcp";
    public static String GRIEVANCE_URL = baseUrl + "/mvd-service/grievance/";
    public static String MY_GRIEVANCE_URL = baseUrl + "/mvd-service/user/grievance/";
    public static String DYNAMIC_URL = baseUrl + "/mvd-service/grievance/dynamiclabel?";



    //---- your language
    public static void yourLanguage(Context context, String language)
    {

        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        //getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
        context.getResources().updateConfiguration(config,context.getResources().getDisplayMetrics());
    }

    /*public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, AndroidLocalize.class);
        startActivity(refresh);
        finish();
    }*/


}