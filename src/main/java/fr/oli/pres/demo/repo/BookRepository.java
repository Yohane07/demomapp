package fr.oli.pres.demo.repo;

import fr.oli.pres.demo.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
