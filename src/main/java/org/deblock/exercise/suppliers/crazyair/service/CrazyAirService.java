package org.deblock.exercise.suppliers.crazyair.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.deblock.exercise.common.crazyair.domain.CrazyAirResponse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CrazyAirService implements CommandLineRunner {

    List<CrazyAirResponse> crazyAirs;

    public List<CrazyAirResponse> getFlights(String origin, String destination, String departureDate, String returnDate, String passengerCount) {
        List<CrazyAirResponse> response = crazyAirs.stream()
                .filter(r -> r.getDepartureTrimDate().equals(departureDate))
                .filter(r -> r.getArrivalTrimDate().equals(returnDate))
                .filter(r -> r.getDepartureAirportCode().equals(origin))
                .filter(r -> r.getDestinationAirportCode().equals(destination))
                .collect(Collectors.toList());
        return response;
    }

    @Override
    public void run(String... arg0) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<CrazyAirResponse>> typeReference = new TypeReference<List<CrazyAirResponse>>() {
        };
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("flightsdata/CrazyAirs.json");
        try {
            crazyAirs = mapper.readValue(inputStream, typeReference);
        } catch (IOException e) {
            System.out.println("Unable to read CrazyAirs.json file " + e.getMessage());
            crazyAirs = new ArrayList<CrazyAirResponse>();
        }
    }
}
