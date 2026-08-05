import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    int width = 360;
    int height = 640;

    // IMAGE
    Image bgimg;
    Image birdimg;
    Image topimg;
    Image bottomimg;

    // Birb
    int birdx = width / 8;
    int birdy = height / 2;
    int birdw = 34;
    int birdh = 24;

    class Bird {
        int x = birdx;
        int y = birdy;
        int width = birdw;
        int height = birdh;
        Image img;

        Bird(Image img) {
            this.img = img;
        }
    }

    //pipes
    int pipex = width;
    int pipey = 0;
    int pipew = 64;
    int pipeh = 512;

    class Pipe{
        int x = pipex;
        int y = pipey;
        int width = pipew;
        int height = pipeh;
        Image img;
        boolean passed = false;

        Pipe(Image img) {
            this.img = img;
        }
    }

    //logic
    Bird birb;
    int Vx = -4;
    int Vy = 0; 
    int gravity = 1;

    ArrayList<Pipe> pipes;
    Random random = new Random();

    Timer loop;
    Timer placePipeTimer;

    boolean over = false;
    double score = 0;

    FlappyBird() {
        setPreferredSize(new Dimension(width, height));
        // setBackground(Color.BLUE);
        setFocusable(true);
        addKeyListener(this);

        // Load Images
        bgimg = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdimg = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        topimg = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomimg = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

        // bird
        birb = new Bird(birdimg);
        pipes = new ArrayList<Pipe>();

        //place pipes timer
        placePipeTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                placePipes();
            }
        });
        placePipeTimer.start();

        //timer
        loop = new Timer(1000/60, this);
        loop.start();
    }

    public void placePipes() {
        int randomPipeY = (int)(pipey - pipeh/4 - Math.random()*(pipeh/2));
        int openingSpace = height/4;
        
        Pipe toppipe = new Pipe(topimg);
        toppipe.y = randomPipeY;
        pipes.add(toppipe);

        Pipe bottompipe = new Pipe(bottomimg);
        bottompipe.y = toppipe.y + pipeh + openingSpace;
        pipes.add(bottompipe);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        //bg
        g.drawImage(bgimg, 0, 0, width, height, null);

        //birb
        g.drawImage(birb.img, birb.x, birb.y, birb.width, birb.height, null);
        
        //pipes
        for (int i = 0; i < pipes.size(); i++){
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }

        // score
        g.setColor(Color.white);
        g.setFont(new Font("Monospaced", Font.BOLD, 20));
        if (over){
            g.drawString("Game over T-T : " + String.valueOf((int)score), 10, 35);
        }
        else {
            g.drawString(String.valueOf((int)score), 10, 35);
        }
    }

    public void move(){
        //bird
        Vy += gravity;
        birb.y += Vy;
        birb.y = Math.max(birb.y, 0);

        //pipes
        for (int i = 0; i < pipes.size(); i++){
            Pipe pipe = pipes.get(i);
            pipe.x += Vx;

            if (!pipe.passed && birb.x > pipe.x + pipe.width){
                pipe.passed = true;
                score += 0.5; // 0.5 for one pipe, 1 for a pair 
            }

            if (collision(birb, pipe)){
                over = true;
            }
        }

        if (birb.y > height){
            over = true;
        }
    }

    public boolean collision(Bird a, Pipe b){
        return a.x < b.x + b.width &&
               a.x + a.width > b.x &&
               a.y < b.y + b.height &&
               a.y + a.height > b.y;  
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (over){
            placePipeTimer.stop();
            loop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            Vy = -9;
            if (over){
                // restart with reset
                birb.y = birdy;
                Vy = 0;
                pipes.clear();
                score = 0;
                over = false;
                loop.start();
                placePipeTimer.start();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
        
    @Override
    public void keyReleased(KeyEvent e) {}
}
