package yte.intern.alertsystem.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpMethod;
import yte.intern.alertsystem.models.Alert;
import yte.intern.alertsystem.models.Result;
import yte.intern.alertsystem.repositories.AlertRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ScheduledServiceTest {

    @Mock
    AlertRepository alertRepository;

    @Mock
    RequestService requestService;

    @Mock
    Alert alert;

    @InjectMocks
    ScheduledService scheduledService;

    @Test
    public void scheduleAlerts() {
        Alert alert1 = new Alert(null,"deneme", "https://www.google.com/", HttpMethod.GET, 1L, new HashSet<Result>());
        List<Alert> alerts = new ArrayList<>();
        alerts.add(alert1);

        when(alertRepository.findAll()).thenReturn(alerts);

        scheduledService.scheduleAlerts();

        verify(alertRepository, times(1)).findAll();
        verify(requestService, times(1)).sendRequest(any());
    }
}