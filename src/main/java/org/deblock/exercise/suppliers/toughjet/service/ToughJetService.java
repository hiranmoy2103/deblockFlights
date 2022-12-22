package org.deblock.exercise.suppliers.toughjet.service;

import org.deblock.exercise.common.toughjet.domain.ToughJetResponse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ToughJetService implements CommandLineRunner {

    List<ToughJetResponse> toughJets;

    public List<ToughJetResponse> getFlights(String from,String to,String outboundDate,String inboundDate,String numberOfAdults) {
        List<ToughJetResponse> response = toughJets.stream()
        .filter(r -> r.getDepartureAirportName().equals(from))
        .filter(r -> r.getArrivalAirportName().equals(to))
        .filter(r -> r.getOutboundTrimDate().equals(outboundDate))
        .filter(r -> r.getInboundTrimDate().equals(inboundDate))
        .collect(Collectors.toList());
        return response;
    }

    @Override
    public void run(String... arg0) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<ToughJetResponse>> typeReference = new TypeReference<List<ToughJetResponse>>() {
        };
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("flightsdata/ToughJets.json");
        try {
            toughJets = mapper.readValue(inputStream, typeReference);
        } catch (IOException e) {
            System.out.println("Unable to read ToughJets.json file " + e.getMessage());
            toughJets = new ArrayList<ToughJetResponse>();
        }
    }
}
