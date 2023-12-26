package com.tongji.gomokuserver;

import java.util.*;
import java.awt.*;

// 树节点
public class TreeNode {
    public TreeNode bestChild=null;
    public ArrayList<TreeNode> child=new ArrayList<TreeNode>();
    public Point p=new Point();
    public int mark;
    TreeNode(){
        this.child.clear();
        bestChild=null;
        mark=0;
    }
    public void setPoint(Point r){
        p.x=r.x;
        p.y=r.y;
    }
    public void addChild(TreeNode r){
        this.child.add(r);
    }
    public TreeNode getLastChild(){
        return child.get(child.size()-1);
    }
}


