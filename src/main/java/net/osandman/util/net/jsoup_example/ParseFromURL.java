package net.osandman.util.net.jsoup_example;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;

public class ParseFromURL {
    public static void main(String[] args) throws IOException {
        URL url = new URL("http://lib.ru");
        HttpContent httpContent = new HttpContent(url.toString());
        Document document = null;
        if (httpContent.connect()) {
            document = httpContent.getContent();
        }
        //<strong class="u">
//        System.out.println(document.text());
        Elements lines = document.select("div");
        for (Element element : lines) {
            if (!"".equals(element.text())) {
                System.out.println(element.text());
            }
        }
    }
}
