package org.deblock.exercise.suppliers.toughjet.services;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.deblock.exercise.common.toughjet.domain.ToughJetResponse;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import org.deblock.exercise.suppliers.toughjet.service.ToughJetService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("service2test")
public class ToughJetServiceTest {

    @Autowired
    private ToughJetService toughJetService;

    @Test
    public void crazyAirSearchTest() {
        List<ToughJetResponse> response = toughJetService.getFlights("CDG", "LHR", "01-08-2022", "10-08-2022", "2");

        Assert.notNull(response);
        assertThat(response, not(IsEmptyCollection.empty()));
        assertThat(response, hasSize(1));
    }

}
