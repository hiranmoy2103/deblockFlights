package org.deblock.exercise.suppliers.crazyair;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.deblock.exercise.common.crazyair.domain.CrazyAirResponse;
import org.deblock.exercise.suppliers.crazyair.service.CrazyAirService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("crazytest")
public class CrazyAirIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CrazyAirService crazyAirService;

    @Test
    public void crazyAirValidApiTest() throws JsonProcessingException, Exception {
        MvcResult result = mockMvc.perform(get("/crazyair/flights")
                        .param("origin", "DEL")
                        .param("destination", "LHR")
                        .param("departureDate", "2022-08-01")
                        .param("returnDate", "2022-08-10")
                        .param("passengerCount", "3"))
                .andExpect(status().isOk())
                .andReturn();

        TypeReference<List<CrazyAirResponse>> typeReference = new TypeReference<List<CrazyAirResponse>>() {
        };
        List<CrazyAirResponse> flights = objectMapper.readValue(result.getResponse().getContentAsByteArray(), typeReference);

        Assert.assertNotNull(flights);
    }

}
