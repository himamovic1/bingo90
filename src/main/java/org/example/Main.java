package org.example;

import org.example.bingo.BingoGenerator;
import org.example.bingo.Utils;
import org.example.random.ShuffleRangeGenerator;

import java.util.ArrayList;
import java.util.Random;

public class Main {
    private static final int BATCH = 10000;


    public static void main(String[] args) {
        System.out.println("Generating single bingo ticket");

        // generate indices for 4 blanks in each row
        var rangeGenerator = new ShuffleRangeGenerator(4, 9);
        var bingoGenerator = new BingoGenerator(rangeGenerator);

        var boards = new ArrayList<int[][]>(BATCH);
        var startTime = System.currentTimeMillis();

        for (int i = 0; i < BATCH; i++)
            boards.add(bingoGenerator.generateBingo90Board());

        var endTime = System.currentTimeMillis();

        System.out.printf("Created [%s] bingo boards with total execution time [%s ms] %n", BATCH, endTime - startTime);
        System.out.println("Random example of a bingo ticked from the generated ones:");

        var rnd = new Random();
        Utils.printBingoBoard(boards.get(rnd.nextInt(BATCH)), 18, 9);
    }
}