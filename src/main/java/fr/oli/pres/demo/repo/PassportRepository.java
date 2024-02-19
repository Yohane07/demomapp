package fr.oli.pres.demo.repo;

import fr.oli.pres.demo.entity.Passport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PassportRepository extends JpaRepository<Passport, Long> {
    @Query("SELECT p FROM Passport p JOIN FETCH p.person")
    public List<Passport> getAllPassports();
}
