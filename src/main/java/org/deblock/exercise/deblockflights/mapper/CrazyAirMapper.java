package org.deblock.exercise.deblockflights.mapper;

import org.deblock.exercise.deblockflights.domain.DeblockflightsResponse;
import org.deblock.exercise.common.crazyair.domain.CrazyAirResponse;
import org.deblock.exercise.enums.SuppliersName;

public class CrazyAirMapper {
    private static final String SUPPLIER = SuppliersName.CRAZYAIR.getSupplierName();

    public static DeblockflightsResponse convertCrazyResponse(CrazyAirResponse response) {
        DeblockflightsResponse deblockflightsResponse = new DeblockflightsResponse();
        deblockflightsResponse.setAirline(response.getAirline());
        deblockflightsResponse.setSupplier(SUPPLIER);
        deblockflightsResponse.setFare(response.getPrice());
        deblockflightsResponse.setDepartureAirportCode(response.getDepartureAirportCode());
        deblockflightsResponse.setDestinationAirportCode(response.getDestinationAirportCode());
        deblockflightsResponse.setDepartureDate(response.getDepartureDate());
        deblockflightsResponse.setArrivalDate(response.getArrivalDate());
        return deblockflightsResponse;
    }
}
