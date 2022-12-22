package org.deblock.exercise.deblockflights.service;

import java.net.URISyntaxException;
import java.util.List;

import org.deblock.exercise.deblockflights.domain.DeblockflightsResponse;
import org.deblock.exercise.deblockflights.exception.InconsistentDateException;
import org.deblock.exercise.deblockflights.facade.FlightFacade;
import org.deblock.exercise.deblockflights.validator.CustomValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeblockflightsService {

    @Autowired
    FlightFacade facade;

    @Autowired
    private CustomValidator customValidator;

    public List<DeblockflightsResponse> searchFlights(String origin, String destination, String departureDate, String returnDate, String numberOfPassengers) throws URISyntaxException, InterruptedException, java.util.concurrent.ExecutionException {
        if (!customValidator.isValid(departureDate, returnDate)) {
            throw new InconsistentDateException("Either entered dates are in wrong format or the return date is less than the departure date, expected in dd-MM-yyyy");
        }
        return facade.getFlights(origin, destination, departureDate, returnDate, numberOfPassengers);
    }
}
