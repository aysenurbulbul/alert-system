package yte.intern.alertsystem.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import yte.intern.alertsystem.models.Alert;
import yte.intern.alertsystem.models.Result;
import yte.intern.alertsystem.repositories.AlertRepository;

import java.text.SimpleDateFormat;

import java.util.Date;


@Service
@RequiredArgsConstructor
public class RequestService {

    private final AlertRepository alertRepository;
    private final RestTemplate restTemplate;


    @Async
    public void sendRequest(Alert alert){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String strDate = sdf.format(now);
        try {
            restTemplate.exchange(alert.getUrl(),
                    alert.getMethod(),
                    null,
                    String.class);
            alert.getResults().add(new Result(null,1, strDate));
        } catch (RestClientException e){
            alert.getResults().add(new Result(null,0, strDate));
        }
        //alert.setLifeTime(alert.getPeriod());
        alertRepository.save(alert);
    }
}


