package view;

import controller.GameController;
import controller.Loading;
import controller.MyThread;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;


/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {

    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;

    private final int ONE_CHESS_SIZE;

    private static ChessboardComponent chessboardComponent;
    private static GameController gameController;
    private static JLabel statusLabel;

    private static JLabel presentPlayer = new JLabel();

    public JLabel getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(JLabel statusLabel) {
        this.statusLabel = statusLabel;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    private File bgm;
    private Music music = new Music();


    public ChessGameFrame(int width, int height) {
        setTitle("斗兽棋"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.ONE_CHESS_SIZE = (HEIGTH * 4 / 5) / 9;
        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        setVisible(true);
        addChessboard();
        addLabel();
        addRestartButton();
        savejButton();
        addUndoButton();
        addPresentPlayer();
        addExitButton();
        MyThread myThread = new MyThread();
        myThread.start();
        bgm = new File("resource/久石让 - 菊次郎的夏天 (1).wav");
        music.playMusic(bgm);

    }

    public static ChessboardComponent getChessboardComponent() {
        return chessboardComponent;
    }

    public void setChessboardComponent(ChessboardComponent chessboardComponent) {
        this.chessboardComponent = chessboardComponent;
    }

    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE);
        chessboardComponent.setLocation(HEIGTH / 5, HEIGTH / 10);
        add(chessboardComponent);
    }

    /**
     * 在游戏面板中添加标签
     */
    private void addLabel() {
        statusLabel = new JLabel("Round: 1");
        statusLabel.setLocation(HEIGTH, HEIGTH / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }


    public static GameController getGameController() {
        return gameController;
    }

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    private void addRestartButton() {
        JButton button = new JButton("Restart");
        button.addActionListener((e) -> {
            int select = JOptionPane.showConfirmDialog(this, "是否重新开始游戏？", "确认", JOptionPane.YES_NO_OPTION);
            if (select == 0) {
                gameController.initialize();
                statusLabel.setText("Round: 1");
                gameController.setGameRound(1);
            }

        });
        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addUndoButton() {
        JButton button = new JButton("Undo");
        button.addActionListener((e) -> {
            int select = JOptionPane.showConfirmDialog(this, "要悔棋吗？", "确认", JOptionPane.YES_NO_OPTION);
            if (select == 0) {
                gameController.undo();
            }
        });
        button.setLocation(HEIGTH, HEIGTH / 10 + 240);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGTH, HEIGTH / 10 + 360);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
//通过点击当前button可触发的事件都写到括号内
        button.addActionListener(e -> {// 直接在按钮这里写监听器
            try {//重新传入grid
                getGameController().setEachStep(Loading.deserializeStep("resource/Step.txt"));
                getGameController().getModel().setGrid(Loading.deserializeCell("resource/Cell.txt"));
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 7; j++) {
                        ChessboardPoint point = new ChessboardPoint(i, j);
                        if (getGameController().getModel().getChessPieceAt(point) != null) {
                            Cell[][] grid = getGameController().getModel().getGrid();//不一定每一个cell里都有棋子
                            //TODO:把棋子组件加回去
                            if (grid[i][j].getPiece().getName().equals("Leopard")) {
                                ChessPiece chessPiece = grid[i][j].getPiece();
                                getChessboardComponent().getGridComponents()[i][j].add(
                                        new LeopardChessComponent(
                                                chessPiece.getOwner(), ONE_CHESS_SIZE
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
                                System.out.println(gameController.getGameRound());
                                text = "Round: " + gameController.getGameRound();
                            statusLabel.setText(text);
                            getChessboardComponent().repaint();
                            changeCurrentPlayer();
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
        //TODO:注意gameRound有问题，棋子刚开始变小了，但可以正常点击，有时候currentplayer有问题
        //TODO：序列化的实际是cell，里面包括了棋子，从开始界面重新加载棋盘进来
        //TODO：把cell的二维数组读入，初始化棋子以外的组件（cell，traps，dens），然后按照读入的cell数组的信息画出棋子的组件
        //TODO：加载实需要重新画棋盘，但不需要初始化棋子，

    }

    //保存
    private void savejButton() {
        JButton savejButton = new JButton("Save");
        savejButton.setLocation(HEIGTH, HEIGTH / 10 + 360);
        savejButton.setSize(200, 60);
        savejButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(savejButton);
        savejButton.addActionListener(e -> {
            int select = JOptionPane.showConfirmDialog(this, "是否保存当前界面游戏？", "确认", JOptionPane.YES_NO_OPTION);
            if (select == 0) {
                try {
                    Loading.serializeCell(getGameController().getModel().getGrid(), "resource/Cell.txt");
                    Loading.serializeStep(getGameController().getEachStep(), "resource/Step.txt");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                System.out.println(gameController.getGameRound());
            }
        });
    }

    private void addPresentPlayer() {//初始化棋盘的时候用的方法
        presentPlayer.setText("Player: BLUE");
        presentPlayer.setLocation(HEIGTH, HEIGTH / 20);
        presentPlayer.setSize(200, 60);
        presentPlayer.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(presentPlayer);
    }

    //TODO:当前玩家随轮数改变？？
    public static void changeCurrentPlayer() {
        if (Chessboard.getCurrentSide() == PlayerColor.BLUE) {
            presentPlayer.setText("Player: BLUE");
        }
        if (Chessboard.getCurrentSide() == PlayerColor.RED)
            presentPlayer.setText("Player: RED");
    }

    private void addExitButton() {
        JButton button = new JButton("Exit");
        button.addActionListener((e) -> {
            int select = JOptionPane.showConfirmDialog(this, "是否退出游戏？", "确认", JOptionPane.YES_NO_OPTION);
            if (select == 0) {
                SwingUtilities.invokeLater(() -> {
                    this.dispose();
                    SwingUtilities.invokeLater(() -> {
                        Menu mainMenu = new Menu(1100, 810);
                    });
                });
            }
        });
        button.setLocation(HEIGTH, HEIGTH / 10 + 480);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }
}
