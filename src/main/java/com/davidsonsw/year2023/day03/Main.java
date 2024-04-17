package com.davidsonsw.year2023.day03;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {

    private final static String INPUT_DATA_PATH = "/Users/s0d01gr/dev/reposolns/advent-of-code/src/main/resources/year2023/day03/input.txt";

    private final static Character EMPTY_COLUMN_DELIMITER = '.';
    private final static List<Character> NUMBER_CHARACTERS_LIST = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');

    public static record Coordinate(int line, int column) {
        public static Coordinate of(int line, int column) {
            return new Coordinate(line, column);
        }
    }

    ;

    public static record CoordinateRange(Coordinate start, Coordinate end) {
        public static CoordinateRange of(Coordinate start, Coordinate end) {
            return new CoordinateRange(start, end);
        }

        public static CoordinateRange of(Coordinate start) {
            return new CoordinateRange(start, start);
        }
    }

    ;

    public static record Symbol(String value, Coordinate coordinate) {
        public static Symbol of(String value, Coordinate coordinate) {
            return new Symbol(value, coordinate);
        }
    }

    ;

    public static record PartNumber(String value, CoordinateRange coordinateRange) {
        public static PartNumber of(String value, CoordinateRange coordinateRange) {
            return new PartNumber(value, coordinateRange);
        }

        public static PartNumber of(String value, Coordinate start, Coordinate end) {
            return new PartNumber(value, CoordinateRange.of(start, end));
        }
    }

    ;

    public static Map<Integer, List<PartNumber>> PART_NUMBER_MAP = new HashMap<>();

    public static Map<Integer, List<Symbol>> SYMBOL_MAP = new HashMap<>();


    public static void main(String[] args) {
//        partOne();
        partTwo();
    }

    public static void partOne() {

        parseInput(INPUT_DATA_PATH);

        System.out.println("********* \n********* \n********* \n********* \n");

        int sum = 0;
        for (int lineNumber = 0; lineNumber < PART_NUMBER_MAP.size(); lineNumber++) {
            List<Symbol> previousSymbols = previousSymbolList(lineNumber);
            List<Symbol> followingSymbols = followingSymbolList(lineNumber);

            for (PartNumber partNumber : PART_NUMBER_MAP.get(lineNumber)) {

                System.out.println("(" + lineNumber + ") - partNumber = " + partNumber);
                boolean isWithin = isWithinSymbolRange(previousSymbols, SYMBOL_MAP.get(lineNumber), followingSymbols, partNumber);
                System.out.println("isWithinSymbolRange: " + isWithin);
                System.out.println("---\n");
                if (isWithin) {
                    sum += Integer.parseInt(partNumber.value());
                }
            }
        }
        System.out.println("sum = " + sum);
    }

    public static void partTwo() {

        parseInput(INPUT_DATA_PATH);

        System.out.println("********* \n********* \n********* \n********* \n");

        int sum = 0;
        for (int lineNumber = 0; lineNumber < PART_NUMBER_MAP.size(); lineNumber++) {
            List<PartNumber> previousPartNumbers = previousPartNumberList(lineNumber);
            List<PartNumber> followingPartnumbers = followingPartNumberList(lineNumber);

            for (Symbol symbol : SYMBOL_MAP.get(lineNumber)) {

                System.out.println("(" + lineNumber + ") - symbol = " + symbol);
                int gearRatio = gearRatio(previousPartNumbers, PART_NUMBER_MAP.get(lineNumber), followingPartnumbers, symbol);
                System.out.println("gearRatio: " + gearRatio);
                System.out.println("---\n");
                sum += gearRatio;
            }
        }
        System.out.println("sum = " + sum);
    }

    private static int gearRatio(List<PartNumber> previousPartNumbers, List<PartNumber> linearPartNumbers, List<PartNumber> followingPartNumbers, Symbol symbol) {
        System.out.println(previousPartNumbers.size() + " , " + linearPartNumbers.size() + " , " + followingPartNumbers.size());

        if (!symbol.value().contentEquals("*")) {
            return 0;
        }

        int gearCount = 0;
        int gearRatio = 1;

        // TODO : refactor this (fail fast) to standalone reusable method (partOne version too)
        for (PartNumber partNumber : previousPartNumbers) {
            if ((partNumber.coordinateRange().start().column() - 1) <= symbol.coordinate().column() &&
                    (partNumber.coordinateRange().end().column() + 1) >= symbol.coordinate().column()) {
                gearCount++;
                gearRatio *= Integer.parseInt(partNumber.value());
            }
        }
        for (PartNumber partNumber : linearPartNumbers) {
            if ((partNumber.coordinateRange().start().column() - 1) <= symbol.coordinate().column() &&
                    (partNumber.coordinateRange().end().column() + 1) >= symbol.coordinate().column()) {
                gearCount++;
                gearRatio *= Integer.parseInt(partNumber.value());
            }
        }
        for (PartNumber partNumber : followingPartNumbers) {
            if ((partNumber.coordinateRange().start().column() - 1) <= symbol.coordinate().column() &&
                    (partNumber.coordinateRange().end().column() + 1) >= symbol.coordinate().column()) {
                gearCount++;
                gearRatio *= Integer.parseInt(partNumber.value());
            }
        }

        if (gearCount == 2) {
            return gearRatio;
        } else {
            return 0;
        }
    }

    private static boolean isWithinSymbolRange(List<Symbol> previousSymbols, List<Symbol> linearSymbols, List<Symbol> followingSymbols, PartNumber partNumber) {

        System.out.println(previousSymbols.size() + " , " + linearSymbols.size() + " , " + followingSymbols.size());
        for (Symbol symbol : previousSymbols) {
            if ((partNumber.coordinateRange().start().column() - 1) <= symbol.coordinate().column() &&
                    (partNumber.coordinateRange().end().column() + 1) >= symbol.coordinate().column()) {
                return true;
            }
        }

        for (Symbol symbol : linearSymbols) {
            System.out.println("linearSymbol: " + symbol);
            if ((partNumber.coordinateRange().start().column() - 1) <= symbol.coordinate().column() &&
                    (partNumber.coordinateRange().end().column() + 1) >= symbol.coordinate().column()) {
                return true;
            }
        }
        for (Symbol symbol : followingSymbols) {
            System.out.println("followingSymbol: " + symbol);
            if ((partNumber.coordinateRange().start().column() - 1) <= symbol.coordinate().column() &&
                    (partNumber.coordinateRange().end().column() + 1) >= symbol.coordinate().column()) {
                return true;
            }
        }

        return false;
    }

    private static List<Symbol> previousSymbolList(int lineNumber) {
        if (0 == lineNumber) {
            return new ArrayList<Symbol>();
        } else {
            return SYMBOL_MAP.get(lineNumber - 1);
        }
    }

    private static List<PartNumber> previousPartNumberList(int lineNumber) {
        if (0 == lineNumber) {
            return new ArrayList<PartNumber>();
        } else {
            return PART_NUMBER_MAP.get(lineNumber - 1);
        }
    }

    private static List<Symbol> followingSymbolList(int lineNumber) {
        if (SYMBOL_MAP.size() == lineNumber + 1) {
            return new ArrayList<Symbol>();
        } else {
            return SYMBOL_MAP.get(lineNumber + 1);
        }
    }

    private static List<PartNumber> followingPartNumberList(int lineNumber) {
        if (PART_NUMBER_MAP.size() == lineNumber + 1) {
            return new ArrayList<PartNumber>();
        } else {
            return PART_NUMBER_MAP.get(lineNumber + 1);
        }
    }

    public static void parseInput(String inputDataPath) {

        List<String> lines = input(inputDataPath);
        assert lines != null;

        int lineNumber = 0;
        for (String line : lines) {

            List<PartNumber> partNumberList = new ArrayList<>();
            PART_NUMBER_MAP.put(lineNumber, partNumberList);
            List<Symbol> symbolList = new ArrayList<>();
            SYMBOL_MAP.put(lineNumber, symbolList);

            PartNumber activePartNumber = null;

            for (int column = 0; column < line.length(); column++) {

                char character = line.charAt(column);

                if (EMPTY_COLUMN_DELIMITER == character) {
                    if (activePartNumber != null) {
                        partNumberList.add(activePartNumber);
                        activePartNumber = null;
                    }
                } else if (NUMBER_CHARACTERS_LIST.contains(character)) {
                    if (activePartNumber != null) {
                        activePartNumber = extendPartnumber(activePartNumber, Character.toString(character));
                    } else {
                        activePartNumber = PartNumber.of(Character.toString(character),
                                CoordinateRange.of(Coordinate.of(lineNumber, column)));
                    }
                } else {
                    if (activePartNumber != null) {
                        partNumberList.add(activePartNumber);
                        activePartNumber = null;
                    }
                    Symbol symbol = Symbol.of(Character.toString(character), Coordinate.of(lineNumber, column));
                    symbolList.add(symbol);
                }

            }
            if (activePartNumber != null) {
                partNumberList.add(activePartNumber);
            }
            lineNumber++;

            System.out.println("PartNumbers: (" + lineNumber + "): " + partNumberList);
            System.out.println("Symbols: (" + lineNumber + "): " + symbolList);
            System.out.println("Test Result: " + testParse(line, partNumberList, symbolList));
        }
    }

    private static boolean testParse(String line, List<PartNumber> partNumbers, List<Symbol> symbols) {

        int partNumberIndex = 0;
        int symbolIndex = 0;
        StringBuilder stringBuilder = new StringBuilder();
        int currentColumn = 0;
        while (currentColumn < line.length()) {
            if (!partNumbers.isEmpty() && partNumberIndex < partNumbers.size()
                    && currentColumn == partNumbers.get(partNumberIndex).coordinateRange().start().column()) {
                stringBuilder.append(partNumbers.get(partNumberIndex).value());
                currentColumn += partNumbers.get(partNumberIndex).value().length();
                partNumberIndex++;
            } else if (!symbols.isEmpty() && symbolIndex < symbols.size()
                    && currentColumn == symbols.get(symbolIndex).coordinate().column()) {
                stringBuilder.append(symbols.get(symbolIndex).value());
                currentColumn += symbols.get(symbolIndex).value().length();
                symbolIndex++;
            } else {
                stringBuilder.append(".");
                currentColumn++;
            }
        }
        System.out.println("Orig Line: " + line);
        System.out.println("Test Line: " + stringBuilder.toString());

        return line.contentEquals(stringBuilder.toString());
    }


    private static PartNumber extendPartnumber(PartNumber partNumber, String partNumberValueExtension) {
        String newValue = partNumber.value() + partNumberValueExtension;
        Coordinate newEndCoordinate = new Coordinate(partNumber.coordinateRange().end().line(),
                partNumber.coordinateRange().end().column() + 1);
        return new PartNumber(newValue, new CoordinateRange(partNumber.coordinateRange().start(), newEndCoordinate));
    }


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
