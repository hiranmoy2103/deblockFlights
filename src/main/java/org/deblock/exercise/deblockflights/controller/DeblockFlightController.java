package org.deblock.exercise.deblockflights.controller;

import java.net.URISyntaxException;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.deblock.exercise.deblockflights.domain.DeblockflightsResponse;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.deblock.exercise.deblockflights.service.DeblockflightsService;

@RestController
@RequestMapping("/deblockflights")
@Validated
public class DeblockFlightController {

    @Autowired
    DeblockflightsService deblockflightsService;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<List<DeblockflightsResponse>> searchFlights(@RequestParam("origin") @NotNull @Size(min = 3, max = 3) String origin, @RequestParam("destination") @NotNull @Size(min = 3, max = 3) String destination, @RequestParam("departureDate") @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String departureDate, @RequestParam("returnDate") @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String returnDate, @RequestParam("numberOfPassengers") @NotNull @Range(min = 1, max = 4) String numberOfPassengers) throws URISyntaxException, InterruptedException, java.util.concurrent.ExecutionException {
        return new ResponseEntity<>(deblockflightsService.searchFlights(origin, destination, departureDate, returnDate, numberOfPassengers), HttpStatus.OK);
    }
}
