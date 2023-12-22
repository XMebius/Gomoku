package com.tongji.gomokuserver;

import org.springframework.stereotype.Service;

@Service
public class GameService {
    private Board board = new Board();
    private int[] nextStep = new int[2];
    public GameService() {
        // 初始化棋盘
        board.initBoard();
    }
    public void process(int posx, int posy, int player) {
        System.out.println("GameService.process() called");
        // 处理落子
        board.makeMove(posx, posy, player);
        System.out.println("Player " + player + " made move at " + posx + " " + posy);
        // 计算下一步
        this.nextStep = Search.minMax(board, 1);
        board.makeMove(this.nextStep[0], nextStep[1], 2);
        board.printBoard();
    }

    public int[] getPos(){
        return this.nextStep;
    }

    public void setDifficulty(int difficulty) {
        Search.depth = difficulty;
        System.out.println("Difficulty set to " + difficulty);
    }
}
