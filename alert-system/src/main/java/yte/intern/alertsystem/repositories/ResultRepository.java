package yte.intern.alertsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import yte.intern.alertsystem.models.Result;

public interface ResultRepository extends JpaRepository<Result,Long> {
}
