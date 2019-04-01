package com.ats.rusa_app.interfaces;


import com.ats.rusa_app.model.AppToken;
import com.ats.rusa_app.model.Baner;
import com.ats.rusa_app.model.CompanyModel;
import com.ats.rusa_app.model.ContactUs;
import com.ats.rusa_app.model.Detail;
import com.ats.rusa_app.model.EventRegCheck;
import com.ats.rusa_app.model.EventRegistration;
import com.ats.rusa_app.model.GallaryDetailList;
import com.ats.rusa_app.model.Info;
import com.ats.rusa_app.model.Login;
import com.ats.rusa_app.model.MenuModel;
import com.ats.rusa_app.model.NewDetail;
import com.ats.rusa_app.model.OTPVerification;
import com.ats.rusa_app.model.PageData;
import com.ats.rusa_app.model.PreviousEvent;
import com.ats.rusa_app.model.Reg;
import com.ats.rusa_app.model.ResendOTP;
import com.ats.rusa_app.model.Testomonial;
import com.ats.rusa_app.model.UpcomingEvent;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface InterfaceApi {

    @POST("getTopMenuList")
    Call<MenuModel> getMenuData(@Query("langId") int langId,@Query("type") int type);

    @POST("getDataBySlugName")
    Call<PageData> getPageData(@Query("slugName") String slugName,@Query("langId") int langId);

    @POST("getLastFourNewsByLangId")
    Call<ArrayList<NewDetail>> getNewsData(@Query("langId") int langId);

    @GET("getLastTenVideos")
    Call<ArrayList<GallaryDetailList>> getVideoGallery();

    @GET("getLastTenPhotos")
    Call<ArrayList<GallaryDetailList>> getImageGallery();

    @GET("getAllImageLinkList")
    Call<ArrayList<CompanyModel>> getCompSlider();

    @GET("getLastSliderImagesByStatus")
    Call<Baner> getBaner();

    @GET("getLastFiveTestImonials")
    Call<ArrayList<Testomonial>> getTestimonial();

    @POST("saveReg")
    Call<Reg> saveRegistration(@Body Reg registration);

    @POST("saveRegistration")
    Call<Reg> editProfile(@Body Reg registration);
    //saveRegistration

    @POST("saveAppTokens")
    Call<AppToken> saveAppToken(@Body AppToken appToken);

    @POST("verifyOtpResponse")
    Call<OTPVerification> verifyOtpResponse(@Query("userOtp") String userOtp,@Query("uuid") String uuid);

    @POST("getAllHomeData")
    Call<Detail> getAllHomeData(@Query("langId") int langId);

    @POST("verifyResendOtpResponse")
    Call<ResendOTP> verifyResendOtpResponse(@Query("uuid") String uuid);

    @POST("loginFrontEnd")
    Call<Login> getLogin(@Query("userName") String userName,@Query("password") String password);

    @POST("forgetPassword")
    Call<Login> getForgotPass(@Query("email") String email,@Query("mobileNumber") String mobileNumber);

    @POST("getAllUpcomingEvents")
    Call<ArrayList<UpcomingEvent>> getUpcomingEvent(@Query("langId") int langId);

    @POST("getAllPreviousEvents")
    Call<ArrayList<PreviousEvent>> getPreviousEvent(@Query("langId") int langId);

    @POST("saveEventRegister")
    Call<EventRegistration> saveEventRegister(@Body EventRegistration eventRegistration);

    @POST("getAppliedEvents")
    Call<ArrayList<EventRegCheck>> getAppliedEvents(@Query("newsblogsId") int newsblogsId,@Query("userId") int userId);

    @POST("changePassword")
    Call<Info> changePassword(@Query("regId") int regId,@Query("password") String password);

    @POST("saveContactUs")
    Call<ContactUs> saveContactUs(@Body ContactUs contactUs);
}
