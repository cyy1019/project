package view;

import controller.GameController;
import controller.Loading;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static view.ChessGameFrame.*;
import static view.ChessboardComponent.mainFrame;

public class Menu extends JFrame {
    private final int WIDTH;
    private final int HEIGTH;

    public Menu(int width,int height) {
        this.WIDTH=width;
        this.HEIGTH=height;
        this.setVisible(true);
        this.setSize(this.WIDTH,this.HEIGTH);
        this.setLocationRelativeTo(null);
        this.setTitle("斗兽棋");
        this.setLayout(null);
        startButton();
        loadButton();
        addLabel();
        exitButton();
    }
    private void addLabel() {
        JLabel winLabel = new JLabel("斗兽棋");
        winLabel.setLocation(430, HEIGTH/10+30);
        winLabel.setSize(400, 100);
        winLabel.setFont(new Font("Rockwell", Font.BOLD, 70));
        add(winLabel);
    }
    private void startButton() {
        JButton button = new JButton("Start");
        button.addActionListener((e) -> {
            SwingUtilities.invokeLater(() -> {
                this.dispose();
                mainFrame= new ChessGameFrame(1100, 810);
                GameController gameController = new GameController(getChessboardComponent(), new Chessboard());
                mainFrame.setGameController(gameController);
                gameController.setStatusLabel(mainFrame.getStatusLabel());
            });
        });
        button.setLocation(450, HEIGTH / 10+200);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }
    private void loadButton() {
        JButton button = new JButton("Load");
        button.addActionListener((e) -> {
            SwingUtilities.invokeLater(() -> {
                this.dispose();
                mainFrame= new ChessGameFrame(1100, 810);
                GameController gameController = new GameController(getChessboardComponent(), new Chessboard());
                mainFrame.setGameController(gameController);
                gameController.setStatusLabel(mainFrame.getStatusLabel());
                getChessboardComponent().removeAllComponent();//前端把所有棋子组件移除了
                getGameController().getModel().removeAll();//在后端chessboard上把所有棋子移走了
                try {//重新传入grid
                    getGameController().setEachStep(Loading.deserializeStep("resource/Step.txt"));
                    getGameController().getModel().setGrid(Loading.deserializeCell("resource/Cell.txt"));
                    System.out.println(gameController.getEachStep().size());
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 7; j++) {
                            ChessboardPoint point = new ChessboardPoint(i, j);
                            if (getGameController().getModel().getChessPieceAt(point) != null) {
                                Cell[][] grid = getGameController().getModel().getGrid();//不一定每一个cell里都有棋子
                                //TODO:把棋子组件加回去
                                if (grid[i][j].getPiece().getName().equals("Leopard")) {
                                    ChessPiece chessPiece = grid[i][j].getPiece();
                                    ChessGameFrame.getChessboardComponent().getGridComponents()[i][j].add(
                                            new LeopardChessComponent(
                                                    chessPiece.getOwner(),getChessboardComponent().getCHESS_SIZE()
                                            ));
                                }
                                if (grid[i][j].getPiece().getName().equals("Elephant")) {
                                    ChessPiece chessPiece = grid[i][j].getPiece();
                                    System.out.println(chessPiece.getOwner());
                                    getChessboardComponent().getGridComponents()[i][j].add(
                                            new ElephantChessComponent(
                                                    chessPiece.getOwner(),
                                                    getChessboardComponent().getCHESS_SIZE()));
                                }
                                if (grid[i][j].getPiece().getName().equals("Tiger")) {
                                    ChessPiece chessPiece = grid[i][j].getPiece();
                                    System.out.println(chessPiece.getOwner());
                                    getChessboardComponent().getGridComponents()[i][j].add(
                                            new TigerChessComponent(
                                                    chessPiece.getOwner(),
                                                    getChessboardComponent().getCHESS_SIZE()));
                                }
                                if (grid[i][j].getPiece().getName().equals("Lion")) {
                                    ChessPiece chessPiece = grid[i][j].getPiece();
                                    System.out.println(chessPiece.getOwner());
                                    getChessboardComponent().getGridComponents()[i][j].add(
                                            new LionChessComponent(
                                                    chessPiece.getOwner(),
                                                    getChessboardComponent().getCHESS_SIZE()));
                                }
                                if (grid[i][j].getPiece().getName().equals("Wolf")) {
                                    ChessPiece chessPiece = grid[i][j].getPiece();
                                    System.out.println(chessPiece.getOwner());
                                    getChessboardComponent().getGridComponents()[i][j].add(
                                            new WolfChessComponent(
                                                    chessPiece.getOwner(),
                                                    getChessboardComponent().getCHESS_SIZE()));
                                }
                                if (grid[i][j].getPiece().getName().equals("Dog")) {
                                    ChessPiece chessPiece = grid[i][j].getPiece();
                                    System.out.println(chessPiece.getOwner());
                                    getChessboardComponent().getGridComponents()[i][j].add(
                                            new DogChessComponent(
                                                    chessPiece.getOwner(),
                                                    getChessboardComponent().getCHESS_SIZE()));
                                }
                                if (grid[i][j].getPiece().getName().equals("Cat")) {
                                    ChessPiece chessPiece = grid[i][j].getPiece();
                                    System.out.println(chessPiece.getOwner());
                                    getChessboardComponent().getGridComponents()[i][j].add(
                                            new CatChessComponent(
                                                    chessPiece.getOwner(),
                                                    getChessboardComponent().getCHESS_SIZE()));
                                }
                                if (grid[i][j].getPiece().getName().equals("Rat")) {
                                    ChessPiece chessPiece = grid[i][j].getPiece();
                                    System.out.println(chessPiece.getOwner());
                                    getChessboardComponent().getGridComponents()[i][j].add(
                                            new RatChessComponent(
                                                    chessPiece.getOwner(),
                                                    getChessboardComponent().getCHESS_SIZE()));
                                }
                                System.out.println(gameController.getEachStep().size());
                                String text;
                                    gameController.setGameRound(gameController.getEachStep().size() / 2 + 1);
                                    text = "Round: " + gameController.getGameRound();
                                getGameController().getStatusLabel().setText(text);
                                getChessboardComponent().repaint();
                                if (getGameController().getEachStep().size() % 2 == 1 &&
                                        getGameController().getCurrentPlayer() == PlayerColor.BLUE) {
                                    changeCurrentPlayer();
                                    getGameController().setCurrentPlayer(PlayerColor.RED);
                                }
                                if(getGameController().getEachStep().size() %2 == 0
                                        && getGameController().getCurrentPlayer() == PlayerColor.RED){
                                    changeCurrentPlayer();
                                    getGameController().setCurrentPlayer(PlayerColor.BLUE);
                                }
                                //TODO:重新setgameround
                            }
                        }
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            });
        });
        button.setLocation(450, HEIGTH / 10+350);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }
    private void exitButton() {
        JButton button = new JButton("Exit");
        button.addActionListener((e) -> {
            this.dispose();
        });
        button.setLocation(450, HEIGTH / 10+500);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

}
