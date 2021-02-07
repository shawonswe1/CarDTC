package com.r.cardtc.Api;

import com.r.cardtc.Model.CarList;
import com.r.cardtc.Model.Data;
import com.r.cardtc.Model.GetCar;
import com.r.cardtc.Model.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
////eta puraton ta
public interface MyRetrofit {

    @GET("/android/getCar.php")
    Call<List<GetCar>> getCar(@Query("country") String country);

    @GET("/android/getDetails.php")
    Call<List<model>> getDetails(@Query("cuid")String cuid,
                                        @Query("code")String code);
   

}
