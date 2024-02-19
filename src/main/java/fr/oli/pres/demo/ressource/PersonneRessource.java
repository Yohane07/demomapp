package fr.oli.pres.demo.ressource;

import fr.oli.pres.demo.entity.Author;
import fr.oli.pres.demo.entity.Book;
import fr.oli.pres.demo.entity.Passport;
import fr.oli.pres.demo.entity.Person;
import fr.oli.pres.demo.repo.AuthorRepository;
import fr.oli.pres.demo.repo.PassportRepository;
import fr.oli.pres.demo.repo.PersonneRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/persons")
public class PersonneRessource {

    private final PersonneRepository personneRepository;
    private final PassportRepository passportRepository;

    @Autowired
    public PersonneRessource(PersonneRepository personneRepository,
                             PassportRepository passportRepository){
        this.personneRepository=personneRepository;
        this.passportRepository = passportRepository;
    }

    @GetMapping
    @Transactional
    public String getPersonne() {

        Optional<Person> person = personneRepository.findById(Long.valueOf("1"));

        List<Person> listPerson = personneRepository.findAll();


        List<Passport> listPassport = passportRepository.findAll();




//        List<Passport> passports=passportRepository.findAll();
//
//        for (Passport p :
//                passports) {
//            Person pers = p.getPerson();
//            System.out.println(pers.getName());
//        }

        return "terminé";
    }

    @GetMapping("/insert")
    public void insertPersonne(){
        Person p = new Person();
        p.setName("olivier");

        Passport passport = new Passport();
        passport.setNumber("1234567890");

        p.setPassport(passport);
        passport.setPerson(p);

        personneRepository.save(p);

    }

}
