package com.ats.rusa_app.interfaces;


import com.ats.rusa_app.model.GallaryDetailList;
import com.ats.rusa_app.model.MenuModel;
import com.ats.rusa_app.model.NewDetail;
import com.ats.rusa_app.model.PageData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface InterfaceApi {


    @POST("getTopMenuList")
    Call<MenuModel> getMenuData(@Query("langId") int langId);

    @POST("getDataBySlugName")
    Call<PageData> getPageData(@Query("slugName") String slugName,@Query("langId") int langId);

    @POST("getLastFourNewsByLangId")
    Call<ArrayList<NewDetail>> getNewsData(@Query("langId") int langId);

    @GET("getLastTenPhotos")
    Call<ArrayList<GallaryDetailList>> getImageGallery();

}
