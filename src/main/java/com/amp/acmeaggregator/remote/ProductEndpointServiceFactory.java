package com.amp.acmeaggregator.remote;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;

import static com.amp.acmeaggregator.remote.FactoryCommons.makeGson;
import static com.amp.acmeaggregator.remote.FactoryCommons.makeRetrofit;

@Service
public class ProductEndpointServiceFactory {
    //TODO To change it to proper config
    private static final String BASE_URL = "http://localhost:8082/services/AcmeCategories/";

    @Bean
    public static ProductEndpointService makeCategoryService() {
        return makeCategoryService(makeGson());
    }

    private static ProductEndpointService makeCategoryService(Gson gson) {
        Retrofit retrofit = makeRetrofit(gson, BASE_URL);
        return retrofit.create(ProductEndpointService.class);
    }
}
