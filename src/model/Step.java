
    package model;

import view.ChessComponent;
import view.ChessboardComponent;

import java.io.Serializable;

    public class Step implements Serializable {
        private ChessPiece chessPiece;
        private ChessboardPoint chessboardPointStart;
        private ChessboardPoint chessboardPointEnd;
        private ChessPiece eatenChesspiece;
        private ChessComponent eatenChesspieceComponent;

        public ChessComponent getEatenChesspieceComponent() {
            return eatenChesspieceComponent;
        }

        public void setEatenChesspieceComponent(ChessComponent eatenChesspieceComponent) {
            this.eatenChesspieceComponent = eatenChesspieceComponent;
        }

        public ChessPiece getEatenChesspiece() {
            return eatenChesspiece;
        }

        public void setEatenChesspiece(ChessPiece eatenChesspiece) {
            this.eatenChesspiece = eatenChesspiece;
        }

        public Step(ChessPiece chessPiece, ChessboardPoint start, ChessboardPoint end){
          this.chessPiece =chessPiece ;
          this.chessboardPointStart=start;
          this.chessboardPointEnd= end;
          this.eatenChesspiece=null;
          this.eatenChesspieceComponent=null;
        }

        public ChessPiece getChessPiece() {
            return chessPiece;
        }

        public void setChessPiece(ChessPiece chessPiece) {
            this.chessPiece = chessPiece;
        }

        public ChessboardPoint getChessboardPointStart() {
            return chessboardPointStart;
        }

        public void setChessboardPointStart(ChessboardPoint chessboardPointStart) {
            this.chessboardPointStart = chessboardPointStart;
        }

        public ChessboardPoint getChessboardPointEnd() {
            return chessboardPointEnd;
        }

        public void setChessboardPointEnd(ChessboardPoint chessboardPointEnd) {
            this.chessboardPointEnd = chessboardPointEnd;
        }
    }


