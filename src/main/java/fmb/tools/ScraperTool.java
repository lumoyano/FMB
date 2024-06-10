package fmb.tools;

import fmb.model.Product;
import fmb.serviceData.ProductData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class ScraperTool {

    public static ArrayList<String> getIngredients(String link) throws IOException {
        try {
            Document doc = Jsoup.connect(link).get();
            Element container = doc.getElementById("showmore-section-ingredlist-short");
            Elements hrefs = container.getElementsByClass("ingred-link");
            ArrayList<String> listItems = new ArrayList<>();
            for (Element e :
                    hrefs) {
                listItems.add(e.text());
            }
            return listItems;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static Product getItemSoup(String link) throws IOException {
        Product currentItem = new Product();
        try {
            Document doc = Jsoup.connect(link).get();
            Element productBrand = doc.getElementById("product-brand-title");
            Element productTitle = doc.getElementById("product-title");
            Element container = doc.getElementById("showmore-section-ingredlist-short");
            Elements hrefs = container.getElementsByClass("ingred-link");
            ArrayList<String> ingredients = new ArrayList<>();
            for (Element e :
                    hrefs) {
                ingredients.add(e.text());
            }
            currentItem.setProductID(ProductData.getInstance().getNextID());
            currentItem.setProductCategory(1);
            currentItem.setProductType(1);
            currentItem.setProductBrand(productBrand.text());
            currentItem.setProductName(productTitle.text());
            currentItem.setIngredients(ingredients);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currentItem;
    }

    public static ArrayList<String> getBrandSoup(String link) throws IOException {
        boolean pageExists = true;
        int offset = 0;
        String currentPage = link + "?offset=";
        ArrayList<String> finalLinkList = new ArrayList<>();

        while (pageExists){
            ArrayList<String> currentList = scrapeFullPage(currentPage+offset);
            offset++;
            if (currentList.isEmpty())
                pageExists=false;
            else
                finalLinkList.addAll(currentList);
        }
        System.out.println(finalLinkList);
        return finalLinkList;
    }

    private static ArrayList<String> scrapeFullPage(String link) throws IOException {
        ArrayList<String> linkList = new ArrayList<>(60);
        try{
            Document doc = Jsoup.connect(link).get();
            Elements aElements = doc.getElementsByTag("a");
            for (Element e :
                    aElements) {
                linkList.add(e.attr("href"));
            }
            System.out.println(linkList);
            linkList.removeIf(s -> !s.contains("/products/"));
            System.out.println(linkList);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return linkList;
    }
}
