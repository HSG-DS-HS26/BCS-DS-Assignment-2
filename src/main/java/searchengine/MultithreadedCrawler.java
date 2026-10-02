package searchengine;

public class MultithreadedCrawler {

    private static final int DEFAULT_MAX_THREADS = 5;
    private final int maxThreads;

    public MultithreadedCrawler() {
        this(DEFAULT_MAX_THREADS);
    }

    MultithreadedCrawler(int maxThreads) {
        if (maxThreads < 1) {
            throw new IllegalArgumentException("maxThreads must be positive");
        }
        this.maxThreads = maxThreads;
    }

    public static void main(String[] args) {
        if (args.length < 1 || args.length > 2 || args[0].isBlank()) {
            throw new IllegalArgumentException("Expected a seed URL and optional maxThreads");
        }

        new MultithreadedCrawler(maxThreadsFromArgs(args)).search(args[0]);
    }

    static int maxThreadsFromArgs(String[] args) {
        if (args.length == 1) {
            return DEFAULT_MAX_THREADS;
        }
        try {
            int maxThreads = Integer.parseInt(args[1]);
            if (maxThreads < 1) {
                throw new IllegalArgumentException("maxThreads must be positive");
            }
            return maxThreads;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("maxThreads must be a positive integer", e);
        }
    }

    public void search(String seedUrl) {
        // TODO: Implement the multithreaded crawling algorithm.
        System.out.println("Multithreaded crawler template: " + seedUrl
                + " (max threads: " + maxThreads + ")");
    }
}
