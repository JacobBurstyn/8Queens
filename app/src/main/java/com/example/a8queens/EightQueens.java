package com.example.a8queens;

import java.util.Arrays;

public class EightQueens {
    boolean[][] board = new boolean[8][8];

    private int[] colsUsed= new int[8];
    private int[] rowsUsed = new int[8];
    int[] upDiagonal= new int[16];
    int[] downDiagonal= new int[16];

    boolean[][] problemSquares;


    public boolean isWinner(){
        // reset to 0;
        Arrays.fill(colsUsed,0);
        Arrays.fill(rowsUsed,0);
        Arrays.fill(upDiagonal,0);
        Arrays.fill(downDiagonal, 0);

        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {
                if(board[row][col]){
                    rowsUsed[row]++;
                    colsUsed[col]++;
                    upDiagonal[(row-col) + (8-1)]++;
                    downDiagonal[row+col]++;
                }
            }
        }
        problemSquares = new boolean[8][8];
        boolean winner = true;
        for (int i = 0; i < 8; i++) {
            if(rowsUsed[i] != 1 ) {
                for (int j = 0; j < 8; j++) {
                    problemSquares[i][j] = true;
                }
                winner = false;
            }
            if(colsUsed[i] != 1){
                for (int j = 0; j < 8; j++) {
                    problemSquares[j][i] = true;
                }
                winner = false;
            }
        }
        for (int i = 0; i < 16; i++) {
            if(upDiagonal[i] > 1 || downDiagonal[i] > 1){
                winner = false;
            }
        }
        return winner;
    }



}
