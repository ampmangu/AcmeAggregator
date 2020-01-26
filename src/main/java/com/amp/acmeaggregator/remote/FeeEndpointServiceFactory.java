package com.amp.acmeaggregator.remote;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;

import static com.amp.acmeaggregator.remote.FactoryCommons.makeGson;
import static com.amp.acmeaggregator.remote.FactoryCommons.makeRetrofit;

@Service
public class FeeEndpointServiceFactory {
    //TODO To change it to proper config
    private static final String BASE_URL = "http://localhost:8083/services/AcmeFees/";

    @Bean
    public static FeeEndpointService makeFeeService() {
        return makeFeeService(makeGson());
    }

    private static FeeEndpointService makeFeeService(Gson gson) {
        Retrofit retrofit = makeRetrofit(gson, BASE_URL);
        return retrofit.create(FeeEndpointService.class);
    }
}
