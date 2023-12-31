package com.tongji.gomokuserver;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {
    private Board board = new Board();
    private int[] nextStep = new int[2];
    private boolean gameEnded = false;
    private Search search = new Search();
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
        this.nextStep = search.getAIPos(board.board, 2);
        System.out.println("AI made move at " + nextStep[0] + " " + nextStep[1]);
        //this.nextStep = search.minMax(board, 1);
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
        search.setDifficulty(difficulty);
    }

    public void restGame() {
        board.initBoard();
        gameEnded = false;
        winner = 0;
    }

    public void undoMoves(List<Move> moves) {
        for (Move move : moves) {
            board.undoMove(move.getPos()[0], move.getPos()[1]);
            System.out.println("Undo move at " + move.getPos()[0] + " " + move.getPos()[1]);
        }
    }


    public int getWinner() {
        return this.winner;
    }

    public boolean isGameEnded() {
        return this.gameEnded;
    }
}
