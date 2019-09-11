package yte.intern.alertsystem.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import yte.intern.alertsystem.models.Alert;
import yte.intern.alertsystem.services.AlertService;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class AlertController {

    private final AlertService alertService;

    @GetMapping("/alerts")
    public List<Alert> getAlerts(){
        return alertService.getAlerts();
    }

    @GetMapping("/alerts/{alertName}")
    public Alert getAlert(@PathVariable String alertName){
        return alertService.getAlert(alertName);
    }

    @PostMapping("/alerts")
    public Alert addAlert(@RequestBody final Alert alert){
        return alertService.addAlert(alert);
    }

    @PutMapping("/updateAlert")
    public Alert updateAlert(@RequestBody final Alert alert) { return alertService.updateAlert(alert);}

    @DeleteMapping("/deleteAlert/{alertId}")
    public void deleteAlert(@PathVariable Long alertId){
        alertService.deleteAlert(alertId);
    }

}
