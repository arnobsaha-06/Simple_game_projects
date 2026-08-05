import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        int width = 360;
        int height = 640;

        JFrame frame = new JFrame("Furappy Bardo desu!!!");
        frame.setVisible(true);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FlappyBird birdie = new FlappyBird();
        frame.add(birdie);
        frame.pack();
        birdie.requestFocus();
        frame.setVisible(true);

    }
}
