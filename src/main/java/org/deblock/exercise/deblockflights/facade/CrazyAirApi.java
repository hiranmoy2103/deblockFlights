package org.deblock.exercise.deblockflights.facade;

import org.deblock.exercise.deblockflights.mapper.CrazyAirMapper;
import org.deblock.exercise.deblockflights.domain.DeblockflightsResponse;
import org.deblock.exercise.common.crazyair.domain.CrazyAirResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Component
public class CrazyAirApi {

    @Value("${supplier.endpoint.flight.crazyAir}")
    private String crazyAirUrl;

    @Async
    public CompletableFuture<List<DeblockflightsResponse>> getFlights(String origin, String destination, String departureDate, String returnDate, String passengerCount) throws RestClientException {
        List<DeblockflightsResponse> listOfBusyFlight = new ArrayList<>();
        URI uri = UriComponentsBuilder
                .fromHttpUrl(crazyAirUrl)
                .queryParam("origin", origin)
                .queryParam("destination", destination)
                .queryParam("departureDate", departureDate)
                .queryParam("returnDate", returnDate)
                .queryParam("passengerCount", passengerCount)
                .build()
                .toUri();
        CrazyAirResponse[] responseEntity = new RestTemplate().getForEntity(uri, CrazyAirResponse[].class).getBody();
        Arrays.stream(responseEntity).forEach(item -> listOfBusyFlight.add(CrazyAirMapper.convertCrazyResponse(item)));
        return CompletableFuture.completedFuture(listOfBusyFlight);
    }
}
