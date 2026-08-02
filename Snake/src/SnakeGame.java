import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener{
    private class Tile{
        int x;
        int y;

        Tile (int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    int width;
    int height;
    int tileSize = 25;

    //Snake
    Tile head;
    ArrayList<Tile> body;

    //Food
    Tile food;
    Random random;

    //game logic
    Timer loop;
    int Vx;
    int Vy;
    boolean over = false;

    SnakeGame(int width, int height){
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.LIGHT_GRAY);
        addKeyListener(this);
        setFocusable(true);

        head = new Tile(5, 5);
        body = new ArrayList<Tile>();

        food = new Tile(10, 10);
        random = new Random();
        placeFood();

        Vx = 0;
        Vy = 0;

        loop = new Timer(100, this);
        loop.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        //Grid
        //for (int i = 0; i < width/tileSize; i++){
        //   g.drawLine(i * tileSize, 0, i * tileSize, height);
        // g.drawLine(0, i * tileSize, width, i * tileSize);
        //}

        //Food
        g.setColor(Color.ORANGE);
        g.fill3DRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize, true);

        //Snake head
        g.setColor(Color.BLACK);
        g.fill3DRect(head.x * tileSize, head.y * tileSize, tileSize, tileSize, true);

        //Snake body
        for (int i = 0; i < body.size(); i++) {
            Tile part = body.get(i);
            g.fill3DRect(part.x * tileSize, part.y * tileSize, tileSize, tileSize, true);
        }

        //Score
        g.setFont(new Font("Monospaced", Font.BOLD, 16));
        if (over){
            g.setColor(Color.RED);
            g.drawString("Game Over T-T |> " + "Score: " + String.valueOf(body.size()), tileSize -16, tileSize);
        }
        else {
            g.drawString("Score: " + String.valueOf(body.size()), tileSize - 16, tileSize);
        }
    }

    public void placeFood() {
        food.x = random.nextInt(width/tileSize);
        food.y = random.nextInt(height/tileSize);
    }

    public boolean collision(Tile t1, Tile t2){
        return t1.x == t2.x && t1.y == t2.y;
    }

    public void move(){
        if (collision(head, food)) {
            body.add(new Tile(food.x, food.y));
            placeFood();
        }

        //Snake Body
        for (int i = body.size() - 1; i >= 0; i--){
            Tile part = body.get(i);
            if (i == 0){
                part.x = head.x;
                part.y = head.y;
            }
            else{
                Tile prevpart = body.get(i - 1);
                part.x = prevpart.x;
                part.y = prevpart.y;
            }
        }

        //Snake head
        head.x += Vx;
        head.y += Vy;

        //game over 
        for (int i = 0; i < body.size(); i++){
            Tile part = body.get(i);
            if (collision(head, part)){
                over = true;
            }
        }

        if (head.x * tileSize < 0 || head.x * tileSize > width || head.y * tileSize < 0 || head.y * tileSize > height){
            over = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (over){
            loop.stop();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
        
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && Vy != -1 && Vy != 1){
            Vx = 0;
            Vy = -1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN && Vy != 1 && Vy != -1){
            Vx = 0;
            Vy = 1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT && Vx != -1 && Vx != 1){
            Vx = -1;
            Vy = 0;
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT && Vx != 1 && Vx != -1){
            Vx = 1;
            Vy = 0;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
