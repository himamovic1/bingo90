package org.example;

public class Main {
    public static void main(String[] args) {
        System.out.println("Generating single bingo ticket");

        var startTime = System.currentTimeMillis();
        var bingo = BingoGenerator.generateBingo90Board();
        var endTime = System.currentTimeMillis();

        System.out.println("Total execution time: " + (endTime - startTime) + "ms");
        Utils.printBingoBoard(bingo, 18, 9);
    }
}