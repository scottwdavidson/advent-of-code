package com.davidsonsw.year2023.day02;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class Main {

    public static enum Color {
        RED("red"), GREEN("green"), BLUE("blue");

        private final String color;

        Color(String color) {
            this.color = color;
        }

        String getColor(){
            return this.color;
        }

        public static Color fromValue(String colorValue) {
            for (Color color: values()) {
                if (color.getColor().equals(colorValue)) {
                    return color;
                }
            }
            throw new IllegalArgumentException("No enum constant " + Color.class.getCanonicalName() + "." + colorValue);
        }
    }

    public static record ColoredCube (Color color, int count){};

    public static record TurnCubes(Map<Color, ColoredCube> coloredCubesMap){};

    public static record Game(int gameNumber, List<TurnCubes> turnCubesList){};

    private final static String INPUT_DATA_PATH = "/Users/s0d01gr/dev/reposolns/advent-of-code/src/main/resources/year2023/day02/input.txt";

    private final static String GAME_SEPARATOR = ":";
    private final static int GAME_HEADING_OFFSET = 5;

    private final static String TURN_SEPARATOR = ";";
    private final static String GAMECUBE_COLOR_SEPARATOR = ",";

    private final static ColoredCube RED_LEGAL_TURN = new ColoredCube(Color.RED,12);
    private final static ColoredCube BLUE_LEGAL_TURN = new ColoredCube(Color.BLUE,14);
    private final static ColoredCube GREEN_LEGAL_TURN = new ColoredCube(Color.GREEN,13);


    static final  Map<Color, ColoredCube> LEGAL_TURN_MAP = new HashMap<Color, ColoredCube>() {{
        put(Color.RED, RED_LEGAL_TURN);
        put(Color.BLUE, BLUE_LEGAL_TURN);
        put(Color.GREEN, GREEN_LEGAL_TURN);
    }};

    private final static TurnCubes LEGAL_TURN = new TurnCubes(LEGAL_TURN_MAP);

    public static void main(String[] args) {
//        partOne();
        partTwo();
    }

    public static void partOne() {

        List<Game> games = inputGames(INPUT_DATA_PATH);
        assert ! games.isEmpty();
        System.out.println("Games: " + games);

        int sum = 0;

        for (Game game : games) {
            System.out.println("Game: " + game.gameNumber());
            if (possible(game, LEGAL_TURN)) {
                sum += game.gameNumber();
                System.out.println("--> Legal : (" + sum + ")");
            }
        }
        System.out.println("Sum: " + sum);

    }

    public static void partTwo() {

        List<Game> games = inputGames(INPUT_DATA_PATH);
        assert ! games.isEmpty();
        System.out.println("Games: " + games);

        int sum = 0;
        for (Game game : games) {
            System.out.println("Game: " + game.gameNumber());
            int power = power(game);
            sum += power;
            System.out.println("--> Legal : (" + power + ") --> " + sum);
        }
        System.out.println("Sum: " + sum);

    }

    private static int power(Game game){
        TurnCubes minimumSet = minimumSet(game);
        int returnPower = 1;
        for(Color color : Color.values()){
            returnPower *= minimumSet.coloredCubesMap().get(color).count();
        }
        return returnPower;
    }

    private static TurnCubes minimumSet(Game game){

        Map<Color, ColoredCube> turnCubesMap = new HashMap<>();
        turnCubesMap.put(Color.RED, initialColoredCube(Color.RED));
        turnCubesMap.put(Color.BLUE, initialColoredCube(Color.BLUE));
        turnCubesMap.put(Color.GREEN, initialColoredCube(Color.GREEN));

        for(TurnCubes turnCubes : game.turnCubesList()){

            for(Color color : Color.values()){
                int colorTurnCount = count(turnCubes, color);
                if (turnCubesMap.get(color).count() < colorTurnCount){
                    turnCubesMap.put(color, new ColoredCube(color,colorTurnCount));
                }
            }
        }
        return new TurnCubes(turnCubesMap);
    }

    private static int count(TurnCubes turnCubes, Color color){
        return turnCubes.coloredCubesMap().get(color) == null ? 0 : turnCubes.coloredCubesMap().get(color).count();
    }

    private static ColoredCube initialColoredCube(Color color){
        return new ColoredCube(color,0);
    }
    private static boolean possible(Game game, TurnCubes legalTurn) {

        for(TurnCubes turnCubes : game.turnCubesList()){
            for(Color color : Color.values()){
                if (!legal(turnCubes, legalTurn, color)){
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean legal(TurnCubes gameTurn, TurnCubes legalTurn, Color color) {

        if ( null == gameTurn.coloredCubesMap().get(color)) {
            return true;
        }

        return gameTurn.coloredCubesMap().get(color).count() <= legalTurn.coloredCubesMap().get(color).count();
    }
    private static List<Game> inputGames(String inputDataPath){

        List<String> inputLines = input(inputDataPath);
        assert inputLines != null;

        List<Game> games = new ArrayList<>();
        for(String inputLine : inputLines){

            // Extract game number - starting at offset ( zero based ) 5 up to first colon
            int gameNumberOffset = inputLine.indexOf(GAME_SEPARATOR);
            int gameNumber = Integer.parseInt(inputLine.substring(GAME_HEADING_OFFSET,gameNumberOffset).trim());
            System.out.println("gameNumber: " + gameNumber);

            // Extract turns - substring out the game number info, using split to extract turns
            String turnsLine = inputLine.substring(gameNumberOffset + 1);
            System.out.println("turnsLine: " + turnsLine);
            String[] turns = turnsLine.split(TURN_SEPARATOR);

            List<TurnCubes> turnCubesList = new ArrayList<>();
            for(String turn : turns){

                Map<Color, ColoredCube> coloredCubesMap = new TreeMap<>();
                String[] colorElements = turn.split(GAMECUBE_COLOR_SEPARATOR);
                for (String colorElement : colorElements){
                    System.out.println("colorElement: <" + colorElement + ">");
                    colorElement = colorElement.trim();
                    System.out.println("colorElement: <" + colorElement + ">");
                    String[] countAndColor = colorElement.split(" ");
                    System.out.println("count(before conversion: <" + countAndColor[0] + ">");
                    System.out.println("color(before conversion: <" + countAndColor[1] + ">");
                    int count = Integer.parseInt(countAndColor[0].trim());
                    Color color = Color.fromValue(countAndColor[1].trim());
                    ColoredCube coloredCube = new ColoredCube(color, count);
                    coloredCubesMap.put(color, coloredCube);
                }

                System.out.println("coloredCubesMap: " + coloredCubesMap);

                turnCubesList.add(new TurnCubes(coloredCubesMap));
            }

            games.add(new Game(gameNumber,turnCubesList));
        }
        return games;
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
