package org.alexbrownx;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DayThree {

    private record LocatedNumber(int partNumber, int startIndex, int endIndex) { }

    public static void run() throws IOException, URISyntaxException {
        final List<String> rows = Files.readAllLines(Paths.get(DayOne.class.getClassLoader().getResource("daythree.txt").toURI()));

        final int partOneResult = partOne(rows);
        System.out.println("Day 3 Part 1 Result = " + partOneResult);
    }

    private static int partOne(final List<String> rows) {
        int result = 0;
        int rowNumber = 0;

        for (final String row : rows) {
            final List<LocatedNumber> locatedNumbers = findNumbers(row);

            for (LocatedNumber locatedNumber : locatedNumbers) {
                if (isPartNumber(rows, rowNumber, locatedNumber)) {
                    result += locatedNumber.partNumber;
                }
            }

            rowNumber++;
        }

        return result;
    }

    private static boolean isPartNumber(final List<String> rows, final int rowNumber, final LocatedNumber locatedNumber) {
        // check left
        if (locatedNumber.startIndex > 0) {
            final String row = rows.get(rowNumber);
            final char charToTheLeft = row.toCharArray()[locatedNumber.startIndex - 1];
            if (isPartSymbol(charToTheLeft)) {
                return true;
            }
        }

        // check right
        if (locatedNumber.endIndex < rows.get(rowNumber).length() - 1) {
            final String row = rows.get(rowNumber);
            final char charToTheRight = row.toCharArray()[locatedNumber.endIndex + 1];
            if (isPartSymbol(charToTheRight)) {
                return true;
            }
        }

        // check above
        if (rowNumber > 0) {
            final String row = rows.get(rowNumber - 1);
            if (hasPartAdjacent(locatedNumber, row)) {
                return true;
            }
        }

        // check below
        if (rowNumber < rows.size() - 1) {
            final String row = rows.get(rowNumber + 1);
            if (hasPartAdjacent(locatedNumber, row)) {
                return true;
            }
        }

        return false;
    }

    private static boolean hasPartAdjacent(final LocatedNumber locatedNumber, final String row) {
        final int startIndex = locatedNumber.startIndex > 0 ? locatedNumber.startIndex - 1 : locatedNumber.startIndex;
        final int endIndex = locatedNumber.endIndex < row.length() - 1 ? locatedNumber.endIndex + 1 : locatedNumber.endIndex;

        for (int i = startIndex; i <= endIndex ; i++) {
            if (isPartSymbol(row.toCharArray()[i])) {
                return true;
            }
        }

        return false;
    }

    private static boolean isPartSymbol(final char charToCheck) {
        return charToCheck != '.';
    }

    private static List<LocatedNumber> findNumbers(final String line) {
        final Matcher matcher = Pattern.compile("\\d+").matcher(line);
        final List<LocatedNumber> rowNumbers = new ArrayList<>();

        while(matcher.find()) {
            final int number = Integer.parseInt(matcher.group());
            final int startIndex = matcher.start();
            final int endIndex = matcher.end() - 1;

            rowNumbers.add(new LocatedNumber(number, startIndex, endIndex));
        }

        return rowNumbers;
    }
}
