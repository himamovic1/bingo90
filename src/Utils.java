public final class Utils {

    public static void printBingoBoard(int[][] bingo, int rows, int cols) {
        for (int row = 0; row < rows; row++) {
            // separate strips with empty rows
            if (row > 0 && row % 3 == 0) {
                System.out.println("---------------------------");
            }

            // print out each column in the row
            for (int col = 0; col < cols; col++) {
                if (bingo[row][col] < 1) System.out.print("   ");
                else System.out.printf("%2s ", bingo[row][col]);
            }

            System.out.printf("%n");
        }
    }
}
