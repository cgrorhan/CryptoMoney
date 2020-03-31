package com.cagriorhan.kriptopara.services;

import com.cagriorhan.kriptopara.model.CryptoModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CryptoAPI {
    //https://api.nomics.com/v1/currencies/ticker?key=55b01e9bfb1247f09de0bf57c6fde250

    @GET("currencies/ticker?key=55b01e9bfb1247f09de0bf57c6fde250")
    Observable<List<CryptoModel>> getData();

}
