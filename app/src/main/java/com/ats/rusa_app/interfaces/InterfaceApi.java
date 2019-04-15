package com.ats.rusa_app.interfaces;


import com.ats.rusa_app.model.AppToken;
import com.ats.rusa_app.model.Baner;
import com.ats.rusa_app.model.CompanyModel;
import com.ats.rusa_app.model.ContactUs;
import com.ats.rusa_app.model.Detail;
import com.ats.rusa_app.model.EventRegistration;
import com.ats.rusa_app.model.FeedbackSave;
import com.ats.rusa_app.model.GallaryDetailList;
import com.ats.rusa_app.model.Gallery;
import com.ats.rusa_app.model.GetGalleryCategory;
import com.ats.rusa_app.model.Info;
import com.ats.rusa_app.model.Login;
import com.ats.rusa_app.model.MenuModel;
import com.ats.rusa_app.model.NewDetail;
import com.ats.rusa_app.model.OTPVerification;
import com.ats.rusa_app.model.PageData;
import com.ats.rusa_app.model.PrevEvent;
import com.ats.rusa_app.model.PrevEventFeedback;
import com.ats.rusa_app.model.PreviousEvent;
import com.ats.rusa_app.model.PreviousRecord;
import com.ats.rusa_app.model.Reg;
import com.ats.rusa_app.model.ResendOTP;
import com.ats.rusa_app.model.Testomonial;
import com.ats.rusa_app.model.UpcomingEvent;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface InterfaceApi {

    @POST("getTopMenuList")
    Call<MenuModel> getMenuData(@Query("langId") int langId, @Query("type") int type);

    @POST("getDataBySlugName")
    Call<PageData> getPageData(@Query("slugName") String slugName, @Query("langId") int langId);

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

    @POST("savePreviousRecord")
    Call<PreviousRecord> savePreviousRecord(@Body PreviousRecord previousRecord);

    @POST("getRegUserbyRegId")
    Call<Reg> getRegUserbyRegId(@Query("regId") int regId);
    //saveRegistration

    @POST("saveAppTokens")
    Call<AppToken> saveAppToken(@Body AppToken appToken);

    @POST("verifyOtpResponse")
    Call<OTPVerification> verifyOtpResponse(@Query("userOtp") String userOtp, @Query("uuid") String uuid);

    @POST("getAllHomeData")
    Call<Detail> getAllHomeData(@Query("langId") int langId);

    @POST("verifyResendOtpResponse")
    Call<ResendOTP> verifyResendOtpResponse(@Query("uuid") String uuid);

    @POST("loginFrontEnd")
    Call<Login> getLogin(@Query("userName") String userName, @Query("password") String password);

    @POST("forgetPassword")
    Call<Login> getForgotPass(@Query("email") String email, @Query("mobileNumber") String mobileNumber);

    @POST("getAllUpcomingEvents")
    Call<ArrayList<UpcomingEvent>> getUpcomingEvent(@Query("langId") int langId);

    @POST("getAllPreviousEvents")
    Call<ArrayList<PreviousEvent>> getPreviousEvent(@Query("langId") int langId);

    @POST("saveEventRegister")
    Call<EventRegistration> saveEventRegister(@Body EventRegistration eventRegistration);

    @POST("getAppliedEvents")
    Call<Info> getAppliedEvents(@Query("newsblogsId") int newsblogsId, @Query("userId") int userId);

    @POST("changePassword")
    Call<Info> changePassword(@Query("regId") int regId, @Query("password") String password);

    @POST("saveContactUs")
    Call<ContactUs> saveContactUs(@Body ContactUs contactUs);

    @POST("getAllCatIdBySectionIdOrderByDesc")
    Call<List<GetGalleryCategory>> getCatBySection(@Query("sectionId") int sectionId);

    @POST("getImages")
    Call<PageData> getGalleryData(@Query("slugName") String slugName, @Query("langId") int langId);

    @Multipart
    @POST("docUpload")
    Call<JSONObject> docUpload(@Part MultipartBody.Part filePath, @Part("docName") RequestBody docName);

    @POST("allPreviousEventWithApllied ")
    Call<ArrayList<PrevEvent>> getAllPreviousEventWithApllied(@Query("langId") int langId,@Query("userId") int userId);

    @POST("updateEventFeedback")
    Call<FeedbackSave> getUpdateEventFeedback(@Query("eventId") int eventId, @Query("userId") int userId,@Query("messge") String messge,@Query("value") int value);

    @POST("checkUniqueField")
    Call<Info> getCheckUniqueField(@Query("inputValue") String inputValue, @Query("valueType") int valueType,@Query("primaryKey") int primaryKey);

    @POST("getPrevRecordByRegId ")
    Call<PreviousRecord> getPrevRecordByRegId(@Query("regId") int regId);

    @POST("getFeedbackByUserIdAndNewsblogsId ")
    Call<PrevEventFeedback> getFeedbackByUserIdAndNewsblogsId(@Query("userId") int userId,@Query("newsblogsId") int newsblogsId);

    @POST("getCategoryListWithImageCount")
    Call<ArrayList<Gallery>> getCategoryListWithImageCount(@Query("sectionId") int sectionId,@Query("langId") int langId);

}
