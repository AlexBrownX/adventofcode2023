package org.alexbrownx;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DayOne {

    private enum Numbers {
        zero, one, two, three, four, five, six, seven, eight, nine
    }

    public static void run() throws IOException, URISyntaxException {
        final List<String> lines = Files.readAllLines(Paths.get(DayOne.class.getClassLoader().getResource("dayone.txt").toURI()));

        final int partOneResult = partOne(lines);
        System.out.println("Day 1 Part 1 Result = " + partOneResult);

        final int partTwoResult = partTwo(lines);
        System.out.println("Day 1 Part 2 Result = " + partTwoResult);
    }

    private static int partOne(final List<String> lines) {
        int result = 0;

        for (final String line : lines) {
            final Matcher matcher = Pattern.compile("\\d").matcher(line);
            final List<Integer> results = new ArrayList<>();

            while(matcher.find()) {
                results.add(Integer.parseInt(matcher.group()));
            }

            int lineTotal = Integer.parseInt(results.get(0).toString() + results.get(results.size() - 1).toString());
            result += lineTotal;
        }

        return result;
    }

    private static int partTwo(final List<String> lines) {
        int result = 0;

        for (final String line : lines) {
            final String modifiedLine = separateOverlaps(line);
            final Matcher matcher = Pattern.compile("\\d|(one|two|three|four|five|six|seven|eight|nine)").matcher(modifiedLine);
            final List<Integer> results = new ArrayList<>();

            while(matcher.find()) {
                final String matchString = matcher.group();

                if (matchString.length() == 1) {
                    results.add(Integer.parseInt(matchString));
                } else {
                    final int digit = Numbers.valueOf(matchString).ordinal();
                    results.add(digit);
                }
            }

            final String toAdd = results.get(0).toString() + results.get(results.size() - 1).toString();
            int lineTotal = Integer.parseInt(toAdd);
            result += lineTotal;
        }

        return result;
    }

    private static String separateOverlaps(final String line) {
        String modified = line.replaceAll("twone", "twoone");
        modified = modified.replaceAll("oneight", "oneeight");
        modified = modified.replaceAll("threeight", "threeeight");
        modified = modified.replaceAll("fiveight", "fiveeight");
        modified = modified.replaceAll("nineight", "nineeight");
        modified = modified.replaceAll("sevenine", "sevennine");
        modified = modified.replaceAll("eighthree", "eightthree");
        modified = modified.replaceAll("eightwo", "eighttwo");
        return modified;
    }
}