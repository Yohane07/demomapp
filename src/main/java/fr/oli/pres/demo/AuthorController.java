package fr.oli.pres.demo;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import fr.oli.pres.demo.eip.MyGateway;
import fr.oli.pres.demo.eip.SplitterAgreggatorConfig;
import fr.oli.pres.demo.entity.Author;
import fr.oli.pres.demo.entity.Book;
import fr.oli.pres.demo.repo.AuthorRepository;
import fr.oli.pres.demo.repo.BookRepository;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    private final AuthorRepository authorRepository;

    private final BookRepository bookRepository;

    private final MyGateway myGateway;

    private final PublishSubscribeChannel pubsubChannel;

//    @PersistenceContext
//    private EntityManager entityManager;

    @Autowired
    public AuthorController(AuthorRepository authorRepository, BookRepository bookRepository, MyGateway myGateway, MessageChannel pubsubChannel) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.myGateway = myGateway;
        this.pubsubChannel = (PublishSubscribeChannel) pubsubChannel;
    }


    @GetMapping
    public String getAllAuthors() {


        for (int i = 1; i < 20; i++) {
            Author a = authorRepository.findById((long) i).get();
            List<Book> list = a.getBooks();
            myGateway.sendMessage(a);
        }

        return "terminé";
    }

    @GetMapping("/author")
    public String getAuthor() {

        List<Author> listauthor = authorRepository.findAll();
            for (int i = 0; i < 2; i++) {
                Author a = listauthor.get(i);
                List<Book> listbook = a.getBooks();
                for (Book b :
                        listbook) {
                    System.out.println(b.getTitle());
                }
            }


        return "terminé";
    }

    @GetMapping("/authors")
    //@Transactional
    public List<Author> getAuthors() {
        return authorRepository.getAllAuthors();
    }
//    public List<Author> getAuthors() {
//        return authorRepository.findAll();
//    }

    @GetMapping("/insert")
    @Transactional
    public void insert(){
        String csvFile = "C:\\Users\\yendoke\\Downloads\\demo\\csv\\books.csv"; // Remplacez par le chemin de votre fichier CSV

        try {
            // Créez un lecteur CSV en utilisant OpenCSV



            CSVReader reader =  new CSVReaderBuilder(new FileReader(csvFile)).withCSVParser(new CSVParserBuilder().withSeparator(';').build()).build();



            // Lire toutes les lignes du fichier CSV dans une liste de tableaux de chaînes
            List<String[]> rows = reader.readAll();

            // Triez la liste en fonction de la première colonne (indice 0)
            Collections.sort(rows, new Comparator<String[]>() {
                @Override
                public int compare(String[] o1, String[] o2) {
                    // Comparez les éléments en fonction de la première colonne
                    return o1[2].compareTo(o2[2]);
                }
            });

            Set<String> auteurs = new HashSet<>();
            ArrayList<Author> authors = new ArrayList<>();

            Map<String, List<String[]>> groupedByFirstLetter = rows.stream()
                    .collect(Collectors.groupingBy(r -> r[2]));

            int i = 0;
            List<Author> listAuteurs = new ArrayList<>();
            for (Map.Entry<String, List<String[]>> entry : groupedByFirstLetter.entrySet()) {

                String key = entry.getKey();
                List<String[]> value = entry.getValue();

                Author a = new Author();
                a.setName(key);

                List<Book> books = new ArrayList<>();
                for (String[] entree:
                     value) {
                    Book b =new Book(entree[1]);
                    b.setAuthor(a);
                    books.add(b);
                }

                a.setBooks(books);
                authorRepository.save(a);
//                entityManager.persist(a);
                //listAuteurs.add(a);


//                if (i % 20 == 0) { // déclencher le flush après chaque 20 auteurs (ajustez selon vos besoins)
//                    entityManager.flush();
//                    entityManager.clear();
//                }

                i++;
                if (i == 1000) break ;

            }

           // authorRepository.saveAll(listAuteurs);

            // Parcourir chaque ligne et afficher son contenu
            for (String[] row : rows) {
                auteurs.add(row[2]);
                for (String cell : row) {
                    System.out.print(cell + "\t");
                }
                System.out.println(); // Passer à la ligne suivante
            }

            for (String auteur : auteurs ) {
                Author a = new Author();
                a.setName(auteur);
                authors.add(a);
                System.out.println(a.getName());
            }





            // Fermez le lecteur CSV
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
    }
}
