package com.amp.acmeaggregator.web.rest;

import com.amp.acmeaggregator.remote.DateProductEndpointService;
import com.amp.acmeaggregator.remote.FeeEndpointService;
import com.amp.acmeaggregator.remote.ProductEndpointService;
import com.amp.acmeaggregator.remote.models.DateProductResponse;
import com.amp.acmeaggregator.remote.models.FeeResponse;
import com.amp.acmeaggregator.remote.models.ProductResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AggregatorResource {
    private final Logger log = LoggerFactory.getLogger(AggregatorResource.class);

    private final ProductEndpointService productEndpointService;
    private final FeeEndpointService feeEndpointService;
    private final DateProductEndpointService dateProductEndpointService;

    AggregatorResource(ProductEndpointService productEndpointService, FeeEndpointService feeEndpointService,
                       DateProductEndpointService dateProductEndpointService) {
        this.productEndpointService = productEndpointService;
        this.feeEndpointService = feeEndpointService;
        this.dateProductEndpointService = dateProductEndpointService;
    }

    @GetMapping("/data")
    public ResponseEntity<Object> getAggregationData() {

        throw new UnsupportedOperationException();
    }

    @GetMapping("/data/products")
    public ResponseEntity<List<ProductResponse>> getProducts() throws IOException {
        Response<List<ProductResponse>> productResponseResponse = productEndpointService.getProducts().execute();
        if (productResponseResponse.isSuccessful()) {
            return new ResponseEntity<>(productResponseResponse.body(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/data/fees")
    public ResponseEntity<Object> getFees() throws IOException {
        Response<List<FeeResponse>> feeResponseResponse = feeEndpointService.getFees().execute();
        if (feeResponseResponse.isSuccessful()) {
            return new ResponseEntity<>(feeResponseResponse.body(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/data/dates")
    public ResponseEntity<Object> getDates() throws IOException {
        Response<List<DateProductResponse>> dateProductResponseResponse =
            dateProductEndpointService.getDateProducts().execute();
        if (dateProductResponseResponse.isSuccessful()) {
            return new ResponseEntity<>(dateProductResponseResponse.body(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
