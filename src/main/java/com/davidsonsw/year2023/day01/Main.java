package com.davidsonsw.year2023.day01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    public static enum ForwardNumberSearch {
        ZERO("0", 0), ONE("1", 1), TWO("2", 2),
        THREE("3", 3), FOUR("4", 4),
        FIVE("5", 5), SIX("6", 6), SEVEN("7", 7),
        EIGHT("8", 8), NINE("9", 9),
        ZERO_WORD("zero", 0), ONE_WORD("one", 1), TWO_WORD("two", 2),
        THREE_WORD("three", 3), FOUR_WORD("four", 4),
        FIVE_WORD("five", 5), SIX_WORD("six", 6), SEVEN_WORD("seven", 7),
        EIGHT_WORD("eight", 8), NINE_WORD("nine", 9);


        private final String searchElement;
        private final int value;

        ForwardNumberSearch(String searchElement, int value) {
            this.searchElement = searchElement;
            this.value = value;
        }

        String getSearchElement() {
            return this.searchElement;
        }

        int getValue() {
            return value;
        }

        public static Stream<ForwardNumberSearch> stream() {
            return Stream.of(ForwardNumberSearch.values());
        }

    }

    private final static String INPUT_DATA_PATH = "/Users/s0d01gr/dev/reposolns/advent-of-code/src/main/resources/year2023/day01/input.txt";
    private final static String NUMBERS = "0123456789";
    private final static Character ZERO = '0';

    public static void main(String[] args) {

        List<String> lines = input();

        // Print each line to the console
        assert lines != null;

        int sum = 0;
        for (String line : lines) {
            System.out.println("Line: " + line);
            int first = firstNumber(line);
            int last = lastNumber(line);
            System.out.println("First Number: " + first);
            System.out.println("Last Number: " + last);
            int total = first * 10 + last;
            sum += total;
        }
        System.out.println("Sum: " + sum);

    }

    public static int firstNumber(String line) {

        int firstOccurrenceIndexOf = line.length();
        ForwardNumberSearch firstOccurrence = ForwardNumberSearch.ZERO;
        for (ForwardNumberSearch number : ForwardNumberSearch.values()) {
//            System.out.println("search element: " + number.getSearchElement());
            int occurrenceIndexOf = line.indexOf(number.getSearchElement());
//            System.out.println("occurrence index of: " + occurrenceIndexOf + "(" + firstOccurrenceIndexOf + ")");
            if (occurrenceIndexOf != -1 && occurrenceIndexOf < firstOccurrenceIndexOf) {
                firstOccurrenceIndexOf = occurrenceIndexOf;
                firstOccurrence = number;
            }
//            System.out.println("firstOccurrenceIndexOf: " + firstOccurrenceIndexOf);
//            System.out.println("firstOccurrence: " + firstOccurrence);
        }
        return firstOccurrence.getValue();
    }

    public static int lastNumber(String line) {
        int lastOccurrenceIndexOf = 0;
        ForwardNumberSearch lastOccurrence = ForwardNumberSearch.ZERO;
        for (ForwardNumberSearch number : ForwardNumberSearch.values()) {
//            System.out.println("search element: " + number.getSearchElement());
            int occurrenceIndexOf = line.lastIndexOf(number.getSearchElement());
//            System.out.println("occurrence index of: " + occurrenceIndexOf + "(" + firstOccurrenceIndexOf + ")");
            if (occurrenceIndexOf != -1 && occurrenceIndexOf >= lastOccurrenceIndexOf) {
                lastOccurrenceIndexOf = occurrenceIndexOf;
                lastOccurrence = number;
            }
//            System.out.println("firstOccurrenceIndexOf: " + firstOccurrenceIndexOf);
//            System.out.println("firstOccurrence: " + firstOccurrence);
        }
        return lastOccurrence.getValue();

    }

    private static List<String> input() {

        // Provide the path to your text file
        Path filePath = Path.of(INPUT_DATA_PATH);

        try {
            // Read all lines from the file into a List of Strings
            return Files.readAllLines(filePath);

        } catch (IOException e) {
            // Handle file reading errors
            e.printStackTrace();
            return null;
        }
    }

    public static int firstAlphaNumber(String line) {

        for (int lineIndex = 0; lineIndex < line.length(); lineIndex++) {
//            System.out.println("charAt: " + line.charAt(lineIndex));
            if (-1 != NUMBERS.indexOf(line.charAt(lineIndex))) {
                return Character.getNumericValue(line.charAt(lineIndex));
            }
        }
        return -1;
    }

    public static int lastAlphaNumber(String line) {

        for (int lineIndex = line.length() - 1; lineIndex >= 0; lineIndex--) {
//            System.out.println("charAt: " + line.charAt(lineIndex));
            if (-1 != NUMBERS.indexOf(line.charAt(lineIndex))) {
                return Character.getNumericValue(line.charAt(lineIndex));
            }
        }
        return -1;
    }

    private static int firstNumericNumber(String line) {

        for (int lineIndex = 0; lineIndex < line.length(); lineIndex++) {
//            System.out.println("charAt: " + line.charAt(lineIndex));
            if (-1 != NUMBERS.indexOf(line.charAt(lineIndex))) {
                return Character.getNumericValue(line.charAt(lineIndex));
            }
        }
        return -1;
    }

    private static int lastNumericNumber(String line) {

        for (int lineIndex = line.length() - 1; lineIndex >= 0; lineIndex--) {
//            System.out.println("charAt: " + line.charAt(lineIndex));
            if (-1 != NUMBERS.indexOf(line.charAt(lineIndex))) {
                return Character.getNumericValue(line.charAt(lineIndex));
            }
        }
        return -1;
    }

}
