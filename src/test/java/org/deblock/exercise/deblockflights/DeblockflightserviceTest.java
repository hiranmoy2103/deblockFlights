package org.deblock.exercise.deblockflights;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.deblock.exercise.deblockflights.exception.InconsistentDateException;
import org.deblock.exercise.enums.SuppliersName;
import org.deblock.exercise.deblockflights.facade.ToughJetApi;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.deblock.exercise.deblockflights.domain.DeblockflightsResponse;
import org.deblock.exercise.deblockflights.facade.CrazyAirApi;
import org.deblock.exercise.deblockflights.service.DeblockflightsService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("servicetest")
public class DeblockflightserviceTest {

    private static final double DELTA = 1e-15;

    @Autowired
    private DeblockflightsService deblockflightsService;

    @MockBean
    private CrazyAirApi crazyAirApi;
    @MockBean
    private ToughJetApi toughJetApi;

    static final double CRAZY_AIR_PRICE = 210.45;
    static final double TOUGH_JET_PRICE = 187.88;

    @Test
    public void flightService_shouldbeInjected() {
        assertNotNull(deblockflightsService);
    }

    @Before
    public void setup() {
        DeblockflightsResponse deblockflightsResponse = new DeblockflightsResponse();
        deblockflightsResponse.setSupplier(SuppliersName.CRAZYAIR.getSupplierName());
        deblockflightsResponse.setFare(CRAZY_AIR_PRICE);
        when(crazyAirApi.getFlights("CDG", "LHR", "01-08-2022", "10-08-2022", "2")).
                thenReturn(CompletableFuture.completedFuture(Arrays.asList(deblockflightsResponse)));

        DeblockflightsResponse deblockflightsResponse2 = new DeblockflightsResponse();
        deblockflightsResponse2.setSupplier(SuppliersName.TOUGHJET.getSupplierName());
        deblockflightsResponse2.setFare(TOUGH_JET_PRICE);
        when(toughJetApi.getFlights("CDG", "LHR", "01-08-2022", "10-08-2022", "2")).
                thenReturn(CompletableFuture.completedFuture(Arrays.asList(deblockflightsResponse2)));
    }

    @Test
    @DisplayName("When suppliers are provided then application returns aggregated output")
    public void searchFlight_whenSuppliersSearchesAreProvided_thenAggregateResultIntoListOfFlight() throws URISyntaxException, InterruptedException, java.util.concurrent.ExecutionException {
        final List<DeblockflightsResponse> flights = deblockflightsService.searchFlights("CDG", "LHR", "01-08-2022", "10-08-2022", "2");
        assertEquals(flights.size(), 2);
    }

    @Test
    @DisplayName("When suppliers are provided then application returns aggregated output ordered by fare in ASC order")
    public void searchFlight_whenSuppliersSearchesAreProvided_thenOrderResultByFareAsc() throws URISyntaxException, InterruptedException, java.util.concurrent.ExecutionException {
        final List<DeblockflightsResponse> flights = deblockflightsService.searchFlights("CDG", "LHR", "01-08-2022", "10-08-2022", "2");

        assertEquals(flights.get(0).getSupplier(), SuppliersName.TOUGHJET.getSupplierName());
        assertEquals(flights.get(0).getFare(), TOUGH_JET_PRICE, DELTA);

        assertEquals(flights.get(1).getSupplier(), SuppliersName.CRAZYAIR.getSupplierName());
        assertEquals(flights.get(1).getFare(), CRAZY_AIR_PRICE, DELTA);
    }

    @Test
    @DisplayName("When return date is less than departure date")
    public void returnDateLessThanDepartureDate() throws URISyntaxException {
        Throwable exception = Assertions.assertThrows(
                InconsistentDateException.class, () -> {
                    deblockflightsService.searchFlights("CDG", "LHR", "10-08-2022", "01-08-2022", "2");
                }
        );
        assertEquals("Either entered dates are in wrong format or the return date is less than the departure date, expected in dd-MM-yyyy", exception.getMessage());
    }

    @Test
    @DisplayName("When return date format is not expected")
    public void dateFormatInvalid() {
        Throwable exception = Assertions.assertThrows(
                InconsistentDateException.class, () -> {
                    deblockflightsService.searchFlights("CDG", "LHR", "01-AUG-2022", "10-AUG-2022", "3");
                }
        );
        assertEquals("Either entered dates are in wrong format or the return date is less than the departure date, expected in dd-MM-yyyy", exception.getMessage());
    }

}
