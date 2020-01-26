package com.amp.acmeaggregator.web.rest;

import com.amp.acmeaggregator.domain.DataRow;
import com.amp.acmeaggregator.remote.DateProductEndpointService;
import com.amp.acmeaggregator.remote.FeeEndpointService;
import com.amp.acmeaggregator.remote.ProductEndpointService;
import com.amp.acmeaggregator.remote.models.DateProductResponse;
import com.amp.acmeaggregator.remote.models.FeeResponse;
import com.amp.acmeaggregator.remote.models.ProductResponse;
import org.jetbrains.annotations.NotNull;
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
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

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
    public ResponseEntity<Object> getAggregationData() throws ParseException, IOException {
        List<DataRow> dataRows = new ArrayList<>();
        ResponseEntity<List<DateProductResponse>> dates = getDates();
        ResponseEntity<List<FeeResponse>> fees = getFees();
        ResponseEntity<List<ProductResponse>> products = getProducts();
        if (connectionsAreStablished(fees, dates, products)) {
            List<DateProductResponse> datesBody = dates.getBody();
            List<FeeResponse> feesBody = fees.getBody();
            List<ProductResponse> productBody = products.getBody();
            datesBody.forEach(dateProductResponse -> {
                String product = dateProductResponse.getName();
                String deliveryDate = dateProductResponse.getDeliveryDate();
                Optional<ProductResponse> optionalCategory = productBody.stream().filter(productResponse -> productResponse.getProduct().equalsIgnoreCase(product)).findAny();
                String category = "Generic";
                if (optionalCategory.isPresent()) {
                    category = optionalCategory.get().getCategory();
                }
                final String finalCategory = category;
                List<List<String>> feesList = feesBody.stream().filter(feeResponse -> feeResponse.getCategory().equalsIgnoreCase(finalCategory)).map(FeeResponse::getFees).collect(Collectors.toList());
                //TODO Using random for now, ask about quantity
                double amount = ThreadLocalRandom.current().nextDouble(0, 332000);
                List<String> properFees = chooseFee(amount, feesList);
                String feePercentage = properFees.get(2);
                Double feePercentageNumber, feeAmount;
                try {
                    feePercentageNumber = Double.parseDouble(feePercentage.replace("%","").replace(",","."));
                    feeAmount = (amount * feePercentageNumber) / 10;
                } catch (NumberFormatException nfe) {
                    feePercentageNumber = 0.0d;
                    feeAmount = 0.0d;
                    log.error("Wrong percentage format, skipping {}", feePercentage);
                }
                DataRow dataRow = new DataRow(deliveryDate, product, category, amount, feePercentageNumber, feeAmount);
                dataRows.add(dataRow);
            });
            log.info("Connections are made");
        }
        return new ResponseEntity<>(dataRows, HttpStatus.OK);
    }

    private boolean connectionsAreStablished(
        @NotNull ResponseEntity<List<FeeResponse>> fees, @NotNull ResponseEntity<List<DateProductResponse>> dates,
        @NotNull ResponseEntity<List<ProductResponse>> products) {
        return fees.getStatusCode().is2xxSuccessful() && fees.hasBody() && dates.getStatusCode().is2xxSuccessful() &&
            dates.hasBody() && products.getStatusCode().is2xxSuccessful() && products.hasBody();
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
    public ResponseEntity<List<FeeResponse>> getFees() throws IOException {
        Response<List<FeeResponse>> feeResponseResponse = feeEndpointService.getFees().execute();
        if (feeResponseResponse.isSuccessful()) {
            return new ResponseEntity<>(feeResponseResponse.body(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/data/dates")
    public ResponseEntity<List<DateProductResponse>> getDates() throws IOException {
        Response<List<DateProductResponse>> dateProductResponseResponse =
            dateProductEndpointService.getDateProducts().execute();
        if (dateProductResponseResponse.isSuccessful()) {
            return new ResponseEntity<>(dateProductResponseResponse.body(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public List<String> chooseFee(Double amount, List<List<String>> feesList) {
        for (List<String> feeList : feesList) {
            String min = feeList.get(0);
            double minimum = Double.parseDouble(min.replace(".", ""));
            String max = feeList.get(1);
            if (max.equalsIgnoreCase("unlimited")) {
                if (minimum < amount) {
                    return feeList;
                }
            } else {
                double maximum = Double.parseDouble(max.replace(".", ""));
                if (minimum < amount && amount < maximum) {
                    return feeList;
                }
            }
        }
        //Default one
        return feesList.get(0);
    }
}
