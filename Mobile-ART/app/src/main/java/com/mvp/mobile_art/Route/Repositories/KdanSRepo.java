package com.mvp.mobile_art.Route.Repositories;

import com.mvp.mobile_art.Model.Responses.KdanSResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by jcla123ns on 11/08/17.
 */

public interface KdanSRepo {
    @Headers("Accept: application/json")
    @POST("api/critism/")
    Call<KdanSResponse> postSaran(@Body HashMap map);
}
