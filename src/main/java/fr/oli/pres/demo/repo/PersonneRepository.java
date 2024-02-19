package fr.oli.pres.demo.repo;

import fr.oli.pres.demo.entity.Book;
import fr.oli.pres.demo.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonneRepository extends JpaRepository<Person, Long> {
}
