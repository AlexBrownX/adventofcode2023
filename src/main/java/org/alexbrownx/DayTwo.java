package org.alexbrownx;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class DayTwo {

    private enum Colour {
        red(12),
        green(13),
        blue(14);

        private final int limit;

        Colour(int limit) {
            this.limit = limit;
        }
    }

    private record Pair<Integer, Colour>(Integer count, Colour color) { }

    public static void run() throws IOException, URISyntaxException {
        final List<String> lines = Files.readAllLines(Paths.get(DayOne.class.getClassLoader().getResource("daytwo.txt").toURI()));

        final int partOneResult = partOne(lines);
        System.out.println("Day 2 Part 1 Result = " + partOneResult);

        final int partTwoResult = partTwo(lines);
        System.out.println("Day 2 Part 2 Result = " + partTwoResult);
    }

    private static int partOne(final List<String> lines) {
        int result = 0;
        int gameNumber = 0;

        for (final String line : lines) {
            gameNumber++;
            final List<Pair<Integer, Colour>> subGames = getSubGames(line);

            if (isGamePossible(subGames)) {
                result += gameNumber;
            }
        }

        return result;
    }

    private static int partTwo(final List<String> lines) {
        int result = 0;

        for (final String line : lines) {
            final Map<Colour, Integer> highestColours = getHighestColours(line);
            final int power = highestColours.values().stream().reduce(1, (a, b) -> a * b);

            result += power;
        }

        return result;
    }

    private static Map<Colour, Integer> getHighestColours(final String line) {
        final Map<Colour, Integer> highestColours = new HashMap<>();
        final List<Pair<Integer, Colour>> subGamesSorted = getSubGames(line).stream().sorted(Comparator.comparing(o -> o.count)).toList();

        for (Pair<Integer, Colour> subGame : subGamesSorted) {
            highestColours.put(subGame.color, subGame.count);
        }

        return highestColours;
    }

    private static List<Pair<Integer, Colour>> getSubGames(final String line) {
        final String[] lineSplit = line.split("[:;]");

        final List<String> splitGames = new ArrayList<>();
        for (String subGame : lineSplit) {
            if (!subGame.contains("Game")) {
                splitGames.add(subGame);
            }
        }

        final List<Pair<Integer, Colour>> subGames = new ArrayList<>();
        for (String subGame : splitGames) {
            final String[] gamePairs = subGame.split(",");

            for (String gamePair : gamePairs) {
                final String[] pair = gamePair.trim().split(" ");
                subGames.add(new Pair<>(Integer.parseInt(pair[0]), Colour.valueOf(pair[1])));
            }
        }

        return subGames;
    }

    private static boolean isGamePossible(final List<Pair<Integer, Colour>> subGames) {
        for (Pair<Integer, Colour> subGame : subGames) {
            if (subGame.count > subGame.color.limit) {
                return false;
            }
        }

        return true;
    }
}