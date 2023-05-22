package view;

import controller.GameController;
import model.Chessboard;
import model.PlayerColor;

import javax.swing.*;
import java.awt.*;
;import static view.ChessboardComponent.mainFrame;

public class winFrame extends JFrame {
    private final int width;
    private final int height;
    PlayerColor Loser;
    public winFrame(int width,int height,PlayerColor loser){
        this.width=width;
        this.height=height;
        this.setVisible(true);
        this.setSize(this.width,this.height);
        this.setLocationRelativeTo(null);
        this.setTitle("斗兽棋");
        this.setLayout(null);
        this.Loser =loser;
        if (loser==PlayerColor.BLUE){
            addRedLabel();
        }else {
            addBlueLabel();
        }addLabel();
        RestartButton();
        ExitButton();
    }

    private void addBlueLabel() {
        JLabel winLabel = new JLabel("恭喜蓝方胜利");
        winLabel.setLocation(height-400, height/10+120);
        winLabel.setSize(400, 100);
        winLabel.setFont(new Font("Rockwell", Font.ITALIC, 50));
        winLabel.setForeground(Color.BLUE);
        add(winLabel);
    }
    private void addRedLabel() {
        JLabel winLabel = new JLabel("恭喜红方胜利");
        winLabel.setLocation(height-400, height/10+120);
        winLabel.setSize(400, 100);
        winLabel.setFont(new Font("Rockwell", Font.ITALIC, 50));
        winLabel.setForeground(Color.RED);
        add(winLabel);
    }
    private void addLabel() {
        JLabel winLabel = new JLabel("游戏结束");
        winLabel.setLocation(height-350, height/10+300);
        winLabel.setSize(400, 100);
        winLabel.setFont(new Font("Rockwell", Font.ITALIC, 50));
        add(winLabel);
    }
    private void RestartButton() {
        JButton button = new JButton("Restart");
        button.addActionListener((e) -> {
            SwingUtilities.invokeLater(() -> {
                this.dispose();
                mainFrame= new ChessGameFrame(1100, 810);
                GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard());
                mainFrame.setGameController(gameController);
                gameController.setStatusLabel(mainFrame.getStatusLabel());
            });
        });
        button.setLocation(300, height / 10+480);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }
    private void ExitButton() {
        JButton button = new JButton("Exit");
        button.addActionListener((e) -> {
            SwingUtilities.invokeLater(() -> {
                this.dispose();
                SwingUtilities.invokeLater(() -> {
                    Menu mainMenu=new Menu(1100,810);
                });

            });
        });
        button.setLocation(650, height / 10+480);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }
}
