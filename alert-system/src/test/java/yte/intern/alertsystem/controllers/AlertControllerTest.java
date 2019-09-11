package yte.intern.alertsystem.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import yte.intern.alertsystem.models.Alert;
import yte.intern.alertsystem.models.Result;
import yte.intern.alertsystem.repositories.AlertRepository;
import yte.intern.alertsystem.services.AlertService;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.Silent.class)
@AutoConfigureMockMvc
public class AlertControllerTest {


    @Mock
    AlertRepository alertRepository;

    @Mock
    AlertService alertService;

    @InjectMocks
    AlertController alertController;

    @Autowired
    MockMvc mockMvc;

    @Before
    public void dummy() {
        mockMvc = MockMvcBuilders.standaloneSetup(alertController).build();
    }

    @Test
    public void getAlerts() throws Exception{
        mockMvc.perform(get("/alerts")).andExpect(status().isOk());
        then(alertService).should().getAlerts();
    }

    @Test
    public void getAlert() throws Exception {
        Alert alert = new Alert();
        alert.setName("deneme");

        when(alertService.getAlert("deneme")).thenReturn(alert);

        mockMvc.perform(get("/alerts/{alertName}", "deneme")).andExpect(status().isOk());

        then(alertService).should().getAlert("deneme");
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void addAlert() throws Exception {
        Alert alert = new Alert();
        alert.setId(1L);
        alert.setName("deneme");

        when(alertService.addAlert(any(Alert.class))).thenReturn(alert);

        mockMvc.perform(post("/alerts")
        .content(asJsonString(new Alert()))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());

        then(alertService).should().addAlert(any(Alert.class));
    }

    //TODO
    @Test
    public void updateAlert() throws Exception {
        Alert alert1 = new Alert(1L, "deneme", "https://www.google.com/", HttpMethod.GET, 5L, new HashSet<Result>());
        Alert alert2 = new Alert(1L, "deneme", "https://dictionary.cambridge.org/", HttpMethod.POST, 5L, new HashSet<Result>());


        when(alertService.updateAlert(any(Alert.class))).thenReturn(alert2);

        mockMvc.perform(put("/updateAlert")
                .content(asJsonString(new Alert(1L, "deneme", "https://dictionary.cambridge.org/", HttpMethod.POST, 5L, new HashSet<Result>())))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.method").value("POST"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.url").value("https://dictionary.cambridge.org/"));

        then(alertService).should().updateAlert(any(Alert.class));
    }

    @Test
    public void deleteAlert() throws Exception{
        Alert alert = new Alert();
        alert.setId(1L);
        when(alertRepository.findById(anyLong())).thenReturn(Optional.of(alert));

        mockMvc.perform(delete("/deleteAlert/{alertID}",1L))
                .andExpect(status().isOk());

        then(alertService).should().deleteAlert(anyLong());
    }
}