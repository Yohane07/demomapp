package fr.oli.pres.demo.ressource;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import fr.oli.pres.demo.eip.MyGateway;
import fr.oli.pres.demo.entity.Author;
import fr.oli.pres.demo.entity.Book;
import fr.oli.pres.demo.repo.AuthorRepository;
import fr.oli.pres.demo.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookRessource {
    private final AuthorRepository authorRepository;

    private final BookRepository bookRepository;

    private final MyGateway myGateway;

    private final PublishSubscribeChannel pubsubChannel;

    @Autowired
    public BookRessource(AuthorRepository authorRepository, BookRepository bookRepository, MyGateway myGateway, MessageChannel pubsubChannel) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.myGateway = myGateway;
        this.pubsubChannel = (PublishSubscribeChannel) pubsubChannel;
    }


    @GetMapping
    public String getBook() {
        Book book = bookRepository.getReferenceById(1L);
        return book.getTitle();
    }

   }
