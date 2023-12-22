package com.tongji.gomokuserver;

import org.springframework.stereotype.Service;

@Service
public class GameService {
    private Board board = new Board();
    private int[] nextStep = new int[2];
    private boolean gameEnded = false;
    private int winner = 0;
    public GameService() {
        // 初始化棋盘
        board.initBoard();
    }
    public void process(int posx, int posy, int player) {
        if(gameEnded) {
            System.out.println("GameService.process() called but game has ended");
            return;
        }
        System.out.println("GameService.process() called");
        // 处理落子
        board.makeMove(posx, posy, player);
        if(board.isWin(player)) {
            gameEnded = true;
            winner = player;
            return;
        }
        System.out.println("Player " + player + " made move at " + posx + " " + posy);
        // 计算下一步
        this.nextStep = Search.minMax(board, 1);
        board.makeMove(this.nextStep[0], nextStep[1], 2);
        if(board.isWin(2)) {
            gameEnded = true;
            winner = 2;
            return;
        }
        board.printBoard();
    }

    public int[] getPos(){
        return this.nextStep;
    }

    public void setDifficulty(int difficulty) {
        Search.depth = difficulty;
        System.out.println("Difficulty set to " + difficulty);
    }

    public void restGame() {
        board.initBoard();
        gameEnded = false;
        winner = 0;
    }

    public int getWinner() {
        return this.winner;
    }

    public boolean isGameEnded() {
        return this.gameEnded;
    }
}
