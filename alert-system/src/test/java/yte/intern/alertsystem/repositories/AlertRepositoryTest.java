package yte.intern.alertsystem.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import yte.intern.alertsystem.models.Alert;
import yte.intern.alertsystem.models.Result;

import java.util.HashSet;

import static org.junit.Assert.*;

//@RunWith(SpringJUnit4ClassRunner.class)
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AlertRepositoryTest {

    @Autowired
    AlertRepository alertRepository;

    @Test
    public void findByName() {
        Alert alert1 = new Alert(1L,"deneme", "https://www.google.com/", HttpMethod.GET, 5L, new HashSet<Result>());

        alertRepository.save(alert1);

        Alert alert2 = alertRepository.findByName(alert1.getName());

        assertEquals("deneme",alert2.getName());
    }
}