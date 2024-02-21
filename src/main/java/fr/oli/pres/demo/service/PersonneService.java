package fr.oli.pres.demo.service;

import fr.oli.pres.demo.entity.Person;
import fr.oli.pres.demo.repo.PersonneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonneService {

    private final PersonneRepository personRepository;

    @Autowired
    public PersonneService(PersonneRepository personRepository) {
        this.personRepository = personRepository;
    }

    // Méthode pour récupérer toutes les personnes
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    // Méthode pour récupérer une personne par son ID
    public Optional<Person> getPersonById(Long id) {
        return personRepository.findById(id);
    }

    // Méthode pour sauvegarder une personne
    public Person savePersonne(Person person) {
        return personRepository.save(person);
    }

    // Méthode pour supprimer une personne par son ID
    public void deletePersonById(Long id) {
        personRepository.deleteById(id);
    }
}
