package yte.intern.alertsystem.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpMethod;
import yte.intern.alertsystem.models.Alert;
import yte.intern.alertsystem.models.Result;
import yte.intern.alertsystem.repositories.AlertRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class AlertServiceTest {

    @Mock
    AlertRepository alertRepository;

    @InjectMocks
    AlertService alertService;

    /*@Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        alertService = new AlertService(alertRepository);
    }*/

    @Test
    public void getAlerts() {

        //given
        Alert alert = new Alert();
        List<Alert> alerts = new ArrayList<>();
        alerts.add(alert);

        //given(alertRepository.findAll()).willReturn(alerts);
        when(alertRepository.findAll()).thenReturn(alerts);

        //when
        List<Alert> allAlerts = alertService.getAlerts();

        //then
        then(alertRepository).should().findAll();
        assertEquals(alerts,allAlerts);
    }

    @Test
    public void getAlert() {

        //given
        Alert alert1 = new Alert();
        alert1.setName("deneme");
        alert1.setId(1L);

        ArgumentCaptor<String> nameArgumentCaptor = ArgumentCaptor.forClass(String.class);
        when(alertRepository.findByName(Mockito.any(String.class))).thenReturn(alert1);
        Alert found_alert=alertService.getAlert("deneme");
        verify(alertRepository).findByName(nameArgumentCaptor.capture());
        Assert.assertTrue(nameArgumentCaptor.getValue().equals("deneme"));
        Assert.assertEquals("deneme",found_alert.getName());
        Mockito.verifyNoMoreInteractions(alertRepository);
        verify(alertRepository, Mockito.times(1)).findByName("deneme");
    }

    @Test
    public void addAlert() {

        //given
        Alert alert = new Alert();
        alert.setId(1L);
        Optional<Alert> alertOptional = Optional.of(alert);
        //unnecessary stubbing - put lenient() or Silent
        when(alertRepository.findById(anyLong())).thenReturn(alertOptional);
        when(alertRepository.save(any(Alert.class))).thenReturn(alert);

        //when
        Alert resultAlert = alertService.addAlert(alert);

        //then
        assertNotNull("Alert cannot be null", resultAlert);
        verify(alertRepository, times(1)).save(any());
    }

    @Test
    public void deleteAlert() {

        //given
        Long id = 1L;
        //unnecessary stubbing - put lenient(). or Silent
        when(alertRepository.existsById(id)).thenReturn(true);

        //when
        alertService.deleteAlert(id);

        //then
        verify(alertRepository,times(1)).deleteById(anyLong());
    }

    @Test
    public void updateAlert() {
        Alert alert1 = new Alert(1L, "deneme", "https://www.google.com/", HttpMethod.GET, 5L, new HashSet<Result>());
        Alert alert2 = new Alert(1L, "deneme", "https://www.google.com/", HttpMethod.POST, 5L, new HashSet<Result>());

        when(alertRepository.findByName(anyString())).thenReturn(alert1);

        when(alertRepository.save(any(Alert.class))).thenReturn(alert2);

        alertService.updateAlert(alert2);

        verify(alertRepository, times(1)).findByName(anyString());
        verify(alertRepository, times(1)).save(any(Alert.class));


    }
}