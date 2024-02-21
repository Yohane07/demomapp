package fr.oli.pres.demo.ressource;

import fr.oli.pres.demo.entity.Person;

import fr.oli.pres.demo.service.PersonneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/persons")
public class PersonneController {

 /*   private final PersonneRepository personneRepository;
    private final PassportRepository passportRepository;*/

    private final PersonneService personneService;

    //TODO: Enlever tout ce qui n'a pas de lien avec la relation OneOne et le N+1 requete
    //TODO: Faire des TU sur les repository

    //TODO: Faire un powerPoint en présentant
    // 0- Le contexte donc OnetoOne relation LAZY,
    // 1- Le Problème lié au N+1 requête,
    // 2- How to resolve it (MapsId + explication de comment ça marche)
    // créer deux relations OneToOne pour illustrer les deux situations

  /*  @Autowired
    public PersonneRessource(PersonneRepository personneRepository,
                             PassportRepository passportRepository){
        this.personneRepository=personneRepository;
        this.passportRepository = passportRepository;
    }*/
   /* @GetMapping
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

    }*/
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

  /*  @PostMapping("/save")
    public ResponseEntity<Person> savePersonnne(@RequestBody Person person) {
        Person savedPerson = personneService.savePersonne(person);
        return new ResponseEntity<>(savedPerson,
                httpStatus.CREATED);
    }
*/

}
