package net.osandman.util.net.jsoup_example;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;


public class HttpContent implements ConnectManager {
    String url;
    Document document;
    Connection connection;
    private static final Logger LOGGER = Logger.getLogger(HttpContent.class.getName());

    public HttpContent(String url) {
        this.url = url;
    }

    static {
        LOGGER.setLevel(Level.ALL);
        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        LOGGER.addHandler(consoleHandler);
        LOGGER.setUseParentHandlers(false);
    }

    @Override
    public boolean connect() {
        try {
            connection = Jsoup.connect(url)
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .referrer("https://yandex.ru/")
                    .timeout(5000);
/*
                    .cookie("cookiename", "val234")
                    .cookie("anothercookie", "ilovejsoup")
                    .referrer("http://google.com")
                    .header("headersecurity", "xyz123");
*/

            document = connection.get();
            LOGGER.fine("title - " + document.title());
            return true;
        } catch (IOException e) {
            LOGGER.warning(e.getMessage());
            return false;
        }
    }

    public Document getContent() {
        return document;
    }

}
