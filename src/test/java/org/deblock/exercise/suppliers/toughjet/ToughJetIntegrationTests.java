package org.deblock.exercise.suppliers.toughjet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.deblock.exercise.common.toughjet.domain.ToughJetResponse;
import org.deblock.exercise.suppliers.toughjet.service.ToughJetService;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("ToughJettest")
public class ToughJetIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ToughJetService toughJetService;

    @Test
    public void toughJetValidApiTest() throws JsonProcessingException, Exception {
        MvcResult result = mockMvc.perform(get("/toughjet/flights")
                        .param("from", "MAA")
                        .param("to", "LHR")
                        .param("outboundDate", "2022-08-01")
                        .param("inboundDate", "2022-08-10")
                        .param("numberOfAdults", "3"))
                .andExpect(status().isOk())
                .andReturn();

        TypeReference<List<ToughJetResponse>> typeReference = new TypeReference<List<ToughJetResponse>>() {
        };
        List<ToughJetResponse> flights = objectMapper.readValue(result.getResponse().getContentAsByteArray(), typeReference);

        Assert.assertNotNull(flights);
    }

}
