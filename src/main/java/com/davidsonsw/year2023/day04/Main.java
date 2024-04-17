package com.davidsonsw.year2023.day04;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    private final static String INPUT_DATA_PATH = "/Users/s0d01gr/dev/reposolns/advent-of-code/src/main/resources/year2023/day04/input.txt";

    private final static String CARD_ID_SEPARATOR = ":";
    private final static String WINNERS_PLAYERS_SEPARATOR = "\\|";
    private final static String WINNERS_PLAYERS_NUMBER_SEPARATOR = "\\s+";

    public static record Card(int id, List<String> winners, List<String> players) {
        public static Card of(int id, List<String> winners, List<String> players) {
            return new Card(id, winners, players);
        }
    }

    public static record ProcessedCard(Card card, int matches, int timesProcessed){
        public static ProcessedCard of(Card card, int matches){
            return new ProcessedCard(card, matches, 1);
        }
        public static ProcessedCard reprocessedCard(ProcessedCard processedCard){
            return new ProcessedCard(processedCard.card(), processedCard.matches(), processedCard.timesProcessed() + 1);
        }
    }

    public static Map<Integer, Card> SCRATCH_CARDS = new HashMap<>();
    public static Map<Card, ProcessedCard> PROCESSED_CARDS = new HashMap<>();


    public static void main(String[] args) {
//        partOne();
        partTwo();
    }

    public static void partOne() {

        parseInput(INPUT_DATA_PATH);

        System.out.println("********* \n********* \n********* \n********* \n");

        int sum = 0;
        for (Card card: SCRATCH_CARDS.values()) {
            System.out.println("Card: " + card);
            sum+= points(card);
        }
        System.out.println("sum = " + sum);
    }

    public static void partTwo() {

        parseInput(INPUT_DATA_PATH);

        System.out.println("********* \n********* \n********* \n********* \n");

        for (Card card: SCRATCH_CARDS.values()) {
            System.out.println("Card: " + card);
            process(card);
        }

        int sum = 0;
        for (ProcessedCard processedCard: PROCESSED_CARDS.values()) {
            System.out.println("ProcessedCard: " + processedCard);
            sum+= processedCard.timesProcessed();
        }
        System.out.println("sum = " + sum);
    }

    private static void process(Card card){

        int matches = 0;
        if(PROCESSED_CARDS.get(card) == null){
            matches = matches(card);
            PROCESSED_CARDS.put(card, ProcessedCard.of(card, matches));
        } else {
            ProcessedCard processedCard = PROCESSED_CARDS.get(card);
            matches = processedCard.matches();
            PROCESSED_CARDS.put(card, ProcessedCard.reprocessedCard(processedCard));
        }

        for(int id = card.id() + 1; id < card.id() + matches + 1; id++){
            process(SCRATCH_CARDS.get(id));
        }

    }
    private static int matches(Card card) {

        int matchingWinners = 0;
        for (String winner : card.winners()) {
            if (card.players().contains(winner)) {
                matchingWinners++;
            }
        }
        return matchingWinners;
    }
    private static int points(Card card) {

        int matchingWinners = -1;
        for (String winner : card.winners()) {
            if (card.players().contains(winner)) {
                matchingWinners++;
            }
        }
        int returnPoints = (-1 == matchingWinners) ? 0 : (int) Math.pow(2, matchingWinners);
        System.out.println("points: " + returnPoints);
        return returnPoints;
    }

    private static void parseInput(String inputDataPath) {

        List<String> lines = input(inputDataPath);
        assert lines != null;

        int lineNumber = 0;
        for (String line : lines) {

            String[] cardAndNumbers = line.split(CARD_ID_SEPARATOR);
            String[] winnersAndPlayers = cardAndNumbers[1].split(WINNERS_PLAYERS_SEPARATOR);
            String[] winners = winnersAndPlayers[0].trim().split(WINNERS_PLAYERS_NUMBER_SEPARATOR);
            String[] players = winnersAndPlayers[1].trim().split(WINNERS_PLAYERS_NUMBER_SEPARATOR);
            System.out.println("cnn: " + Arrays.toString(cardAndNumbers));
            System.out.println("wnp: " + Arrays.toString(winnersAndPlayers));
            System.out.println("w: " + Arrays.toString(winners));
            System.out.println("p: " + Arrays.toString(players));

            SCRATCH_CARDS.put(lineNumber, Card.of(lineNumber, Arrays.asList(winners), Arrays.asList(players)));
            lineNumber++;
        }
        System.out.println("size: " + SCRATCH_CARDS.size());
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
