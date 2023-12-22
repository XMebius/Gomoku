package com.tongji.gomokuserver;

public class Search {
    protected static int depth = 1; // 搜索深度

    public static int[] minMax(Board board, int depth) {
        int[] result = new int[3];

        result = Evaluate.evaluate(board);
        return result;
    }

//    public void setDepth(int depth) {
//        this.depth = depth;
//    }
}
