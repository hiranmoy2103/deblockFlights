package org.deblock.exercise.deblockflights;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.deblock.exercise.deblockflights.domain.DeblockflightsResponse;
import org.deblock.exercise.deblockflights.service.DeblockflightsService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ExerciseApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DeblockflightsService deblockflightsService;

    @Test
    public void exceedsPassengerLimit() throws Exception {
        MvcResult result = mockMvc.perform(get("/deblockflights/search")
                        .param("origin", "DEL")
                        .param("destination", "LHR")
                        .param("departureDate", "2022-10-01")
                        .param("returnDate", "2022-10-10")
                        .param("numberOfPassengers", "5"))
                .andExpect(status().isBadRequest())
                .andReturn();

        Assert.assertEquals("searchFlights.numberOfPassengers: must be between 1 and 4", result.getResolvedException().getMessage());
    }

    @Test
    public void wrongAirportCode() throws Exception {
        MvcResult result = mockMvc.perform(get("/deblockflights/search")
                        .param("origin", "Dubai")
                        .param("destination", "LHR")
                        .param("departureDate", "2022-10-01")
                        .param("returnDate", "2022-10-10")
                        .param("numberOfPassengers", "3"))
                .andExpect(status().isBadRequest())
                .andReturn();
        Assert.assertEquals("searchFlights.origin: size must be between 3 and 3", result.getResolvedException().getMessage());
    }

    @Test
    public void validResponseTypeAndSize() throws Exception {
        when(deblockflightsService.searchFlights(any(), any(), any(), any(), any())).thenReturn(Arrays.asList(new DeblockflightsResponse()));
        MvcResult result = mockMvc.perform(get("/deblockflights/search")
                        .param("origin", "BOM")
                        .param("destination", "LHR")
                        .param("departureDate", "2022-10-01")
                        .param("returnDate", "2022-10-10")
                        .param("numberOfPassengers", "4"))
                .andExpect(status().isOk())
                .andReturn();
        TypeReference<List<DeblockflightsResponse>> typeReference = new TypeReference<List<DeblockflightsResponse>>() {
        };
        List<DeblockflightsResponse> flights = objectMapper.readValue(result.getResponse().getContentAsByteArray(), typeReference);
        Assert.assertEquals(1, flights.size());
    }
}
