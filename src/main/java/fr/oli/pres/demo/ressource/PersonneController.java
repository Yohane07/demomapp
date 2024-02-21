package fr.oli.pres.demo.ressource;

import fr.oli.pres.demo.entity.Person;

import fr.oli.pres.demo.service.PersonneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/persons")
public class PersonneController {

 /* private final PersonneRepository personneRepository;
    private final PassportRepository passportRepository;*/

    private final PersonneService personneService;
    @Autowired
    public PersonneController(PersonneService personneService){
        this.personneService = personneService;
    }

    @GetMapping("/{id}")
    public Optional<Person> getPerson(@PathVariable Long id){
        return personneService.getPersonById(id);
    }

    @GetMapping()
    public List<Person> getAllPersons(){
        return personneService.getAllPersons();
    }

  @PostMapping("/save")
    public Person savePersonnne(@RequestBody Person person) {
      return personneService.savePersonne(person);
  }

}
