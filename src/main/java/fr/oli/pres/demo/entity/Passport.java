package fr.oli.pres.demo.entity;

import javax.persistence.*;
@Entity
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;

    //Premier cas Typical mapping: Comment cette relation
    // est mappé le plus souvent
    @OneToOne(fetch = FetchType.LAZY)

    //TYPICAL MAPPING
   /* @JoinColumn(name = "person_id")
    private Person person; */

    //@MAPSID pour le gain de performance
    // La façon la plus efficace de mapper
    // Avec le @OneToOne @MapsId directement au niveau de l'entité enfant
    // à partir duquel JPA reconnaîtra le parent grâce au MapsId qui prend en compte l'annotation
    // @Id@GeneratedValue pour identifier l'entité, en somme il match les deux ids

    @MapsId
    private Person person;

    // Getters and setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
