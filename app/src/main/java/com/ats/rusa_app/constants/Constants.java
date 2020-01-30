package com.ats.rusa_app.constants;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.widget.Toast;

import com.ats.rusa_app.interfaces.InterfaceApi;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Constants {


   //public static final String BASE_URL = "http://192.168.2.22:8094/";

  //  public static final String BASE_URL = "http://115.124.111.54:8080/RusaWebapi/";
    public static final String BASE_URL = "http://192.168.2.18:8094/";

    public static final String PDF_URL = "http://115.124.111.54:8080/mediarusa/pdf/";
    public static final String DOC_URL = "http://115.124.111.54:8080/mediarusa/userdocument/";
    public static final String GALLERY_URL = "http://115.124.111.54:8080/mediarusa/gallery/";
    public static final String BANENR_URL = "http://115.124.111.54:8080/mediarusa/banenr/";

    public static final String userName = "aaryatech";
    public static final String password = "Aaryatech@1cr";

    public static final String base = userName + ":" + password;
    public static final String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);


    public static OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .header("Accept", "application/json")
                            .method(original.method(), original.body())
                            .build();

                    Response response = chain.proceed(request);
                    return response;
                }
            })
            .readTimeout(10000, TimeUnit.SECONDS)
            .connectTimeout(10000, TimeUnit.SECONDS)
            .writeTimeout(10000, TimeUnit.SECONDS)
            .build();


    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create()).build();

    public static InterfaceApi myInterface = retrofit.create(InterfaceApi.class);

    public static boolean isOnline(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
            Toast.makeText(context, "No Internet Connection ! ", Toast.LENGTH_LONG).show();
            //new ConnectivityDialog(context).show();
            return false;
        }
        return true;
    }




}
