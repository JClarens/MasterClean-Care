package com.mvp.mobile_art.Route.Repositories;


import com.mvp.mobile_art.Model.Responses.WalletTransactionResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Zackzack on 20/07/2017.
 */

public interface WalletTransactionRepo {
    @Headers("Accept: application/json")
    @POST("api/wallet_transaction/")
    Call<WalletTransactionResponse> postwallettransaction(@Body HashMap map);
}
