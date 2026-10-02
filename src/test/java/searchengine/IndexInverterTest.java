package searchengine;

import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class IndexInverterTest {

    @TempDir
    Path tempDirectory;

    @Test
    void writesSortedTermsWithSortedUniqueUrls() throws Exception {
        Path index = writeIndex(
                "https://example.test/page-b,shared,beta,shared",
                "https://example.test/page-a,alpha,shared,gamma",
                "https://example.test/page-c,beta,alpha,gamma"
        );
        Path invertedIndex = tempDirectory.resolve("inverted.csv");

        new IndexInverter().invertIndex(index.toString(), invertedIndex.toString());

        try (CSVReader reader = new CSVReader(new FileReader(invertedIndex.toFile()))) {
            List<String[]> rows = reader.readAll();
            assertEquals(4, rows.size());
            assertArrayEquals(new String[]{"alpha", "https://example.test/page-a", "https://example.test/page-c"}, rows.get(0));
            assertArrayEquals(new String[]{"beta", "https://example.test/page-b", "https://example.test/page-c"}, rows.get(1));
            assertArrayEquals(new String[]{"gamma", "https://example.test/page-a", "https://example.test/page-c"}, rows.get(2));
            assertArrayEquals(new String[]{"shared", "https://example.test/page-a", "https://example.test/page-b"}, rows.get(3));
        }
    }

    private Path writeIndex(String... rows) throws IOException {
        Path index = tempDirectory.resolve("index.csv");
        Files.write(index, List.of(rows));
        return index;
    }
}
