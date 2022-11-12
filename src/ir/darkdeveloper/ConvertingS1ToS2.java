package ir.darkdeveloper;

import java.util.Scanner;
import java.util.stream.IntStream;

public class ConvertingS1ToS2 {
    public static void main(String[] args) {
        try (var in = new Scanner(System.in)) {

            System.out.print("Enter S1 => ");
            var s1 = in.nextLine();
            System.out.print("Enter S2 => ");
            var s2 = in.nextLine();

            System.out.printf("At least we need %d changes to convert %s to %s", findEditDistance(s1, s2), s1, s2);
        }
    }

    public static int findEditDistance(String s1, String s2) {
        var arr = new int[s1.length() + 1][s2.length() + 1];

        for (int i = 0; i < s1.length(); i++)
            arr[i][0] = i;
        for (int j = 0; j < s2.length(); j++)
            arr[0][j] = j;

        for (int i = 1; i < s1.length(); i++) {
            for (int j = 1; j < s2.length(); j++) {
                var distance1 = arr[i - 1][j - 1];
                if (s1.charAt(i) != s2.charAt(j)) distance1++;
                var distance2 = arr[i - 1][j] + 1;
                var distance3 = arr[i][j - 1] + 1;
                arr[i][j] = min(distance1, distance2, distance3);
            }
        }

        for (int i = 0; i < s1.length(); i++) {
            for (int j = 0; j < s2.length() ; j++) {
                System.out.print(arr[i][j] + "\t");
            }
            System.out.println();
        }
        return arr[s1.length() - 1][s2.length() - 1];
    }

    private static int min(int... arr) {
        if (arr == null || arr.length == 0)
            throw new IllegalArgumentException();
        return IntStream.of(arr)
                .reduce(Integer::min)
                .orElse(0);
    }
}
