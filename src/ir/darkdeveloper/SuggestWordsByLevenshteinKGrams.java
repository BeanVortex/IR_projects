package ir.darkdeveloper;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static ir.darkdeveloper.ConvertingS1ToS2.findEditDistance;
import static ir.darkdeveloper.SuggestWord.*;

public class SuggestWordsByLevenshteinKGrams {
    public static void main(String[] args) throws IOException {
        var dict = indexDictionary();
        try (var in = new Scanner(System.in)) {
            System.out.print("Enter a word => ");
            var q = in.nextLine();
            System.out.print("Enter min jaccard index => ");
            var jaccard = in.nextFloat();
            in.nextLine();
            System.out.print("Enter min char difference => ");
            var minCharDif = in.nextInt();
            in.nextLine();

            var suggestions = findSuggestions(dict, q, jaccard);
            var suggestionsByLevenshtein = findSuggestionsByLevenshtein(suggestions, q, minCharDif);
            System.out.println("Suggested words: " + suggestionsByLevenshtein);
        }
    }

    private static List<String> findSuggestionsByLevenshtein(List<String> suggestions, String q, int minCharDif) {
        var calculatedEditDistance = new HashMap<String, Integer>();
        suggestions.forEach(s -> {
            int editDistance = findEditDistance(s, q, true);
            calculatedEditDistance.put(s, editDistance);
        });
        return calculatedEditDistance.entrySet().stream()
                .filter(entry -> entry.getValue() <= minCharDif)
                .map(Map.Entry::getKey)
                .toList();
    }

}
