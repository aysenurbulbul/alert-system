package yte.intern.alertsystem.services;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import yte.intern.alertsystem.models.Alert;
import yte.intern.alertsystem.repositories.AlertRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduledService {

    final RequestService requestService;
    final AlertRepository alertRepository;
    private static int perSec = 0;

    @Scheduled(fixedRate = 1000) //1sn
    public void scheduleAlerts(){
        perSec+=1;
        List<Alert> alerts = alertRepository.findAll();
        for(Alert alert : alerts){


            System.out.println(perSec);
            if(perSec % alert.getPeriod() == 0L){
                requestService.sendRequest(alert);
            }

            /*if (alert.getLifeTime() == 0) {
                requestService.sendRequest(alert);
            } else {
                alert.setLifeTime(alert.getLifeTime() - 1L);
                alertRepository.save(alert);
            }*/
        }

    }
}
