package org.deblock.exercise.deblockflights.facade;

import org.deblock.exercise.common.toughjet.domain.ToughJetResponse;
import org.deblock.exercise.deblockflights.mapper.ToughJetMapper;
import org.deblock.exercise.deblockflights.domain.DeblockflightsResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Component
public class ToughJetApi {

    @Value("${supplier.endpoint.flight.toughJet}")
    private String toughJetUrl;

    @Async
    public CompletableFuture<List<DeblockflightsResponse>> getFlights(String from, String to, String outboundDate, String inboundDate, String numberOfAdults) {
        List<DeblockflightsResponse> listOfBusyFlight = new ArrayList<>();
        Map<String, String> params = new HashMap<>();
        params.put("from", from);
        params.put("to", to);
        params.put("outboundDate", outboundDate);
        params.put("inboundDate", inboundDate);
        params.put("numberOfAdults", numberOfAdults);
        URI uri = UriComponentsBuilder
                .fromHttpUrl(toughJetUrl)
                .queryParam("from", from)
                .queryParam("to", to)
                .queryParam("outboundDate", outboundDate)
                .queryParam("inboundDate", inboundDate)
                .queryParam("numberOfAdults", numberOfAdults)
                .build()
                .toUri();
        ToughJetResponse[] response = new RestTemplate().getForEntity(uri, ToughJetResponse[].class).getBody();
        Arrays.stream(response).forEach(item -> listOfBusyFlight.add(ToughJetMapper.convertToughResponse(item)));
        return CompletableFuture.completedFuture(listOfBusyFlight);
    }
}
