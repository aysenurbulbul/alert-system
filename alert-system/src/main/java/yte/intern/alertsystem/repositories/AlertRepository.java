package yte.intern.alertsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import yte.intern.alertsystem.models.Alert;

public interface AlertRepository extends JpaRepository<Alert,Long> {
    Alert findByName(String alertName);
}
