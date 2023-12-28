package com.tongji.gomokuserver;

public class Board {
    protected int[][] board;
    protected static final int N = 19;

    public void initBoard() {
        board = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                board[i][j] = 0;
            }
        }
        printBoard();
    }

    // 仅在控制台时期才使用
    public void printBoard() {
        System.out.println("  0 1 2 3 4 5 6 7 8 9 A B C D E F G H I");
        for (int i = 0; i < N; i++) {
            // 根据索引i来决定打印的字
            if (i <= 9) {
                System.out.print(i + " ");
            } else if (i >= 10 && i <= 15) {
                System.out.print((char) ('A' + i - 10) + " ");
            } else if (i >= 16 && i <= 18) {
                System.out.print((char) ('G' + i - 16) + " ");
            }
            for (int j = 0; j < N; j++) {
                if (board[i][j] == 0) {
                    System.out.print("+" + " ");
                } else {
                    System.out.print(board[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    public void makeMove(int x, int y, int player) {
        board[x][y] = player;
    }

    public void undoMove(int x, int y) {
        // 假设 0 代表棋盘上的空位
        if (x >= 0 && x < N && y >= 0 && y < N) {
            board[x][y] = 0;
            // 打印棋盘
            printBoard();
        } else {
            System.out.println("Invalid board position for undo: " + x + ", " + y);
        }
    }

    public boolean isWin(int player) {
        // Check rows
        for (int i = 0; i < N; i++) {
            for (int j = 0; j <= N - 5; j++) {
                if (board[i][j] == player && board[i][j + 1] == player &&
                        board[i][j + 2] == player && board[i][j + 3] == player &&
                        board[i][j + 4] == player) {
                    return true;
                }
            }
        }
        // Check columns
        for (int j = 0; j < N; j++) {
            for (int i = 0; i <= N - 5; i++) {
                if (board[i][j] == player && board[i + 1][j] == player &&
                        board[i + 2][j] == player && board[i + 3][j] == player &&
                        board[i + 4][j] == player) {
                    return true;
                }
            }
        }
        // Check diagonals (top-left to bottom-right)
        for (int i = 0; i <= N - 5; i++) {
            for (int j = 0; j <= N - 5; j++) {
                if (board[i][j] == player && board[i + 1][j + 1] == player &&
                        board[i + 2][j + 2] == player && board[i + 3][j + 3] == player &&
                        board[i + 4][j + 4] == player) {
                    return true;
                }
            }
        }
        // Check diagonals (top-right to bottom-left)
        for (int i = 0; i <= N - 5; i++) {
            for (int j = N - 1; j >= 4; j--) {
                if (board[i][j] == player && board[i + 1][j - 1] == player &&
                        board[i + 2][j - 2] == player && board[i + 3][j - 3] == player &&
                        board[i + 4][j - 4] == player) {
                    return true;
                }
            }
        }
        return false;
    }
}
