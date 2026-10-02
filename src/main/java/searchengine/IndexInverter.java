package searchengine;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;


public class IndexInverter {

    private final Logger logger = LoggerFactory.getLogger(IndexInverter.class);

    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Expected input and output CSV file paths");
        }
        new IndexInverter().invertIndex(args[0], args[1]);
    }

    public void invertIndex(String indexFileName, String invertedIndexFileName){
        Map<String, TreeSet<String>> urlsByKeyword = new TreeMap<>();

        try (CSVReader reader = new CSVReader(new FileReader(indexFileName))) {
            for (String[] row : reader) {
                if (row.length < 8) {
                    throw new IOException("Index row must contain a URL, four cache/link fields, and three keywords");
                }

                String url = row[0];
                for (int i = 5; i < 8; i++) {
                    urlsByKeyword
                            .computeIfAbsent(row[i], ignored -> new TreeSet<>())
                            .add(url);
                }
            }

            try (CSVWriter writer = new CSVWriter(new FileWriter(invertedIndexFileName))) {
                for (Map.Entry<String, TreeSet<String>> entry : urlsByKeyword.entrySet()) {
                    List<String> invertedRow = new ArrayList<>();
                    invertedRow.add(entry.getKey());
                    invertedRow.addAll(entry.getValue());
                    writer.writeNext(invertedRow.toArray(String[]::new));
                }
            }
            logger.debug("number of keywords: {}", urlsByKeyword.size());
        } catch (IOException e) {
            logger.error("Unable to invert index", e);
        }
    }

}
