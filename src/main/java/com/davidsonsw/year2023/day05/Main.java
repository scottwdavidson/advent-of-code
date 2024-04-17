package com.davidsonsw.year2023.day05;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {

    private final static String INPUT_DATA_PATH = "/Users/s0d01gr/dev/reposolns/advent-of-code/src/main/resources/year2023/day05/easy-input.txt";


    private final static String SEEDS_LABEL_DATA_SEPARATOR = ":";
    private final static String VALUES_NUMBER_SEPARATOR = "\\s+";

    private final static String SOURCE_TO_DESTINATION_SEPARATOR = "-to-";

    public static record Range(int start, int end){
        public boolean within(int point){
            return (start() <= point && end() >= point);
        }
    }

    public static record Seeds(List<Integer> seeds){}
    public static Seeds SEEDS = null;
    public static record ElementMap(Range elementRange, int mapOffset){
        public static ElementMap of(Range elementRange, int mapOffset){
            return new ElementMap(elementRange, mapOffset);
        }
    }

    public static record ElementMapping(String source, String destination, List<ElementMap> elementMaps){}

    public static Map<String, List<ElementMapping>> ELEMENT_MAPPINGS = new HashMap<>();

    public static void main(String[] args) {
        partOne();
//        partTwo();
    }

    public static void partOne() {

        parseInput(INPUT_DATA_PATH);

        System.out.println("********* \n********* \n********* \n********* \n");

        int sum = 0;
        System.out.println("sum = " + sum);
    }

    public static void partTwo() {

        parseInput(INPUT_DATA_PATH);

        System.out.println("********* \n********* \n********* \n********* \n");

        int sum = 0;
        System.out.println("sum = " + sum);
    }


    private static void parseInput(String inputDataPath) {

        List<String> lines = input(inputDataPath);
        assert lines != null;


        int lineIndex = 0;
        parseSeeds(lines.get(lineIndex));

        for (String line : lines) {

            lineIndex ++;
        }
        System.out.println("size: ");
    }

    private static void parseSeeds(String seedsLine){

        String[] seedsLineSplit = seedsLine.split(SEEDS_LABEL_DATA_SEPARATOR);
        String[] seedsValueSplit = seedsLineSplit[1].split(VALUES_NUMBER_SEPARATOR);
        List<Integer> seedsValues = new ArrayList<>();
        for(String seedsValue: seedsValueSplit){
            seedsValues.add()
        }

    }
//    private static boolean testParse(String line, List<PartNumber> partNumbers, List<Symbol> symbols) {
//
//        int partNumberIndex = 0;
//        int symbolIndex = 0;
//        StringBuilder stringBuilder = new StringBuilder();
//        int currentColumn = 0;
//        while (currentColumn < line.length()) {
//            if (!partNumbers.isEmpty() && partNumberIndex < partNumbers.size()
//                    && currentColumn == partNumbers.get(partNumberIndex).coordinateRange().start().column()) {
//                stringBuilder.append(partNumbers.get(partNumberIndex).value());
//                currentColumn += partNumbers.get(partNumberIndex).value().length();
//                partNumberIndex++;
//            } else if (!symbols.isEmpty() && symbolIndex < symbols.size()
//                    && currentColumn == symbols.get(symbolIndex).coordinate().column()) {
//                stringBuilder.append(symbols.get(symbolIndex).value());
//                currentColumn += symbols.get(symbolIndex).value().length();
//                symbolIndex++;
//            } else {
//                stringBuilder.append(".");
//                currentColumn++;
//            }
//        }
//        System.out.println("Orig Line: " + line);
//        System.out.println("Test Line: " + stringBuilder.toString());
//
//        return line.contentEquals(stringBuilder.toString());
//    }


    private static List<String> input(String inputDataPath) {

        // Provide the path to your text file
        Path filePath = Path.of(inputDataPath);

        try {
            // Read all lines from the file into a List of Strings
            return Files.readAllLines(filePath);

        } catch (IOException e) {
            // Handle file reading errors
            return null;
        }
    }

}
