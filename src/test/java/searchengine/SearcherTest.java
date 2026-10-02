package searchengine;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SearcherTest {

    @TempDir
    Path tempDirectory;

    @Test
    void returnsAbsoluteUrlsFromTheInvertedIndex() throws Exception {
        Path invertedIndex = tempDirectory.resolve("inverted_index.csv");
        Files.write(invertedIndex, List.of(
                "\"butterfly\",\"https://example.test/page-a\"",
                "\"gate\",\"https://example.test/page-b\""
        ));

        List<String> results = new Searcher().search("butterfly", invertedIndex.toString());

        assertEquals(List.of("https://example.test/page-a"), results);
    }
}
