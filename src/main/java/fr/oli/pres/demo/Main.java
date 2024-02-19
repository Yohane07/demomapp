package fr.oli.pres.demo;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class Main {
    public static void main(String[] args) {
        // Chemin vers le fichier CSV
        String csvFile = "C:\\Users\\yendoke\\Downloads\\demo\\csv\\books.csv"; // Remplacez par le chemin de votre fichier CSV

        try {
            // Créez un lecteur CSV en utilisant OpenCSV
            CSVReader reader = new CSVReader(new FileReader(csvFile));

            // Lire toutes les lignes du fichier CSV dans une liste de tableaux de chaînes
            List<String[]> rows = reader.readAll();



            // Parcourir chaque ligne et afficher son contenu
            for (String[] row : rows) {
                for (String cell : row) {
                    System.out.print(cell + "\t");
                }
                System.out.println(); // Passer à la ligne suivante
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

