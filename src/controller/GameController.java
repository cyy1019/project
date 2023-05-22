package controller;


import listener.GameListener;
import model.*;
import view.CellComponent;
import view.ChessComponent;
import view.ChessGameFrame;
import view.ChessboardComponent;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and onPlayerClickChessPiece()]
 * 前后端连在一块的地方
 */
public class GameController implements GameListener {
    //TODO：给各个组件写监听器，事件发生则调用监听器的代码，先完成需要被调用的方法
    //TODO：需要给各个组件写监听器（？？？），在监听器的代码里更新前端
    //TODO:找到棋子的监听器代码看看
    //controller类里面会调用model的方法

    private Chessboard model;
    private ChessboardComponent view;



    private PlayerColor currentPlayer;

    // Record whether there is a selected piece before，即已经被选择的点
    private ChessboardPoint selectedPoint;
    public PlayerColor winner;
    private int gameRound = 1;
    private JLabel statusLabel;
    private ArrayList<Step> eachStep = new ArrayList<>();

    public JLabel getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(JLabel statusLabel) {
        this.statusLabel = statusLabel;
    }

    public ChessboardPoint getSelectedPoint() {
        return selectedPoint;
    }

    public GameController(ChessboardComponent view, Chessboard model) {//constructor
        this.view = view;
        this.model = model;
        this.currentPlayer = PlayerColor.BLUE;
        this.gameRound = 1;
        view.registerController(this);
        //initialize();//在new一个gamecontroller的时候会初始化，初始化啥？？？
        view.initiateChessComponent(model);
        view.repaint();
    }

    public void initialize() {//要自己写吗，初始化什么？棋盘吗？
        model.removeAll();
        model.initPieces();
        view.removeAllComponent();
        view.initiateChessComponent(model);
        currentPlayer = PlayerColor.BLUE;
        view.repaint();
    }

    public PlayerColor getCurrentPlayer() {
        return currentPlayer;
    }
    public void setCurrentPlayer(PlayerColor currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
    // after a valid move swap the player一次移动后交换玩家
    private void swapColor() {
        currentPlayer = currentPlayer == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;
        if (currentPlayer == PlayerColor.BLUE) {
            gameRound++;
        }
    }

    public boolean win() {//检查是否有人赢了的方法，看看判定为赢的条件？
        ChessboardPoint den1 = new ChessboardPoint(0, 3);
        ChessboardPoint den2 = new ChessboardPoint(8, 3);
        if (model.getChessPieceAt(den1) != null && model.getChessPieceAt(den1).getOwner().equals(PlayerColor.BLUE)) {
            winner = PlayerColor.BLUE;
            return true;
        }
        if (model.getChessPieceAt(den2) != null && model.getChessPieceAt(den2).getOwner().equals(PlayerColor.RED)) {
            winner = PlayerColor.RED;
            return true;
        }
        if (model.getNumberOfBlue() == 0) {
            winner = PlayerColor.RED;
            return true;
        }
        if (model.getNumberOfRed() == 0) {
            winner = PlayerColor.BLUE;
            return true;
        }
        return false;
    }//TODO:要完成前端的胜利动画


    public Chessboard getModel() {
        return model;
    }

    /*点到空棋盘格时调用的方法，监听器在ChessboardComponent
     * 实际是移动棋子并在前端画出的方法
     * 包括棋子移进dens和traps*/
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {//点了空棋盘格,传入的是被点的坐标和组件
        if (selectedPoint != null && model.isValidMove(selectedPoint, point)) {
            //如果原来已经选中的点不是null,可以保证已经被选中的点都是棋子（？？）
            if (ChessboardComponent.getBlueTrapCell().contains(point) &&
                    model.getChessPieceAt(selectedPoint).getOwner().equals(PlayerColor.RED)) {
                model.getChessPieceAt(selectedPoint).setRank(0);
            }
            if (ChessboardComponent.getRedTrapCell().contains(point) &&
                    model.getChessPieceAt(selectedPoint).getOwner().equals(PlayerColor.BLUE)) {
                model.getChessPieceAt(selectedPoint).setRank(0);
            }
            if (ChessboardComponent.getRedTrapCell().contains(selectedPoint) ||
                    ChessboardComponent.getBlueTrapCell().contains(selectedPoint)) {
                switch (model.getChessPieceAt(selectedPoint).getName()) {
                    case "Elephant":
                        model.getChessPieceAt(selectedPoint).setRank(8);
                        break;
                    case "Wolf":
                        model.getChessPieceAt(selectedPoint).setRank(4);
                        break;
                    case "Leopard":
                        model.getChessPieceAt(selectedPoint).setRank(5);
                        break;
                    case "Rat":
                        model.getChessPieceAt(selectedPoint).setRank(1);
                        break;
                    case "Cat":
                        model.getChessPieceAt(selectedPoint).setRank(2);
                        break;
                    case "Dog":
                        model.getChessPieceAt(selectedPoint).setRank(3);
                        break;
                    case "Lion":
                        model.getChessPieceAt(selectedPoint).setRank(7);
                        break;
                    case "Tiger":
                        model.getChessPieceAt(selectedPoint).setRank(6);
                        break;

                }
            }
            eachStep.add(new Step(model.getChessPieceAt(selectedPoint), selectedPoint, point));
            view.hidePossibleMove();
            view.setPossibleMovePoint(new ArrayList<>());
            model.moveChessPiece(selectedPoint, point);
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));//只在内存里改变了棋子的位置
            selectedPoint = null;//重置已选中的点
            swapColor();//交换玩家
            ChessGameFrame.changeCurrentPlayer();
            view.repaint();//一定要重画，不然前端不显示
            win();//每次动都检查有没有赢家
        }
    }//TODO:rank只在进对方陷阱时会改变

    public void setGameRound(int gameRound) {
        this.gameRound = gameRound;
    }

    public int getGameRound() {
        statusLabel.setText(String.format("Round: %d", gameRound));
        return gameRound;
    }

    // click a cell with a chess
    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, ChessComponent component) {//传入被点的点和组件
        if (selectedPoint == null) {//如果没有已经被选择的点
            if (model.getChessPieceOwner(point).equals(currentPlayer)) {//选中的棋子的玩家是当前玩家
                selectedPoint = point;//传入的point成为被选择的点
                view.showPossibleMove();
                component.setSelected(true);//让选中的那个棋子selected状态设置为true，各棋子的类都是component的子类
                view.repaint();
                //组件重画，打圈？？？
            }
        } else if (selectedPoint.equals(point)) {//玩家在之前选择的点与本次点击选择的点相同
            selectedPoint = null;//使之前选择的点为null
            view.hidePossibleMove();
            view.setPossibleMovePoint(new ArrayList<>());
            component.setSelected(false);//本次点击的棋子实际上没有被点击
            view.repaint();//重画，把打的圈去掉
        } else if (model.getChessPieceAt(selectedPoint) != null) {
            if (model.isValidCapture(selectedPoint, point)) {
                ChessPiece a = model.getChessPieceAt(point);
                ChessPiece c = model.getChessPieceAt(selectedPoint);

                view.hidePossibleMove();
                view.setPossibleMovePoint(new ArrayList<>());
                ChessComponent d=view.removeChessComponentAtGrid(point);//
                model.captureChessPiece(selectedPoint, point);//让cell里的棋子为null后把进攻棋子挪到指定位置上
                ChessComponent b = view.removeChessComponentAtGrid(selectedPoint);
                view.setChessComponentAtGrid(point, b);//在前端内存里移动棋子位置
                Step step = new Step(c, selectedPoint, point);
                step.setEatenChesspiece(a);
                step.setEatenChesspieceComponent(d);
                eachStep.add(step);
                selectedPoint = null;//重置已选中的点
                swapColor();//交换玩家
                ChessGameFrame.changeCurrentPlayer();
                view.repaint();//一定要重画，不然前端不显示
                win();//每次动都检查有没有赢家
            }
        }
    }


    public void undo() {
        model.moveChessPiece(eachStep.get(eachStep.size() - 1).getChessboardPointEnd(), eachStep.get(eachStep.size() - 1).getChessboardPointStart());
        view.setChessComponentAtGrid(eachStep.get(eachStep.size() - 1).getChessboardPointStart(), view.removeChessComponentAtGrid(eachStep.get(eachStep.size() - 1).getChessboardPointEnd()));
        if (eachStep.get(eachStep.size() - 1).getEatenChesspiece() != null) {
            model.setChessPiece(eachStep.get(eachStep.size() - 1).getChessboardPointEnd(), eachStep.get(eachStep.size() - 1).getEatenChesspiece());
            view.setChessComponentAtGrid(eachStep.get(eachStep.size() - 1).getChessboardPointEnd(), eachStep.get(eachStep.size() - 1).getEatenChesspieceComponent());
        }
        view.repaint();
        eachStep.remove(eachStep.get(eachStep.size() - 1));
        
        swapColor();
        ChessGameFrame.changeCurrentPlayer();
        gameRound--;
        getGameRound();
    }

    public ArrayList<Step> getEachStep() {
        return eachStep;
    }

    public void setEachStep(ArrayList<Step> eachStep) {
        this.eachStep = eachStep;
    }
}
