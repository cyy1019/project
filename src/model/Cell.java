package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * This class describe the slot for Chess in Chessboard
 *一个槽，可以用来放棋子的所有东西？？？
 * */
public class Cell implements Serializable {//一个可以序列化的对象
    @Serial
    private static final long serialVersionUID = 5426132773483803718L;
    // the position for chess
    private ChessPiece Pieces;//cell槽里面可能有一个棋子，可能会是null
    public ChessPiece getPiece() {
        return Pieces;
    }

    public void setPiece(ChessPiece chessPieces) {
        this.Pieces = chessPieces;
    }

    public void removePiece() {
        this.Pieces = null;
    }//cell里面的piece是null了
    public void removePiece(ChessPiece chessPiece){
        
    }

}
/*这个类怎么用，要加代码吗？？？
实现了serializable的类，创建的对象可以序列化，即：可以把对象转化成一个字节序列，保存对象的状态，把该字节序列保存起来（到一个文件？）
之后可以随时将字节序列恢复为原来的对象，
可以用来实现数据的传输，在别的电脑上也能打开，在别的没有装ide的电脑上可以吗？？？
需要serialVersionUID吗？
反序列化可以用来在别的电脑上重新加载棋盘和棋子吗？（好像不太行，反序列化只能恢复为java对象）
 */