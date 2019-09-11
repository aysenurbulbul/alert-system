package yte.intern.alertsystem.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import yte.intern.alertsystem.models.Alert;
import yte.intern.alertsystem.models.Result;
import yte.intern.alertsystem.repositories.AlertRepository;

import java.util.HashSet;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RequestServiceTest {

    @Mock
    AlertRepository alertRepository;

    @Spy
    RestTemplate restTemplate;

    @InjectMocks
    RequestService requestService;

    @Test
    public void sendRequest() {
        Alert alert1 = new Alert(null,"deneme", "https://www.google.com/", HttpMethod.GET, 5L, new HashSet<Result>());

        when(restTemplate.exchange("https://www.google.com/", HttpMethod.GET, null, String.class)).thenCallRealMethod();

        requestService.sendRequest(alert1);

        verify(restTemplate, times(2)).exchange("https://www.google.com/", HttpMethod.GET, null, String.class);
        verify(alertRepository, times(1)).save(any());

    }
}