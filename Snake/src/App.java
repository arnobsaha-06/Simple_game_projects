import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        int width = 600;
        int height = width;

        JFrame f = new JFrame("Hebi Geemu Desu!!!");
        f.setVisible(true);
        f.setSize(width, height);
        f.setLocationRelativeTo(null);
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SnakeGame geemu = new SnakeGame(width, height);
        f.add(geemu);
        f.pack();
        geemu.requestFocus();
        f.setVisible(true);
    }
}
