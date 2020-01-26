package com.amp.acmeaggregator.remote;

import com.amp.acmeaggregator.remote.models.FeeResponse;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface FeeEndpointService {
    @GET("api/fees")
    Call<List<FeeResponse>> getFees();
}
