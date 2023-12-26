package com.tongji.gomokuserver;
import java.util.Vector;

public class Search extends java.util.Vector {
    private int []Bestmove=new int[2];
    private final int N = Board.N;
    private int AI_SEARCH_DEPTH = 4;
    private int AI_LIMITED_MOVE_NUM = 20;
    private int FIVE,FOUR,THREE,TWO,SFOUR,STHREE,STWO;
    private  int SCORE_MAX = 0x7fffffff,SCORE_MIN = -1 * SCORE_MAX;
    private int SCORE_FIVE=100000, SCORE_FOUR=10000, SCORE_SFOUR =1000;
    private int SCORE_THREE=100, SCORE_STHREE=10, SCORE_TWO=8, SCORE_STWO =  2;
    private int [][][]record=new int[N][N][4];
    private int [][]count=new int[2][8];

    public void setDifficulty(int difficulty) {
        switch (difficulty) {
            case 1:
                AI_SEARCH_DEPTH = 2;
                AI_LIMITED_MOVE_NUM = 10;
                break;
            case 2:
                AI_SEARCH_DEPTH = 3;
                AI_LIMITED_MOVE_NUM = 20;
                break;
            case 3:
                AI_SEARCH_DEPTH = 4;
                AI_LIMITED_MOVE_NUM = 30;
                break;
            default:
                AI_SEARCH_DEPTH = 4;
                AI_LIMITED_MOVE_NUM = 20;
                break;
        }
    }

    public boolean CheckWin(int [][]board,int turn){
        if(Evaluate(board,turn)>=SCORE_FIVE)
            return true;
        else
            return false;
    }
    class CHESS_TYPE{
        int NONE = 0,
                SLEEP_TWO = 1,
                LIVE_TWO = 2,
                SLEEP_THREE = 3,
                LIVE_THREE = 4,
                CHONG_FOUR = 5,
                LIVE_FOUR = 6,
                LIVE_FIVE = 7;
    }

    class Point{
        int score,x,y;
        private Point(int score,int x,int y){
            this.score=score;
            this.x=x;
            this.y=y;
        }
    }

    private void Reset(){
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < 4; k++) {
                    record[i][j][k]=0;
                }
            }
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                count[i][j]=0;
            }
        }
    }

    public Search(){
        CHESS_TYPE type=new CHESS_TYPE();
        FIVE=type.LIVE_FIVE;
        FOUR=type.LIVE_FOUR;
        THREE=type.LIVE_THREE;
        TWO=type.LIVE_TWO;
        SFOUR=type.CHONG_FOUR;
        STHREE=type.SLEEP_THREE;
        STWO=type.SLEEP_TWO;
        Bestmove[0]=8;
        Bestmove[1]=8;
        Reset();
    }

    public int[] getAIPos(int [][]board,int turn){
        int score=Alpha_Beta(board,turn,AI_SEARCH_DEPTH,SCORE_MIN,SCORE_MAX);
//        System.out.println(score);
        return Bestmove;
    }

    private int Evaluate(int [][]board,int turn){
        int score,mine,opponent;
        Reset();
        if (turn==1){
            mine=1;
            opponent=2;
        }
        else{
            mine=2;
            opponent=1;
        }
        for (int x = 0; x < N; x++) {
            for (int y = 0; y <N ; y++) {
                if(board[x][y]==mine){
//                    System.out.println("X+Y"+x+" "+y);
                    EvaluatePoint(board,x,y,mine,opponent,false);
                }
                else if(board[x][y]==opponent){
//                    System.out.println("X+Y"+x+" "+y);
                    EvaluatePoint(board,x,y,opponent,mine,false);
                }
            }
        }
        int []mine_count=count[mine-1];
        int []opponent_count=count[opponent-1];
        score=GetScore(mine_count,opponent_count);
        return score;
    }


    private void EvaluatePoint(int[][]board,int x,int y,int mine,int opponent,boolean ex){
        int [][]offset=new int[][]{{1,0},{0,1},{1,1},{1,-1}};//从左到右判断一条线
//        if(ex)
//            System.out.println(111);
        for (int i = 0; i < 4; i++) {
            if (record[x][y][i]==0 ||ex){
                AnalysisLine(board,x,y,i,offset[i],mine,opponent);
            }
        }

    }


    private int AnalysisLine(int [][]board,int x,int y,int index,int []offset,int mine,int opponent){
        int right=4,left=4;
        int empty=0;
        int []line=GetLine(board,x,y,offset,mine,opponent);
//        for (int i = 0; i < 9; i++) {
//            System.out.print(line[i]);
//        }
//        System.out.println();
        //记录己方连在一起的棋子
        while (right<8){
            if(line[right+1]!=mine){
                break;
            }
            right+=1;
        }
        while (left>0){
            if (line[left-1]!=mine)
                break;
            left-=1;
        }
        //记录己方的空格
        int right_range=right,left_range=left;
        while (right_range<8){
            if (line[right_range+1]==opponent)
                break;
            right_range+=1;
        }
        while (left_range>0){
            if (line[left_range-1]==opponent)
                break;
            left_range-=1;
        }
        int chess_range = right_range - left_range + 1;//己方有效棋子
        if(chess_range<5){
            setRecord(x, y, left_range, right_range, index, offset);
            return 0;
        }
        //记录已经分析过的棋子
        setRecord( x, y, left, right, index, offset);
        //记录一色点
        int m_range = right - left + 1;
        //长连
        if(m_range>=5)
            count[mine-1][FIVE]+=1;
        //活四和冲四
//         Live Four : XMMMMX
////       Chong Four : XMMMMP, PMMMMX
        if(m_range==4){
            boolean left_empty =false, right_empty = false;
            if (line[left - 1] == empty)
                left_empty = true;
            if (line[right + 1] == empty)
                right_empty = true;
            if (left_empty && right_empty)
                count[mine-1][FOUR] += 1;
            else if (left_empty || right_empty)
                count[mine-1][SFOUR] += 1;
        }
//        # Chong Four : MXMMM, MMMXM, the two types can both exist
//        # Live Three : XMMMXX, XXMMMX
//        # Sleep Three : PMMMX, XMMMP, PXMMMXP
        if(m_range==3){
            boolean left_empty=false,  right_empty = false;
            boolean left_four=false , right_four = false;
            if (line[left - 1] == empty){
                if (line[left - 2] == mine){//  # MXMMM
                    setRecord( x, y, left - 2, left - 1, index, offset);
                    count[mine-1][SFOUR] += 1;
                    left_four = true;
                }
                left_empty=true;
            }
            if (line[right + 1] == empty){
                if (line[right + 2] == mine){//MMMXM
                    setRecord( x, y, right + 1, right + 2, index, offset);
                    count[mine-1][SFOUR] += 1;
                    right_four = true;
                }
                right_empty = true;
            }
            if (left_four || right_four){

            }
            else if(left_empty && right_empty){
                if (chess_range > 5)       //# XMMMXX, XXMMMX
                    count[mine-1][THREE] += 1;
                else   //# PXMMMXP
                    count[mine-1][STHREE] += 1;
            }
            else if(left_empty || right_empty) //PMMMX, XMMMP
                count[mine-1][STHREE] += 1;
        }
//        # Chong Four: MMXMM, only check right direction
////        # Live Three: XMXMMX, XMMXMX the two types can both exist
////        # Sleep Three: PMXMMX, XMXMMP, PMMXMX, XMMXMP
////        # Live Two: XMMX
////        # Sleep Two: PMMX, XMMP
        if(m_range==2){
            boolean left_empty =false, right_empty = false;
            boolean left_three = false,right_three = false;
            if (line[left - 1] == empty) {
                if (line[left - 2] == mine) {
                    setRecord(x, y, left - 2, left - 1, index, offset);
                    if (line[left - 3] == empty) {
                        if (line[right + 1] == empty) { //XMXMMX
                            count[mine - 1][THREE] += 1;
                        } else {
                            count[mine-1][STHREE] += 1;
                        }
                        left_three = true;
                    } else if (line[left - 3] == opponent) { //# PMXMMX
                        if (line[right + 1] == empty) {
                            count[mine - 1][STHREE] += 1;
                            left_three = true;
                        }
                    }
                }
                left_empty = true;
            }
            if(line[right + 1] == empty){
                if(line[right + 2] == mine){
                    if(line[right + 3] == mine){ //MMXMM
                        setRecord( x, y, right + 1, right + 2, index, offset);
                        count[mine-1][SFOUR] += 1;
                        right_three = true;
                    }
                    else if(line[right + 3] == empty){
                        if (left_empty){ //XMMXMX
                            count[mine-1][THREE] += 1;
                        }
                        else
                            count[mine-1][STHREE]+=1;
                        right_three = true;
                    }
                    else if(left_empty){ //XMMXMP
                        count[mine-1][STHREE] += 1;
                        right_three = true;
                    }
                }
                right_empty = true;

            }
            if(left_three || right_three){

            }
            else if(left_empty && right_empty){ //XMMX
                count[mine-1][TWO] += 1;
            }
            else if(left_empty || right_empty){ //# PMMX, XMMP
                count[mine-1][STWO] += 1;
            }
        }
//        Live Two: XMXMX, XMXXMX only check right direction
//        # Sleep Two: PMXMX, XMXMP
        if(m_range==1){
            boolean left_empty =false,right_empty = false;
            if(line[left - 1] == empty){
                if(line[left - 2] == mine){
                    if(line[left - 3] == empty){
                        if(line[right + 1] == opponent){ //XMXMP
                            count[mine-1][STWO] += 1;
                        }
                    }
                }
                left_empty = true;
            }
            if(line[right + 1] == empty){
                if(line[right + 2] == mine){
                    if(line[right + 3] == empty){
                        if(left_empty){ //XMXMX
                            count[mine-1][TWO] += 1;
                        }
                        else {  //PMXMX
                            count[mine-1][STWO] += 1;
                        }
                    }
                }
                else  if(line[right + 2] == empty){
                    if(line[right + 3] == mine && line[right + 4] == empty){ //XMXXMX
                        count[mine-1][TWO] += 1;
                    }
                }
            }
        }
        return 0;


    }


    private void setRecord(int x,int  y,int  left, int right, int dir_index, int []dir_offset){
        int tmp_x = x + (-5 + left) * dir_offset[1];
        int tmp_y = y + (-5 + left) * dir_offset[0];
        for (int i=left;i<=right;i++){
            tmp_x += dir_offset[1];
            tmp_y += dir_offset[0];
        }
        record[tmp_x][tmp_y][dir_index] = 1;
    }


    private int GetScore(int []mine_count,int []op_count){
        int score=0,mscore=0,oscore=0;
        if(mine_count[FIVE]>0)
            return SCORE_FIVE;
        if(op_count[FIVE]>0)
            return -SCORE_FIVE;
        if(mine_count[SFOUR]>=2)
            mine_count[FOUR]+=1;
        if(op_count[SFOUR]>=2)
            op_count[FOUR]+=1;
        if(mine_count[FOUR]>0)
            return 9050;
        if(mine_count[SFOUR]>0)
            return 9040;
        if(op_count[FOUR]>0)
            return -9030;
        if(op_count[SFOUR]>0&&op_count[THREE]>0)
            return -9020;
        if(mine_count[THREE]>0&&op_count[SFOUR]==0)
            return 9010;
        if (op_count[THREE] > 1 && mine_count[THREE] == 0 && mine_count[STHREE] == 0)
            return -9000;
        if (op_count[SFOUR] > 0)
            oscore += 400;

        if (mine_count[THREE] > 1)
            mscore += 500;
        else if (mine_count[THREE] > 0)
            mscore += 100;
        if (op_count[THREE] > 1)
            oscore += 2000;
        else if(op_count[THREE]>0)
            oscore += 400;

        if (mine_count[STHREE] > 0)
            mscore += mine_count[STHREE] * 10;
        if (op_count[STHREE] > 0)
            oscore += op_count[STHREE] * 10;
        if (mine_count[TWO] > 0)
            mscore += mine_count[TWO] * 6;
        if (op_count[TWO] > 0)
            oscore += op_count[TWO] * 6;

        if (mine_count[STWO] > 0)
            mscore += mine_count[STWO] * 2;
        if (op_count[STWO] > 0)
            oscore += op_count[STWO] * 2;

        score=mscore-oscore;
        return score;
    }


    private int[] GetLine(int [][]board, int x, int y, int []offset, int mine, int opponent){
        int []line=new int[9];
        int tmp_x = x + (-5 * offset[1]);
        int tmp_y = y + (-5 * offset[0]);
        for (int i = 0; i < 9; i++) {
            tmp_x += offset[1];
            tmp_y += offset[0];
            if (tmp_x < 0 ||tmp_x >= N ||tmp_y < 0 || tmp_y >= N){
                line[i] = opponent ; //# set out of range as opponent chess
            }
            else
                line[i] = board[tmp_x][tmp_y];
        }

        return line;

    }


    private int  Alpha_Beta(int [][]board,int turn,int depth,int alpha,int beta){
        int op_turn;
        int score=Evaluate(board,turn);
        if(depth<=0||Math.abs(score)>=SCORE_FIVE) {
//            System.out.println("score"+score);
            return score;
        }
        Vector<Point> moves=Genmove(board,turn);
        int []bestmove={0,0};
        if(moves.size()==0)
            return score;
//        System.out.println("11112");
        for (Point point:moves){
//            System.out.println(point.x+" "+point.y);
            board[point.x][point.y]=turn;
            if(turn==1)
                op_turn=2;
            else
                op_turn=1;
            score=-Alpha_Beta(board,op_turn,depth-1,-beta,-alpha);
            board[point.x][point.y]=0;
            if(score>alpha){
                alpha=score;
                bestmove[0]=point.x;
                bestmove[1]=point.y;
                if(alpha>=beta)
                    break;
            }

        }
        if(depth==AI_SEARCH_DEPTH)
            Bestmove=bestmove;
        return alpha;

    }


    private Vector<Point> Genmove (int [][]board, int turn){
        Vector<Point> moves=new Vector<Point>();
        Vector<Point> fives=new Vector<>();
        Vector<Point> mfours=new Vector<Point>();
        Vector<Point> ofours=new Vector<>();
        Vector<Point> msfours=new Vector<>();
        Vector<Point> osfours=new Vector<>();
        int radius=1;
        int mine,opponent;
        if(turn==1){
            mine=1;
            opponent=2;
        }
        else{
            mine=2;
            opponent=1;
        }
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                if(board[x][y]==0&&Hasneighbor(board,x,y,radius)){
//                    System.out.println("xy"+x+" "+y);
                    int []score=EvaluatePointScore(board,x,y,mine,opponent);
                    int mscore=score[0],oscore=score[1];
                    Point point =new Point(Math.max(mscore,oscore),x,y);
//                    System.out.println("score="+mscore+" "+oscore);
                    if(mscore>=SCORE_FIVE||oscore>=SCORE_FIVE)
                        fives.add(point);
                    else if(mscore>=SCORE_FOUR)
                        mfours.add(point);
                    else if(oscore>=SCORE_FOUR)
                        ofours.add(point);
                    else if(mscore>=SCORE_SFOUR)
                        msfours.add(point);
                    else if(oscore>=SCORE_SFOUR)
                        osfours.add(point);
                    moves.add(point);
                }
            }
        }
        if (fives.size()>0)
            return fives;
        if (mfours.size()>0)
            return mfours;
        if(ofours.size()>0 ){
            if(msfours.size()==0)
                return ofours;
            else{
                ofours.addAll(mfours);
                return ofours;
            }
        }
        moves=Sort(moves);
        if(AI_SEARCH_DEPTH>2&&moves.size()>AI_LIMITED_MOVE_NUM)
            moves.subList(AI_LIMITED_MOVE_NUM,moves.size()).clear();
        return moves;
    }

    private boolean Hasneighbor(int [][]board,int x,int y,int radius){
        int start_x=x-radius,end_x=x+radius;
        int start_y=y-radius,end_y=y+radius;
        for (int i = start_x; i <=end_x; i++) {
            for (int j = start_y; j <= end_y; j++) {
                if(i>=0&&i<N&&j>=0&&j<N){
                    if (board[i][j]!=0){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private int[] EvaluatePointScore(int [][]board,int x,int y,int mine ,int opponent){
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                count[i][j]=0;
            }
        }
        board[x][y]=mine;
        EvaluatePoint(board,x,y,mine,opponent,true);
        int []mine_count=count[mine-1];
//        System.out.print("count");
//        for (int i = 0; i < mine_count.length; i++) {
//            System.out.print(mine_count[i]);
//        }
//        System.out.println();
        board[x][y]=opponent;
        EvaluatePoint(board,x,y,opponent,mine,true);
        int []op_count=count[opponent-1];
//        System.out.print("count");
//        for (int i = 0; i < op_count.length; i++) {
//            System.out.print(op_count[i]);
//        }
//        System.out.println();
        board[x][y]=0;

        int mscore=GetPointScore(mine_count);
        int oscore=GetPointScore(op_count);
        int []score=new int[2];
        score[0]=mscore;
        score[1]=oscore;
        return score;
    }

    private int GetPointScore(int []count1){
        int score=0;
        if(count1[FIVE]>0)
            return SCORE_FIVE;
        if(count1[FOUR]>0)
            return SCORE_FOUR;

        if(count1[SFOUR]>1)
            score+=count1[SFOUR]*SCORE_SFOUR;
        else if(count1[SFOUR]>0&&count1[THREE]>0)
            score += count1[SFOUR] * SCORE_SFOUR;
        else if(count1[SFOUR]>0)
            score+=SCORE_THREE;

        if(count1[THREE]>1)
            score+=5*SCORE_THREE;
        else if(count1[THREE]>0)
            score+=SCORE_THREE;

        if(count1[STHREE]>0)
            score+=count1[STHREE]*SCORE_STHREE;

        if(count1[TWO]>0)
            score+=count1[TWO]*SCORE_TWO;

        if(count1[STWO]>0)
            score+=count1[STWO]*SCORE_STWO;
        return score;
    }

    private Vector<Point> Sort(Vector<Point> points){
        for (int i = 0; i < points.size() - 1; i++) {
            int temp=i;
            for (int j = i+1; j < points.size(); j++) {
                if(points.get(temp).score<=points.get(j).score)
                    temp=j;
            }
            if(temp!=i){
                Point point=points.get(temp);
                points.set(temp,points.get(i));
                points.set(i,point);
            }
        }
        return points;
    }

//    public static void main(String[] args) {
//        //Scanner sc=new Scanner(System.in);
//        ChessAI AI=new ChessAI();
//        int [][]board=new int[N][N];
//        int turn=2;
//        for (int i = 0; i < N; i++) {
//            for (int j = 0; j < N; j++) {
//                board[i][j]=0;
//            }
//        }
//        board[7][7]=1;
////        board[8][8]=2;
////        board[8][6]=1;
//        int []Bestmove=AI.FindBestmove(board,turn);
//        System.out.println(Bestmove[0]);
//        System.out.println(Bestmove[1]);
//    }
}