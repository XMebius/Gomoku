package com.tongji.gomokuserver;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173") // 允许前端端口的跨域请求
public class ChessController {
    private GameService gameService = new GameService();
    @PostMapping("/api/chess/move")
    public ResponseEntity<?> handleChessMove(@RequestBody Move move) {
        // 输出收到的数据结果
        System.out.println(move);
        System.out.println("Move received: " + move.getRole() + " " + move.getPos()[0] + " " + move.getPos()[1]);

        // 处理落子
        gameService.process(move.getPos()[0], move.getPos()[1], move.getRole());

        // 返回结果
        int [] nextStep = gameService.getPos();
        System.out.println("Next step: " + nextStep[0] + " " + nextStep[1]);

        // 返回数据到前端
        if (gameService.isGameEnded()) {
            Move endMove = new Move();
            if(gameService.getWinner()==1){
                endMove.setRole(3); // 表示人类胜利
                endMove.setPos(-1,-1);
                System.out.println("人类胜利，endMove设置成功");
            }
            else{
                endMove.setRole(4); // 表示AI胜利
                endMove.setPos(nextStep[0], nextStep[1]);
                System.out.println("AI胜利，endMove设置成功");
            }
            return ResponseEntity.ok().body(endMove);
        }
        // 非结束状态
        Move aiMove = new Move();
        aiMove.setPos(nextStep[0], nextStep[1]);
        aiMove.setRole(2);  // AI黑棋
        System.out.println("非结束状态，AI落子成功");
        return ResponseEntity.ok().body(aiMove);    // 返回AI的下一步棋
    }

    @PostMapping("/api/chess/difficulty")
    public ResponseEntity<?> handleDifficultySetting(@RequestBody Map<String, Integer> difficultyMap) {
        int difficulty = difficultyMap.getOrDefault("difficulty", 1); // 简单：1；一般：2；困难：3
        System.out.println("Difficulty received: " + difficulty);
        gameService.setDifficulty(difficulty);
        return ResponseEntity.ok().body("难度设置成功");
    }

    @PostMapping("/api/chess/reset")
    public ResponseEntity<?> resetGame() {
        System.out.println("Game reset");
        gameService.restGame();
        return ResponseEntity.ok().body("Game reset successfully");
    }

    @PostMapping("/api/chess/undo")
    public ResponseEntity<?> handleUndoMove(@RequestBody List<Move> moves) {
        if (moves.size() != 2) {
            return ResponseEntity.badRequest().body("Invalid move count for undo");
        }
        System.out.println("Undo moves received");
        gameService.undoMoves(moves);
        return ResponseEntity.ok().body("Undo successful");
    }
}

class Move {
    private int x;
    private int y;
    private int role; // 人类白棋，1为白棋，2为黑棋（AI）

    public int[] getPos() {
        int[] pos = new int[2];
        pos[0] = this.x;
        pos[1] = this.y;
        return pos;
    }
    public int getRole() {
        return this.role;
    }

   public void setPos(int x,int y){
        this.x = x;
        this.y = y;
   }
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    public void setRole(int role) {
        this.role = role;
    }
}