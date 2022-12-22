package org.deblock.exercise.suppliers.crazyair.controller;

import org.deblock.exercise.common.crazyair.domain.CrazyAirResponse;
import org.deblock.exercise.suppliers.crazyair.service.CrazyAirService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/crazyair")
public class CrazyAirController {

    @Autowired
    CrazyAirService crazyAirService;

    @RequestMapping(value = "/flights", method = RequestMethod.GET)
    public ResponseEntity<List<CrazyAirResponse>> getFlights(@RequestParam("origin") @NotNull String origin, @RequestParam("destination") @NotNull String destination, @RequestParam("departureDate") @NotNull String departureDate, @RequestParam("returnDate") @NotNull String returnDate, @RequestParam("passengerCount") @NotNull String passengerCount) {
        return new ResponseEntity<>(crazyAirService.getFlights(origin, destination, departureDate, returnDate, passengerCount), HttpStatus.OK);
    }
}
