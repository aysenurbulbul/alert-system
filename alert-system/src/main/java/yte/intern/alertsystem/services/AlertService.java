package yte.intern.alertsystem.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yte.intern.alertsystem.models.Alert;
import yte.intern.alertsystem.repositories.AlertRepository;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AlertService {

    private final AlertRepository alertRepository;

    public List<Alert> getAlerts(){
        return alertRepository.findAll();
    }

    public Alert getAlert(String alertName){
        return alertRepository.findByName(alertName);
    }

    public Alert addAlert(final Alert alert){
        alert.setResults(new HashSet<>());
        return alertRepository.save(alert);
    }

    public void deleteAlert(final Long alertId){
        alertRepository.deleteById(alertId);
    }

    public Alert updateAlert(final Alert alert) {
        Alert alertFromDB = alertRepository.findByName(alert.getName());

        if(alertFromDB != null){
            Long id = alertFromDB.getId();
            alert.setId(id);
            return alertRepository.save(alert);
        }
        return null;
    }
}
