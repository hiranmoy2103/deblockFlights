package org.deblock.exercise.deblockflights.facade;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.deblock.exercise.deblockflights.domain.DeblockflightsResponse;

@Component
public class FlightFacade {

    @Autowired
    private CrazyAirApi crazyAirApi;
    @Autowired
    private ToughJetApi toughJetApi;

    public List<DeblockflightsResponse> getFlights(String origin, String destination, String departureDate, String returnDate, String numberOfPassengers) throws InterruptedException, java.util.concurrent.ExecutionException {
        List<DeblockflightsResponse> response = new ArrayList<>();
        response.addAll(crazyAirApi.getFlights(origin, destination, departureDate, returnDate, numberOfPassengers).get());
        response.addAll(toughJetApi.getFlights(origin, destination, departureDate, returnDate, numberOfPassengers).get());
        return response.stream().sorted(Comparator.comparing(DeblockflightsResponse::getFare)).collect(Collectors.toList());
    }
}
