package ir.darkdeveloper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SuggestWord {


    public static void main(String[] args) throws IOException {
        var dict = indexDictionary();
        try (var in = new Scanner(System.in)) {
            System.out.print("Enter a word => ");
            var q = in.nextLine();
            System.out.print("Enter min jaccard index => ");
            var jaccard = in.nextFloat();
            in.nextLine();
            var suggestions = findSuggestions(dict, q, jaccard);
            System.out.println("Suggested words: " + suggestions);
        }
    }

    public static List<String> findSuggestions(Map<String, List<String>> dict, String q, float minJaccard) {
        var qKGrams = createBiGrams(q);
        var calculatedKGrams = new HashMap<String, Float>();
        dict.forEach((key, kGrams) -> {

            var union = new HashSet<String>();
            var sub = new HashSet<String>();

            var minIndex = Math.min(kGrams.size(), qKGrams.size());
            if (minIndex == kGrams.size())
                union.addAll(qKGrams);
            else
                union.addAll(kGrams);

            for (int i = 0; i < minIndex; i++) {
                if (kGrams.get(i).equals(qKGrams.get(i))) {
                    sub.add(kGrams.get(i));
                    union.add(kGrams.get(i));
                } else {
                    union.add(kGrams.get(i));
                    union.add(qKGrams.get(i));
                }
            }

            var jaccard = (float) sub.size() / union.size();
            calculatedKGrams.put(key, jaccard);
        });

        return calculatedKGrams.entrySet().stream()
                .filter(entry -> entry.getValue() >= minJaccard)
                .map(Map.Entry::getKey)
                .toList();
    }

    public static LinkedList<String> createBiGrams(String s) {
        s = "$" + s + "$";
        var list = new LinkedList<String>();
        for (int i = 0; i < s.length() - 1; i++)
            list.add(s.substring(i, i + 2));
        return list;
    }

    public static HashMap<String, List<String>> indexDictionary() throws IOException {
        var file = new BufferedReader(new FileReader("words.txt"));
        var dict = new HashMap<String, List<String>>();
        String line;
        while ((line = file.readLine()) != null)
            dict.put(line, createBiGrams(line));
        return dict;
    }

}
