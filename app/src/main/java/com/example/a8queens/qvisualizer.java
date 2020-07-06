package com.example.a8queens;

public class qvisualizer {
    private int n;
    public int[] solution;
    private boolean[] rowsUsed;
    private boolean[] upDiagonal;
    private boolean[] downDiagonal;
    private int solNum = 0;

    public qvisualizer(int n) {
        this.n = n;
        this.solution = new int[n];
        this.rowsUsed = new boolean[n];
        this.upDiagonal = new boolean[2 * n];
        this.downDiagonal = new boolean[2 * n];
    }

    public int[] queens(int row, int column) {
        boolean done = false;
       int[] rowthencol = new int[2];

        while (!done) {
            if (checkSquare(row, column)) {
                solution[column] = row;
                setSquare(row, column, true);
                rowthencol[0] = row;
                rowthencol[1] = column;
                if (column + 1 >= n) {
                    System.out.println(n + "-Queens Solution #" + (++solNum));
                    printBoard();
                    setSquare(row, column, false);
                    rowthencol[0] = row;
                    rowthencol[1] = column;
                    return rowthencol;
                } else {
                    column++;
                    row = 0;
                    continue;
                }
            } else if (row + 1 < n) {
                row++;
                continue;
            }
            if (n == 1) {// corner case if n==1
                break;
            }
            // Backtracking Loop
            while (column > 0) {
                column--;
                setSquare(solution[column], column, false);
                row = solution[column] + 1;

                if (row >= n) {
                    continue;
                }
                break;
            }
            if (column == 0 && row == n) {
                done = true;
            }
        }
        return rowthencol;
    }

    private boolean checkSquare(int row, int column) {
        return !rowsUsed[row] && !upDiagonal[(row - column) + (n - 1)] && !downDiagonal[row + column];
    }

    private void setSquare(int row, int column, boolean used) {
        rowsUsed[row] = used;
        upDiagonal[(row - column) + (n - 1)] = used;
        downDiagonal[row + column] = used;
    }

    public void printBoard() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < solution[i]; j++) {
                System.out.print("|O| ");
            }
            System.out.print("|Q| ");
            for (int j = solution[i] + 1; j < n; j++) {
                System.out.print("|O| ");
            }
            System.out.println();
        }
    }
}