package org.deblock.exercise.deblockflights.mapper;

import org.deblock.exercise.common.toughjet.domain.ToughJetResponse;
import org.deblock.exercise.deblockflights.domain.DeblockflightsResponse;
import org.deblock.exercise.enums.SuppliersName;

public class ToughJetMapper {
    private static final String SUPPLIER = SuppliersName.TOUGHJET.getSupplierName();

    public static DeblockflightsResponse convertToughResponse(ToughJetResponse response) {
        DeblockflightsResponse deblockflightsResponse = new DeblockflightsResponse();
        deblockflightsResponse.setAirline(response.getCarrier());
        deblockflightsResponse.setSupplier(SUPPLIER);
        deblockflightsResponse.setFare(calculateFinalPrice(response));
        deblockflightsResponse.setDepartureAirportCode(response.getDepartureAirportName());
        deblockflightsResponse.setDestinationAirportCode(response.getArrivalAirportName());
        deblockflightsResponse.setDepartureDate(response.getOutboundDateTime());
        deblockflightsResponse.setArrivalDate(response.getInboundDateTime());
        return deblockflightsResponse;
    }

    private static double calculateFinalPrice(ToughJetResponse response) {
        double price = response.getBasePrice() + response.getTax();
        double finalPrice = price - (price * response.getDiscount() / 100);
        return finalPrice;
    }
}
