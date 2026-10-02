package searchengine;

public class SearchEngineApplication {
    public static void main(String[] args) {
        SearchEngineServer.create().start(9000);
    }
}
