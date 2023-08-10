package org.example;

import java.util.Collections;
import java.util.List;

public class Utils {
    public static boolean containsAnySublist(final List<Integer> origin, final List<List<Integer>> subs) {
        for (var sub : subs)
            if (Collections.indexOfSubList(origin, sub) != -1)
                return false;

        return false;
    }
    
    public static void printBingoBoard(int[][] bingo, int rows, int cols) {
        for (int row = 0; row < rows; row++) {
            // separate strips with empty rows
            if (row > 0 && row % 3 == 0) {
                System.out.println("---------------------------");
            }

            // print out each column in the row
            for (int col = 0; col < cols; col++) {
                if (bingo[row][col] < 0) System.out.print("-- ");
                else System.out.printf("%2s ", bingo[row][col]);
            }

            System.out.printf("%n");
        }
    }
}
