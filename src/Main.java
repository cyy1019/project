import view.Menu;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Menu mainMenu=new Menu(1100,810);
        });
    }
}
