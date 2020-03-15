package com.example.babycareproject.activity.activity.activity;

import com.example.babycareproject.activity.activity.models.AdminEditProfilePojo;
import com.example.babycareproject.activity.activity.models.EditProfilePojo;
import com.example.babycareproject.activity.activity.models.NaniesNursesSchedulePojo;
import com.example.babycareproject.activity.activity.models.NanniesPojo;
import com.example.babycareproject.activity.activity.models.NursesPojo;
import com.example.babycareproject.activity.activity.models.ReviewRatingPojo;
import com.example.babycareproject.activity.activity.models.SchedulePojo;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface EndPointUrl {
    @GET("BabySitter/user_registeration.php?")
    Call<ResponseData> user_registration(
            @Query("name") String name,
            @Query("phone") String phone,
            @Query("emailid") String emailid,
            @Query("uname1") String uname1,
            @Query("pwd1") String pwd

    );

    @GET("/BabySitter/updateScheduleStatus.php")
    Call<ResponseData> updateScheduleStatus(@Query("id") String id, @Query("status") String status);

    @GET("/BabySitter/getNaniesNurseSchedule.php")
    Call<List<NaniesNursesSchedulePojo>> getNaniesNurseSchedule(
            @Query("schedule_nanies_nurse_uname") String schedule_nanies_nurse_uname
    );

    @GET("BabySitter/add_schedule.php?")
    Call<ResponseData> add_schedule(
            @Query("schedule_date") String schedule_date,
            @Query("schedule_time") String schedule_time,
            @Query("schedule_by") String schedule_by,
            @Query("nanies_nurse_name") String nanies_nurse_name,
            @Query("nanies_nurse_phone") String nanies_nurse_phone,
            @Query("nanies_nurse_email") String nanies_nurse_email,
            @Query("schedule_nanies_nurse_uname") String schedule_nanies_nurse_uname,
            @Query("user_type") String user_type
    );
    @GET("/BabySitter/getMySchdule.php")
    Call<List<SchedulePojo>> getMySchdule(@Query("schedule_by") String schedule_date);

    @Multipart
    @POST("BabySitter/user_reg_photo.php?")
    Call<ResponseData> user_registration1(
            @Part MultipartBody.Part file,
            @PartMap Map<String, String> partMap

    );


    @GET("BabySitter/user_login.php?")
    Call<ResponseData> user_login(
            @Query("uname") String uname,
            @Query("pwd") String pwd
    );

    @GET("/BabySitter/forgotPassword.php")
    Call<ResponseData> forgotPassword
            (
                    @Query("uname") String uname,
                    @Query("emailid") String emailid
            );

    @GET("BabySitter/caretaker_registration.php?")
    Call<ResponseData> nanies_nurse_registration(
            @Query("name") String name,
            @Query("phone") String phone,
            @Query("emailid") String emailid,
            @Query("uname1") String uname1,
            @Query("pwd1") String pwd,
            @Query("experience") String experience,
            @Query("user_type") String user_type,
            @Query("location") String location
    );

    @Multipart
    @POST("BabySitter/nanies_nurse_reg.php?")
    Call<ResponseData> nanies_nurse_registration1(
            @Part MultipartBody.Part file,
            @PartMap Map<String, String> partMap
    );


    @GET("BabySitter/caretaker_login.php?")
    Call<ResponseData> nanies_nurse_login(
            @Query("uname") String uname,
            @Query("pwd") String pwd
    );
    @GET("BabySitter/addReviewRating.php?")
    Call<ResponseData> review_rating(
            @Query("nanie_nurse_uname") String nanie_nurse_uname,
            @Query("uname") String uname,
            @Query("rating") String rating,
            @Query("review") String review
    );
    @GET("BabySitter/getUserProfile.php")
    Call<List<EditProfilePojo>> getUserProfile
            (
                    @Query("uname") String uname
            );


    @Multipart
    @POST("BabySitter/update_user_profile.php")
    Call<ResponseData> update_user_profile(
            @Part MultipartBody.Part file,
            @PartMap Map<String, String> partMap

    );

    @GET("BabySitter/getCareTakerProfile.php")
    Call<List<AdminEditProfilePojo>> getCareTakerProfile
            (
                    @Query("uname") String uname
            );

    @Multipart
    @POST("BabySitter/update_caretaker_profile.php")
    Call<ResponseData> admin_update_profile(
            @Part MultipartBody.Part file,
            @PartMap Map<String, String> partMap

    );
   /* @GET("BabySitter/update_caretaker_profile.php")
    Call<ResponseData> admin_update_profile(

            @Query("name") String name,
            @Query("phone") String phone,
            @Query("emailid") String emailid,
            @Query("uname1") String uname1,
            @Query("pwd1") String pwd1,
            @Query("experience") String experience,
            @Query("location") String location
    );*/

    @GET("BabySitter/getNanies.php")
    Call<List<NanniesPojo>> getNanies();

    @GET("/BabySitter/getNurses.php")
    Call<List<NursesPojo>> getNurses();

    @GET("BabySitter/getSearchData.php")
    Call<List<NanniesPojo>> getSearchData();

    @GET("BabySitter/getReviewRating.php")
    Call<List<ReviewRatingPojo>> getReviewRating(@Query("nuname") String uname);

    @GET("BabySitter/getReviewRating.php")
    Call<List<ReviewRatingPojo>> getnannisRevieew(@Query("nuname") String uname);
}
