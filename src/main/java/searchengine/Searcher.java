package searchengine;

import com.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Searcher {

    private final Logger logger = LoggerFactory.getLogger(Searcher.class);

    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Expected a keyword and an inverted-index CSV file path");
        }

        new Searcher()
                .search(args[0], args[1])
                .forEach(System.out::println);
    }

    public List<String> search(String keyword, String flippedIndexFileName){
        long startTime = System.currentTimeMillis();
        List<String> urls = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(flippedIndexFileName))) {
            for (String[] line : reader) {
                if (line.length > 0 && line[0].equals(keyword)) {
                    for (int i = 1; i < line.length; i++) {
                        urls.add(line[i]);
                    }
                    break;
                }
            }
        } catch (IOException e) {
            logger.error("Unable to search inverted index", e);
        }

        long duration = System.currentTimeMillis() - startTime;
        logger.info("duration search inverted index: {} ms", duration);
        return urls;
    }


}
