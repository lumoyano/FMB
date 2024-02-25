package fmb.serviceData;

import java.util.ArrayList;

public class ScraperData {
    private static ArrayList<String> scrapedIngredients = new ArrayList<>();

    private static ScraperData instance = new ScraperData();

    private ScraperData() {}

    public static ScraperData getInstance() {
        return instance;
    }

    public ArrayList<String> getScrapedIngredients() {
        return scrapedIngredients;
    }

    public void setScrapedIngredients(ArrayList<String> scrapedIngredients) {
        this.scrapedIngredients = scrapedIngredients;
    }

    public void clearScrapedIngredients() {
        scrapedIngredients.clear();
    }
}
