package view;

import javax.swing.*;
import java.awt.*;

public class River extends Component {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Font font = new Font("楷体", Font.PLAIN, getWidth() / 2);
        g2.setFont(font);
        g2.setColor(Color.BLACK);
        g2.drawString("河", getWidth() / 4, getHeight() * 5 / 8);
    }
}
