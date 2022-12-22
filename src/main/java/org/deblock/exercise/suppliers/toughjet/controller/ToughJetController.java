package org.deblock.exercise.suppliers.toughjet.controller;

import org.deblock.exercise.common.toughjet.domain.ToughJetResponse;
import org.deblock.exercise.suppliers.toughjet.service.ToughJetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/toughjet")
public class ToughJetController {

    @Autowired
    ToughJetService toughJetService;

    @RequestMapping(value = "/flights", method = RequestMethod.GET)
    public ResponseEntity<List<ToughJetResponse>> getFlights(@RequestParam("from") @NotNull String from, @RequestParam("to") @NotNull String to, @RequestParam("outboundDate") @NotNull String outboundDate, @RequestParam("inboundDate") @NotNull String inboundDate, @RequestParam("numberOfAdults") @NotNull String numberOfAdults) {
        return new ResponseEntity<>(toughJetService.getFlights(from,to,outboundDate,inboundDate,numberOfAdults), HttpStatus.OK);
    }
}
