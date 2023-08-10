package org.example;

import org.example.rows.ShuffleRangeGenerator;

public class Main {
    public static void main(String[] args) {
        System.out.println("Generating single bingo ticket");

        // generate indices for 4 blanks in each row
        var rangeGenerator = new ShuffleRangeGenerator(4, 9);
        var bingoGenerator = new BingoGenerator(rangeGenerator);

        var startTime = System.currentTimeMillis();
        var bingo = bingoGenerator.generateBingo90Board();
        var endTime = System.currentTimeMillis();

        System.out.println("Total execution time: " + (endTime - startTime) + "ms");
        Utils.printBingoBoard(bingo, 18, 9);
    }
}