package searchengine;

public class SimpleCrawler {

    public static void main(String[] args) {
        if (args.length != 1 || args[0].isBlank()) {
            throw new IllegalArgumentException("Expected a seed URL");
        }

        new SimpleCrawler().search(args[0]);
    }

    public void search(String seedUrl) {
        System.out.println("Run simple crawler with starting URL: " + seedUrl);
        // TODO: Implement the single-threaded crawling algorithm.
    }
}
