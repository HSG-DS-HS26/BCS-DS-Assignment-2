package searchengine;

import io.javalin.Javalin;

public class SearchEngineServer {
    public static Javalin create() {
        return Javalin.create()
                .get("/", ctx -> ctx.result("Search engine is running"));
        //TODO: add the other required operations
    }
}
