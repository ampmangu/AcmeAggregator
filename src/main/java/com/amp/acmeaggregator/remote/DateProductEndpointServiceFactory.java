package com.amp.acmeaggregator.remote;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;

import static com.amp.acmeaggregator.remote.FactoryCommons.makeGson;
import static com.amp.acmeaggregator.remote.FactoryCommons.makeRetrofit;

@Service
public class DateProductEndpointServiceFactory {

    //TODO To change it to proper config
    private static final String BASE_URL = "http://localhost:8084/services/AcmeDates/";

    @Bean
    public static DateProductEndpointService makeDateProductService() {
        return makeDateProductService(makeGson());
    }

    private static DateProductEndpointService makeDateProductService(Gson gson) {
        Retrofit retrofit = makeRetrofit(gson, BASE_URL);
        return retrofit.create(DateProductEndpointService.class);
    }
}
