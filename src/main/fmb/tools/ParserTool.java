package main.fmb.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParserTool {

    public static void main(String[] args) {
        System.out.println(ParserTool.getIng("Lucas loves to eat, Yay! Joseph"));
    }
    public static List<String> getIng(String ingredient) {
        // Separate ingredients into an array list by removing commas and symbols
        String[] ingredientsArray = ingredient.split("[,\\s]+");
        List<String> ingredientsList = new ArrayList<>(Arrays.asList(ingredientsArray));

        // Remove symbols from each ingredient
        for (int i = 0; i < ingredientsList.size(); i++) {
            String cleanedIngredient = ingredientsList.get(i).replaceAll("[^a-zA-Z0-9]", "");
            ingredientsList.set(i, cleanedIngredient);
        }

        return ingredientsList;
    }

}
