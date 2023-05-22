package model;

import java.io.Serializable;

/**
 * This class represents positions on the chessboard, such as (0, 0), (0, 7), and so on，棋盘上格子的坐标
 */
public class ChessboardPoint implements Serializable {
    private final int row;
    private final int col;//每个格子的坐标都是final

    public ChessboardPoint(int row, int col) {//constructor
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public int hashCode() {
        return row + col;
    }

    @Override
    @SuppressWarnings("ALL")
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        ChessboardPoint temp = (ChessboardPoint) obj;//强制转换了
        return (temp.getRow() == this.row) && (temp.getCol() == this.col);
    }//返回传入的这个object的row和column和？？？是否相等

    @Override
    public String toString() {
        return "("+row + ","+col+") " + "on the chessboard is clicked!";
    }
}
