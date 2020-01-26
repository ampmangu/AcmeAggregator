package com.amp.acmeaggregator.remote;

import com.amp.acmeaggregator.remote.models.ProductResponse;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface ProductEndpointService {
    @GET("api/products")
    Call<List<ProductResponse>> getProducts();
}
