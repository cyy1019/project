package view;

import javax.swing.*;
import java.awt.*;

public class BlueTrap extends Component {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Font font = new Font("楷体", Font.PLAIN, getWidth() / 2);
        g2.setFont(font);
        g2.setColor(Color.BLUE);
        g2.drawString("陷阱", getWidth() / 20, getHeight() * 5 / 8);

    }
}