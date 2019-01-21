package com.maker.millionairekey.Rest;

import com.maker.millionairekey.Model.UserGetInfoResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("api/Register")
    Call<Example> getRegistration(@Body String body);

    @Headers("Content-Type: application/json")
    @POST("api/Setpassword")
    Call<Example> getcreatepassword(@Body String json);

    //  panding testing

    @FormUrlEncoded
    @POST("api/user/changepassword")
    Call<Example> getRestpassword(@Header("Authorization") String token, @Field("password") String newpassword, @Field("oldpassword") String oldpassword);

    @Headers("Content-Type: application/json")
    @POST("api/user/withdraw")
    Call<Example> getwithdrawamount(@Header("Authorization") String token,
                                    @Body String json);

    @GET("api/user/transaction")
    Call<Datum> GetAllTransaction(@Header("Authorization") String token);

    @GET("api/user/getdashboard")
    Call<Example> GetDashboard(@Header("Authorization") String token);

    @Headers("Content-Type: application/json")
    @POST("api/UpdatePayStatus")
    Call<Example> GetUpdatePayStatus(@Header("Authorization") String token);
    //  complete

    @FormUrlEncoded
    @POST("token")
    Call<Example> getloginuser(
            @Field("UserName") String referral_Id,
            @Field("Password") String password,
            @Field("grant_type") String ppassword);

    @Headers("Content-Type: application/json")
    @POST("api/user/GetLevelDetail")
    Call<Example> GetLevalListData(@Header("Authorization") String token, @Body String leval_id);

    @GET("api/user/GetUserInfo")
    Call<UserGetInfoResponse> GetAllUserInfo(@Header("Authorization") String token);

    @Headers("Content-Type: application/json")
    @POST("api/user/UpdateBank")
    Call<Example> getInsertBankinfo(@Header("Authorization") String token, @Body String json);

    @GET("api/GetQuestionList")
    Call<Datum> GetAllQuestion();

    @GET("api/GetNewsList")
    Call<Datum> GetAllNews();

    @Headers("Content-Type: application/json")
    @POST("api/ForgotPassword")
    Call<Datum> Postforgotpassword(@Body String json);

    @Headers("Content-Type: application/json")
    @POST("api/UpdatePayStatus")
    Call<Example> getRegistrationUpdateStatus(@Body String json);


    @GET("api/user/GetAchievementDetail")
    Call<AchievmentExample> GetAchievementListData(@Header("Authorization")String token);
}
