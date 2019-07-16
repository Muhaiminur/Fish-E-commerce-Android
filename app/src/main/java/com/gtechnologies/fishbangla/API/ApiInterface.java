package com.gtechnologies.fishbangla.API;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.gtechnologies.fishbangla.API_SEND.Generate_Pin;
import com.gtechnologies.fishbangla.API_SEND.SEND_AUCTION_AMOUNT;
import com.gtechnologies.fishbangla.API_SEND.SEND_AUCTION_ID;
import com.gtechnologies.fishbangla.API_SEND.SEND_DISCOUNT;
import com.gtechnologies.fishbangla.API_SEND.SEND_TAG;
import com.gtechnologies.fishbangla.API_SEND.Send_Cart;
import com.gtechnologies.fishbangla.API_SEND.Send_Delivery_address;
import com.gtechnologies.fishbangla.API_SEND.Send_List;
import com.gtechnologies.fishbangla.API_SEND.Send_Product;
import com.gtechnologies.fishbangla.API_SEND.Send_Product_List;
import com.gtechnologies.fishbangla.API_SEND.Send_Ratting;
import com.gtechnologies.fishbangla.API_SEND.Send_Registration;
import com.gtechnologies.fishbangla.API_SEND.Send_Seller;
import com.gtechnologies.fishbangla.API_SEND.Send_Suggestion;
import com.gtechnologies.fishbangla.API_SEND.Send_Update_Profile;
import com.gtechnologies.fishbangla.API_SEND.Send_Upozilla;
import com.gtechnologies.fishbangla.API_SEND.Send_UserID;
import com.gtechnologies.fishbangla.API_SEND.Send_Zilla;
import com.gtechnologies.fishbangla.API_SEND.Verify_Pin;

import org.json.JSONObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiInterface {

    //1
    @Headers("Content-Type: application/json")
    @POST("/sendsms")
    Call<JsonElement> FishBangla_Send_PIN(@Body Generate_Pin generatePin);

    //2
    @Headers("Content-Type: application/json")
    @POST("/verify")
    Call<JsonElement> FishBangla_Verify_PIN(@Body Verify_Pin verifyPin);

    //3
    @Headers("Content-Type: application/json")
    @POST("/registration")
    Call<JsonElement> FishBangla_Registration(@Body Send_Registration sendRegistration);

    //4
    @Headers("Content-Type: application/json")
    @POST("/login")
    Call<JsonElement> FishBangla_Login(@Body Verify_Pin verifyPin);

    //5
    @Headers("Content-Type: application/json")
    @POST("/forgetpin")
    Call<JsonElement> FishBangla_Forget_Pin(@Body Generate_Pin forget_pin);

    //6
    //@Headers("Content-Type: application/json")
    @GET("/tag/list")
    Call<JsonElement> FishBangla_Tag_List();

    //7
    @Headers("Content-Type:application/json")
    @POST("/product/list/filter")
    Call<JsonElement> FishBangla_Fish_List(@Body Send_List send_list);


    //8
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @GET("/division/list")
    Call<JsonElement> FishBangla_Division_List();

    //9
    @Headers("Content-Type:application/json")
    @POST("/district/list")
    Call<JsonElement> FishBangla_zilla_List(@Body Send_Zilla s);

    //10
    @Headers("Content-Type:application/json")
    @POST("/upazilla/list")
    Call<JsonElement> FishBangla_Upozilla_List(@Body Send_Upozilla s);

    //11
    @Headers("Content-Type:application/json")
    @POST("/address/add")
    Call<JsonElement> FishBangla_Delivery_address_add(@Body Send_Delivery_address s);

    //12
    @Headers("Content-Type:application/json")
    @POST("/order/history/buy")
    Call<JsonElement> FishBangla_Buy_History(@Body Send_UserID u);

    //13
    @Headers("Content-Type:application/json")
    @POST("/rating/list")
    Call<JsonElement> FishBangla_Seller_ratting_List(@Body Send_Seller s);

    //14
    @Headers("Content-Type:application/json")
    @POST("/product/vendor")
    Call<JsonElement> FishBangla_Seller_Fish_List(@Body Send_UserID u);

    //15
    @Headers("Content-Type:application/json")
    @POST("/rating/add")
    Call<JsonElement> FishBangla_Seller_Rating(@Body Send_Ratting r);

    //16
    @Headers("Content-Type:application/json")
    @POST("/user/getprofile")
    Call<JsonElement> FishBangla_Seller_Profile(@Body Send_UserID u);

    //17
    @Headers("Content-Type:application/json")
    @POST("/product/suggestion")
    Call<JsonElement> FishBangla_Seller_Suggestion(@Body Send_Suggestion u);

    //18
    @Headers("Content-Type:application/json")
    @POST("/user/address/list")
    Call<JsonElement> FishBangla_Address_List(@Body Send_UserID u);

    //19
    @Headers("Content-Type:application/json")
    @POST("/order/add")
    Call<JsonElement> FishBangla_Order(@Body Send_Cart c);


    //20
    @Headers("Content-Type:application/json")
    @GET("/productlist/")
    Call<JsonElement> FishBangla_Fish_list();

    //21
    @Headers("Content-Type:application/json")
    @POST("/product")
    Call<JsonElement> FishBangla_Fish_add(@Body Send_Product sendProduct);

    //22
    @Headers("Content-Type:application/json")
    @POST("/user/referral/list")
    Call<JsonElement> FishBangla_Refer_List(@Body Send_UserID u);

    /*//23
    @Headers("Content-Type:application/json")
    @POST("/user/getprofile")
    Call<JsonElement> FishBangla_Seller_Profile(@Body Send_UserID u);*/


    //24
    @Headers("Content-Type:application/json")
    @GET("/auction/list")
    Call<ResponseBody> getAuctionList();

    //25
    @Headers("Content-Type:application/json")
    @POST("/auction/{auctionId}")
    Call<ResponseBody> addTopAuctionAmount(@Path("auctionId") String auctionId, @Body RequestBody jsonObject);

    //26
    @Headers("Content-Type:application/json")
    @GET("/productlist")
    Call<ResponseBody> getProductList();

    //27
    @Headers("Content-Type:application/json")
    @POST("/product/search")
    Call<JsonElement> getSearchList(@Body Send_Product_List send_product_list);

    //28
    //@Headers("Content-Type:application/json")
    @Multipart
    @POST("/product/image/{auctionId}")
    Call<JsonElement> FishBangla_Product_image_upload(@Path("auctionId") String auctionId, @Part MultipartBody.Part file);

    //28
    //@Headers("Content-Type:application/json")
    @Multipart
    @POST("/user/image/{auctionId}")
    Call<JsonElement> FishBangla_Profile_image_upload(@Path("auctionId") String auctionId, @Part MultipartBody.Part file);


    //29
    @Headers("Content-Type:application/json")
    @POST("/updateuserprofile")
    Call<JsonElement> FishBangla_Profile_Update(@Body Send_Update_Profile profile);

    //30
    @Headers("Content-Type:application/json")
    @POST("/discount/code")
    Call<JsonElement> FishBangla_Discount_Code(@Body SEND_DISCOUNT discount);

    //31
    @Headers("Content-Type:application/json")
    @POST("/auction/topamount")
    Call<JsonElement> FishBangla_Max_Auction(@Body SEND_AUCTION_ID auctionId);

    //32
    @Headers("Content-Type:application/json")
    @POST("/auction/participant")
    Call<JsonElement> FishBangla_Amount_Auction(@Body SEND_AUCTION_AMOUNT auctionId);

    //33
    @Headers("Content-Type:application/json")
    @POST("/product/saleType/tagId")
    Call<JsonElement> FishBangla_TAG_LIST(@Body SEND_TAG auctionId);


    //34
    @Headers("Content-Type:application/json")
    @GET("/config")
    Call<JsonElement> FishBangla_Config();

    //35
    @Multipart
    @POST("/product/video/{auctionId}")
    Call<JsonElement> FishBangla_Product_video_upload(@Path("auctionId") String auctionId, @Part MultipartBody.Part file);
}
