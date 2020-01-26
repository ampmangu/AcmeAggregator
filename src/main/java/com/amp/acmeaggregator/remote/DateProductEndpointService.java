package com.amp.acmeaggregator.remote;

import com.amp.acmeaggregator.remote.models.DateProductResponse;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface DateProductEndpointService {
    @GET("api/dates")
    Call<List<DateProductResponse>> getDateProducts();
}
