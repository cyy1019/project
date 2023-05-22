package model;

import controller.GameController;
import view.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * This class store the real chess information.
 * The Chessboard has 9*7 cells, and each cell has a position for chess
 */
public class Chessboard {
    public static List<Step> stepSet = new ArrayList<>();
    public PlayerColor currentSide;
    public static Cell[][] grid;
    //grid是用来放cell的，
    //要改？？？
    //创建一个方法initialpiecesfromfile，把数组里的数字变成棋子（？用什么方式存储棋盘）
    public int numberOfBlue = 8;
    public int numberOfRed = 8;

    public Chessboard() {//constructor
        this.grid =
                new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];//9*7
        initGrid();
        initPieces();
    }

    private void initGrid() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j] = new Cell();//让棋盘的每一个格子都是cell这个槽
            }
        }
    }

    public void initPieces() {//initialize
        grid[6][0].setPiece(new ChessPiece(PlayerColor.BLUE, "Elephant", 8));//在棋盘grid该位置放一个棋子对象
        grid[2][6].setPiece(new ChessPiece(PlayerColor.RED, "Elephant", 8));
        grid[6][2].setPiece(new ChessPiece(PlayerColor.BLUE, "Wolf", 4));
        grid[2][4].setPiece(new ChessPiece(PlayerColor.RED, "Wolf", 4));
        grid[6][4].setPiece(new ChessPiece(PlayerColor.BLUE, "Leopard", 5));
        grid[2][2].setPiece(new ChessPiece(PlayerColor.RED, "Leopard", 5));
        grid[6][6].setPiece(new ChessPiece(PlayerColor.BLUE, "Rat", 1));
        grid[2][0].setPiece(new ChessPiece(PlayerColor.RED, "Rat", 1));
        grid[7][1].setPiece(new ChessPiece(PlayerColor.BLUE, "Cat", 2));
        grid[1][5].setPiece(new ChessPiece(PlayerColor.RED, "Cat", 2));
        grid[7][5].setPiece(new ChessPiece(PlayerColor.BLUE, "Dog", 3));
        grid[1][1].setPiece(new ChessPiece(PlayerColor.RED, "Dog", 3));
        grid[8][6].setPiece(new ChessPiece(PlayerColor.BLUE, "Lion", 7));
        grid[0][0].setPiece(new ChessPiece(PlayerColor.RED, "Lion", 7));
        grid[8][0].setPiece(new ChessPiece(PlayerColor.BLUE, "Tiger", 6));
        grid[0][6].setPiece(new ChessPiece(PlayerColor.RED, "Tiger", 6));
    }

    public void removeAll() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                if (grid[i][j].getPiece() != null) {
                    grid[i][j].setPiece(null);
                }
            }
        }
    }

    public ChessPiece getChessPieceAt(ChessboardPoint point) {
        return getGridAt(point).getPiece();
    }
    //在传入的点找到棋盘格，返回棋盘格的cell槽里面的piece

    public Cell getGridAt(ChessboardPoint point) {
        return grid[point.getRow()][point.getCol()];
    }//传入一个point，返回在该位置的棋盘的cell槽

    private int calculateDistance(ChessboardPoint src, ChessboardPoint dest) {//传入两个point
        return Math.abs(src.getRow() - dest.getRow()) + Math.abs(src.getCol() - dest.getCol());
    }//计算距离，距离是dy+dx

    public ChessPiece removeChessPiece(ChessboardPoint point) {
        ChessPiece chessPiece = getChessPieceAt(point);
        getGridAt(point).removePiece();//remove the piece in the cell
        return chessPiece;
    }//传入point，移除同时返回被移除的棋子，移除同时返回！！！

    public void setChessPiece(ChessboardPoint point, ChessPiece chessPiece) {
        getGridAt(point).setPiece(chessPiece);
    }

    //传入想要放置棋子的坐标和想放置的棋子，把棋子放在那个位置
    public void moveChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (!isValidMove(src, dest)) {
            throw new IllegalArgumentException("Illegal chess move!");
        }
        setChessPiece(dest, removeChessPiece(src));//把src位置的棋子移除并移到dest位置上
    }

    public void captureChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (!isValidCapture(src, dest)) {
            throw new IllegalArgumentException("Illegal chess capture!");
        }
        removeChessPiece(dest);//dest位置被吃掉,remove the piece in the cell at dest
        moveChessPiece(src, dest);//把src位置的棋子挪到dest
        if (getChessPieceAt(dest).getOwner() == PlayerColor.BLUE) {
            numberOfBlue--;
        }
        if (getChessPieceAt(dest).getOwner() == PlayerColor.RED) {
            numberOfRed--;
        }
    }//capture：令进攻棋子原本的槽里的piece为null，被进攻棋子槽里的piece换成进攻动物

    public Cell[][] getGrid() {
        return grid;
    }//返回在棋盘格上的槽

    public void setGrid(Cell[][] grid) {
        this.grid = grid;
    }

    public PlayerColor getChessPieceOwner(ChessboardPoint point) {
        return getGridAt(point).getPiece().getOwner();
    }

    public boolean isValidMove(ChessboardPoint src, ChessboardPoint dest) {
        if (getChessPieceAt(src) == null || getChessPieceAt(dest) != null) {
            return false;//同时满足：在坐标src处的棋子不是null同时要移动的目标坐标上没有棋子
        }
        if (ChessboardComponent.getBlueDensCell().contains(dest)
                && getChessPieceAt(src).getOwner().equals(PlayerColor.BLUE)) {
            return false;
        }
        if (ChessboardComponent.getRedDensCell().contains(dest)
                && getChessPieceAt(src).getOwner().equals(PlayerColor.RED)) {
            return false;
        }
        if (ChessboardComponent.getRiverCell().contains(dest)) {
            return getChessPieceAt(src).getRank() == 1 && calculateDistance(src, dest) == 1;
        }
        if (getChessPieceAt(src).getRank() == 7 || getChessPieceAt(src).getRank() == 6) {
            ChessboardPoint p = new ChessboardPoint((src.getRow() + dest.getRow()) / 2, src.getCol());
            ChessboardPoint p2 = new ChessboardPoint(src.getRow(), (src.getCol() + dest.getCol() + 1) / 2);
            if (ChessboardComponent.getRiverCell().contains(p)) {
                ChessboardPoint p3 = new ChessboardPoint(p.getRow() + 1, p.getCol());
                ChessboardPoint p4 = new ChessboardPoint(p.getRow() - 1, p.getCol());
                if (getChessPieceAt(p3) != null || getChessPieceAt(p4) != null || getChessPieceAt(p) != null) {
                    return false;
                }
                return calculateDistance(src, dest) == 4;
            }
            if (ChessboardComponent.getRiverCell().contains(p2)) {
                ChessboardPoint p3 = new ChessboardPoint(p2.getRow(), p2.getCol() - 1);
                if (getChessPieceAt(p2) != null || getChessPieceAt(p3) != null) {
                    return false;
                }
                return calculateDistance(src, dest) == 3 && (src.getCol() == dest.getCol() | src.getRow() == dest.getRow());
            } else {
                return calculateDistance(src, dest) == 1;
            }
        }
        return calculateDistance(src, dest) == 1;
    }//移动方法不同


    public boolean isValidCapture(ChessboardPoint src, ChessboardPoint dest) {
        if (getChessPieceAt(src) == null || getChessPieceAt(dest) == null
                || getChessPieceOwner(src).equals(getChessPieceOwner(dest))) {//capture两个位置上的棋子都不是null
            return false;
        }
        if (getChessPieceAt(src).getRank() == 8 && getChessPieceAt(dest).getRank() == 1) {
            return false;
        }
        if (getChessPieceAt(src).getRank() == 1 && getChessPieceAt(dest).getRank() == 8) {
            if (getGridAt(src).getClass().equals(River.class)) {//rat is in the water
                return false;
            }
            return calculateDistance(src, dest) == 1;
        }
        if (getChessPieceAt(src).getRank() >= getChessPieceAt(dest).getRank()) {
            if (getChessPieceAt(dest).getRank() == 1
                    && ChessboardComponent.getRiverCell().contains(dest)) {
                //the one being captured is rat and in the river
                return false;//cannot be captured
            }
            return calculateDistance(src, dest) == 1;
        } else {
            int rank1 = 0;
            if (ChessboardComponent.getBlueTrapCell().contains(src) || ChessboardComponent.getRedTrapCell().contains(src)) {
                switch (getChessPieceAt(src).getName()) {
                    case "Elephant":
                        rank1 = 8;
                        break;
                    case "Wolf":
                        rank1 = 4;
                        break;
                    case "Leopard":
                        rank1 = 5;
                        break;
                    case "Rat":
                        rank1 = 1;
                        break;
                    case "Cat":
                        rank1 = 2;
                        break;
                    case "Dog":
                        rank1 = 3;
                        break;
                    case "Lion":
                        rank1 = 7;
                        break;
                    case "Tiger":
                        rank1 = 6;
                        break;

                }
            }
            if (rank1 >= getChessPieceAt(dest).getRank()) {
                return calculateDistance(src, dest) == 1;
            }
        }
        return false;//判断是否等于1，等于1则可以capture
    }

    public int getNumberOfBlue() {
        return numberOfBlue;
    }

    public int getNumberOfRed() {
        return numberOfRed;
    }

    public static PlayerColor getCurrentSide() {
        if (ChessGameFrame.getGameController().getEachStep().size() % 2 == 1) {
            return PlayerColor.RED;
        }
        return PlayerColor.BLUE;
    }

    public List<Step> getStepSet() {
        return stepSet;
    }

    public void setStepSet(List<Step> stepSet) {
        this.stepSet = stepSet;
    }
}

