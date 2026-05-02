package com.ulp.vigiaespacialarg.database;

import com.ulp.vigiaespacialarg.model.IssResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IssService {
    @GET("satellites/{id}")
    Call<IssResponse> getSatelliteData(@Path("id") int id);
}