package fr.oli.pres.demo.entity;

import javax.persistence.*;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    //Premier cas Typical mapping: Comment cette relation est mappé le plus souvent
   @OneToOne(mappedBy = "person", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    private Passport passport;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }
}

