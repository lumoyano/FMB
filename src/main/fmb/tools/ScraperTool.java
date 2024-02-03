package main.fmb.tools;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class ScraperTool {

    /*public static void main(String[] args) throws IOException {
        String test = getSoup("https://incidecoder.com/products/mac-lipglass");
    }*/

    public static String getSoup(String link) throws IOException {
        Document doc = Jsoup.connect(link).get();
        Element container = doc.getElementById("showmore-section-ingredlist-short");
        Elements hrefs = container.getElementsByClass("ingred-link");
        ArrayList<String> listItems = new ArrayList<>();
        for (Element e :
                hrefs) {
            listItems.add(e.text());
        }
        System.out.println(listItems);
        return listItems.toString();
    }
}
