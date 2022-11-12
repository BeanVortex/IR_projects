package ir.darkdeveloper;

import java.util.*;

public class SuggestWord {


    public static void main(String[] args) {
        var dict = Map.of(
                "book", List.of("$b", "bo", "oo", "ok", "k$"),
                "boost", List.of("$b", "bo", "oo", "os", "st", "t$"),
                "body", List.of("$b", "bo", "od", "dy", "y$"),
                "butterfly", List.of("$b", "bu", "ut", "tt", "te", "er", "rf", "fl", "ly", "y$")
        );
        try (var in = new Scanner(System.in)) {
            System.out.print("Enter word 'booi' => ");
            var q = in.nextLine();
            System.out.print("Enter min jaccard index => ");
            var jaccard = in.nextFloat();
            in.nextLine();
            var suggestions = findSuggestions(dict, q, jaccard);
            System.out.println("Suggested words: " + suggestions);
        }
    }

    private static List<String> findSuggestions(Map<String, List<String>> dict, String q, float minJaccard) {
        var qKGrams = createBiGrams("$" + q + "$");
        var calculatedKGrams = new HashMap<Float, String>();
        for (var entry : dict.entrySet()) {

            var key = entry.getKey();
            var kGrams = entry.getValue();

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
                } else
                    union.add(kGrams.get(i));
            }

            var jaccard = (float) sub.size() / union.size();
            calculatedKGrams.put(jaccard, key);
        }
        System.out.println("Calculated jaccard for dict: " + calculatedKGrams);
        return calculatedKGrams.entrySet().stream()
                .filter(entry -> entry.getKey() >= minJaccard)
                .map(Map.Entry::getValue)
                .toList();
    }

    private static LinkedList<String> createBiGrams(String s) {
        var list = new LinkedList<String>();
        for (int i = 0; i < s.length() - 1; i++) {
            list.add(s.substring(i, i + 2));
            System.out.print(list.get(i) + "\t");
        }
        System.out.println();
        return list;
    }

}
